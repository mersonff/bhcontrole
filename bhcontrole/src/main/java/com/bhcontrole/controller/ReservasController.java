package com.bhcontrole.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.model.EstadoReserva;
import com.bhcontrole.model.Reserva;
import com.bhcontrole.model.Usuario;
import com.bhcontrole.service.ReservaService;
import com.bhcontrole.service.UsuarioService;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Environment env;

	@Autowired
	private MailSender mailSender;

	@RequestMapping("/adiciona")
	public ModelAndView adiciona(@ModelAttribute Reserva reserva) {
		ModelAndView modelAndView = new ModelAndView("reservas/adiciona");
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		modelAndView.addObject("dataSolicitacao", dateFormat.format(date));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="reservas", allEntries=true)
	public ModelAndView salvar(@ModelAttribute("reserva") @Valid Reserva reserva, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		Reserva existe = reservaService.findByNomeEDataSolicitacao(reserva.getNome(), reserva.getDataSolicitacao());

		if (existe != null) {
			result.rejectValue("nome", "reserva.ja.existe");
		}

		if (reserva.getDataSolicitacao() != null && reserva.getDataEntrada() != null) {
			//if (!comparaDatas(reserva.getDataSolicitacao(), reserva.getDataEntrada())) {
			if(reserva.getDataSolicitacao().after(reserva.getDataEntrada())){
				result.rejectValue("dataEntrada", "data.entrada.menor");
			}
		}

		if (result.hasErrors()) {
			return adiciona(reserva).addObject("estados", EstadoReserva.values());
		}

		Usuario usuarioQueCadastrou = userService.findByLogin(getPrincipal());		
		reserva.setUsuarioQueCadastrou(usuarioQueCadastrou);
		
		reservaService.save(reserva);
		
		if(!reserva.getEmail().isEmpty()){
			SimpleMailMessage mail = constroiMensagem(reserva, locale);
			mailSender.send(mail);
		}
		
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("reserva.cadastrado.sucesso",
				new String[] { reserva.getId().toString() }, locale));
		return new ModelAndView("redirect:/reservas");
	}

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value="reservas")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("reservas/lista");
		modelAndView.addObject("reservas", reservaService.findAll());
		return modelAndView;
	}
	
	@RequestMapping("/{id}/atualiza")
	public ModelAndView atualizar(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("reservas/atualiza");
		Reserva reserva = reservaService.find(id);
		if (reserva == null) {
			return new ModelAndView("redirect:/reservas");
		}
		modelAndView.addObject("estados", EstadoReserva.values());
		modelAndView.addObject("reserva", reserva);
		return modelAndView;
	}
	
	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	@CacheEvict(value="reservas", allEntries=true)
	public ModelAndView atualiza(@ModelAttribute("reserva") @Valid Reserva reserva, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		
		Reserva existe = reservaService.findByNomeEDataSolicitacao(reserva.getNome(), reserva.getDataEntrada());

		if (existe != null && existe.getId() != reserva.getId()) {
			result.rejectValue("nome", "reserva.ja.existe");
		}

		if (reserva.getDataSolicitacao() != null && reserva.getDataEntrada() != null) {
			if (reserva.getDataSolicitacao().after(reserva.getDataEntrada())) {
				result.rejectValue("dataEntrada", "data.entrada.menor");
			}
		}
		
		if (result.hasErrors()) {
			return new ModelAndView("reservas/atualiza").addObject("estados", EstadoReserva.values());
		}
		Usuario usuarioQueAtualizou = userService.findByLogin(getPrincipal());		
		reserva.setUsuarioQueCadastrou(usuarioQueAtualizou);
		reservaService.update(reserva);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("reserva.atualizado.sucesso",
				new String[] { reserva.getId().toString() }, locale));
		return new ModelAndView("redirect:/reservas");
	}
	
	@RequestMapping("/{id}/deleta")
	@CacheEvict(value="reservas", allEntries=true)
	public ModelAndView deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		Reserva reserva = reservaService.find(id);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("reserva.deletado.sucesso",
				new String[] { reserva.getId().toString() }, locale));
		reservaService.removeById(id);
		return new ModelAndView("redirect:/reservas");
	}
	
	@RequestMapping("/{id}/detalhes")
	public ModelAndView detalhes(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("reservas/detalhes");
		Reserva reserva = reservaService.find(id);
		modelAndView.addObject("reserva", reserva);
		return modelAndView;
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
	
	private SimpleMailMessage constroiMensagem(Reserva reserva, Locale locale) {
		String message = messageSource.getMessage("mensagem.reservaRealizada", null, locale);
		String subject = messageSource.getMessage("subject.reservaRealizada", null, locale);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		String mensagem = "Hóspede: "+reserva.getNome()+"\n"
				+"Quantidade de Hóspedes: "+reserva.getQuantidadeDeHospedes()+"\n"
				+"Quantidade de Quartos: "+reserva.getQuantidadeDeQuartos()+"\n"
				+"Data de Solicitação: "+df.format(reserva.getDataSolicitacao().getTime())+"\n"
				+"Data de Check-in: "+df.format(reserva.getDataEntrada().getTime())+"\n"
				+"Fixo: "+ reserva.getTelefones().getTelefoneFixo()+"\n"
				+"Celular: "+reserva.getTelefones().getTelefoneCelular()+"\n"
				+"Comentário: "+reserva.getComentario();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(reserva.getEmail());
		email.setSubject(subject);
		email.setText(message + "\n\n" + mensagem);
		email.setFrom(env.getProperty("mail.login"));
		return email;
		
	}

}
