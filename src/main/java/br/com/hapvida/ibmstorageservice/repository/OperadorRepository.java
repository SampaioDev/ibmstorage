package br.com.hapvida.ibmstorageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hapvida.ibmstorageservice.entity.OperadorEntity;

@Repository("operadorRepository")
public interface OperadorRepository extends  JpaRepository<OperadorEntity, Long>{

} 
