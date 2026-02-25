package br.com.tecnotran.bmi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cargo")
public class Cargo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_cargo")
	private Long id;
	
	@Column(name = "descricao", nullable = false, length = 80)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_cargo", nullable = false, foreignKey = @ForeignKey(name="FK_cargo_tp_cargo"))
	private TipoCargo tipoCargo;
	
	@Column(name = "numero", nullable = false)
	private Long numero;
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}
	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}


}
