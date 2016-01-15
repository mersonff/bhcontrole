package com.bhcontrole.model;

public enum EstadoReserva {
	EMABERTO("Em aberto"), CONCLUIDO("Concluído"), CANCELADA("Cancelada");
	
	private String estado;
	
	private EstadoReserva(final String estado){
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
