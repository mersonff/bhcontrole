package com.bhcontrole.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.ReservaDAO;
import com.bhcontrole.model.Reserva;
import com.bhcontrole.service.ReservaService;

@Service
@Transactional
public class ReservaServiceImpl extends GenericServiceImpl<Reserva, Long> implements ReservaService{
	
	@Autowired
	private ReservaDAO dao;
	
	@Override
	public Reserva findByNomeEDataSolicitacao(String nome, Calendar dataSolicitacao) {
		return dao.findByNomeEDataSolicitacao(nome, dataSolicitacao);
	}

}
