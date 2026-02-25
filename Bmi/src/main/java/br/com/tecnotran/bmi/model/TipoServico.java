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
@Table(name = "tipo_servico")
public class TipoServico extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_tipo_servico", nullable = false)
	private Long id;

	@Column(name = "numero", nullable = false)
	private Long numero;

	@Column(name = "descricao", nullable = false, length = 80)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_categoria_serv", nullable = false, foreignKey = @ForeignKey(name = "FK_tpServ_catServ"))
	private CategoriaServico categoriaServico;

	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}

	public void setCategoriaServico(CategoriaServico categoriaServico) {
		this.categoriaServico = categoriaServico;
	}

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

	/*
	 * public CategoriaServico getCategoriaServico() { return categoriaServico; }
	 * 
	 * public void setCategoriaServico(CategoriaServico categoriaServico) {
	 * this.categoriaServico = categoriaServico; }
	 */

}
