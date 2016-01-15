package com.bhcontrole.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.ClienteDAO;
import com.bhcontrole.model.Cliente;

@Repository
public class ClienteDAOImpl extends GenericDAOImpl<Cliente, Long> implements ClienteDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Cliente findByCpf(String cpf) {
		TypedQuery<Cliente> query = em.createQuery("select c from Cliente c where c.cpf= :cpf", Cliente.class)
				.setParameter("cpf", cpf);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
