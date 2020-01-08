package br.com.hapvida.ibmstorageservice.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SdkClientException;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.AbortMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.CompleteMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadRequest;
import com.ibm.cloud.objectstorage.services.s3.model.InitiateMultipartUploadResult;
import com.ibm.cloud.objectstorage.services.s3.model.PartETag;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartRequest;
import com.ibm.cloud.objectstorage.services.s3.model.UploadPartResult;

import br.com.hapvida.ibmstorageservice.dto.EnvioIBM;
import br.com.hapvida.ibmstorageservice.entity.ArquivoEntity;
import br.com.hapvida.ibmstorageservice.entity.LogEntity;
import br.com.hapvida.ibmstorageservice.entity.ParametrosEntity;
import br.com.hapvida.ibmstorageservice.enums.Status;
import br.com.hapvida.ibmstorageservice.enums.TipoOperacao;
import br.com.hapvida.ibmstorageservice.repository.ArquivoRepository;
import br.com.hapvida.ibmstorageservice.repository.LogRepository;
import br.com.hapvida.ibmstorageservice.repository.ParametroRepository;
import br.com.hapvida.ibmstorageservice.service.interfaces.IUploadService;

@Service
public class UploadService implements IUploadService{

	private long PART_SIZE = 1024 * 1024 * 5;
	private Logger logger = Logger.getLogger(UploadService.class);
	private AmazonS3 cosClient;

	@Autowired 
	private LogRepository logRepository;
	@Autowired 
	private ArquivoRepository arquivoRepository;
	@Autowired 
	private ParametroRepository parametroRepository;
	
	private void upload(String bucketName, String fileName, File file, Long cdOperador, Long cpfCliente) throws Exception {
		logger.info("Creating new item: " + fileName);
        LogEntity entidade = new LogEntity();
        ArquivoEntity arquivoEntidade = new ArquivoEntity();
        EnvioIBM dadosEnvio = new EnvioIBM(bucketName, fileName);
		String extensao = fileName.split("\\.")[1];
		String retorno = null;
        try {
        	cosClient.putObject(bucketName, fileName, file); 
        	logger.info("Item: %s created!" + fileName);	
        	arquivoEntidade = montarArquivo(fileName, cdOperador, cpfCliente, extensao, fileName);
        	retorno = addArquivo(arquivoEntidade);
        	if(retorno != null) {
        		throw new Exception(retorno);
        	}
        	entidade = montarLog(fileName, cdOperador, cpfCliente, TipoOperacao.UPLOAD, Status.ENVIO_SUCESSO, "Upload feito com Sucesso!", HttpStatus.OK, dadosEnvio.objectToString(dadosEnvio));
        	addLog(entidade);
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	if(retorno == null) {
        		entidade = montarLogError(fileName, cdOperador, cpfCliente, TipoOperacao.UPLOAD, Status.ERRO_COMUNICACAO, "Erro no upload de arquivo.", HttpStatus.NOT_FOUND, dadosEnvio.objectToString(dadosEnvio), e.getMessage());        		
        	}else {
        		entidade = montarLogError(fileName, cdOperador, cpfCliente, TipoOperacao.UPLOAD, Status.ERRO_GRAVAR_DADOS_BANCO, "Erro de validação ou atualização da base de dados.", HttpStatus.NOT_FOUND, dadosEnvio.objectToString(dadosEnvio), e.getMessage());        		        		        		
        		cosClient.deleteObject(bucketName, fileName);
            	logger.info("Item: %s deteled!" + fileName);	
        	}
        	addLog(entidade);
        }
	}

	@Override
	public void upload(MultipartFile file, Long cdOperador, Long cpfCliente) throws Exception {
		try {
			if(cosClient == null) {
				cosClient = initiateClient();				
			}
			long fileSize = file.getSize();
			File arquivo = multipartToFile(file, file.getOriginalFilename());
			ParametrosEntity parametros = parametroRepository.findOne((long) 1);
			if(fileSize > PART_SIZE) {
				multiPartUpload(parametros.getBucketName(), file.getOriginalFilename(), arquivo, cdOperador, cpfCliente);
			}else {
				upload(parametros.getBucketName(), file.getOriginalFilename() , arquivo, cdOperador, cpfCliente);
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	private File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	private void multiPartUpload(String bucketName, String fileName, File file, Long cdOperador, Long cpfCliente) {
		logger.info("Starting multi-part upload for %s to bucket: %s\n" + fileName + "," + bucketName);  
		
		InitiateMultipartUploadResult mpResult = cosClient.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, fileName));
		String uploadID = mpResult.getUploadId();
		
		long fileSize = file.length();
		long partCount = ((long)Math.ceil(fileSize / PART_SIZE)) + 1;
		List<PartETag> dataPacks = new ArrayList<PartETag>();
		EnvioIBM dadosEnvio = new EnvioIBM();
		LogEntity entidade = new LogEntity();
		ArquivoEntity arquivoEntidade = new ArquivoEntity();
		String extensao = fileName.split("\\.")[1];
		try {
		    long position = 0;
		    for (int partNum = 1; position < fileSize; partNum++) {
		    	long particao = Math.min(PART_SIZE, (fileSize - position));
		
		    	logger.info("Uploading to %s (part %s of %s)\n" + bucketName + partNum + partCount+ ".");  
		 
			    UploadPartRequest upRequest = new UploadPartRequest()
			            .withBucketName(bucketName)
			            .withKey(fileName)
			            .withUploadId(uploadID)
			            .withPartNumber(partNum)
			            .withFileOffset(position)
			            .withFile(file)
			            .withPartSize(particao);
			    
			    UploadPartResult upResult = cosClient.uploadPart(upRequest);
			    dataPacks.add(upResult.getPartETag());
			
			    position += particao;
		    } 
		
		cosClient.completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, fileName, uploadID, dataPacks));
    	entidade = montarLog(fileName, cdOperador, cpfCliente, TipoOperacao.UPLOAD, Status.ENVIO_SUCESSO, "Upload feito com Sucesso!", HttpStatus.OK, dadosEnvio.objectToString(dadosEnvio));
    	arquivoEntidade = montarArquivo(fileName, cdOperador, cpfCliente, extensao, fileName);
    	String retorno = addArquivo(arquivoEntidade);
    	if(retorno != null) {
    		throw new Exception(retorno);
    	}
    	addLog(entidade);
		logger.info("Upload for %s Complete!\n" + fileName);
		} catch (Exception e) {
    		entidade = montarLogError(fileName, cdOperador, cpfCliente, TipoOperacao.UPLOAD, Status.ERRO_COMUNICACAO, "Erro no upload de arquivo.", HttpStatus.NOT_FOUND, dadosEnvio.objectToString(dadosEnvio), e.getMessage());        		
	    	addLog(entidade);
	    	logger.error("Multi-part upload aborted for " + fileName);
	    	logger.error("Upload Error: " + e.getMessage());
			cosClient.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, fileName, uploadID));
		}
	}
	
	private LogEntity montarLog(String fileName, Long cdOperador, Long cpfCliente, TipoOperacao upload, Status envioSucesso, String msgRetorno, HttpStatus status, String envioIBM) {
		Date date = new Date();
		LogEntity entidade = new LogEntity(upload, fileName, status, envioSucesso, msgRetorno, cpfCliente, cdOperador, envioIBM, date);
		return entidade;
	}
	
	private LogEntity montarLogError(String fileName, Long cdOperador, Long cpfCliente, TipoOperacao upload,
			Status envioSucesso, String msgRetorno, HttpStatus status, String envioIBM, String retorno) {
		Date date = new Date();
		LogEntity entidade = new LogEntity(upload, fileName, status, envioSucesso, msgRetorno, cpfCliente, cdOperador, envioIBM, date, retorno);
		return entidade;
	}
	
	private ArquivoEntity montarArquivo(String fileName, Long cdOperador, Long cpfCliente, String extensao, String nomeArmazenado) {
		Date date = new Date();
		ArquivoEntity entidade = new ArquivoEntity(arquivoRepository.getSequenceNumber(), fileName, nomeArmazenado, extensao, cpfCliente, cdOperador, date, date);
		return entidade;
	}
	
	private void addLog(LogEntity entidade) {
		logRepository.save(entidade);
	}
	
	private String addArquivo(ArquivoEntity entidade) {
		HashMap<Integer, String> result = arquivoRepository.inserirArquivo(entidade);
		String msgErro = null;
		if(result.containsKey(1)) {
			
		}else if(result.containsKey(0)){
			msgErro = result.get(0);
		}else {
			msgErro = result.get(2);
		}
		return msgErro;
	}
	
	private AmazonS3 initiateClient() {
		ParametrosEntity parametros = parametroRepository.findOne(1L);
		AmazonS3 client = null;
		if(parametros != null) {
			client = createClient(parametros.getApiKey(), parametros.getServiceInstanceID(), parametros.getEndpointURL(), parametros.getLocation());			
		}
		return client;
	}
	
	private AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url, String location)
    {
        AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(90000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration (new EndpointConfiguration(endpoint_url, location)) .withPathStyleAccessEnabled (true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }
}