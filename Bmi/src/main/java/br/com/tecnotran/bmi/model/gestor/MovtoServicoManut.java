package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[movto-servico-manut]")
public class MovtoServicoManut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="[cod-empresa]")
	private Integer codEmpresa;

	@Column(name="[mes-ano]")
	private Date mesAno;

	@Column(name="[cod-servico]")
	private Integer codServico;
	
	@Column(name="[tipmov]")
	private Integer tipoMov;

	@Column(name="[valor-servico]")
	private BigDecimal valor;

	@Column(name="[especificacao]")
	private String especificacao;

	@Column(name="[qtde-serv]")
	private Integer qtdeServ;

	public Integer getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Integer codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public Integer getCodServico() {
		return codServico;
	}

	public void setCodServico(Integer codServico) {
		this.codServico = codServico;
	}

	public Integer getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Integer tipMov) {
		this.tipoMov = tipMov;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}

	public Integer getQtdeServ() {
		return qtdeServ;
	}

	public void setQtdeServ(Integer qtdeServ) {
		this.qtdeServ = qtdeServ;
	}

}
