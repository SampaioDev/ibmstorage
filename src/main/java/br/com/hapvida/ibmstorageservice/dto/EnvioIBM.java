package br.com.hapvida.ibmstorageservice.dto;

public class EnvioIBM {
	private String bucketName;
	private String fileName;
	
	public EnvioIBM() {
	}
	
	public EnvioIBM(String bucketName, String fileName) {
		this.bucketName = bucketName;
		this.fileName = fileName;
	}
	
	public String objectToString(EnvioIBM object) {
		String text = "Repositório:" + object.getBucketName() + "\n Nome do Arquivo: " + object.getFileName() + ".";
		return text;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
