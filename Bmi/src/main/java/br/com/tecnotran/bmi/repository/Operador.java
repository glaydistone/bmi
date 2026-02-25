package br.com.tecnotran.bmi.repository;

public enum Operador {
	IGUAL(" = "),
	DIFERENTE(" != "),
	MENOR_OU_IGUAL(" < "),
	MENOR(" < "),
	CONTIDO(" % "),
	MAIOR_OU_IGUAL(" >= "),
	MAIOR(" > ");
	
private String descricao;
	
	Operador(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
