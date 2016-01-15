package com.bhcontrole.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.HospedagemDAO;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.service.HospedagemService;

@Service
@Transactional
public class HospedagemServiceImpl extends GenericServiceImpl<Hospedagem, Long> implements HospedagemService{
	
	@Autowired
	private HospedagemDAO dao;
	
	@Override
	public Hospedagem findByNomeEDataEntrada(Cliente cliente, Calendar dataEntrada) {
		return dao.findByNomeEDataEntrada(cliente, dataEntrada);
	}
	@Override
	public List<Hospedagem> findHospedagemByClienteAndDatas(Cliente cliente, Calendar dataInicial, Calendar dataFinal) {
		return dao.findHospedagemByClienteAndDatas(cliente,dataInicial,dataFinal);
	}
	@Override
	public List<Hospedagem> findByDatas(Calendar dataInicial, Calendar dataFinal) {
		return dao.findByDatas(dataInicial, dataFinal);
	}
}
