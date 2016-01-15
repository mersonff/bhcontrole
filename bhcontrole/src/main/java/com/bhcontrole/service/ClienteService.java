package com.bhcontrole.service;

import com.bhcontrole.model.Cliente;

public interface ClienteService extends GenericService<Cliente, Long> {

	Cliente findByCpf(String cpf);

}
