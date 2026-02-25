package br.com.tecnotran.bmi.model;

public enum TipoMovimento {
	SEMI_URBANO("Semi-urbano"),
	RODOVIARIO("Rodoviário");
	
private String descricao;
	
	TipoMovimento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
