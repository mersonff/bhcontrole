package com.bhcontrole.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.config.StringToApartamento;
import com.bhcontrole.config.StringToCliente;
import com.bhcontrole.model.Apartamento;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.model.Despesa;
import com.bhcontrole.model.EstadoApartamento;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.model.TipoDespesa;
import com.bhcontrole.model.Usuario;
import com.bhcontrole.service.ApartamentoService;
import com.bhcontrole.service.ClienteService;
import com.bhcontrole.service.DespesaService;
import com.bhcontrole.service.HospedagemService;
import com.bhcontrole.service.UsuarioService;

@Controller
@RequestMapping("/hospedagens")
public class HospedagensController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DespesaService despesaService;

	@Autowired
	private ApartamentoService apartamentoService;

	@Autowired
	private HospedagemService hospedagemService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StringToCliente stringToCliente;

	@Autowired
	private StringToApartamento stringToApartamento;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cliente.class, this.stringToCliente);
		binder.registerCustomEditor(Apartamento.class, this.stringToApartamento);
	}

	@RequestMapping("/adiciona")
	public ModelAndView adiciona(@ModelAttribute Hospedagem hospedagem) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/adiciona");

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

		modelAndView.addObject("dataEntrada", dateFormat.format(date));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView salvar(@ModelAttribute("hospedagem") @Valid Hospedagem hospedagem, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		Hospedagem existe = hospedagemService.findByNomeEDataEntrada(hospedagem.getCliente(),
				hospedagem.getDataEntrada());

		Apartamento apartamento = hospedagem.getApartamento();

		if (existe != null) {
			result.rejectValue("cliente", "hospedagem.ja.existe");
		}

		if (apartamento != null && !apartamento.getEstado().equals("Disponível")) {
			result.rejectValue("apartamento", "apartamento.indisponivel");
		}

		if (result.hasErrors()) {
			return adiciona(hospedagem);
		}

		Usuario usuarioQueCadastrou = usuarioService.findByLogin(getPrincipal());
		hospedagem.setUsuarioQueCadastrou(usuarioQueCadastrou);

		apartamento.setEstado(EstadoApartamento.OCUPADO.getEstado());
		hospedagem.setApartamento(apartamento);

		hospedagemService.save(hospedagem);
		System.out.println("Hospedagem: " + hospedagem);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("hospedagem.cadastrado.sucesso",
				new String[] { hospedagem.getId().toString() }, locale));
		return new ModelAndView("redirect:/hospedagens");
	}

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value="hospedagens")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("hospedagens/lista");
		modelAndView.addObject("hospedagens", hospedagemService.findAll());
		return modelAndView;
	}

	@RequestMapping("/{id}/atualiza")
	public ModelAndView atualizar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/atualiza");
		Hospedagem hospedagem = hospedagemService.find(id);
		if (hospedagem == null) {
			return new ModelAndView("redirect:/hospedagens");
		}
		if (hospedagem.getDataSaida() != null) {
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("hospedagem.checkout.ja.cadastrado",
					new String[] { hospedagem.getId().toString() }, locale));
			return new ModelAndView("redirect:/hospedagens");
		}
		modelAndView.addObject("hospedagem", hospedagem);
		return modelAndView;
	}

	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView atualiza(@ModelAttribute("hospedagem") @Valid Hospedagem hospedagem, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {

		Hospedagem existe = hospedagemService.findByNomeEDataEntrada(hospedagem.getCliente(),
				hospedagem.getDataEntrada());
		Hospedagem hospedagemSalva = hospedagemService.find(hospedagem.getId());
		Apartamento apartamento = hospedagem.getApartamento();
		Apartamento apartamentoSalvo = hospedagemService.find(hospedagem.getId()).getApartamento();

		if (existe != null && existe.getId() != hospedagem.getId()) {
			result.rejectValue("cliente", "hospedagem.ja.existe");
		}

		if (apartamento != null && !apartamento.getEstado().equals("Disponível")
				&& hospedagemSalva.getId() != hospedagem.getId()) {
			result.rejectValue("apartamento", "apartamento.indisponivel");
		}

		if (result.hasErrors()) {
			return new ModelAndView("hospedagens/atualiza");
		}

		if (apartamento != apartamentoSalvo) {
			apartamentoSalvo.setEstado(EstadoApartamento.DISPONIVEL.getEstado());
			apartamento.setEstado(EstadoApartamento.OCUPADO.getEstado());
		}

		Usuario usuarioQueCadastrou = usuarioService.findByLogin(getPrincipal());
		hospedagem.setUsuarioQueCadastrou(usuarioQueCadastrou);

		List<Despesa> despesas = hospedagemService.find(hospedagem.getId()).getDespesas();
		hospedagem.setDespesas(despesas);

		hospedagemService.update(hospedagem);
		System.out.println("Hospedagem: " + hospedagem);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("hospedagem.atualizado.sucesso",
				new String[] { hospedagem.getId().toString() }, locale));
		return new ModelAndView("redirect:/hospedagens");
	}

	@RequestMapping("/{id}/checkout")
	public ModelAndView checkout(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/checkout");
		Hospedagem hospedagem = hospedagemService.find(id);
		if (hospedagem == null) {
			return new ModelAndView("redirect:/hospedagens");
		}
		if (hospedagem.getDataSaida() != null) {
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("hospedagem.checkout.ja.cadastrado",
					new String[] { hospedagem.getId().toString() }, locale));
			return new ModelAndView("redirect:/hospedagens");
		}

		modelAndView.addObject("hospedagem", hospedagem);
		return modelAndView;
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView doCheckout(@ModelAttribute("hospedagem") @Valid Hospedagem hospedagem, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {

		Hospedagem existe = hospedagemService.findByNomeEDataEntrada(hospedagem.getCliente(),
				hospedagem.getDataEntrada());

		if (hospedagem.getDataSaida() == null) {
			result.rejectValue("dataSaida", "dataSaida.obritoria");
		}

		if (hospedagem.getDataEntrada() != null && hospedagem.getDataSaida() != null) {
			if (hospedagem.getDataEntrada().after(hospedagem.getDataSaida())) {
				result.rejectValue("dataSaida", "data.saida.menor");
			}
		}

		if (existe != null && existe.getId() != hospedagem.getId()) {
			result.rejectValue("cliente", "hospedagem.ja.existe");
		}

		if (result.hasErrors()) {
			return new ModelAndView("hospedagens/checkout");
		}

		Usuario usuarioQueCadastrou = usuarioService.findByLogin(getPrincipal());
		hospedagem.setUsuarioQueCadastrou(usuarioQueCadastrou);

		Apartamento apartamento = apartamentoService.find(hospedagem.getApartamento().getId());
		apartamento.setEstado(EstadoApartamento.SUJO.getEstado());

		List<Despesa> despesas = hospedagemService.find(hospedagem.getId()).getDespesas();

		hospedagem.setApartamento(apartamento);
		hospedagem.setDespesas(despesas);

		hospedagemService.update(hospedagem);
		System.out.println("Hospedagem: " + hospedagem);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("hospedagem.checkout.sucesso",
				new String[] { hospedagem.getId().toString() }, locale));
		return new ModelAndView("redirect:/hospedagens/" + hospedagem.getId() + "/detalhes");
	}

	@RequestMapping("/{id}/despesas")
	public ModelAndView despesas(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/despesas");
		Hospedagem hospedagem = hospedagemService.find(id);

		if (hospedagem == null) {
			return new ModelAndView("redirect:/hospedagens");
		}

		if (hospedagem.getDataSaida() != null) {
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("hospedagem.checkout.ja.cadastrado",
					new String[] { hospedagem.getId().toString() }, locale));
			return new ModelAndView("redirect:/hospedagens");
		}

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

		List<Despesa> hosp_despesas = hospedagem.getDespesas();
		int contador = hosp_despesas.size();

		modelAndView.addObject("contador", contador);
		modelAndView.addObject("hosp_despesas", hosp_despesas);
		modelAndView.addObject("tipos", TipoDespesa.values());
		modelAndView.addObject("dataHoje", dateFormat.format(date));
		modelAndView.addObject("show", true);
		modelAndView.addObject("hospedagem", hospedagem);
		return modelAndView;
	}

	@RequestMapping(value = "/despesas", method = RequestMethod.POST)
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView addDespesa(@ModelAttribute("hospedagem") Hospedagem hospedagem, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {

		Hospedagem hospedagemSalva = hospedagemService.find(hospedagem.getId());

		List<Despesa> despesas = hospedagem.getDespesas();
		int contador = despesas.size() - 1;

		if (hospedagem.getDespesas().get(contador).getDataDaDespesa() == null) {
			result.rejectValue("despesas[" + contador + "].dataDaDespesa", "dataDaDespesa.obrigatoria");
		}
		if (hospedagem.getDespesas().get(contador).getDescricao().isEmpty()) {
			result.rejectValue("despesas[" + contador + "].descricao", "descricao.obrigatoria");
		}
		if (hospedagem.getDespesas().get(contador).getTipo() == null) {
			result.rejectValue("despesas[" + contador + "].tipo", "tipo.obrigatoria");
		}
		if (hospedagem.getDespesas().get(contador).getValor() == null) {
			result.rejectValue("despesas[" + contador + "].valor", "valor.obrigatorio");
		}
		if (result.hasErrors()) {
			return new ModelAndView("hospedagens/despesas").addObject("contador", contador)
					.addObject("tipos", TipoDespesa.values()).addObject("show", false);
		}

		Despesa despesa = new Despesa();
		despesa = despesas.get(contador);

		despesaService.save(despesa);

		Usuario usuarioQueCadastrou = usuarioService.findByLogin(getPrincipal());
		despesas.get(contador).setUsuarioQueCadastrou(usuarioQueCadastrou);

		hospedagemSalva.getDespesas().add(despesas.get(contador));

		hospedagemService.update(hospedagemSalva);
		System.out.println("Hospedagem: " + hospedagemSalva);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("hospedagem.cadastro.despesa.sucesso",
				new String[] { hospedagem.getId().toString() }, locale));
		return new ModelAndView("redirect:/hospedagens/" + hospedagem.getId() + "/despesas");
	}

	@RequestMapping("/{id}/atualizaDespesa")
	public ModelAndView atualizaDespesa(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/atualizaDespesa");
		Despesa despesa = despesaService.find(id);

		if (despesa == null) {
			return new ModelAndView("redirect:/hospedagens");
		}

		modelAndView.addObject("tipos", TipoDespesa.values());
		modelAndView.addObject("despesa", despesa);
		return modelAndView;
	}

	@RequestMapping(value = "/atualizaDespesa", method = RequestMethod.POST)
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView atualizarDespesa(@ModelAttribute("despesa") @Valid Despesa despesa, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {

		List<Hospedagem> hospedagens = hospedagemService.findAll();

		Hospedagem hospedagemComADespesa = new Hospedagem();

		for (Hospedagem hospedagem : hospedagens) {
			List<Despesa> despesas = hospedagem.getDespesas();
			for (Despesa d : despesas) {
				if (d.getId() == despesa.getId())
					hospedagemComADespesa = hospedagem;
			}
		}

		if (result.hasErrors()) {
			return new ModelAndView("hospedagens/atualizaDespesa");
		}

		Usuario usuarioQueCadastrou = usuarioService.findByLogin(getPrincipal());
		despesa.setUsuarioQueCadastrou(usuarioQueCadastrou);

		despesaService.update(despesa);
		System.out.println("Despesa: " + despesa);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("despesa.atualizado.sucesso",
				new String[] { despesa.getId().toString() }, locale));
		return new ModelAndView("redirect:/hospedagens/" + hospedagemComADespesa.getId() + "/despesas");
	}

	@RequestMapping("/{id}/deleta")
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		Hospedagem hospedagem = hospedagemService.find(id);
		Apartamento apartamento = hospedagem.getApartamento();

		if (hospedagem.getDataSaida() == null && !apartamento.getEstado().equals("Disponível")) {
			apartamento.setEstado(EstadoApartamento.DISPONIVEL.getEstado());
		}

		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("hospedagem.deletado.sucesso",
				new String[] { hospedagem.getId().toString() }, locale));
		hospedagemService.removeById(id);
		return new ModelAndView("redirect:/hospedagens");
	}

	@RequestMapping("/{id}/deletaDespesa")
	@CacheEvict(value="hospedagens", allEntries=true)
	public ModelAndView deletaDespesa(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale) {
		Despesa despesa = despesaService.find(id);

		List<Hospedagem> hospedagens = hospedagemService.findAll();

		Hospedagem hospedagemComADespesa = new Hospedagem();

		for (Hospedagem hospedagem : hospedagens) {
			List<Despesa> despesas = hospedagem.getDespesas();
			for (Despesa d : despesas) {
				if (d.getId() == despesa.getId())
					hospedagemComADespesa = hospedagem;
			}
		}

		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("despesa.deletado.sucesso",
				new String[] { despesa.getId().toString() }, locale));
		Hospedagem hospedagem = despesaService.findHospedagemByDespesa(despesa);
		hospedagem.getDespesas().remove(despesa);
		despesaService.removeById(id);
		return new ModelAndView("redirect:/hospedagens/" + hospedagemComADespesa.getId() + "/despesas");
	}

	@RequestMapping("/{id}/detalhes")
	public ModelAndView detalhes(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("hospedagens/detalhes");
		Hospedagem hospedagem = hospedagemService.find(id);
		List<Despesa> hosp_despesas = hospedagem.getDespesas();
		modelAndView.addObject("hosp_despesas", hosp_despesas);
		modelAndView.addObject("hospedagem", hospedagem);
		return modelAndView;
	}

	@ModelAttribute("clientes")
	public List<Cliente> inicializaHospedes() {
		return clienteService.findAll();
	}

	@ModelAttribute("apartamentos")
	public List<Apartamento> inicializaApartamentos() {
		return apartamentoService.findAll();
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
