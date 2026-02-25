package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[Movto-custos-operac]")

public class MovtoCustosOperac implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;
	
	@Column(name = "[mes-ano]")
	private Date mesAno;
	
	@Column(name = "[cod-cargo]")
	private Integer codCargo;
	
	@Column(name = "[tipMov]")
	private Integer tipoMov;
	
	@Column(name = "[qtde-elementos]")
	private Integer qtdeElementos;
	
	@Column(name = "[val-salario]")
	private BigDecimal valSalario;
	
	@Column(name = "[Val-premios]")
	private BigDecimal ValPremios;
	
	@Column(name = "[val-horaex]")
	private BigDecimal valHoraex;
	
	@Column(name = "[val-outros]")
	private BigDecimal valOutros;
	
	@Column(name = "[val-encargos]")
	private BigDecimal valEncargos;

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

	public Integer getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(Integer cod_cargo) {
		this.codCargo = cod_cargo;
	}

	public Integer getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Integer tipmov) {
		this.tipoMov = tipmov;
	}

	public Integer getQtdeElementos() {
		return qtdeElementos;
	}

	public void setQtdeElementos(Integer qtde_elementos) {
		this.qtdeElementos = qtde_elementos;
	}

	public BigDecimal getValSalario() {
		return valSalario;
	}

	public void setValSalario(BigDecimal val_salario) {
		this.valSalario = val_salario;
	}

	public BigDecimal getValPremios() {
		return ValPremios;
	}

	public void setValPremios(BigDecimal val_premios) {
		ValPremios = val_premios;
	}

	public BigDecimal getValHoraex() {
		return valHoraex;
	}

	public void setValHoraex(BigDecimal val_horaex) {
		this.valHoraex = val_horaex;
	}

	public BigDecimal getValOutros() {
		return valOutros;
	}

	public void setValOutros(BigDecimal val_outros) {
		this.valOutros = val_outros;
	}

	public BigDecimal getValEncargos() {
		return valEncargos;
	}

	public void setValEncargos(BigDecimal val_encargos) {
		this.valEncargos = val_encargos;
	}


}
