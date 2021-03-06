package com.bhcontrole.dao;

import java.util.List;

public interface GenericDAO<E, K> {
	public void save(E entity);

	public List<E> findAll();

	public E find(K id);

	public void update(E entity);

	public void removeById(K id);

	public void remove(E entity);
}
