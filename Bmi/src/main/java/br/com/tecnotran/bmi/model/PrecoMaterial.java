/**
 * 
 */
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

/**
 * @author TONE_
 * Preços de referência considerados para os materiais
 *
 */
@Entity
@Table(name="preco_material")
public class PrecoMaterial extends BaseEntity {
	
	private static final long serialVersionUID = -5766806657494758065L;
	
	@Id
	@GeneratedValue
	@Column(name="id_preco_material")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_material", nullable = false, foreignKey = @ForeignKey(name="FK_precoMaterial_tipoMaterial"))
	private TipoMaterial tipoMaterial;
	
	@Column(length = 10, scale = 4, nullable = false)
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name = "id_planilha_preco", nullable = false, foreignKey = @ForeignKey(name="FK_material_preco"))
	private PlanilhaPadrao planilhaPreco;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public PrecoMaterial() {
		
	}
	
	public PlanilhaPadrao getPlanilhaPreco() {
		return planilhaPreco;
	}

	public void setPlanilhaPreco(PlanilhaPadrao planilhaPreco) {
		this.planilhaPreco = planilhaPreco;
	}

	public PrecoMaterial(TipoMaterial tipoMaterial, BigDecimal preco) {
		this.setTipoMaterial(tipoMaterial);
		this.setValor(preco);
	}
	
	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public void setId(Long id) {
		this.id = id;
	}
	


}
