package com.bhcontrole.service;

import java.util.Calendar;
import java.util.List;

import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.Cliente;

public interface HospedagemService extends GenericService<Hospedagem, Long> {
	public Hospedagem findByNomeEDataEntrada(Cliente cliente, Calendar dataEntrada);

	public List<Hospedagem> findHospedagemByClienteAndDatas(Cliente cliente, Calendar dataInicial, Calendar dataFinal);

	public List<Hospedagem> findByDatas(Calendar dataInicial, Calendar dataFinal);
}
