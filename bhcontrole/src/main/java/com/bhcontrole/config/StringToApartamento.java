package com.bhcontrole.config;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bhcontrole.model.Apartamento;
import com.bhcontrole.service.ApartamentoService;

@Component
public class StringToApartamento extends PropertyEditorSupport{
	
	@Autowired
	private ApartamentoService apartamentoService;
	
	public void setAsText(String text){
		Apartamento c = this.apartamentoService.find(Long.valueOf(text));
		
		this.setValue(c);
	}

}
