package com.bhcontrole.controller;

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

import com.bhcontrole.model.Apartamento;
import com.bhcontrole.model.EstadoApartamento;
import com.bhcontrole.service.ApartamentoService;

@Controller
@RequestMapping("/apartamentos")
public class ApartamentosController {

	@Autowired
	private ApartamentoService apartamentoService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="apartamentos", allEntries=true)
	public ModelAndView salvar(@ModelAttribute("apartamento") @Valid Apartamento apartamento, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		Apartamento existe = apartamentoService.findByNumero(apartamento.getNumero());

		if (existe != null) {
			result.rejectValue("numero", "apartamento.ja.existe");
		}

		if (result.hasErrors()) {
			return adiciona(apartamento);
		}
		apartamentoService.save(apartamento);
		System.out.println("Apartamento: " + apartamento);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("apartamento.cadastrado.sucesso",
				new String[] { apartamento.getId().toString() }, locale));
		return new ModelAndView("redirect:/apartamentos");
	}

	@RequestMapping("/adiciona")
	public ModelAndView adiciona(@ModelAttribute Apartamento apartamento) {
		ModelAndView modelAndView = new ModelAndView("apartamentos/adiciona");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value="apartamentos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("apartamentos/lista");
		modelAndView.addObject("apartamentos", apartamentoService.findAll());
		return modelAndView;
	}

	@RequestMapping("/{id}/atualiza")
	public ModelAndView atualizar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("apartamentos/atualiza");
		Apartamento apartamento = apartamentoService.find(id);
		if (apartamento == null) {
			return new ModelAndView("redirect:/apartamentos");
		}
		
		if(apartamento.getEstado().equals("Ocupado")){
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("apartamento.ocupado.error",
					new String[] { apartamento.getId().toString() }, locale));
			return new ModelAndView("redirect:/apartamentos");
		}
		modelAndView.addObject("estados", getEstadosSemOcupado());
		modelAndView.addObject("apartamento", apartamento);
		return modelAndView;
	}

	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	@CacheEvict(value="apartamentos")
	public ModelAndView atualiza(@ModelAttribute("apartamento") @Valid Apartamento apartamento, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		Apartamento existe = apartamentoService.findByNumero(apartamento.getNumero());

		if (existe != null && existe.getId() != apartamento.getId()) {
			result.rejectValue("numero", "apartamento.ja.existe");
		}

		if (result.hasErrors()) {
			return new ModelAndView("apartamentos/atualiza").addObject("estados", EstadoApartamento.values());
		}
		apartamentoService.update(apartamento);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("apartamento.atualizado.sucesso",
				new String[] { apartamento.getId().toString() }, locale));
		return new ModelAndView("redirect:/");
	}

	@RequestMapping("/{id}/deleta")
	@CacheEvict(value="apartamentos", allEntries=true)
	public ModelAndView deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		Apartamento apartamento = apartamentoService.find(id);

		if (!apartamento.getEstado().equals("Disponível")) {
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("apartamento.deletado.error",
					new String[] { apartamento.getId().toString() }, locale));
			return new ModelAndView("redirect:/apartamentos");
		}
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("apartamento.deletado.sucesso",
				new String[] { apartamento.getId().toString() }, locale));
		apartamentoService.removeById(id);
		return new ModelAndView("redirect:/apartamentos");
	}

	private EstadoApartamento[] getEstadosSemOcupado() {
		EstadoApartamento estadosApartamento[] = {EstadoApartamento.DISPONIVEL,EstadoApartamento.SUJO};
		return estadosApartamento;
	}
}
