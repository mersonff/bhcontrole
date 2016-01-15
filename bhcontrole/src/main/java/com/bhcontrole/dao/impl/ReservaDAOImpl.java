package com.bhcontrole.dao.impl;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.ReservaDAO;
import com.bhcontrole.model.Reserva;

@Repository
public class ReservaDAOImpl extends GenericDAOImpl<Reserva, Long> implements ReservaDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Reserva findByNomeEDataSolicitacao(String nome, Calendar dataEntrada) {
		TypedQuery<Reserva> query = em.createQuery("select r from Reserva r where r.nome= :nome "
				+ "and r.dataEntrada= :dataEntrada", 
				Reserva.class);
		query.setParameter("nome", nome);
		query.setParameter("dataEntrada", dataEntrada);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
