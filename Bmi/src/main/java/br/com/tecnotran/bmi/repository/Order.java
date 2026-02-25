package br.com.tecnotran.bmi.repository;

public enum Order {
	ASC, DESC;

	public boolean isAscending() {
		return ASC.equals(this);
	}

}
