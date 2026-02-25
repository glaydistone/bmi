package br.com.tecnotran.bmi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_veiculo_apoio")
public class TipoVeiculoApoio extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_tipo_veiculo_apoio")
	private Long id;
	
	
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
    public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	@Column(name = "numero", nullable = false)
	private Long numero;
	
	@Column(name = "descricao", nullable = false, length = 80)
	private String descricao;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
