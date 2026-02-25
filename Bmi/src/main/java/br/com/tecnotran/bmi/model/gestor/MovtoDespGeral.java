package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[movto-desp-geral]")
public class MovtoDespGeral implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;
	
	@Column(name = "[mes-ano]")
	private Date mesAno;
	
	@Column(name = "[cod-desp-geral]")
	private Integer codDespesa;

	@Column(name = "[tipmov]")
	private Integer tipoMov;
	
	@Column(name = "[valor-desp-geral]")
	private BigDecimal valor;

	@Column(name = "[especificacao]")
	private String especificacao;

	public Integer getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Integer cod_empresa) {
		this.codEmpresa = cod_empresa;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mes_ano) {
		this.mesAno = mes_ano;
	}

	public Integer getCodDespesa() {
		return codDespesa;
	}

	public void setCodDespesa(Integer cod_despesa) {
		this.codDespesa = cod_despesa;
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
	
	
}
