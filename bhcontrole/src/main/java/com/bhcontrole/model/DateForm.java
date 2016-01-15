package com.bhcontrole.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DateForm {

	@NotNull(message = "{data.obrigatoria}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataInicial;
	
	@NotNull(message = "{data.obrigatoria}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataFinal;
	
	@NotNull(message = "{cliente.obrigatorio}")
	private Cliente cliente;
	
	private String formato = "PDF";

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "DateForm [dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", cliente=" + cliente
				+ ", formato=" + formato + "]";
	}

}
