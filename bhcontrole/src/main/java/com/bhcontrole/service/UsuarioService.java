package com.bhcontrole.service;

import com.bhcontrole.model.Usuario;

public interface UsuarioService extends GenericService<Usuario, Long>{
	Usuario findByLogin(String login);
}
