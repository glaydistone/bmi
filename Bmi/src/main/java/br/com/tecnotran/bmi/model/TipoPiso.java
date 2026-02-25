package br.com.tecnotran.bmi.model;

public enum TipoPiso {
	TIPO1("Piso Tipo I"),
	TIPO2("Piso Tipo II"),
	TIPO3("Piso Tipo III"),;
	
private String descricao;
	
	TipoPiso(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
