package com.bhcontrole.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.UsuarioDAO;
import com.bhcontrole.model.Usuario;

@Repository
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario, Long> implements UsuarioDAO{
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Usuario findByLogin(String login) {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.login= :login", Usuario.class)
				.setParameter("login", login);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
