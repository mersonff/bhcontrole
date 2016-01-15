package com.bhcontrole.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.GenericDAO;
import com.bhcontrole.service.GenericService;

@Service
@Transactional
public abstract class GenericServiceImpl<E,K> implements GenericService<E, K>{
	
	@Autowired
	private GenericDAO<E,K> genericDAO;

	@Override
	public void save(E entity) {
		genericDAO.save(entity);
	}

	@Override
	public List<E> findAll() {
		return genericDAO.findAll();
	}

	@Override
	public E find(K id) {
		return genericDAO.find(id);
	}

	@Override
	public void update(E entity) {
		genericDAO.update(entity);
	}

	@Override
	public void remove(E entity) {
		genericDAO.remove(entity);
	}

	@Override
	public void removeById(K id) {
		genericDAO.removeById(id);
	}

}
