package com.bhcontrole.service;

import com.bhcontrole.model.Funcao;

public interface FuncaoService extends GenericService<Funcao, Long>{
	Funcao findByTipo(String tipo);
	Funcao findById(Long id);
}
