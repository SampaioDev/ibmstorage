package br.com.hapvida.ibmstorageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hapvida.ibmstorageservice.entity.LogEntity;

@Repository("logRepository")
public interface LogRepository extends  JpaRepository<LogEntity, Long>{

} 
