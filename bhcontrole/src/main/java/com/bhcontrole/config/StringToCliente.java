package com.bhcontrole.config;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bhcontrole.model.Cliente;
import com.bhcontrole.service.ClienteService;

@Component
public class StringToCliente extends PropertyEditorSupport{
	
	@Autowired
	private ClienteService clienteService;
	
	public void setAsText(String text){
		Cliente c = this.clienteService.find(Long.valueOf(text));
		
		this.setValue(c);
	}

}
