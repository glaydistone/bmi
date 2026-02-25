package br.com.tecnotran.bmi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empresa")
public class Empresa extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_empresa")
	private Long id;
	
	private Long numero;
	
	@Column(name="razao_social", length = 150, nullable = false)
	private String razaoSocial;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaosocial) {
		this.razaoSocial = razaosocial;
	}

	public String toString() {
		return getNumero().toString().concat("-").concat(getRazaoSocial());
		
	}
}
