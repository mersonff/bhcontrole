package com.bhcontrole.dao;

import com.bhcontrole.model.Despesa;
import com.bhcontrole.model.Hospedagem;

public interface DespesaDAO extends GenericDAO<Despesa, Long>{
	public Hospedagem findHospedagemByDespesa(Despesa despesa);
}
