package br.com.tecnotran.bmi.model.gestor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Movto_Frota")
public class MovtoFrota implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "[cod-empresa]")
	private Integer codEmpresa;
	
	@Column(name = "[mes-ano]")
	@Temporal(TemporalType.DATE)
	private Date mesAno;
	
	@Column(name = "tipMov")
	private Integer tipoMov;
	
	@Column(name = "Veic_efetivos")
	private Integer efetivos;
	
	@Column(name = "Veic_Reservas")
	private Integer reservas;
	
	public Integer getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(Integer cod_empresa) {
		this.codEmpresa = cod_empresa;
	}
	public Date getMesAno() {
		return mesAno;
	}
	public void setMesAno(Date dataReferencia) {
		this.mesAno = dataReferencia;
	}
	public Integer getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(Integer tipoMov) {
		this.tipoMov = tipoMov;
	}
	public Integer getEfetivos() {
		return efetivos;
	}
	public void setEfetivos(Integer efetivos) {
		this.efetivos = efetivos;
	}
	public Integer getReservas() {
		return reservas;
	}
	public void setReservas(Integer reservas) {
		this.reservas = reservas;
	}

}
