package br.com.hapvida.ibmstorageservice.enums;

public enum TipoOperacao {
	UPLOAD(1),
	DOWNLOAD(2);
	
	private final int indice;
	
	TipoOperacao(int value){
		indice = value;
    }
    public int getIndice(){
        return indice;
    }
}
