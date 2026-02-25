/**
 * 
 */
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

/**
 * @author TONE_
 *
 */
@Entity
@Table(name="coeficiente_consumo")
public class CoeficienteConsumo extends BaseEntity {
	
	private static final long serialVersionUID = -5766806657494758065L;
	
	@Id
	@GeneratedValue
	@Column(name="id_coeficiente_consumo")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_material", nullable = false, foreignKey = @ForeignKey(name="FK_precoMaterial_tipoMaterial"))
	private TipoMaterial tipoMaterial;
	
	@ManyToOne
	@JoinColumn(name = "id_planilha_preco", nullable = false, foreignKey = @ForeignKey(name="FK_material_preco"))
	private PlanilhaPadrao planilhaPreco;
	
	@Column(length = 10, scale = 4, nullable = false)
	private BigDecimal coeficiente;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private TipoMovimento tipoMovimento;
	
	
	public PlanilhaPadrao getPlanilhaPreco() {
		return planilhaPreco;
	}

	public void setPlanilhaPreco(PlanilhaPadrao planilhaPreco) {
		this.planilhaPreco = planilhaPreco;
	}
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public CoeficienteConsumo() {
		
	}
		
	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}



	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public BigDecimal getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(BigDecimal coeficiente) {
		this.coeficiente = coeficiente;
	}

	public void setId(Long id) {
		this.id = id;
	}
	


}
