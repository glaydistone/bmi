package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="despesa_geral")
public class DespesaGeral extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_despesa_geral")
	private Long id;
	
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_despesa", nullable = false, foreignKey = @ForeignKey(name="FK_dspGeral_tipoDsp"))
	private TipoDespesa tipoDespesa;
	
	private BigDecimal valor;
	
	@Column(name="fator_ajuste")
	private BigDecimal fatorAjuste = BigDecimal.ONE;
	
	public BigDecimal getFatorAjuste() {
		//return fatorAjuste;  // retornar 1 até avaliar melhor essa alternativa
		return BigDecimal.ONE;
	}

	public void setFatorAjuste(BigDecimal fatorAjuste) {
		this.fatorAjuste = fatorAjuste;
	}

	@Column(name = "descricao", length = 80, nullable = true)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name="FK_dspGeral_boletim"))
	private Boletim boletim;
	
	@Transient
	private BigDecimal valorPlanilha;
	
	@Transient
	private BigDecimal valorUnitario;
	
	@Transient
	private BigDecimal diferencaValor;
	
	

	public BigDecimal getDiferencaValor() {
		if (getValorPlanilha().equals(BigDecimal.ZERO)) {
			return BigDecimal.ONE; 
		} else {
			try {
				return getValorUnitario().divide(getValorPlanilha(),RoundingMode.CEILING).subtract(BigDecimal.ONE).abs();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getValorUnitario() {
		
		if (getBoletim() == null || getBoletim().getElementos() == null || getBoletim().getElementos() == 0) {
			return null;
		} else {
			try {
				return new BigDecimal(getValor().doubleValue()/ getBoletim().getElementos().doubleValue());
			} catch (Exception e) {
				return null;
			}
		}
	}

	public String getLimitePlanilha() {
		if (getValorPlanilha() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat( "#0.00" );
			String retorno = df.format(getValorPlanilha().multiply(new BigDecimal(0.99)));
			retorno = retorno +" a "+df.format(getValorPlanilha().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getValorPlanilha());
			///
			return retorno;
		}
	}

	public BigDecimal getValorPlanilha() {
		return valorPlanilha.multiply(getFatorAjuste());
	}

	public void setValorPlanilha(BigDecimal valorPlanilha) {
		this.valorPlanilha = valorPlanilha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
