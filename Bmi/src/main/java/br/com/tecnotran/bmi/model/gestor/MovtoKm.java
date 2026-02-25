package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[tab-kilometr]")
public class MovtoKm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;
	
	@Column(name = "[mes-ano]")
	private Date mesAno;
	

	@Column(name = "[tipmov]")
	private Integer tipoMov;
	
	@Column(name = "[cod_piso]")
	private Integer codPiso;

	@Column(name = "[Km_rodado]")
	private BigDecimal quilometragem;

	
	public Integer getCodPiso() {
		return codPiso;
	}

	public void setCodPiso(Integer cod_piso) {
		this.codPiso = cod_piso;
	}

	public BigDecimal getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}

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

	public Integer getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Integer tipMov) {
		this.tipoMov = tipMov;
	}
	
	
}
