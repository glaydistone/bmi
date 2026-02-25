package br.com.tecnotran.bmi.repository;

import java.io.Serializable;

public class ClausulaFiltro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String alias;
	private String nomePropriedade;
	private TipoPropriedade tipoPropriedade;
	private Operador operador = Operador.IGUAL;
	
	public Operador getOperador() {
		return operador;
	}
	public void setOperador(Operador operador) {
		this.operador = operador;
	}
	public TipoPropriedade getTipoPropriedade() {
		return tipoPropriedade;
	}
	public void setTipoPropriedade(TipoPropriedade tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}
	private Object valorPropriedade;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getNomePropriedade() {
		return nomePropriedade;
	}
	public void setNomePropriedade(String nomePropriedade) {
		this.nomePropriedade = nomePropriedade;
	}
	public Object getValorPropriedade() {
		return valorPropriedade;
	}
	public void setValorPropriedade(Object valorPropriedade) {
		this.valorPropriedade = valorPropriedade;
	}


}
