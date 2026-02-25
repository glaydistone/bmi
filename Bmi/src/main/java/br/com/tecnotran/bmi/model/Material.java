package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.math.MathContext;
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
@Table(name = "material")
public class Material extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_material")
	private Long id;

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "id_tipo_material", nullable = false, foreignKey = @ForeignKey(name = "FK_material_tipoMaterial"))
	private TipoMaterial tipoMaterial;

	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name = "FK_dspGeral_boletim"))
	private Boletim boletim;

	private BigDecimal valor = BigDecimal.ZERO;
	
	@Column(name="fator_ajuste")
	private BigDecimal fatorAjuste = BigDecimal.ONE;
	public BigDecimal getFatorAjuste() {
		//return fatorAjuste;  // retornar 1 até avaliar melhor essa alternativa
		return BigDecimal.ONE;
	}

	public void setFatorAjuste(BigDecimal fatorAjuste) {
		this.fatorAjuste = fatorAjuste;
	}

	private BigDecimal quantidade = BigDecimal.ZERO;
	
	@Transient
	private String limiteConsumoPlanilha;
	@Transient
	private String limitePrecoPlanilha;
	@Transient
	private String limiteCustoPlanilha;

	public String getLimiteConsumoPlanilha() {
		if (getIndiceConsumoPlanilha() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat( "#0.00000" );
			String retorno = df.format(getIndiceConsumoPlanilha().multiply(new BigDecimal(0.99)));
	//		retorno = retorno +" a "+df.format(getIndiceConsumoPlanilha().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getIndiceConsumoPlanilha());
			/// 
			return retorno;
		}
	}

	public String getLimiteCustoPlanilha() {
		if (getCustoQuilometroPlanilha() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat( "#0.000" );
			String retorno = df.format(getCustoQuilometroPlanilha().multiply(new BigDecimal(0.99)));
			retorno = retorno +" a "+df.format(getCustoQuilometroPlanilha().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getCustoQuilometroPlanilha());
			/// 
			return retorno;
		}
	}
	
	public String getLimitePrecoPlanilha() {
		if (getPrecoPlanilha() == null) {
			return null;
		} else {
			DecimalFormat df = new DecimalFormat( "#0.000" );
			String retorno = df.format(getPrecoPlanilha().multiply(new BigDecimal(0.99)));
			retorno = retorno +" a "+df.format(getPrecoPlanilha().multiply(new BigDecimal(1.015)));
			/// Manter assim até reavaliar
			retorno = df.format(getPrecoPlanilha());
			/// 
			return retorno;
		}
	}

	@Transient
	private BigDecimal indiceConsumo = BigDecimal.ZERO;
	@Transient
	private BigDecimal preco = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoQuilometro = BigDecimal.ZERO;
	@Transient
	private BigDecimal indiceConsumoPlanilha = BigDecimal.ZERO;
	@Transient
	private BigDecimal precoPlanilha = BigDecimal.ZERO;
	@Transient
	private BigDecimal custoQuilometroPlanilha = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaPreco = BigDecimal.ZERO;

	@Transient
	private BigDecimal diferencaCusto = BigDecimal.ZERO;
	@Transient
	private BigDecimal diferencaIndice = BigDecimal.ZERO;

	public BigDecimal getDiferencaPreco() {
		if (getPrecoPlanilha().equals(BigDecimal.ZERO)) {
			return BigDecimal.ONE;
		} else {
			try {
				return getPreco().divide(getPrecoPlanilha(), RoundingMode.CEILING).subtract(BigDecimal.ONE).abs();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getDiferencaCusto() {
		if (getCustoQuilometroPlanilha().equals(BigDecimal.ZERO)) {
			return BigDecimal.ONE;
		} else {
			try {
				return getCustoQuilometro().divide(getCustoQuilometroPlanilha(), RoundingMode.CEILING)
						.subtract(BigDecimal.ONE).abs();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getDiferencaIndice() {
		if (getIndiceConsumoPlanilha().equals(BigDecimal.ZERO)) {
			return BigDecimal.ONE;
		} else {
			try {
				return getIndiceConsumo().divide(getIndiceConsumoPlanilha(), RoundingMode.CEILING).subtract(BigDecimal.ONE)
						.abs();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public BigDecimal getCustoQuilometro() {
		if (getBoletim().getQuilometragemTotal() == null
				|| getBoletim().getQuilometragemTotal().equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		} else {
			try {
				return getValor().divide(getBoletim().getQuilometragemTotal(),
						new MathContext(8, RoundingMode.CEILING));
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		}
	}

	public void setCustoQuilometro(BigDecimal custoQuilometro) {
		this.custoQuilometro = custoQuilometro;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCustoMinimo() {
		return custoQuilometro;
	}

	public void setCustoMinimo(BigDecimal custoQuilometro) {
		this.custoQuilometro = custoQuilometro;
	}

	public BigDecimal getIndiceConsumoPlanilha() {
		return indiceConsumoPlanilha.multiply(getFatorAjuste());
	}

	public void setIndiceConsumoPlanilha(BigDecimal indiceConsumoSistema) {
		this.indiceConsumoPlanilha = indiceConsumoSistema;
	}

	public BigDecimal getPrecoPlanilha() {
		return precoPlanilha.multiply(getFatorAjuste());
	}

	public void setPrecoPlanilha(BigDecimal precoSistema) {
		this.precoPlanilha = precoSistema;
	}

	public BigDecimal getCustoQuilometroPlanilha() {
		return custoQuilometroPlanilha;
	}

	public void setCustoQuilometroPlanilha(BigDecimal custoQuilometroSistema) {
		this.custoQuilometroPlanilha = custoQuilometroSistema;
	}

	public BigDecimal getIndiceConsumo() {
		if (getBoletim().getQuilometragemTotal() == null
				|| getBoletim().getQuilometragemTotal().equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		} else {
			try {
				return getQuantidade().divide(getBoletim().getQuilometragemTotal(),
						new MathContext(8, RoundingMode.HALF_EVEN));
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		}

	}

	public void setIndiceConsumo(BigDecimal indiceConsumo) {
		this.indiceConsumo = indiceConsumo;
	}

	public BigDecimal getPreco() {
		if (getQuantidade().compareTo(BigDecimal.ZERO) > 0) {
			try {
				return (getValor().divide(getQuantidade(), 6, RoundingMode.HALF_EVEN));
			} catch (Exception e) {
				return null;
			}
		} else {
			return BigDecimal.ZERO;
		}

	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
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

	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

}
