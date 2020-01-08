package br.com.hapvida.ibmstorageservice.repository;

import java.util.HashMap;

import br.com.hapvida.ibmstorageservice.entity.ArquivoEntity;

public interface ArquivoRepository{

	Long getSequenceNumber();
	HashMap<Integer, String> inserirArquivo(ArquivoEntity entidade);
} 
