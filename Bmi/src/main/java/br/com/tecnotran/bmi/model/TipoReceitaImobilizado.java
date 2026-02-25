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
@Table(name="tipo_receita_imob")
public class TipoReceitaImobilizado extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_tipo_receita_imob")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria_imob", nullable = false, foreignKey = @ForeignKey(name="FK_tpRecImob_catRecImov"))
	private CategoriaReceitaImobilizado categoriaReceitaImobilizado;
	
	@Column(name = "numero", nullable = false)
	private Long numero;
	
	@Column(name = "descricao", nullable = false, length = 80)
	private String descricao;
	
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaReceitaImobilizado getCategoriaReceitaImobilizado() {
		return categoriaReceitaImobilizado;
	}

	public void setCategoriaReceitaImobilizado(CategoriaReceitaImobilizado categoriaReceitaImobilizado) {
		this.categoriaReceitaImobilizado = categoriaReceitaImobilizado;
	}
	
	

}
