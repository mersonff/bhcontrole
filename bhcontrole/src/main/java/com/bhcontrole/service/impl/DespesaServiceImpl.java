package com.bhcontrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.DespesaDAO;
import com.bhcontrole.model.Despesa;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.service.DespesaService;

@Service
@Transactional
public class DespesaServiceImpl extends GenericServiceImpl<Despesa, Long> implements DespesaService{
	
	@Autowired
	private DespesaDAO dao;
	
	@Override
	public Hospedagem findHospedagemByDespesa(Despesa despesa) {
		return dao.findHospedagemByDespesa(despesa);
	}
	
}
