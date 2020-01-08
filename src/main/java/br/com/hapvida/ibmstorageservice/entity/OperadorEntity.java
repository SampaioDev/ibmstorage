package br.com.hapvida.ibmstorageservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_odon_gravacao_operador")
public class OperadorEntity {
	
	@Id
	@Column(name="CD_OPERADOR")
	private Long cdOperador;
	
	@Column(name="NM_OPERADOR")
	private String nomeOperador;
	
	@Column(name="NU_CPF")
	private Long cpfCliente;
	
	@Column(name="CD_AREA_VENDA")
	private Long cdAreaVenda;
	
	@Column(name="FL_ATIVO")
	private boolean flAtivo;
	
	@Column(name="FL_ADMIN")
	private boolean flAdmin;
	
	@Column(name="CD_SENHA")
	private String senha;
	
	@Column(name="DT_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="DT_STATUS")
	private Date dataStatus;

	public Long getCdOperador() {
		return cdOperador;
	}

	public void setCdOperador(Long cdOperador) {
		this.cdOperador = cdOperador;
	}

	public String getNomeOperador() {
		return nomeOperador;
	}

	public void setNomeOperador(String nomeOperador) {
		this.nomeOperador = nomeOperador;
	}

	public Long getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(Long cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public Long getCdAreaVenda() {
		return cdAreaVenda;
	}

	public void setCdAreaVenda(Long cdAreaVenda) {
		this.cdAreaVenda = cdAreaVenda;
	}

	public boolean isFlAtivo() {
		return flAtivo;
	}

	public void setFlAtivo(boolean flAtivo) {
		this.flAtivo = flAtivo;
	}

	public boolean isFlAdmin() {
		return flAdmin;
	}

	public void setFlAdmin(boolean flAdmin) {
		this.flAdmin = flAdmin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Date dataStatus) {
		this.dataStatus = dataStatus;
	}
	
}