package com.bhcontrole.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.HospedagemDAO;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.model.Hospedagem;

@Repository
public class HospedagemDAOImpl extends GenericDAOImpl<Hospedagem, Long> implements HospedagemDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Hospedagem findByNomeEDataEntrada(Cliente cliente, Calendar dataEntrada) {
		TypedQuery<Hospedagem> query = em.createQuery(
				"from Hospedagem h where h.cliente= :cliente and" + " h.dataEntrada= :dataEntrada", Hospedagem.class);
		query.setParameter("cliente", cliente);
		query.setParameter("dataEntrada", dataEntrada);

		try {
			System.out.println(query.getSingleResult());
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Hospedagem> findHospedagemByClienteAndDatas(Cliente cliente, Calendar dataInicial, Calendar dataFinal) {
		TypedQuery<Hospedagem> query = em.createQuery(
				"from Hospedagem h where h.cliente= :cliente and h.dataEntrada between :dataInicial and :dataFinal",
				Hospedagem.class);
		query.setParameter("cliente", cliente);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();

	}

	@Override
	public List<Hospedagem> findByDatas(Calendar dataInicial, Calendar dataFinal) {
		TypedQuery<Hospedagem> query = em
				.createQuery("from Hospedagem where dataEntrada between :dataInicial and :dataFinal", Hospedagem.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		return query.getResultList();
	}

}
