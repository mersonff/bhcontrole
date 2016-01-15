package com.bhcontrole.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.DespesaDAO;
import com.bhcontrole.model.Despesa;
import com.bhcontrole.model.Hospedagem;

@Repository
public class DespesaDAOImpl extends GenericDAOImpl<Despesa, Long> implements DespesaDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Hospedagem findHospedagemByDespesa(Despesa despesa){
		TypedQuery<Hospedagem> query = em.createQuery(
				"select h from Hospedagem h left join h.despesas d where d.id= :id ", Hospedagem.class);
		query.setParameter("id", despesa.getId());
		
		return query.getSingleResult();
	}
}
