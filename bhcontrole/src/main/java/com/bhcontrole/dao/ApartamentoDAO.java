package com.bhcontrole.dao;

import com.bhcontrole.model.Apartamento;

public interface ApartamentoDAO extends GenericDAO<Apartamento, Long> {
	public Apartamento findByNumero(int numero);

}
