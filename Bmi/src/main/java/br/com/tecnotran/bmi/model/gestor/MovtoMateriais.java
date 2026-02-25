package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[movto-materiais]")
public class MovtoMateriais implements Serializable {

	private static final long serialVersionUID = 1972276321450548949L;
	
	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;
	
	@Column(name = "[mes-ano]")
	private Date mesAno;
	
	@Column(name = "[cod-material]")
	private Integer codMaterial;
	
	@Column(name = "[tipmov]")
	private Integer tipoMov;
	
	@Column(name = "[qtde-material]")
	private Integer qtdeMaterial;
	
	@Column(name = "[preco-unit]")
	private BigDecimal preco;

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

	public Integer getCodMaterial() {
		return codMaterial;
	}

	public void setCodMaterial(Integer cod_material) {
		this.codMaterial = cod_material;
	}

	public Integer getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Integer tipMov) {
		this.tipoMov = tipMov;
	}

	public Integer getQtdeMaterial() {
		return qtdeMaterial;
	}

	public void setQtdeMaterial(Integer qtde_material) {
		this.qtdeMaterial = qtde_material;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
