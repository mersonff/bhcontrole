package com.bhcontrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.ClienteDAO;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.service.ClienteService;

@Service
@Transactional
public class ClienteServiceImpl extends GenericServiceImpl<Cliente, Long> implements ClienteService{
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Override
	public Cliente findByCpf(String cpf) {
		return clienteDAO.findByCpf(cpf);
	}

}
