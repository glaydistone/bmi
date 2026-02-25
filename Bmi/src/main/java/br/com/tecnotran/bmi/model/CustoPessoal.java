package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Entity
@Table(name = "custo_pessoal")
public class CustoPessoal extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "id_custo_pes")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name = "FK_custoPes_boletim"))
	private Boletim boletim;

	@ManyToOne
	@JoinColumn(name = "id_cargo", nullable = false, foreignKey = @ForeignKey(name = "FK_custoPes_cargo"))
	private Cargo cargo;

	@Column(name="fator_ajuste")
	private BigDecimal fatorAjuste = BigDecimal.ONE;
	
	public BigDecimal getFatorAjuste() {
		//return fatorAjuste;  // retornar 1 até avaliar melhor essa alternativa
		return BigDecimal.ONE;
	}

	public void setFatorAjuste(BigDecimal fatorAjuste) {
		this.fatorAjuste = fatorAjuste;
	}

	private Integer elementos = 0;
	private BigDecimal salarios = BigDecimal.ZERO;
	private BigDecimal premios = BigDecimal.ZERO;
	private BigDecimal horasExtras = BigDecimal.ZERO;
	private BigDecimal outrosPagamentos = BigDecimal.ZERO;
	private BigDecimal encargosSociais = BigDecimal.ZERO;

	@Transient
	private TipoCargo tipoCargo;

	@Transient
	BigDecimal remuneracaoTotal = BigDecimal.ZERO;

	@Transient
	BigDecimal taxaEncargos = BigDecimal.ZERO;

	@Transient
	BigDecimal remuneracaoMedia = BigDecimal.ZERO;

	@Transient
	BigDecimal salarioMedio = BigDecimal.ZERO;

	@Transient
	BigDecimal fatorUtilizacao = BigDecimal.ZERO;

	@Transient
	BigDecimal valeAlimentacao = BigDecimal.ZERO;

	@Transient
	BigDecimal custoVeiculo = BigDecimal.ZERO;

	@Transient
	BigDecimal taxaEncargosPlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal remuneracaoMediaPlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal salarioMedioPlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal fatorUtilizacaoPlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal valeAlimentacaoPlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal planoSaudePlanilha = BigDecimal.ZERO;

	@Transient
	BigDecimal custoVeiculoPlanilha = BigDecimal.ZERO;

	@Transient
	Long grupoCusto;

	@Transient
	BigDecimal planoSaude = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaSalario = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaRemunera = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaEncargos = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaVale = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaSaude = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaCusto = BigDecimal.ZERO;
	@Transient
	BigDecimal diferencaFU = BigDecimal.ZERO;


	public BigDecimal getCustoVeiculo() {
		return (getRemuneracaoMedia().add(getValeAlimentacao()).add(getPlanoSaude()))
				.multiply(getFatorUtilizacao()).multiply((BigDecimal.ONE.add(getTaxaEncargos())));
	}

	public BigDecimal getCustoVeiculoPlanilha() {
		return (getRemuneracaoMediaPlanilha().add(getValeAlimentacaoPlanilha()).add(getPlanoSaudePlanilha()))
				.multiply(getFatorUtilizacaoPlanilha()).multiply((BigDecimal.ONE.add(getTaxaEncargosPlanilha())));
	}

	
	public BigDecimal getDiferencaSalario() {
		if (getSalarioMedioPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			return getSalarioMedioPlanilha().subtract(getSalarioMedio()).abs().divide(getSalarioMedioPlanilha(), 2,
					RoundingMode.HALF_EVEN);
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaRemunera() {
		if (getRemuneracaoMediaPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getRemuneracaoMediaPlanilha().subtract(getRemuneracaoMedia()).abs()
						.divide(getRemuneracaoMediaPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaEncargos() {
		try {
			if (getTaxaEncargosPlanilha().compareTo(BigDecimal.ZERO) > 0) {
				return getTaxaEncargosPlanilha().subtract(getTaxaEncargos()).abs().divide(getTaxaEncargosPlanilha(), 2,
						RoundingMode.HALF_EVEN);
			} else
				return BigDecimal.ZERO;
		} catch (Exception e) {
			return null;
		}
	}

	public BigDecimal getDiferencaVale() {
		if (getValeAlimentacaoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getValeAlimentacaoPlanilha().subtract(getValeAlimentacao()).abs()
						.divide(getValeAlimentacaoPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaSaude() {
		if (getPlanoSaudePlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getPlanoSaudePlanilha().subtract(getPlanoSaude()).abs().divide(getPlanoSaudePlanilha(), 2,
						RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaFU() {
		if (getFatorUtilizacaoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getFatorUtilizacaoPlanilha().subtract(getFatorUtilizacao()).abs()
						.divide(getFatorUtilizacaoPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaCusto() {
		try {
			if (getCustoVeiculoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
				return getCustoVeiculoPlanilha().subtract(getCustoVeiculo()).abs().divide(getCustoVeiculoPlanilha(), 2,
						RoundingMode.HALF_EVEN);
			} else
				return BigDecimal.ZERO;
		} catch (Exception e) {
			return null;
		}
	}

	public BigDecimal getTaxaEncargosPlanilha() {
		return taxaEncargosPlanilha;
	}

	public void setTaxaEncargosPlanilha(BigDecimal taxaEncargosPlanilha) {
		this.taxaEncargosPlanilha = taxaEncargosPlanilha;
	}

	public BigDecimal getRemuneracaoMediaPlanilha() {
		return remuneracaoMediaPlanilha;
	}

	public String getLimiteRemuneracaoPlanilha() {
		if (getRemuneracaoMediaPlanilha() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat( "#,##0.00" );
			
			/*
			String retorno = df.format(getRemuneracaoMediaPlanilha().multiply(new BigDecimal(0.99)));
			retorno = retorno +" a "+df.format(getRemuneracaoMediaPlanilha().multiply(new BigDecimal(1.015)));
			*/
			String retorno = df.format(getRemuneracaoMediaPlanilha());
			return retorno;
		}
	}
	
	public void setRemuneracaoMediaPlanilha(BigDecimal remuneracaoMediaPlanilha) {
		this.remuneracaoMediaPlanilha = remuneracaoMediaPlanilha.multiply(getFatorAjuste());
	}

	public BigDecimal getSalarioMedioPlanilha() {
		return salarioMedioPlanilha.multiply(getFatorAjuste());
	}

	public void setSalarioMedioPlanilha(BigDecimal salarioMedioPlanilha) {
		this.salarioMedioPlanilha = salarioMedioPlanilha;
	}

	public BigDecimal getFatorUtilizacaoPlanilha() {
		return fatorUtilizacaoPlanilha;
	}

	public void setFatorUtilizacaoPlanilha(BigDecimal fatorUtilizacaoPlanilha) {
		this.fatorUtilizacaoPlanilha = fatorUtilizacaoPlanilha;
	}

	public BigDecimal getValeAlimentacaoPlanilha() {
		return valeAlimentacaoPlanilha.multiply(getFatorAjuste());
	}

	public void setValeAlimentacaoPlanilha(BigDecimal valeAlimentacaoPlanilha) {
		this.valeAlimentacaoPlanilha = valeAlimentacaoPlanilha;
	}

	public BigDecimal getPlanoSaudePlanilha() {
		return planoSaudePlanilha;
	}

	public void setPlanoSaudePlanilha(BigDecimal planoSaudePlanilha) {
		this.planoSaudePlanilha = planoSaudePlanilha.multiply(getFatorAjuste());;
	}

	public BigDecimal getValeAlimentacao() {
		return valeAlimentacao;
	}

	public void setValeAlimentacao(BigDecimal valeAlimentacao) {
		this.valeAlimentacao = valeAlimentacao;
	}

	public BigDecimal getPlanoSaude() {
		return planoSaude;
	}

	public void setPlanoSaude(BigDecimal planoSaude) {
		this.planoSaude = planoSaude;
	}

	public Long getGrupoCusto() {
		return grupoCusto;
	}

	public void setGrupoCusto(Long grupoCusto) {
		this.grupoCusto = grupoCusto;
	}

	@Override
	public CustoPessoal clone() throws CloneNotSupportedException {
		CustoPessoal retorno = null;
		try {
			retorno = (CustoPessoal) super.clone();
			retorno.id = null;

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());
			;
		}

		return retorno;
	}

	public BigDecimal getRemuneracaoTotal() {
		try {
			return getSalarios().add(getHorasExtras()).add(getOutrosPagamentos()).add(getPremios());
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	public void setRemuneracaoTotal(BigDecimal remuneracaoTotal) {
		this.remuneracaoTotal = remuneracaoTotal;
	}

	public BigDecimal getTaxaEncargos() {
		if (getRemuneracaoTotal().equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		} else {
			try {
				return getEncargosSociais().divide(getRemuneracaoTotal(), 10, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		}
	}

	public void setTaxaEncargos(BigDecimal taxaEncargos) {
		this.taxaEncargos = taxaEncargos;
	}

	public BigDecimal getRemuneracaoMedia() {
		if (getElementos() > 0) {
			return getRemuneracaoTotal().divide(new BigDecimal(getElementos()),2, RoundingMode.HALF_EVEN);
		} else {
			return BigDecimal.ZERO;
		}
	}

	public void setRemuneracaoMedia(BigDecimal remuneracaoMedia) {
		this.remuneracaoMedia = remuneracaoMedia;
	}

	public BigDecimal getSalarioMedio() {
		if (getElementos() > 0) {
			return getSalarios().divide(new BigDecimal(getElementos()), 2, RoundingMode.HALF_EVEN);
		} else {
			return BigDecimal.ZERO;
		}
	}

	public void setSalarioMedio(BigDecimal salarioMedio) {
		this.salarioMedio = salarioMedio;
	}

	public BigDecimal getFatorUtilizacao() {
		if (getBoletim() == null || getElementos() == null || getBoletim().getFrotaTotal() == null || getBoletim().getFrotaTotal() == 0) {
			return null;
		} else {
			return new BigDecimal(getElementos().doubleValue() /getBoletim().getFrotaTotal().doubleValue());
		}

	}

	public void setFatorUtilizacao(BigDecimal fatorUtilizacao) {
		this.fatorUtilizacao = fatorUtilizacao;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Integer getElementos() {
		return elementos;
	}

	public void setElementos(Integer elementos) {
		this.elementos = elementos;
	}

	public BigDecimal getSalarios() {
		return salarios;
	}

	public void setSalarios(BigDecimal salarios) {
		this.salarios = salarios;
	}

	public BigDecimal getPremios() {
		return premios;
	}

	public void setPremios(BigDecimal premios) {
		this.premios = premios;
	}

	public BigDecimal getHorasExtras() {
		return horasExtras;
	}

	public void setHorasExtras(BigDecimal horasExtras) {
		this.horasExtras = horasExtras;
	}

	public BigDecimal getOutrosPagamentos() {
		return outrosPagamentos;
	}

	public void setOutrosPagamentos(BigDecimal outrosPagamentos) {
		this.outrosPagamentos = outrosPagamentos;
	}

	public BigDecimal getEncargosSociais() {
		return encargosSociais;
	}

	public void setEncargosSociais(BigDecimal encargosSociais) {
		this.encargosSociais = encargosSociais;
	}

}
