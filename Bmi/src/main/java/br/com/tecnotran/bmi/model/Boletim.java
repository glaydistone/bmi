package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.tecnotran.bmi.util.jsf.FacesUtil;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "boletim")
public class Boletim extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private static final long PESSOAL_ADM = 1L;
	private static final long PESSOAL_MANUTENCAO = 2L;
	private static final long PESSOAL_OPERACAO = 3L;
	private static final long MOTORISTA = 25L;
	private static final long COBRADOR = 23L;
	private static final long FISCAL = 24L;
	private static final long DIRETOR = 4L;

	private static final long COMBUSTIVEL = 1L;
	private static final long LUBRIFICANTES = 2L;
	private static final long PECAS = 3L;
	private static final long RODAGEM = 4L;

	private static final long GRAXA = 2L;
	private static final long CAIXA = 3L;
	private static final long DIFERENCIAL = 4L;
	private static final long FREIO = 5L;
	private static final long MOTOR = 6L;

	private static final long VALE_ALIMENTACAO = 1L;
	private static final long PLANO_SAUDE = 25L;
	private static final long TRIBUTOS = 8L;

	@Id
	@GeneratedValue
	@Column(name = "id_boletim")
	private Long id;

	@Column(name = "data_referencia")
	@Temporal(TemporalType.DATE)
	private Date dataReferencia;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 2)
	private StatusBoletim status = StatusBoletim.NC;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private TipoMovimento tipoMovimento;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false, foreignKey = @ForeignKey(name = "FK_boletim_empresa"))
	private Empresa empresa;

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("cargo")
	private List<CustoPessoal> custosPessoal = new ArrayList<CustoPessoal>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("tipoDespesa")
	private List<DespesaGeral> despesasGerais = new ArrayList<DespesaGeral>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FrotaApoio> frotasApoio = new ArrayList<FrotaApoio>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("tipoMaterial")
	private List<Material> materiais = new ArrayList<Material>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Quilometragem> quilometragens = new ArrayList<Quilometragem>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("tipoReceitaImobilizado")
	private List<ReceitaImobilizado> receitasImobilizado = new ArrayList<ReceitaImobilizado>();

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("tipoServico")
	private List<Servico> servicos = new ArrayList<Servico>();

	@Column(name = "frota_efetiva", nullable = false)
	private Integer frotaEfetiva = 0;

	@Column(name = "frota_reserva", nullable = false)
	private Integer frotaReserva = 0;

	@Column(name = "quilometragem_1", nullable = false)
	private BigDecimal quilometragem_piso1 = BigDecimal.ZERO;

	@Column(name = "quilometragem_2", nullable = false)
	private BigDecimal quilometragem_piso2 = BigDecimal.ZERO;

	@Column(name = "quilometragem_3", nullable = false)
	private BigDecimal quilometragem_piso3 = BigDecimal.ZERO;

	@Column(name = "digitacao_concluida", nullable = false)
	private boolean digitacaoConcluida = false;

	@Column(name = "bloqueado", nullable = false)
	private boolean bloqueado = false;

	////
	
	/**
	 * Retorna um número aleatório entre 0,90 e 1,10
	 * @return
	 */
	public static BigDecimal getAleatorio() {
		BigDecimal rnd = new BigDecimal(Math.random() * 0.20);
		BigDecimal retorno = new BigDecimal("1.10");
		retorno = retorno.subtract(rnd, MathContext.DECIMAL32);
		return retorno;
	}

	public boolean isDigitacaoConcluida() {
		return digitacaoConcluida;
	}

	public void setDigitacaoConcluida(boolean digitacaoConcluida) {
		this.digitacaoConcluida = digitacaoConcluida;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Transient
	private CustoPessoal custoAdministracao = new CustoPessoal();

	public void setTaxaEncargosAdministracao(BigDecimal taxaEncargosAdministracao) {
		this.taxaEncargosAdministracao = taxaEncargosAdministracao;
	}

	public void setTaxaEncargosManutencao(BigDecimal taxaEncargosManutencao) {
		this.taxaEncargosManutencao = taxaEncargosManutencao;
	}

	public void setTaxaEncargosOutros(BigDecimal taxaEncargosOutros) {
		this.taxaEncargosOutros = taxaEncargosOutros;
	}

	@Transient
	private CustoPessoal custoManutencao = new CustoPessoal();
	@Transient
	private CustoPessoal custoOperacao = new CustoPessoal();
	@Transient
	private CustoPessoal custoMotorista = new CustoPessoal();
	@Transient
	private CustoPessoal custoCobrador = new CustoPessoal();
	@Transient
	private CustoPessoal custoFiscal = new CustoPessoal();

	@Transient
	private BigDecimal fuAdministracao = BigDecimal.ZERO;
	@Transient
	private BigDecimal fuManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal fuOutros = BigDecimal.ZERO;

	@Transient
	private BigDecimal remuneraAdministracao = BigDecimal.ZERO;
	@Transient
	private BigDecimal remuneraManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal remuneraOutros = BigDecimal.ZERO;

	@Transient
	private BigDecimal taxaEncargosAdministracao = BigDecimal.ZERO;
	@Transient
	private BigDecimal taxaEncargosManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal taxaEncargosOutros = BigDecimal.ZERO;

	@Transient
	private BigDecimal custoRodagem = BigDecimal.ZERO;

	@Transient
	private BigDecimal encargoFolhaPagamento = BigDecimal.ZERO;

	@Transient
	private Integer elementos = 0;

	@Transient
	private BigDecimal custoVariavel = BigDecimal.ZERO;

	@Transient
	private BigDecimal quilometragemTotal = BigDecimal.ZERO;
	@Transient
	private Integer frotaTotal = 0;

	@Transient
	private List<Material> combusLubrif = new ArrayList<Material>();

	@Transient
	private List<Material> pecasServicos = new ArrayList<Material>();
	@Transient
	private List<Material> rodagem = new ArrayList<Material>();
	@Transient
	private List<DespesaGeral> valePlano = new ArrayList<DespesaGeral>();
	@Transient
	private List<DespesaGeral> despesasAdm = new ArrayList<DespesaGeral>();
	@Transient
	private List<DespesaGeral> tributos = new ArrayList<DespesaGeral>();

	@Transient
	private List<CustoPessoal> pessoalAdministrativo = new ArrayList<CustoPessoal>();
	@Transient
	private List<CustoPessoal> pessoalManutencao = new ArrayList<CustoPessoal>();
	@Transient
	private List<CustoPessoal> pessoalOperacao = new ArrayList<CustoPessoal>();
	@Transient
	private List<CustoPessoal> pessoalOutros = new ArrayList<CustoPessoal>();

	@Transient
	private BigDecimal diferencaRodagem = BigDecimal.ZERO;

	@Transient
	private Material custoPecas = new Material();
	@Transient
	private BigDecimal custoRodagemKm = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoRodagemKmSistema = BigDecimal.ZERO;
	@Transient
	private String limiteRodagemSistema;
	@Transient
	private BigDecimal valorCustoPessoal = BigDecimal.ZERO;
	@Transient
	private BigDecimal valorCustoPessoalKm = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoPecasKm = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoPecasSistema = BigDecimal.ZERO;
	@Transient
	private String limiteCustoPecasSistema;
	@Transient
	private BigDecimal tributosSistema = BigDecimal.ZERO;
	@Transient
	private String limiteTributosSistema;
	@Transient
	private String limiteDespesasSistema;

	public String getLimiteCustoPecasSistema() {
		if (getCustoPecasSistema() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat("#0.00000");
			String retorno = df.format(getCustoPecasSistema().multiply(new BigDecimal(0.99)));
			retorno = retorno + " a " + df.format(getCustoPecasSistema().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getCustoPecasSistema());
			///
			return retorno;
		}
	}

	public String getLimiteRodagemSistema() {
		if (getCustoRodagemKmSistema() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat("#0.00000");
			String retorno = df.format(getCustoRodagemKmSistema().multiply(new BigDecimal(0.99)));
			retorno = retorno + " a " + df.format(getCustoRodagemKmSistema().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getCustoRodagemKmSistema());
			///
			return retorno;
		}
	}

	public String getLimiteTributosSistema() {
		if (getTributosSistema() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat("#0.00000");
			String retorno = df.format(getTributosSistema().multiply(new BigDecimal(0.99)));
			retorno = retorno + " a " + df.format(getTributosSistema().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getTributosSistema());
			///
			return retorno;
		}
	}

	public String getLimiteDespesasSistema() {
		if (getDespesasSistema() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat("#0.00000");
			String retorno = df.format(getDespesasSistema().multiply(new BigDecimal(0.99)));
			retorno = retorno + " a " + df.format(getDespesasSistema().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getDespesasSistema());
			///
			return retorno;
		}
	}

	@Transient
	private BigDecimal diferencaTributos = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaPecas = BigDecimal.ZERO;
	@Transient
	private BigDecimal despesasKm = BigDecimal.ZERO;
	@Transient
	private BigDecimal despesasSistema = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaDespesas = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoTributosKm = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaFuAdm = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaRemuneraAdm = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaEncargosAdm = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaFuManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaRemuneraManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaEncargosManutencao = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaFuOutrosOperacao = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaRemuneraOutros = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaEncargosOutros = BigDecimal.ZERO;
	@Transient
	private BigDecimal valorTotalDespesaGeral = BigDecimal.ZERO;
	@Transient
	private BigDecimal valorTotalDespesaTributaria = BigDecimal.ZERO;

	public BigDecimal getDespesaGeralKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getQuilometragemTotal() == null) {
			return null;
		}

		return getValorTotalDespesaGeral().divide(getQuilometragemTotal(), new MathContext(8, RoundingMode.HALF_EVEN));
	}

	public BigDecimal getDespesaTributariaKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getQuilometragemTotal() == null) {
			return null;
		}

		return getValorTotalDespesaTributaria().divide(getQuilometragemTotal(),
				new MathContext(8, RoundingMode.HALF_EVEN));
	}

	public BigDecimal getValorTotalDespesaTributaria() {
		valorTotalDespesaTributaria = BigDecimal.ZERO;
		for (DespesaGeral dg : despesasGerais) {
			if (dg.getTipoDespesa().getCategoriaDespesa().getNumero().equals(TRIBUTOS)) {
				valorTotalDespesaTributaria = valorTotalDespesaTributaria.add(dg.getValor());
			}
		}
		return valorTotalDespesaTributaria;
	}

	public BigDecimal getValorTotalDespesaGeral() {
		valorTotalDespesaGeral = BigDecimal.ZERO;
		for (DespesaGeral dg : despesasGerais) {
			if (!dg.getTipoDespesa().getCategoriaDespesa().getNumero().equals(TRIBUTOS)) {
				valorTotalDespesaGeral = valorTotalDespesaGeral.add(dg.getValor());
			}
		}
		return valorTotalDespesaGeral;
	}

	public BigDecimal getValorCustoPessoal() {
		BigDecimal retorno = BigDecimal.ZERO;
		for (CustoPessoal cp : getCustosPessoal()) {
			retorno = retorno.add(cp.getRemuneracaoTotal().add(cp.getEncargosSociais()));
		}
		return retorno;
	}

	public BigDecimal getRemuneraAdministracao() {
		BigDecimal retorno = BigDecimal.ZERO;
		Integer el = 0;
		for (CustoPessoal cp : getPessoalAdministrativo()) {
			retorno = retorno.add(cp.getRemuneracaoTotal());
			el = el + cp.getElementos();
		}
		if (el == 0) {
			return null;
		} else {
			try {
				return retorno.divide(new BigDecimal(el), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getValorCustoPessoalKm() {
		BigDecimal retorno = BigDecimal.ZERO;
		Integer el = 0;
		for (CustoPessoal cp : getPessoalAdministrativo()) {
			retorno = retorno.add(cp.getRemuneracaoTotal());
			el = el + cp.getElementos();
		}
		if (el == 0) {
			return null;
		} else {
			try {
				return retorno.divide(new BigDecimal(el), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getDiferencaFuAdm() {
		if (getCustoAdministracao().getFatorUtilizacaoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoAdministracao().getFatorUtilizacaoPlanilha().subtract(getFuAdministracao()).abs()
						.divide(getCustoAdministracao().getFatorUtilizacaoPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaRemuneraAdm() {
		if (getCustoAdministracao().getRemuneracaoMediaPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoAdministracao().getRemuneracaoMediaPlanilha().subtract(getRemuneraAdministracao()).abs()
						.divide(getCustoAdministracao().getRemuneracaoMediaPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaEncargosAdm() {
		try {
			return getCustoAdministracao().getTaxaEncargosPlanilha().subtract(getTaxaEncargosAdministracao()).abs()
					.divide(getCustoAdministracao().getTaxaEncargosPlanilha(), 2, RoundingMode.HALF_EVEN);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	public BigDecimal getRemuneraManutencao() {
		BigDecimal retorno = BigDecimal.ZERO;
		Integer el = 0;
		for (CustoPessoal cp : getPessoalManutencao()) {
			retorno = retorno.add(cp.getRemuneracaoTotal());
			el = el + cp.getElementos();
		}
		if (el == 0) {
			return null;
		} else {
			try {
				return retorno.divide(new BigDecimal(el), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getRemuneraOutros() {
		BigDecimal retorno = BigDecimal.ZERO;
		Integer el = 0;
		for (CustoPessoal cp : getPessoalOutros()) {
			retorno = retorno.add(cp.getRemuneracaoTotal());
			el = el + cp.getElementos();
		}
		if (el == 0) {
			return null;
		} else {
			try {
				return retorno.divide(new BigDecimal(el), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getTaxaEncargosAdministracao() {
		BigDecimal retorno = BigDecimal.ZERO;
		BigDecimal totalRemunera = BigDecimal.ZERO;
		for (CustoPessoal cp : getPessoalAdministrativo()) {
			totalRemunera = totalRemunera.add(cp.getRemuneracaoTotal());
			retorno = retorno.add(cp.getEncargosSociais());
		}
		if (totalRemunera.equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return retorno.divide(totalRemunera, RoundingMode.FLOOR);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	public BigDecimal getTaxaEncargosManutencao() {
		BigDecimal retorno = BigDecimal.ZERO;
		BigDecimal totalRemunera = BigDecimal.ZERO;
		for (CustoPessoal cp : getPessoalManutencao()) {
			totalRemunera = totalRemunera.add(cp.getRemuneracaoTotal());
			retorno = retorno.add(cp.getEncargosSociais());
		}
		if (totalRemunera.equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return retorno.divide(totalRemunera, RoundingMode.FLOOR);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getTaxaEncargosOutros() {

		BigDecimal retorno = BigDecimal.ZERO;
		BigDecimal totalRemunera = BigDecimal.ZERO;
		for (CustoPessoal cp : getPessoalOutros()) {
			totalRemunera = totalRemunera.add(cp.getRemuneracaoTotal());
			retorno = retorno.add(cp.getEncargosSociais());
		}
		if (totalRemunera.equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return retorno.divide(totalRemunera, RoundingMode.FLOOR);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getEncargosOutros() {
		BigDecimal retorno = BigDecimal.ZERO;
		for (CustoPessoal cp : getPessoalOutros()) {
			retorno = retorno.add(cp.getEncargosSociais());
		}
		return retorno;

	}

	public BigDecimal getFuAdministracao() {
		if (getFrotaTotal() == 0 || getFrotaTotal() == null) {
			return null;
		} else {
			Integer el = 0;
			for (CustoPessoal c : getPessoalAdministrativo()) {
				el = el + c.getElementos();
			}
			return new BigDecimal(el / getFrotaTotal().doubleValue());
		}
	}

	public BigDecimal getFuManutencao() {
		if (getFrotaTotal() == 0 || getFrotaTotal() == null) {
			return null;
		} else {
			Integer el = 0;
			for (CustoPessoal c : getPessoalManutencao()) {
				el = el + c.getElementos();
			}
			return new BigDecimal(el / getFrotaTotal().doubleValue());
		}
	}

	public BigDecimal getFuOutros() {
		if (getFrotaTotal() == 0 || getFrotaTotal() == null) {
			return null;
		} else {
			Integer el = 0;
			for (CustoPessoal c : getPessoalOutros()) {
				el = el + c.getElementos();
			}
			return new BigDecimal(el / getFrotaTotal().doubleValue());
		}
	}

	public BigDecimal getDiferencaFuManutencao() {
		if (getCustoManutencao().getFatorUtilizacaoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoManutencao().getFatorUtilizacaoPlanilha().subtract(getFuManutencao()).abs()
						.divide(getCustoManutencao().getFatorUtilizacaoPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaRemuneraManutencao() {
		if (getCustoManutencao().getRemuneracaoMediaPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoManutencao().getRemuneracaoMediaPlanilha().subtract(getRemuneraManutencao()).abs()
						.divide(getCustoManutencao().getRemuneracaoMediaPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaEncargosManutencao() {
		if (getCustoManutencao().getTaxaEncargosPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoManutencao().getTaxaEncargosPlanilha().subtract(getTaxaEncargosManutencao()).abs()
						.divide(getCustoManutencao().getTaxaEncargosPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaFuOutrosOperacao() {
		if (getCustoOperacao().getFatorUtilizacaoPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoOperacao().getFatorUtilizacaoPlanilha().subtract(getFuOutros()).abs()
						.divide(getCustoOperacao().getFatorUtilizacaoPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaRemuneraOutros() {
		if (getCustoOperacao().getRemuneracaoMediaPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoOperacao().getRemuneracaoMediaPlanilha().subtract(getRemuneraOutros()).abs()
						.divide(getCustoOperacao().getRemuneracaoMediaPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public BigDecimal getDiferencaEncargosOutros() {
		if (getCustoOperacao().getTaxaEncargosPlanilha().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return getCustoOperacao().getTaxaEncargosPlanilha().subtract(getTaxaEncargosOutros()).abs()
						.divide(getCustoOperacao().getTaxaEncargosPlanilha(), 2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		} else
			return BigDecimal.ZERO;
	}

	public List<CustoPessoal> getPessoalAdministrativo() {
		return pessoalAdministrativo;
	}

	public void setPessoalAdministrativo(List<CustoPessoal> pessoalAdministrativo) {
		this.pessoalAdministrativo = pessoalAdministrativo;
	}

	public List<CustoPessoal> getPessoalManutencao() {
		return pessoalManutencao;
	}

	public void setPessoalManutencao(List<CustoPessoal> pessoalManutencao) {
		this.pessoalManutencao = pessoalManutencao;
	}

	public List<CustoPessoal> getPessoalOperacao() {
		return pessoalOperacao;
	}

	public void setPessoalOperacao(List<CustoPessoal> pessoalOperacao) {
		this.pessoalOperacao = pessoalOperacao;
	}

	public List<CustoPessoal> getPessoalOutros() {
		return pessoalOutros;
	}

	public void setPessoalOutros(List<CustoPessoal> pessoalOutros) {
		this.pessoalOutros = pessoalOutros;
	}

	public BigDecimal getTributosSistema() {
		return tributosSistema;
	}

	public BigDecimal getCustoRodagem() {
		BigDecimal retorno = BigDecimal.ZERO;
		for (Material m : getRodagem()) {
			retorno = retorno.add(m.getValor());
		}
		return retorno;
	}

	public void setCustoRodagem(BigDecimal custoRodagem) {
		this.custoRodagem = custoRodagem;
	}

	public void setTributosSistema(BigDecimal tributosSistema) {
		this.tributosSistema = tributosSistema;
	}

	public BigDecimal getDiferencaTributos() {
		if ((getCustoTributosKm() == null || getCustoTributosKm().equals(BigDecimal.ZERO))
				|| (getTributosSistema() == null) || getTributosSistema().equals(BigDecimal.ZERO)) {
			return BigDecimal.ONE;
		} else {
			try {
				return getCustoTributosKm().divide(getTributosSistema(), RoundingMode.CEILING).subtract(BigDecimal.ONE)
						.abs();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getDespesasSistema() {
		return despesasSistema;
	}

	public void setDespesasSistema(BigDecimal despesasSistema) {
		this.despesasSistema = despesasSistema;
	}

	public BigDecimal getDiferencaDespesas() {

		try {
			return getDespesasKm().divide(getDespesasSistema(), RoundingMode.CEILING).subtract(BigDecimal.ONE).abs();
		} catch (Exception e) {
			return BigDecimal.ONE;
		}

	}

	public void setDiferencaDespesas(BigDecimal diferencaDespesas) {
		this.diferencaDespesas = diferencaDespesas;
	}

	public void setDiferencaTributos(BigDecimal diferencaTributos) {
		this.diferencaTributos = diferencaTributos;
	}

	public BigDecimal getDiferencaPecas() {

		try {
			return getCustoPecasKm().divide(getCustoPecasSistema(), RoundingMode.CEILING).subtract(BigDecimal.ONE)
					.abs();
		} catch (Exception e) {
			return BigDecimal.ONE;
		}

	}

	public void setDiferencaPecas(BigDecimal diferencaPecas) {
		this.diferencaPecas = diferencaPecas;
	}

	public BigDecimal getCustoTributosKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getQuilometragemTotal() == null) {
			return null;
		}
		BigDecimal custoKm = BigDecimal.ZERO;
		for (DespesaGeral t : getTributos()) {
			custoKm = custoKm.add(t.getValor());
		}
		try {
			return custoKm.divide(getQuilometragemTotal(), new MathContext(8, RoundingMode.HALF_EVEN));
		} catch (Exception e) {
			return null;
		}
	}

	public BigDecimal getCustoPecasKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getQuilometragemTotal() == null) {
			return null;
		}
		BigDecimal custoKm = BigDecimal.ZERO;
		for (Material t : getPecasServicos()) {
			custoKm = custoKm.add(t.getValor());
		}
		for (Servico t : getServicos()) {
			custoKm = custoKm.add(t.getValor());
		}
		try {
			return custoKm.divide(getQuilometragemTotal(), new MathContext(8, RoundingMode.HALF_EVEN));
		} catch (

		Exception e) {
			return null;
		}
	}

	public BigDecimal getDespesasKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getQuilometragemTotal() == null) {
			return null;
		}
		BigDecimal custoKm = BigDecimal.ZERO;
		for (DespesaGeral t : getDespesasAdm()) {
			custoKm = custoKm.add(t.getValor());
		}
		try {
			return custoKm.divide(getQuilometragemTotal(), new MathContext(8, RoundingMode.HALF_EVEN));
		} catch (Exception e) {
			return null;
		}
	}

	public BigDecimal getCustoPecasSistema() {
		return custoPecasSistema;
	}

	public void setCustoPecasSistema(BigDecimal custoPecasSistema) {
		this.custoPecasSistema = custoPecasSistema;
	}

	public List<DespesaGeral> getTributos() {
		return tributos;
	}

	public void setTributos(List<DespesaGeral> tributos) {
		this.tributos = tributos;
	}

	public List<DespesaGeral> getDespesasAdm() {
		return despesasAdm;
	}

	public void setDespesasAdm(List<DespesaGeral> despesasAdm) {
		this.despesasAdm = despesasAdm;
	}

	public BigDecimal getCustoRodagemKmSistema() {
		return custoRodagemKmSistema;
	}

	public void setCustoRodagemKmSistema(BigDecimal custoRodagemKmSistema) {
		this.custoRodagemKmSistema = custoRodagemKmSistema;
	}

	public BigDecimal getDiferencaRodagem() {

		try {
			return getCustoRodagemKm().divide(getCustoRodagemKmSistema(), RoundingMode.CEILING).subtract(BigDecimal.ONE)
					.abs();
		} catch (Exception e) {
			return BigDecimal.ONE;
		}

	}

	public StatusBoletim getStatus() {
		return status;
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Integer getElementos() {
		return elementos;
	}

	public void setElementos(Integer elementos) {
		this.elementos = elementos;
	}

	public void setCustoRodagemKm(BigDecimal custoRodagem) {
		this.custoRodagemKm = custoRodagem;
	}

	public void setStatus(StatusBoletim status) {
		this.status = status;
	}

	public List<DespesaGeral> getValePlano() {
		return valePlano;
	}

	public void setValePlano(List<DespesaGeral> valePlano) {
		this.valePlano = valePlano;
	}

	public List<Material> getCombusLubrif() {
		return combusLubrif;
	}

	public void setCombusLubrif(List<Material> combusLubrif) {
		this.combusLubrif = combusLubrif;
	}

	public List<Material> getPecasServicos() {
		return pecasServicos;
	}

	public void setPecasServicos(List<Material> pecasServicos) {
		this.pecasServicos = pecasServicos;
	}

	public List<Material> getRodagem() {
		return rodagem;
	}

	public void setRodagem(List<Material> rodagem) {
		this.rodagem = rodagem;
	}

	public BigDecimal getCustoRodagemKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return getCustoRodagem().divide(getQuilometragemTotal(), 6, RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getCustoVariavel() {
		custoVariavel = BigDecimal.ZERO;
		for (Material m : getMateriais()) {
			if (m.getTipoMaterial().getCategoriaMaterial().getNumero().equals(COMBUSTIVEL)
					|| m.getTipoMaterial().getCategoriaMaterial().getNumero().equals(LUBRIFICANTES)
					|| m.getTipoMaterial().getCategoriaMaterial().getNumero().equals(PECAS)
					|| m.getTipoMaterial().getCategoriaMaterial().getNumero().equals(RODAGEM)) {
				custoVariavel = custoVariavel.add(m.getValor());
			}
		}

		return custoVariavel;
	}

	public BigDecimal getCustoVariavelKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getCustoVariavel().equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return getCustoVariavel().divide(getQuilometragemTotal(), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getCustoPessoalKm() {
		if (getQuilometragemTotal() == null || getQuilometragemTotal().equals(BigDecimal.ZERO)
				|| getValorCustoPessoal().equals(BigDecimal.ZERO)) {
			return null;
		} else {
			try {
				return getValorCustoPessoal().divide(getQuilometragemTotal(), RoundingMode.CEILING);
			} catch (Exception e) {
				return null;
			}
		}
	}

	public Material getCustoPecas() {
		return custoPecas;
	}

	public void setCustoPecas(Material custoPecas) {
		this.custoPecas = custoPecas;
	}

	public CustoPessoal getCustoAdministracao() {
		return custoAdministracao;
	}

	public void setCustoAdministracao(CustoPessoal custoAdministracao) {
		this.custoAdministracao = custoAdministracao;
	}

	public CustoPessoal getCustoManutencao() {
		return custoManutencao;
	}

	public void setCustoManutencao(CustoPessoal custoManutencao) {
		this.custoManutencao = custoManutencao;
	}

	public CustoPessoal getCustoOperacao() {
		return custoOperacao;
	}

	public void setCustoOperacao(CustoPessoal custoOperacao) {
		this.custoOperacao = custoOperacao;
	}

	public CustoPessoal getCustoMotorista() {
		return custoMotorista;
	}

	public void setCustoMotorista(CustoPessoal custoMotorista) {
		this.custoMotorista = custoMotorista;
	}

	public CustoPessoal getCustoCobrador() {
		return custoCobrador;
	}

	public void setCustoCobrador(CustoPessoal custoCobrador) {
		this.custoCobrador = custoCobrador;
	}

	public CustoPessoal getCustoFiscal() {
		return custoFiscal;
	}

	public void setCustoFiscal(CustoPessoal custoFiscal) {
		this.custoFiscal = custoFiscal;
	}

	public BigDecimal getQuilometragemTotal() {
		return quilometragem_piso1.add(quilometragem_piso2).add(quilometragem_piso3);
	}

	public void setQuilometragemTotal(BigDecimal quilometragemTotal) {
		this.quilometragemTotal = quilometragemTotal;
	}

	public Integer getFrotaTotal() {
		return frotaEfetiva + frotaReserva;
	}

	public void setFrotaTotal(Integer frotaTotal) {
		this.frotaTotal = frotaTotal;
	}

	public BigDecimal getEncargoFolhaPagamento() {
		return encargoFolhaPagamento;
	}

	public void setEncargoFolhaPagamento(BigDecimal encargoFolhaPagamento) {
		this.encargoFolhaPagamento = encargoFolhaPagamento;
	}

	public Integer getFrotaEfetiva() {
		return frotaEfetiva;
	}

	public void setFrotaEfetiva(Integer frotaEfetiva) {
		this.frotaEfetiva = frotaEfetiva;
	}

	public Integer getFrotaReserva() {
		return frotaReserva;
	}

	public void setFrotaReserva(Integer frotaReserva) {
		this.frotaReserva = frotaReserva;
	}

	public BigDecimal getQuilometragem_piso1() {
		return quilometragem_piso1;
	}

	public void setQuilometragem_piso1(BigDecimal quilometragem_piso1) {
		this.quilometragem_piso1 = quilometragem_piso1;
	}

	public BigDecimal getQuilometragem_piso2() {
		return quilometragem_piso2;
	}

	public void setQuilometragem_piso2(BigDecimal quilometragem_piso2) {
		this.quilometragem_piso2 = quilometragem_piso2;
	}

	public BigDecimal getQuilometragem_piso3() {
		return quilometragem_piso3;
	}

	public void setQuilometragem_piso3(BigDecimal quilometragem_piso3) {
		this.quilometragem_piso3 = quilometragem_piso3;
	}

	public List<CustoPessoal> getCustosPessoal() {
		return custosPessoal;
	}

	public void setCustosPessoal(List<CustoPessoal> custosPessoal) {
		this.custosPessoal = custosPessoal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoServico) {
		this.tipoMovimento = tipoServico;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Date getMesAno() {
		return dataReferencia;
	}

	public void setMesAno(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public List<DespesaGeral> getDespesasGerais() {
		return despesasGerais;
	}

	public void setDespesasGerais(List<DespesaGeral> despesasGerais) {
		this.despesasGerais = despesasGerais;
	}

	public List<FrotaApoio> getFrotasApoio() {
		return frotasApoio;
	}

	public void setFrotasApoio(List<FrotaApoio> frotasApoio) {
		this.frotasApoio = frotasApoio;
	}

	public List<Material> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}

	public List<Quilometragem> getQuilometragens() {
		return quilometragens;
	}

	public void setQuilometragens(List<Quilometragem> quilometagens) {
		this.quilometragens = quilometagens;
	}

	public List<ReceitaImobilizado> getReceitasImobilizado() {
		return receitasImobilizado;
	}

	public void setReceitasImobilizado(List<ReceitaImobilizado> receitasImobilizado) {
		this.receitasImobilizado = receitasImobilizado;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	@Override
	public Boletim clone() throws CloneNotSupportedException {
		Boletim retorno = null;
		try {
			retorno = (Boletim) super.clone();
			retorno.id = null;
			retorno.setMateriais(new ArrayList<Material>());

			retorno.setCustosPessoal(new ArrayList<CustoPessoal>());
			for (CustoPessoal m : getCustosPessoal()) {
				CustoPessoal mc = (CustoPessoal) m.clone();
				mc.setBoletim(retorno);
				mc.setId(null);
				
				mc.setEncargosSociais(BigDecimal.ZERO);
				mc.setHorasExtras(BigDecimal.ZERO);
				mc.setPremios(BigDecimal.ZERO);
				mc.setOutrosPagamentos(BigDecimal.ZERO);
				
				retorno.getCustosPessoal().add(mc);
			}

			retorno.setDespesasGerais(new ArrayList<DespesaGeral>());
			for (DespesaGeral m : getDespesasGerais()) {
				DespesaGeral mc = (DespesaGeral) m.clone();
				mc.setBoletim(retorno);
				mc.setId(null);
				//mc.setValor(BigDecimal.ZERO);
				//mc.setValor(mc.getValor().multiply(getAleatorio()));
				retorno.getDespesasGerais().add(mc);
			}

			retorno.setReceitasImobilizado(new ArrayList<ReceitaImobilizado>());
			for (ReceitaImobilizado m : getReceitasImobilizado()) {

				ReceitaImobilizado mc = (ReceitaImobilizado) m.clone();
				mc.setBoletim(retorno);
				mc.setDescricao(null);
			//	mc.setValor(BigDecimal.ZERO);
				mc.setId(null);
				retorno.getReceitasImobilizado().add(mc);

			}

			retorno.setFrotasApoio(new ArrayList<FrotaApoio>());
			for (FrotaApoio m : getFrotasApoio()) {
				FrotaApoio mc = (FrotaApoio) m.clone();
				mc.setBoletim(retorno);
				mc.setQuilometragem(BigDecimal.ZERO);
				mc.setValor(BigDecimal.ZERO);
				mc.setId(null);
				retorno.getFrotasApoio().add(mc);
			}

			for (Material m : getMateriais()) {
				Material mc = (Material) m.clone();
				
				mc.setPreco(BigDecimal.ZERO);
				mc.setQuantidade(BigDecimal.ZERO);
				mc.setValor(BigDecimal.ZERO);
				
				mc.setBoletim(retorno);
				mc.setId(null);
				retorno.getMateriais().add(mc);
			}

			retorno.setServicos(new ArrayList<Servico>());
			for (Servico m : getServicos()) {
				Servico mc = (Servico) m.clone();
				mc.setBoletim(retorno);
				mc.setQuantidade(BigDecimal.ZERO);
				mc.setValor(BigDecimal.ZERO);
				mc.setDescricao(null);
				mc.setId(null);
				retorno.getServicos().add(mc);
			}

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());
			;
		}

		return retorno;
	}

	public void setCustoLubrificante(BigDecimal divide) {
		// TODO Auto-generated method stub

	}

}
