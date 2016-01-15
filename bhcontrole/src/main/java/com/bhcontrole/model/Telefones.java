package com.bhcontrole.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name="TELEFONES")
public class Telefones {

	@Column(name = "TELEFONE_FIXO")
	private String telefoneFixo;

	@Column(name = "TELEFONE_CELULAR")
	private String telefoneCelular;

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	@Override
	public String toString() {
		return "Telefones [telefoneFixo=" + telefoneFixo + ", telefoneCelular=" + telefoneCelular + "]";
	}

}
