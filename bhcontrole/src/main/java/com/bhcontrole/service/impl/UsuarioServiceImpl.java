package com.bhcontrole.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.dao.UsuarioDAO;
import com.bhcontrole.model.Usuario;
import com.bhcontrole.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long> implements UsuarioService {

	@Autowired
	private UsuarioDAO dao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void save(Usuario usuario){
		usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
		usuario.setSenhaConfirmacao(passwordEncoder.encode(usuario.getSenhaConfirmacao()));
		dao.save(usuario);
	}
	
	public void update(Usuario usuario){
		usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
		usuario.setSenhaConfirmacao(passwordEncoder.encode(usuario.getSenhaConfirmacao()));
		dao.update(usuario);
	}

	@Override
	public Usuario findByLogin(String login) {
		return dao.findByLogin(login);
	}

}
