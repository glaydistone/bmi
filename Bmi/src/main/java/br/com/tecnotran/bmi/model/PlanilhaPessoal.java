package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author TONE_
 * Parâmetros do sistema aplicáveis por tipo de movimento
 *
 */
@Entity
@Table(name="planilha_pessoal")
public class PlanilhaPessoal extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id_planilha_pessoal")
	private Long id;


	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private TipoMovimento tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name = "id_planilha_preco", nullable = false, foreignKey = @ForeignKey(name="FK_material_preco"))
	private PlanilhaPadrao planilhaPreco;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal valeAlimentacao = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal planoSaude = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal taxaEncargos = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioMotorista = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalMotorista = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioCobrador = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalCobrador = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioFiscal = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalFiscal = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioAdministracao = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalAdministracao = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioManutencao = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalManutencao = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal salarioOutros = BigDecimal.ZERO;

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal adicionalOutros = BigDecimal.ZERO;
	

	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuMotorista = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuCobrador = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuFiscal = BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuOutros= BigDecimal.ZERO;
	
	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuAdministracao = BigDecimal.ZERO;
	
	public BigDecimal getSalarioOutros() {
		return salarioOutros;
	}


	public void setSalarioOutros(BigDecimal salarioOutros) {
		this.salarioOutros = salarioOutros;
	}


	public BigDecimal getAdicionalOutros() {
		return adicionalOutros;
	}


	public void setAdicionalOutros(BigDecimal adicionalOutros) {
		this.adicionalOutros = adicionalOutros;
	}


	public BigDecimal getFuOutros() {
		return fuOutros;
	}


	public void setFuOutros(BigDecimal fuOutros) {
		this.fuOutros = fuOutros;
	}



	
	public BigDecimal getFuMotorista() {
		return fuMotorista;
	}


	public void setFuMotorista(BigDecimal fuMotorista) {
		this.fuMotorista = fuMotorista;
	}


	public BigDecimal getFuCobrador() {
		return fuCobrador;
	}


	public void setFuCobrador(BigDecimal fuCobrador) {
		this.fuCobrador = fuCobrador;
	}


	public BigDecimal getFuFiscal() {
		return fuFiscal;
	}


	public void setFuFiscal(BigDecimal fuFiscal) {
		this.fuFiscal = fuFiscal;
	}


	public BigDecimal getFuAdministracao() {
		return fuAdministracao;
	}


	public void setFuAdministracao(BigDecimal fuAdministracao) {
		this.fuAdministracao = fuAdministracao;
	}


	public BigDecimal getFuManutencao() {
		return fuManutencao;
	}


	public void setFuManutencao(BigDecimal fuManutencao) {
		this.fuManutencao = fuManutencao;
	}


	@Column(length = 15, scale = 4, nullable = false)
	private BigDecimal fuManutencao = BigDecimal.ZERO;

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}


	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
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


	public BigDecimal getTaxaEncargos() {
		return taxaEncargos;
	}


	public void setTaxaEncargos(BigDecimal taxaEncargos) {
		this.taxaEncargos = taxaEncargos;
	}


	public BigDecimal getSalarioMotorista() {
		return salarioMotorista;
	}


	public void setSalarioMotorista(BigDecimal salarioMotorista) {
		this.salarioMotorista = salarioMotorista;
	}


	public BigDecimal getAdicionalMotorista() {
		return adicionalMotorista;
	}


	public void setAdicionalMotorista(BigDecimal adicionalMotorista) {
		this.adicionalMotorista = adicionalMotorista;
	}


	public BigDecimal getSalarioCobrador() {
		return salarioCobrador;
	}


	public void setSalarioCobrador(BigDecimal salarioCobrador) {
		this.salarioCobrador = salarioCobrador;
	}


	public BigDecimal getAdicionalCobrador() {
		return adicionalCobrador;
	}


	public void setAdicionalCobrador(BigDecimal adicionalCobrador) {
		this.adicionalCobrador = adicionalCobrador;
	}


	public BigDecimal getSalarioFiscal() {
		return salarioFiscal;
	}


	public void setSalarioFiscal(BigDecimal salarioFiscal) {
		this.salarioFiscal = salarioFiscal;
	}


	public BigDecimal getAdicionalFiscal() {
		return adicionalFiscal;
	}


	public void setAdicionalFiscal(BigDecimal adicionalFiscal) {
		this.adicionalFiscal = adicionalFiscal;
	}


	public BigDecimal getSalarioAdministracao() {
		return salarioAdministracao;
	}


	public void setSalarioAdministracao(BigDecimal salarioAdministracao) {
		this.salarioAdministracao = salarioAdministracao;
	}


	public BigDecimal getAdicionalAdministracao() {
		return adicionalAdministracao;
	}


	public void setAdicionalAdministracao(BigDecimal adicionalAdministracao) {
		this.adicionalAdministracao = adicionalAdministracao;
	}


	public BigDecimal getSalarioManutencao() {
		return salarioManutencao;
	}


	public void setSalarioManutencao(BigDecimal salarioManutencao) {
		this.salarioManutencao = salarioManutencao;
	}


	public BigDecimal getAdicionalManutencao() {
		return adicionalManutencao;
	}


	public void setAdicionalManutencao(BigDecimal adicionalManutencao) {
		this.adicionalManutencao = adicionalManutencao;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}


	public PlanilhaPadrao getPlanilhaPreco() {
		return planilhaPreco;
	}


	public void setPlanilhaPreco(PlanilhaPadrao planilhaPreco) {
		this.planilhaPreco = planilhaPreco;
	}

}
