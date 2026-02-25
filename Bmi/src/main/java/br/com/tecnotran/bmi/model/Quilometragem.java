package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="quilometragem")
public class Quilometragem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue
	@Column(name="id_quilometragem")
	private Long id;
	
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private TipoPiso tipopiso;
	
	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name="FK_quilometragem_boletim"))
	private Boletim boletim;
	
	public Boletim getBoletim() {
		return boletim;
	}


	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	private BigDecimal quilometragem;


	public TipoPiso getTipopiso() {
		return tipopiso;
	}


	public void setTipopiso(TipoPiso tipopiso) {
		this.tipopiso = tipopiso;
	}


	public BigDecimal getQuilometragem() {
		return quilometragem;
	}


	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}
	

}
