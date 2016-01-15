package com.bhcontrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.FuncaoDAO;
import com.bhcontrole.model.Funcao;
import com.bhcontrole.service.FuncaoService;

@Service
@Transactional
public class FuncaoServiceImpl extends GenericServiceImpl<Funcao, Long> implements FuncaoService{

	@Autowired
	private FuncaoDAO dao;
	
	@Override
	public Funcao findByTipo(String tipo) {
		return dao.findByTipo(tipo);
	}

	@Override
	public Funcao findById(Long id) {
		return dao.findById(id);
	}

}
