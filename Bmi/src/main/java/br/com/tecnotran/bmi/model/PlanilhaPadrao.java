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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name="planilha_preco")
public class PlanilhaPadrao extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_planilha_preco")
	private Long id;
	
	@NotNull
	@Column(name="data_referencia", nullable = false, unique = true)
	@Temporal(TemporalType.DATE)
	private Date dataReferencia;
	



	@OneToMany(mappedBy = "planilhaPreco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PlanilhaPessoal> planilhaPessoal = new ArrayList<PlanilhaPessoal>();
	
	@OneToMany(mappedBy = "planilhaPreco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PrecoFrotaApoio> precosFrotaApoio = new ArrayList<PrecoFrotaApoio>();

	@OneToMany(mappedBy = "planilhaPreco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ValoresIndexados> valoresIndexados = new ArrayList<ValoresIndexados>();
	
	@OneToMany(mappedBy = "planilhaPreco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CoeficienteConsumo> coeficienteConsumo = new ArrayList<CoeficienteConsumo>();	
	
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal ipca = BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal igpm= BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fgvRodagem= BigDecimal.ZERO;
	@NotNull
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fgvPecas= BigDecimal.ZERO;
	
	
	
	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PlanilhaPadrao retorno = null;

		try {
			retorno = (PlanilhaPadrao) super.clone();
			retorno.id = null;
			retorno.setCoeficienteConsumo(new ArrayList<CoeficienteConsumo>());
			for (CoeficienteConsumo m : getCoeficienteConsumo()) {
				CoeficienteConsumo mc = (CoeficienteConsumo) m.clone();
				mc.setId(null);
				mc.setPlanilhaPreco(retorno);
				retorno.getCoeficienteConsumo().add(mc);
			}
			
			
			
			retorno.setPlanilhaPessoal(new ArrayList<PlanilhaPessoal>());
			for (PlanilhaPessoal m : getPlanilhaPessoal()) {
				PlanilhaPessoal mc = (PlanilhaPessoal) m.clone();
				mc.setId(null);
				mc.setPlanilhaPreco(retorno);
				retorno.getPlanilhaPessoal().add(mc);
			}
			
			
			retorno.setPrecosFrotaApoio(new ArrayList<PrecoFrotaApoio>());
			for (PrecoFrotaApoio m : getPrecosFrotaApoio()) {
				PrecoFrotaApoio mc = (PrecoFrotaApoio) m.clone();
				mc.setId(null);
				mc.setPlanilhaPreco(retorno);
				retorno.getPrecosFrotaApoio().add(mc);
			}
			
			retorno.setPrecosMaterial(new ArrayList<PrecoMaterial>());
			for (PrecoMaterial m : getPrecosMaterial()) {
				PrecoMaterial mc = (PrecoMaterial) m.clone();
				mc.setId(null);
				mc.setPlanilhaPreco(retorno);
				retorno.getPrecosMaterial().add(mc);
			}
			
			retorno.setValoresIndexados(new ArrayList<ValoresIndexados>());
			for (ValoresIndexados m : getValoresIndexados()) {
				ValoresIndexados mc = (ValoresIndexados) m.clone();
				mc.setId(null);
				mc.setPlanilhaPreco(retorno);
				retorno.getValoresIndexados().add(mc);
			}
		} catch (CloneNotSupportedException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
			
		return retorno;

	}
	
	public Date getMesAno() {
		return dataReferencia;
	}

	public void setMesAno(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public BigDecimal getIpca() {
		return ipca;
	}

	public void setIpca(BigDecimal ipca) {
		this.ipca = ipca;
	}

	public BigDecimal getIgpm() {
		return igpm;
	}

	public void setIgpm(BigDecimal igpm) {
		this.igpm = igpm;
	}

	public BigDecimal getFgvRodagem() {
		return fgvRodagem;
	}

	public void setFgvRodagem(BigDecimal fgvRodagem) {
		this.fgvRodagem = fgvRodagem;
	}

	public BigDecimal getFgvPecas() {
		return fgvPecas;
	}

	public void setFgvPecas(BigDecimal fgvPecas) {
		this.fgvPecas = fgvPecas;
	}

	public List<PrecoFrotaApoio> getPrecosFrotaApoio() {
		return precosFrotaApoio;
	}

	public void setPrecosFrotaApoio(List<PrecoFrotaApoio> precosFrotaApoio) {
		this.precosFrotaApoio = precosFrotaApoio;
	}

	

	public List<CoeficienteConsumo> getCoeficienteConsumo() {
		return coeficienteConsumo;
	}

	public void setCoeficienteConsumo(List<CoeficienteConsumo> coeficienteConsumo) {
		this.coeficienteConsumo = coeficienteConsumo;
	}


	@OneToMany(mappedBy = "planilhaPreco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PrecoMaterial> precosMaterial = new ArrayList<PrecoMaterial>();
	
	public List<ValoresIndexados> getValoresIndexados() {
		return valoresIndexados;
	}

	public void setValoresIndexados(List<ValoresIndexados> valoresIndexados) {
		this.valoresIndexados = valoresIndexados;
	}

	public List<PlanilhaPessoal> getPlanilhaPessoal() {
		return planilhaPessoal;
	}

	public void setPlanilhaPessoal(List<PlanilhaPessoal> planilhaPessoal) {
		this.planilhaPessoal = planilhaPessoal;
	}
	
	public List<PrecoMaterial> getPrecosMaterial() {
		return precosMaterial;
	}

	public void setPrecosMaterial(List<PrecoMaterial> precosMaterial) {
		this.precosMaterial = precosMaterial;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
