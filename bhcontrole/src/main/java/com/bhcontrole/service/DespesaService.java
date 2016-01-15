package com.bhcontrole.service;

import com.bhcontrole.model.Despesa;
import com.bhcontrole.model.Hospedagem;

public interface DespesaService extends GenericService<Despesa, Long>{
	public Hospedagem findHospedagemByDespesa(Despesa despesa);
}
