package com.bhcontrole.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "DESPESA")
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "VALOR", scale = 2)
	@DecimalMin("0.05")
	private BigDecimal valor;

	@Column(name = "TIPO", length = 15, nullable = false)
	private String tipo;

	@Column(name = "DATA_DESPESA")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private Calendar dataDaDespesa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "DESPESA_USUARIO", joinColumns = { @JoinColumn(name = "ID_DESPESA") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_USUARIO") })
	private Usuario usuarioQueCadastrou;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Calendar getDataDaDespesa() {
		return dataDaDespesa;
	}

	public void setDataDaDespesa(Calendar dataDaDespesa) {
		this.dataDaDespesa = dataDaDespesa;
	}

	public Usuario getUsuarioQueCadastrou() {
		return usuarioQueCadastrou;
	}

	public void setUsuarioQueCadastrou(Usuario usuarioQueCadastrou) {
		this.usuarioQueCadastrou = usuarioQueCadastrou;
	}

	@Override
	public String toString() {
		return "Despesa [id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", tipo=" + tipo
				+ ", dataDaDespesa=" + dataDaDespesa + ", usuarioQueCadastrou=" + usuarioQueCadastrou + "]";
	}

}
