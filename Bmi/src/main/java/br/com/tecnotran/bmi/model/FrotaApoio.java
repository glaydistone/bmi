package br.com.tecnotran.bmi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
@Table(name = "frota_apoio")
public class FrotaApoio extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_frota_apoio")
	private Long id;

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@ManyToOne
	@JoinColumn(name = "id_tipo_veiculo_apoio", nullable = false, foreignKey = @ForeignKey(name = "FK_FrtApoio_tipoVeicApoio"))
	private TipoVeiculoApoio tipoVeiculoApoio;

	private BigDecimal valor;
	private BigDecimal quilometragem;

	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false, foreignKey = @ForeignKey(name = "FK_dspGeral_boletim"))
	private Boletim boletim;

	@Transient
	private BigDecimal custoQuilometro;

	public TipoVeiculoApoio getTipoVeiculoApoio() {
		return tipoVeiculoApoio;
	}

	public BigDecimal getCustoQuilometro() {
		if (getQuilometragem() == null || getQuilometragem().equals(BigDecimal.ZERO)) {
			return null;
		} else {
			if ((getValor() == null || getValor().equals(BigDecimal.ZERO))) {
				return BigDecimal.ZERO;
			} else {
			try {
				return getValor().divide(getQuilometragem(), RoundingMode.FLOOR);
			} catch (Exception e) {
				return null;
			}
		}}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipoVeiculoApoio(TipoVeiculoApoio tipoVeiculoApoio) {
		this.tipoVeiculoApoio = tipoVeiculoApoio;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

}
