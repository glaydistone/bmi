package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receita_imobilizado")
public class ReceitaImobilizado extends BaseEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id_receita_imobilizado")
	private Long id;

	private static final long serialVersionUID = 1L;
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_receita_imob", nullable = false, foreignKey = @ForeignKey(name="FK_recImov_tipoRecImob"))
	private TipoReceitaImobilizado tipoReceitaImobilizado;
	
	private BigDecimal valor;
	
	
	@Column(name = "descricao", length = 80)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name="FK_recImob_boletim"))
	private Boletim boletim;

	public TipoReceitaImobilizado getTipoReceitaImobilizado() {
		return tipoReceitaImobilizado;
	}

	public void setTipoReceitaImobilizado(TipoReceitaImobilizado tipoReceitaImobilizado) {
		this.tipoReceitaImobilizado = tipoReceitaImobilizado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
