package com.bhcontrole.service;

import com.bhcontrole.model.Apartamento;

public interface ApartamentoService extends GenericService<Apartamento, Long> {
	public Apartamento findByNumero(int numero);
}
