package com.bhcontrole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhcontrole.model.Funcao;
import com.bhcontrole.model.Usuario;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.findByLogin(login);
		System.out.println("Usuario : " + usuario);
		if (usuario == null) {
			System.out.println("Usuario não encontrado");
			throw new UsernameNotFoundException("Login não encontrado");
		}
		return new org.springframework.security.core.userdetails.User(usuario.getLogin(), usuario.getSenha(),
				usuario.getEstado().equals("Ativo"), true, true, true, getGrantedAuthorities(usuario));
	}

	private List<GrantedAuthority> getGrantedAuthorities(Usuario usuario) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Funcao funcao : usuario.getFuncoes()) {
			System.out.println("Funcao : " + funcao);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + funcao.getTipo()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}

}
