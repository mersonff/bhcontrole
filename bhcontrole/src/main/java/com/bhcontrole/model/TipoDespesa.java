package com.bhcontrole.model;

public enum TipoDespesa {
	BAR("Bar"), RESTAURANTE("Restaurante"), FRIGOBAR("Frigobar"), OUTROS("Outros");

	String tipoDespesa;

	private TipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public String getTipoDespesa() {
		return tipoDespesa;
	}
}
