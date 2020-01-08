package br.com.hapvida.ibmstorageservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.http.HttpStatus;

import br.com.hapvida.ibmstorageservice.enums.Status;
import br.com.hapvida.ibmstorageservice.enums.TipoOperacao;


//tb_odon_gravacao_log
@Entity
@Table(name="tb_odon_gravacao_log")
public class LogEntity {
	
	@Id
	@Column(name="CD_LOG")
	@SequenceGenerator(allocationSize=1, schema="humaster",  name="sq_odon_gravacao_log", sequenceName = "sq_odon_gravacao_log")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sq_odon_gravacao_log")
	private Long cdLog;

	@Column(name="TP_OPERACAO")
	private TipoOperacao tipoOperacao;
	
	@Column(name="NM_ARQUIVO")
	private String nomeArquivo;
	
	@Column(name="CD_RETORNO")
	private HttpStatus cdRetorno;
	
	@Column(name="CD_STATUS")
	private Status cdStatus;
	
	@Column(name="MSG_RETORNO")
	private String msgRetorno;
	
	@Column(name="NU_CPF")
	private Long cpfCliente;

	@Column(name="CD_OPERADOR")
	private Long cdOperador;
	
	@Column(name="XML_ENVIO")
	private String xmlEnvio;
	
	@Column(name="XML_RETORNO")
	private String xmlRetorno;
	
	@Column(name="DT_OPERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTransacao;
	
	@Column(name="MSG_ERRO")
	private String msgErro;
	
	public LogEntity() {
	
	}
	
	public LogEntity(TipoOperacao tipoOperacao, String nomeArquivo, HttpStatus cdRetorno, Status cdStatus,
			String msgRetorno, Long cpfCliente, Long cdOperador, String xmlEnvio, Date dataTransacao) {
		super();
		this.tipoOperacao = tipoOperacao;
		this.nomeArquivo = nomeArquivo;
		this.cdRetorno = cdRetorno;
		this.cdStatus = cdStatus;
		this.msgRetorno = msgRetorno;
		this.cpfCliente = cpfCliente;
		this.cdOperador = cdOperador;
		this.xmlEnvio = xmlEnvio;
		this.dataTransacao = dataTransacao;
	}
	
	public LogEntity(TipoOperacao tipoOperacao, String nomeArquivo, HttpStatus cdRetorno, Status cdStatus,
			String msgRetorno, Long cpfCliente, Long cdOperador, String xmlEnvio, Date dataTransacao, String msgErro) {
		super();
		this.tipoOperacao = tipoOperacao;
		this.nomeArquivo = nomeArquivo;
		this.cdRetorno = cdRetorno;
		this.cdStatus = cdStatus;
		this.msgRetorno = msgRetorno;
		this.cpfCliente = cpfCliente;
		this.cdOperador = cdOperador;
		this.xmlEnvio = xmlEnvio;
		this.dataTransacao = dataTransacao;
		this.msgErro = msgErro;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	public HttpStatus getCdRetorno() {
		return cdRetorno;
	}
	public void setCdRetorno(HttpStatus cdRetorno) {
		this.cdRetorno = cdRetorno;
	}
	public Status getCdStatus() {
		return cdStatus;
	}
	public void setCdStatus(Status cdStatus) {
		this.cdStatus = cdStatus;
	}
	public Long getCdLog() {
		return cdLog;
	}
	public void setCdLog(Long cdLog) {
		this.cdLog = cdLog;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getMsgRetorno() {
		return msgRetorno;
	}
	public void setMsgRetorno(String msgRetorno) {
		this.msgRetorno = msgRetorno;
	}
	public String getMsgErro() {
		return msgErro;
	}
	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
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
	public String getXmlEnvio() {
		return xmlEnvio;
	}
	public void setXmlEnvio(String xmlEnvio) {
		this.xmlEnvio = xmlEnvio;
	}
	public String getXmlRetorno() {
		return xmlRetorno;
	}
	public void setXmlRetorno(String xmlRetorno) {
		this.xmlRetorno = xmlRetorno;
	}
}