package br.com.hapvida.ibmstorageservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


//tb_odon_gravacao_log
@Entity
@Table(name="tb_odon_gravacao_parametros")
public class ParametrosEntity {
	
	@Id
	@Column(name="CD_PARAMETRO")
	private Long cdParametro;
	
	@Column(name="NM_BUCKET")
	private String bucketName;

	@Column(name="NM_API_KEY")
	private String apiKey;
	
	@Column(name="NM_SERVICE_INSTANCE_ID")
	private String serviceInstanceID;
	
	@Column(name="NM_ENDPOINT_URL")
	private String endpointURL;
	
	@Column(name="NM_LOCATION")
	private String location;
	
	@Column(name="CD_OPERADOR")
	private String cdOperador;
	
	@Column(name="DT_ATUALIZACAO")
	private Date dataAtualizacao;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getServiceInstanceID() {
		return serviceInstanceID;
	}

	public void setServiceInstanceID(String serviceInstanceID) {
		this.serviceInstanceID = serviceInstanceID;
	}

	public String getEndpointURL() {
		return endpointURL;
	}

	public void setEndpointURL(String endpointURL) {
		this.endpointURL = endpointURL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCdOperador() {
		return cdOperador;
	}

	public void setCdOperador(String cdOperador) {
		this.cdOperador = cdOperador;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getCdParametro() {
		return cdParametro;
	}

	public void setCdParametro(Long cdParametro) {
		this.cdParametro = cdParametro;
	}
	
}