package com.bhcontrole.dao;

import com.bhcontrole.model.Funcao;

public interface FuncaoDAO extends GenericDAO<Funcao, Long>{
	Funcao findByTipo(String tipo);
	Funcao findById(Long id);
}
