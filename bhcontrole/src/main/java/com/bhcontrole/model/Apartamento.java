package com.bhcontrole.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "APARTAMENTO")
public class Apartamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NUMERO")
	@Min(1)
	private int numero;

	@Column(name = "ESTADO")
	@NotEmpty
	private String estado = EstadoApartamento.DISPONIVEL.getEstado();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNumeroComEstado(){
		return ""+this.numero+" "+this.estado;
	}

	@Override
	public String toString() {
		return "Apartamento [id=" + id + ", numero=" + numero + ", estado=" + estado + "]";
	}

}
