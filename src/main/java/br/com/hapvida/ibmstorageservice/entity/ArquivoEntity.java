package br.com.hapvida.ibmstorageservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//tb_odon_gravacao_log
@Entity
@Table(name="tb_odon_gravacao_arquivo")
public class ArquivoEntity {
	
	@Id
	@Column(name="CD_ARQUIVO")
	private Long cdArquivo;
	
	@Column(name="NM_ARQUIVO_ORIGINAL")
	private String nomeArquivo;
	
	@Column(name="NM_ARQUIVO_ARMAZENADO")
	private String nomeArquivoArmazenado;
	
	@Column(name="NM_EXTENSAO_ARQUIVO")
	private String extensaoArquivo;
	
	@Column(name="NU_CPF")
	private Long cpfCliente;

	@Column(name="CD_OPERADOR")
	private Long cdOperador;
		
	@Column(name="DT_UPLOAD")
	private Date dataUpload;
	
	@Column(name="DT_CONTATO")
	private Date dataContato;

	public ArquivoEntity() {
	}
	
	public ArquivoEntity(Long cdArquivo, String nomeArquivo, String nomeArquivoArmazenado, String extensaoArquivo,
			Long cpfCliente, Long cdOperador, Date dataUpload, Date dataContato) {
		super();
		this.cdArquivo = cdArquivo;
		this.nomeArquivo = nomeArquivo;
		this.nomeArquivoArmazenado = nomeArquivoArmazenado;
		this.extensaoArquivo = extensaoArquivo;
		this.cpfCliente = cpfCliente;
		this.cdOperador = cdOperador;
		this.dataUpload = dataUpload;
		this.dataContato = dataContato;
	}



	public Long getCdArquivo() {
		return cdArquivo;
	}

	public void setCdArquivo(Long cdArquivo) {
		this.cdArquivo = cdArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivoArmazenado() {
		return nomeArquivoArmazenado;
	}

	public void setNomeArquivoArmazenado(String nomeArquivoArmazenado) {
		this.nomeArquivoArmazenado = nomeArquivoArmazenado;
	}

	public String getExtensaoArquivo() {
		return extensaoArquivo;
	}

	public void setExtensaoArquivo(String extensaoArquivo) {
		this.extensaoArquivo = extensaoArquivo;
	}

	public Long getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(Long cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public Long getCdOperador() {
		return cdOperador;
	}

	public void setCdOperador(Long cdOperador) {
		this.cdOperador = cdOperador;
	}

	public Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
	}
	
}