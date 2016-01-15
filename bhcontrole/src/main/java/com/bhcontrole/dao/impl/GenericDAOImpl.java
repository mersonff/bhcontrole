package com.bhcontrole.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bhcontrole.dao.GenericDAO;

@Repository
public abstract class GenericDAOImpl<E, K> implements GenericDAO<E, K> {

	private Class<E> clazz;

	@PersistenceContext
	private EntityManager em;

	public GenericDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		clazz = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(E entity) {
		em.persist(entity);
	}

	@Override
	public List<E> findAll() {
		return em.createQuery("FROM " + clazz.getName()).getResultList();
	}

	@Override
	public E find(K id) {
		return em.find(clazz, id);
	}

	@Override
	public void update(E entity) {
		em.merge(entity);
	}

	public void removeById(K id) {
		E entity = find(id);
		em.remove(entity);
	}

	@Override
	public void remove(E entity) {
		em.remove(entity);
	}

}
