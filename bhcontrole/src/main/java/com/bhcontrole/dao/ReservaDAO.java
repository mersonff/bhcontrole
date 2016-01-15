package com.bhcontrole.dao;

import java.util.Calendar;

import com.bhcontrole.model.Reserva;

public interface ReservaDAO extends GenericDAO<Reserva, Long> {
	public Reserva findByNomeEDataSolicitacao(String nome, Calendar dataSolicitacao);
}
