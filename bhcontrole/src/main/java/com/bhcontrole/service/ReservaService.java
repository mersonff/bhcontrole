package com.bhcontrole.service;

import java.util.Calendar;

import com.bhcontrole.model.Reserva;

public interface ReservaService extends GenericService<Reserva, Long> {
	public Reserva findByNomeEDataSolicitacao(String nome, Calendar dataSolicitacao);
}
