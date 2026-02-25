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
@Table(name = "servico")
public class Servico extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Column(name="id_servico")
	@Id
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	@Override	
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	@ManyToOne
	@JoinColumn(name = "id_tipo_servico", nullable = false, foreignKey = @ForeignKey(name="FK_servico_tipoServico"))
	private TipoServico tipoServico;
	
	private BigDecimal valor;
	private BigDecimal quantidade;
	
	
	@Column(name = "descricao", length = 80)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name="FK_recImob_boletim"))
	private Boletim boletim;


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

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
