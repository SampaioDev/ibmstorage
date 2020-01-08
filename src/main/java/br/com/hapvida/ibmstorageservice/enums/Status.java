package br.com.hapvida.ibmstorageservice.enums;

public enum Status {
	ERRO_COMUNICACAO(0),
	ENVIO_SEM_RETORNO(1),
    ENVIO_COM_ERRO(2),
    ENVIO_SUCESSO(3),
	ERRO_GRAVAR_DADOS_BANCO(4);
	
	private final int indice;
	
	Status(int value){
		indice = value;
    }
    public int getIndice(){
        return indice;
    }
}