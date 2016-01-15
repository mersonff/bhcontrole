package com.bhcontrole.dao;

import java.util.Calendar;
import java.util.List;

import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.Cliente;

public interface HospedagemDAO extends GenericDAO<Hospedagem, Long>{
	
	public Hospedagem findByNomeEDataEntrada(Cliente cliente, Calendar dataEntrada);

	public List<Hospedagem> findHospedagemByClienteAndDatas(Cliente cliente, Calendar dataInicial, Calendar dataFinal);

	public List<Hospedagem> findByDatas(Calendar dataInicial, Calendar dataFinal);

}
