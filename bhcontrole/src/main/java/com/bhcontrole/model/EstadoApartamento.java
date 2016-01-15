package com.bhcontrole.model;

public enum EstadoApartamento {
	DISPONIVEL("Disponível"), SUJO("Sujo"), OCUPADO("Ocupado");
	
	private String estado;
	
	private EstadoApartamento(final String estado){
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
