package com.bhcontrole.model;

public enum TipoFuncao {
	ADMIN("ADMIN"), FUNCIONARIO("FUNCIONARIO");

	String tipoFuncao;

	private TipoFuncao(String tipoFuncao) {
		this.tipoFuncao = tipoFuncao;
	}

	public String getTipoFuncao() {
		return tipoFuncao;
	}

}
