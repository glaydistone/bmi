package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[movto-frota-apoio]")
public class MovtoFrotaApoio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;

	@Column(name = "[mes-ano]")
	private Date mesAno;

	@Column(name = "[cod-equipto]")
	private Integer codEquipto;
	
	@Column(name = "[tipmov]") 
	private Integer tipoMov;
	
	@Column(name = "[valor-equipto]")
	private BigDecimal valorEquipto;

	@Column(name = "[Km-mes]")
	private Integer km;

	@Column(name = "[Valor-mes]")
	private BigDecimal valor;

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

	public Integer getCodEquipto() {
		return codEquipto;
	}

	public void setCodEquipto(Integer cod_equipto) {
		this.codEquipto = cod_equipto;
	}

	public Integer getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Integer tipMov) {
		this.tipoMov = tipMov;
	}

	public BigDecimal getValorEquipto() {
		return valorEquipto;
	}

	public void setValorEquipto(BigDecimal valor_equipto) {
		this.valorEquipto = valor_equipto;
	}

	public Integer getKm() {
		return km;
	}

	public void setKm(Integer km) {
		this.km = km;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
