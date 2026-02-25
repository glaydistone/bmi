package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import br.com.tecnotran.bmi.util.jsf.FacesUtil;

/**
 * @author TONE_
 *
 */
@Entity
@Table(name="valores_indexados")
public class ValoresIndexados extends BaseEntity {

	public PlanilhaPadrao getPlanilhaPreco() {
		return planilhaPreco;
	}

	public void setPlanilhaPreco(PlanilhaPadrao planilhaPreco) {
		this.planilhaPreco = planilhaPreco;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_valores_indexados")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private TipoMovimento tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name = "id_planilha_preco", nullable = false, foreignKey = @ForeignKey(name="FK_material_preco"))
	private PlanilhaPadrao planilhaPreco;
	
	@NotNull
	@Column(name="despesa_Administrativa", length = 15, scale = 4, nullable = false)
	private BigDecimal despesaAdministrativa = BigDecimal.ZERO;
	@NotNull
	@Column(name="taxas_Tributos", length = 15, scale = 4, nullable = false)
	private BigDecimal taxasTributos = BigDecimal.ZERO;
	@NotNull
	@Column(name="servicos_Pecas", length = 15, scale = 4, nullable = false)
	private BigDecimal servicosPecas = BigDecimal.ZERO;
	
	@NotNull
	@Column(name="rodagem", length = 15, scale = 4, nullable = false)
	private BigDecimal rodagem = BigDecimal.ZERO;
	
	public BigDecimal getRodagem() {
		return rodagem;
	}

	public void setRodagem(BigDecimal rodagem) {
		this.rodagem = rodagem;
	}

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal precoVeiculoReferencia= BigDecimal.ZERO;	

	public BigDecimal getPrecoVeiculoReferencia() {
		return precoVeiculoReferencia;
	}

	public void setPrecoVeiculoReferencia(BigDecimal precoVeiculoReferencia) {
		this.precoVeiculoReferencia = precoVeiculoReferencia;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}


	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}


	public BigDecimal getDespesaAdministrativa() {
		return despesaAdministrativa;
	}


	public void setDespesaAdministrativa(BigDecimal despesaAdministrativa) {
		this.despesaAdministrativa = despesaAdministrativa;
	}


	public BigDecimal getTaxasTributos() {
		return taxasTributos;
	}


	public void setTaxasTributos(BigDecimal taxasTributos) {
		this.taxasTributos = taxasTributos;
	}


	public BigDecimal getServicosPecas() {
		return servicosPecas;
	}


	public void setServicosPecas(BigDecimal servicosPecas) {
		this.servicosPecas = servicosPecas;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	public ValoresIndexados clone() throws CloneNotSupportedException {
		ValoresIndexados retorno = null;
		try {
			retorno = (ValoresIndexados) super.clone();
			retorno.id = null;

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());;
		}

		return retorno;
	}
}
