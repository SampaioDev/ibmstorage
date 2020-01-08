package br.com.hapvida.ibmstorageservice.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	void upload(MultipartFile file, Long cdOperador, Long cpfCliente) throws Exception;
	
}
