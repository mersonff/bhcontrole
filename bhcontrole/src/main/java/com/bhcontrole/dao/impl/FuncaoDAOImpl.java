package com.bhcontrole.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.FuncaoDAO;
import com.bhcontrole.model.Funcao;

@Repository
public class FuncaoDAOImpl extends GenericDAOImpl<Funcao, Long> implements FuncaoDAO{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Funcao findByTipo(String tipo) {
		TypedQuery<Funcao> query = em.createQuery("select f from Funcao f where f.tipo= :tipo", Funcao.class)
				.setParameter("tipo", tipo);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Funcao findById(Long id) {
		TypedQuery<Funcao> query = em.createQuery("select f from Funcao f where f.id= :id", Funcao.class)
				.setParameter("id", id);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
