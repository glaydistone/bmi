package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

/**
 * @author TONE_
 *
 */
@Entity
@Table(name="indices_economicos")
public class IndicesEconomicos extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_indice_economico")
	private Long id;
	
	@NotNull
	@Column(name="data_referencia", nullable = false, unique = true)
	@Temporal(TemporalType.DATE)
	private Date dataReferencia;
	
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal ipca = BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal igpm= BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fgvRodagem= BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fgvPecas= BigDecimal.ZERO;	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal precoVeiculoReferencia= BigDecimal.ZERO;	

	public BigDecimal getPrecoVeiculoReferencia() {
		return precoVeiculoReferencia;
	}

	public void setPrecoVeiculoReferencia(BigDecimal precoVeiculoReferencia) {
		this.precoVeiculoReferencia = precoVeiculoReferencia;
	}

	
	public Date getMesAno() {
		return dataReferencia;
	}


	public void setMesAno(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}


	public BigDecimal getIpca() {
		return ipca;
	}


	public void setIpca(BigDecimal ipca) {
		this.ipca = ipca;
	}


	public BigDecimal getIgpm() {
		return igpm;
	}


	public void setIgpm(BigDecimal igpm) {
		this.igpm = igpm;
	}


	public BigDecimal getFgvRodagem() {
		return fgvRodagem;
	}


	public void setFgvRodagem(BigDecimal fgvRodagem) {
		this.fgvRodagem = fgvRodagem;
	}


	public BigDecimal getFgvPecas() {
		return fgvPecas;
	}


	public void setFgvPecas(BigDecimal fpgPecas) {
		this.fgvPecas = fpgPecas;
	}




	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
