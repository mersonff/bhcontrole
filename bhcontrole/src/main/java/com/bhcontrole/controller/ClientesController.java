package com.bhcontrole.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.model.Cliente;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.ValidaCPFeCNPJ;
import com.bhcontrole.service.ClienteService;
import com.bhcontrole.service.HospedagemService;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired 
	private HospedagemService hospedagemService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="clientes", allEntries=true)
	public ModelAndView salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		Cliente existe = clienteService.findByCpf(cliente.getCpf());
		
		if(!ValidaCPFeCNPJ.isCPF(cliente.getCpf())){
			result.rejectValue("cpf", "cpf.invalido");
		}

		if (existe != null) {
			result.rejectValue("cpf", "cliente.ja.existe");
		}
		
		if(!cliente.getCnpj().isEmpty() && !ValidaCPFeCNPJ.isCNPJ(cliente.getCnpj())){
			result.rejectValue("cnpj", "cnpj.invalido");
		}
		
		if (result.hasErrors()) {
			return adiciona(cliente);
		}
		
		clienteService.save(cliente);
		System.out.println("Cliente: "+cliente);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("cliente.cadastrado.sucesso",
				new String[] { cliente.getId().toString() }, locale));
		return new ModelAndView("redirect:/clientes");
	}

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value="clientes")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("clientes/lista");
		modelAndView.addObject("clientes", clienteService.findAll());
		return modelAndView;
	}

	@RequestMapping("/adiciona")
	public ModelAndView adiciona(@ModelAttribute Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("clientes/adiciona");
		return modelAndView;
	}

	@RequestMapping("/{id}/detalhes")
	public ModelAndView detalhes(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("clientes/detalhes");
		Cliente cliente = clienteService.find(id);
		modelAndView.addObject("cliente", cliente);
		return modelAndView;
	}

	@RequestMapping("/{id}/atualiza")
	public ModelAndView atualizar(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("clientes/atualiza");
		Cliente cliente = clienteService.find(id);
		if (cliente == null) {
			return new ModelAndView("redirect:/clientes");
		}
		String estado = cliente.getEndereco().getEstado();
		String cidade = cliente.getEndereco().getCidade();
		modelAndView.addObject("estado", estado);
		modelAndView.addObject("cidade", cidade);
		modelAndView.addObject("cliente", cliente);
		return modelAndView;
	}

	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	@CacheEvict(value="clientes", allEntries=true)
	public ModelAndView atualiza(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result, 
			RedirectAttributes redirectAttributes, Locale locale) {		
		Cliente existe = clienteService.findByCpf(cliente.getCpf());
		
		if (existe != null && existe.getId() != cliente.getId()) {
			result.rejectValue("cpf", "cliente.ja.existe");
		}
		
		if(!ValidaCPFeCNPJ.isCPF(cliente.getCpf())){
			result.rejectValue("cpf", "cpf.invalido");
		}
		
		if(!cliente.getCnpj().isEmpty() && !ValidaCPFeCNPJ.isCNPJ(cliente.getCnpj())){
			result.rejectValue("cnpj", "cnpj.invalido");
		}
		
		if (result.hasErrors()) {
			return new ModelAndView("clientes/atualiza");
		}
		clienteService.update(cliente);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("cliente.atualizado.sucesso",
				new String[] { cliente.getId().toString() }, locale));
		return new ModelAndView("redirect:/clientes");
	}

	@RequestMapping("/{id}/deleta")
	@CacheEvict(value="clientes", allEntries=true)
	public ModelAndView deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		Cliente cliente = clienteService.find(id);
		
		List<Hospedagem> hospedagens = hospedagemService.findAll();
		int count = 0;
		for (Hospedagem hospedagem : hospedagens) {
			if(hospedagem.getCliente() == cliente){
				count++;
			}
		}
		if(count>0){
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("cliente.deletado.error",
					new String[] { cliente.getId().toString() }, locale));
			return new ModelAndView("redirect:/clientes");
		}
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("cliente.deletado.sucesso",
				new String[] { cliente.getId().toString() }, locale));
		clienteService.removeById(id);
		return new ModelAndView("redirect:/clientes");
	}

}
