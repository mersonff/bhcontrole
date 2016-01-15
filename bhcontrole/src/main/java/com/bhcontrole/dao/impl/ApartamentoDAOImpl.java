package com.bhcontrole.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.ApartamentoDAO;
import com.bhcontrole.model.Apartamento;

@Repository
public class ApartamentoDAOImpl extends GenericDAOImpl<Apartamento, Long> implements ApartamentoDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Apartamento> findAll() {
		TypedQuery<Apartamento> query = em.createQuery("FROM Apartamento ORDER BY numero", Apartamento.class);
		return query.getResultList();

	}

	@Override
	public Apartamento findByNumero(int numero) {
		TypedQuery<Apartamento> query = em.createQuery("select a from Apartamento a where a.numero= :numero",
				Apartamento.class);
		query.setParameter("numero", numero);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
