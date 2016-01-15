package com.bhcontrole.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DateForm2 {

	@NotNull(message = "{data.obrigatoria}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataInicial;
	
	@NotNull(message = "{data.obrigatoria}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataFinal;

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

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "DateForm [dataInicial=" + dataInicial + ", dataFinal=" + dataFinal + ", formato=" + formato + "]";
	}

}
