package br.com.hapvida.ibmstorageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hapvida.ibmstorageservice.entity.ParametrosEntity;

@Repository("parametroRepository")
public interface ParametroRepository extends  JpaRepository<ParametrosEntity, Long>{
	
} 
