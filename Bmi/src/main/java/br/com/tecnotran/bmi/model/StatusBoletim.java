package br.com.tecnotran.bmi.model;

public enum StatusBoletim {
	NC("Não consistido"), IC("Com erros/avisos"), CO("Sem erros/avisos");

	private String descricao;

	StatusBoletim(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
