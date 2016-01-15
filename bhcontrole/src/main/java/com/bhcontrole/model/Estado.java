package com.bhcontrole.model;

public enum Estado {
	ATIVO("Ativo"), INATIVO("Inativo"), DELETADO("Deletado"), BLOQUEADO("Bloqueado");

	private String estado;

	private Estado(final String estado){
        this.estado = estado;
    }

	public String getEstado() {
		return this.estado;
	}

	@Override
	public String toString() {
		return this.estado;
	}

	public String getName() {
		return this.name();
	}
}
