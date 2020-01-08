package br.com.hapvida.ibmstorageservice.entity;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Repository;

import br.com.hapvida.ibmstorageservice.repository.ArquivoRepository;

@Repository("arquivoRepository")
public class ArquivoRepositoryImpl implements ArquivoRepository{
	
	@PersistenceContext
	EntityManager entityManager;	
	
	@SuppressWarnings("finally")
	public HashMap<Integer, String> inserirArquivo(ArquivoEntity entidade) {
		HashMap<Integer, String> result = new HashMap<>();
	    try {
	      String sql = "{ call humaster.pr_odon_gravacao_arquivo(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
	      SessionImpl session = (SessionImpl) entityManager.getDelegate();
	      Connection connection = session.connection();
	      
	      Date data = new Date();
	      
	      CallableStatement callableStatement = connection.prepareCall(sql);
	      callableStatement.setLong(1, entidade.getCdArquivo());
	      callableStatement.setString(2, entidade.getNomeArquivo());
	      callableStatement.setString(3, entidade.getNomeArquivoArmazenado());
	      callableStatement.setString(4, entidade.getExtensaoArquivo());
	      callableStatement.setLong(5, entidade.getCpfCliente());
	      callableStatement.setLong(6, 1234L);
	      callableStatement.setDate(7, new java.sql.Date(data.getTime()));
	      callableStatement.registerOutParameter(8, Types.INTEGER);
	      callableStatement.registerOutParameter(9, Types.VARCHAR);
	
	      callableStatement.execute();
	
	      result.put(callableStatement.getInt(8), callableStatement.getString(9));

	      
	    } catch (Exception e) {
	    	e.getMessage();
	    }finally {
	    	return result;	    	
	    }
	}

	@Override
	public Long getSequenceNumber() {
	    Query query = entityManager.createNativeQuery("SELECT sq_odon_gravacao_arquivo.nextval FROM dual");
	    Object result = query.getSingleResult();
	    Long sequenceNumber = ((BigDecimal) result).setScale(0,BigDecimal.ROUND_UP).longValueExact();;
	    return sequenceNumber;
	}
}