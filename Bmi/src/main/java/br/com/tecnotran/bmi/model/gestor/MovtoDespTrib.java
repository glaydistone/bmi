package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movto-desp-trib")
public class MovtoDespTrib implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "[cod-empresa]")
	private Integer cod_empresa;

	@Column(name = "[mes-ano]")
	private Date mesAno;

	@Column(name = "[cod-despesa]")
	private Integer codDespesa;
	
	@Column(name = "[tipoMov]")
	private Integer tipMov;

	@Column(name = "[valor-desp]")
	private BigDecimal valorDesp;

	@Column(name="[especificacao]")
	private String especificacao; 

	@Column(name = "[Valor-mes]")
	private BigDecimal valor;

	public Integer getCodEmpresa() {
		return cod_empresa;
	}

	public void setCodEmpresa(Integer cod_empresa) {
		this.cod_empresa = cod_empresa;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public Integer getCodDespesa() {
		return codDespesa;
	}

	public void setCodDespesa(Integer codDespesa) {
		this.codDespesa = codDespesa;
	}

	public Integer getTipoMov() {
		return tipMov;
	}

	public void setTipoMov(Integer tipMov) {
		this.tipMov = tipMov;
	}

	public BigDecimal getValorDesp() {
		return valorDesp;
	}

	public void setValorDesp(BigDecimal valorDesp) {
		this.valorDesp = valorDesp;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
