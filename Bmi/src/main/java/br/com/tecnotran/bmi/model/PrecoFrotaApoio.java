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
@Table(name="preco_frota_apoio")
public class PrecoFrotaApoio extends BaseEntity {
	
	private static final long serialVersionUID = -5766806657494758065L;
	
	@Id
	@GeneratedValue
	@Column(name="id_preco_frota_apoio")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_planilha_preco", nullable = false, foreignKey = @ForeignKey(name="FK_material_preco"))
	private PlanilhaPadrao planilhaPreco;

	@ManyToOne
	@JoinColumn(name = "id_tipo_veiculo_apoio", nullable = false, foreignKey = @ForeignKey(name="FK_planilha_tipoVeicApoio"))
	private TipoVeiculoApoio tipoVeiculoApoio;
	
	@Column(name="custo_minio", length = 15, scale = 4, nullable = false)
	private BigDecimal custoMinimo = BigDecimal.ZERO;
	
	@Column(name="custo_maximo", length = 15, scale = 4, nullable = false)
	private BigDecimal custoMaximo = BigDecimal.ZERO;
	
	public BigDecimal getCustoMaximo() {
		return custoMaximo;
	}

	public void setCustoMaximo(BigDecimal custoMaximo) {
		this.custoMaximo = custoMaximo;
	}

	public PrecoFrotaApoio(TipoVeiculoApoio tipoVeiculoApoio, BigDecimal custoQuilometro) {
		this.custoMinimo = custoQuilometro;
		this.tipoVeiculoApoio = tipoVeiculoApoio;
	}
	
	public TipoVeiculoApoio getTipoVeiculoApoio() {
		return tipoVeiculoApoio;
	}

	public void setTipoVeiculoApoio(TipoVeiculoApoio tipoVeiculoApoio) {
		this.tipoVeiculoApoio = tipoVeiculoApoio;
	}

	public BigDecimal getCustoMinimo() {
		return custoMinimo;
	}

	public void setCustoMinimo(BigDecimal custoQuilometro) {
		this.custoMinimo = custoQuilometro;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public PrecoFrotaApoio() {
		
	}
	


	public void setId(Long id) {
		this.id = id;
	}

	public PlanilhaPadrao getPlanilhaPreco() {
		return planilhaPreco;
	}

	public void setPlanilhaPreco(PlanilhaPadrao planilhaPreco) {
		this.planilhaPreco = planilhaPreco;
	}
	


}
