package com.bhcontrole.dao;

import com.bhcontrole.model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Long>{
	Usuario findByLogin(String login);
}
