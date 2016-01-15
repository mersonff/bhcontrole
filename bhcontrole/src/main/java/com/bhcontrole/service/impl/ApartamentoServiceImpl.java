package com.bhcontrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.ApartamentoDAO;
import com.bhcontrole.model.Apartamento;
import com.bhcontrole.service.ApartamentoService;

@Service
@Transactional
public class ApartamentoServiceImpl extends GenericServiceImpl<Apartamento, Long> implements ApartamentoService{
	
	@Autowired
	private ApartamentoDAO dao;
	
	@Override
	public Apartamento findByNumero(int numero) {
		return dao.findByNumero(numero);
	}
}
