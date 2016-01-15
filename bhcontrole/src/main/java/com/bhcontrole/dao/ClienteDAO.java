package com.bhcontrole.dao;

import com.bhcontrole.model.Cliente;

public interface ClienteDAO extends GenericDAO<Cliente, Long> {

	Cliente findByCpf(String cpf);

}
