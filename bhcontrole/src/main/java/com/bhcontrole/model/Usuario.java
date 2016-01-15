package com.bhcontrole.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USUARIO")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	@NotNull
	@Size(min = 4, max = 50, message = "{nome.error.tamanho}")
	private String nome;

	@Column(name = "TELEFONES")
	private Telefones telefones;

	@Column(name = "EMAIL")
	@NotEmpty(message="{email.obrigatorio}")
	@Email(message = "{email.invalido}")
	private String email;

	@Column(name = "LOGIN")
	@NotNull
	@Size(min = 5, max = 15, message = "{login.error.tamanho}")
	private String login;
	
	@Column(name = "SENHA")
	@NotNull
	@Size(min = 5, max = 100, message = "{senha.error.tamanho}")
	private String senha;
	
	@Column(name = "SENHA_CONFIRMACAO")
	@NotNull
	@Size(min = 5, max = 100, message = "{senha.error.tamanho}")
	private String senhaConfirmacao;

	@Column(name = "ESTADO")
	@NotEmpty
	private String estado = Estado.ATIVO.getEstado();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_FUNCAO", joinColumns = { @JoinColumn(name = "ID_USUARIO") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_FUNCAO") })
	@NotNull(message="{funcao.obrigatorio}")
	private Set<Funcao> funcoes = new HashSet<Funcao>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Telefones getTelefones() {
		return telefones;
	}

	public void setTelefones(Telefones telefones) {
		this.telefones = telefones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Set<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return funcoes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", telefones=" + telefones + ", email=" + email + ", login="
				+ login + ", senha=" + senha + ", estado=" + estado + ", funcoes=" + funcoes + "]";
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

}
