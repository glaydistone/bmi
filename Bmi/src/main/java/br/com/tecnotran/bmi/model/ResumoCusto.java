package br.com.tecnotran.bmi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResumoCusto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Empresa empresa;
	private Date dataReferencia;
	private TipoMovimento tipoMovimento	;
	private BigDecimal frota = BigDecimal.ZERO;
	private BigDecimal quilometragem = BigDecimal.ZERO;
	private BigDecimal custoVarivel = BigDecimal.ZERO;
	private BigDecimal custoPessoal = BigDecimal.ZERO;
	private BigDecimal despesas = BigDecimal.ZERO;
	private BigDecimal custoServicos = BigDecimal.ZERO;
	private BigDecimal custoFrotaApoio = BigDecimal.ZERO;
	private BigDecimal custoTotal = BigDecimal.ZERO;
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Date getDataReferencia() {
		return dataReferencia;
	}
	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoServico) {
		this.tipoMovimento = tipoServico;
	}
	public BigDecimal getFrota() {
		return frota;
	}
	public void setFrota(BigDecimal frota) {
		this.frota = frota;
	}
	public BigDecimal getQuilometragem() {
		return quilometragem;
	}
	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}
	public BigDecimal getCustoVarivel() {
		return custoVarivel;
	}
	public void setCustoVarivel(BigDecimal custoVarivel) {
		this.custoVarivel = custoVarivel;
	}
	public BigDecimal getCustoPessoal() {
		return custoPessoal;
	}
	public void setCustoPessoal(BigDecimal custoPessoal) {
		this.custoPessoal = custoPessoal;
	}
	public BigDecimal getDespesas() {
		return despesas;
	}
	public void setDespesas(BigDecimal despesas) {
		this.despesas = despesas;
	}
	public BigDecimal getCustoServicos() {
		return custoServicos;
	}
	public void setCustoServicos(BigDecimal custoServicos) {
		this.custoServicos = custoServicos;
	}
	public BigDecimal getCustoFrotaApoio() {
		return custoFrotaApoio;
	}
	public void setCustoFrotaApoio(BigDecimal custoFrotaApoio) {
		this.custoFrotaApoio = custoFrotaApoio;
	}
	public BigDecimal getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}

	

}
