package com.bhcontrole.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.config.StringToApartamento;
import com.bhcontrole.model.Apartamento;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.Reserva;
import com.bhcontrole.service.ApartamentoService;
import com.bhcontrole.service.ClienteService;
import com.bhcontrole.service.HospedagemService;
import com.bhcontrole.service.ReservaService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private ApartamentoService apartamentoService;
	
	@Autowired
	private ReservaService reservaService;
	
	@Autowired
	private HospedagemService hospedagemService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private StringToApartamento stringToApartamento;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Apartamento.class, this.stringToApartamento);
	}
	
	@RequestMapping(value={"/", "/home"})
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView("home");
		List<Apartamento> apartamentos = apartamentoService.findAll();
		List<Reserva> reservas = reservaService.findAll();
		List<Reserva> reservasEmAberto = new ArrayList<>();
		for(Reserva reserva: reservas){
			if(reserva.getEstado().equals("Em aberto")){
				reservasEmAberto.add(reserva);
			}
		}
		modelAndView.addObject("apartamentos", apartamentos);
		modelAndView.addObject("reservas", reservasEmAberto);
		return modelAndView;
	}
	
	@RequestMapping("/hospedagem/{id}/acoes")
	public ModelAndView abreHospedagem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale){
		
		Apartamento apartamento = apartamentoService.find(id);
		
		Hospedagem hospedagem = new Hospedagem();
		
		List<Hospedagem> hospedagens = hospedagemService.findAll();
		
		for (Hospedagem h : hospedagens) {
			if(h.getDataSaida() == null && h.getApartamento() == apartamento){
				hospedagem = h;
			}
		}
		
		if(hospedagem.getId() == null){
			if(apartamento.getEstado().equals("Sujo")){
				return new ModelAndView("redirect:/apartamentos/"+apartamento.getId()+"/atualiza");
			}
			return new ModelAndView("redirect:/hospedagens/adiciona").addObject("apartamento", apartamento.getId());
		}
		
		

		ModelAndView modelAndView = new ModelAndView("hospedagens/detalhes");
		modelAndView.addObject("hosp_despesas", hospedagem.getDespesas());
		modelAndView.addObject("hospedagem", hospedagem);
		return modelAndView;
	}
	
	@ModelAttribute("clientes")
	public List<Cliente> inicializaHospedes(){
		return clienteService.findAll();
	}
	
	@ModelAttribute("apartamentos")
	public List<Apartamento> inicializaApartamentos(){
		return apartamentoService.findAll();
	}
	

}
