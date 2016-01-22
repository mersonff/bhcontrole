package com.bhcontrole.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
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

import com.bhcontrole.model.Estado;
import com.bhcontrole.model.Funcao;
import com.bhcontrole.model.Usuario;
import com.bhcontrole.service.ClienteService;
import com.bhcontrole.service.FuncaoService;
import com.bhcontrole.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FuncaoService funcaoService;
	
	@Autowired
	private ClienteService hospedeService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/adiciona")
	public ModelAndView adiciona(@ModelAttribute Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuarios/adiciona");
		modelAndView.addObject("estados", Estado.values());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="usuarios", allEntries=true)
	public ModelAndView salvar(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result,
			RedirectAttributes redirectAttributes, Locale locale) {
		boolean existe = usuarioService.findByLogin(usuario.getLogin()) != null;
		
		if (existe) {
			result.rejectValue("login", "usuario.ja.existe");
		}
		
		if (result.hasErrors()) {
			return adiciona(usuario);
		}
		usuarioService.save(usuario);
		System.out.println("Usuario: "+usuario);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("usuario.cadastrado.sucesso",
				new String[] { usuario.getId().toString() }, locale));
		return new ModelAndView("redirect:/usuarios");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value="usuarios")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("usuarios/lista");
		modelAndView.addObject("usuarios", usuarioService.findAll());
		return modelAndView;
	}
	
	@RequestMapping("/{id}/detalhes")
	public ModelAndView detalhes(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("usuarios/detalhes");
		Usuario usuario = usuarioService.find(id);
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@RequestMapping("/{id}/atualiza")
	public ModelAndView atualizar(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("usuarios/atualiza");
		Usuario usuario = usuarioService.find(id);
		if (usuario == null) {
			return new ModelAndView("redirect:/usuarios");
		}
		Set<Funcao> funcoes = usuario.getFuncoes();
		modelAndView.addObject("funcoes", funcoes);
		modelAndView.addObject("estados", Estado.values());
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	@CacheEvict(value="usuarios", allEntries=true)
	public ModelAndView atualiza(@ModelAttribute("usuario") @Valid Usuario usuario,
			BindingResult result, RedirectAttributes redirectAttributes, Locale locale, HttpSession session) {
		
		Usuario existe = usuarioService.findByLogin(usuario.getLogin());
		
		if (existe != null && existe.getId() != usuario.getId()) {
			result.rejectValue("login", "usuario.ja.existe");
		}
		
		if(!(usuario.getSenha().equals(usuario.getSenhaConfirmacao()))){
			result.rejectValue("senha", "senha.nao.combina");
		}
		
		if (result.hasErrors()) {
			return new ModelAndView("usuarios/atualiza").addObject("estados", Estado.values());
		}
		
		String estado = null;
		if(usuario.getEstado() != null){
		 estado = existe.getEstado();
		 usuario.setEstado(estado);
		}
		
		usuarioService.update(usuario);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("usuario.atualizado.sucesso",
				new String[] { usuario.getId().toString() }, locale));
		
		if(usuario.getId().equals(getPrincipal().getId())){
			session.invalidate();	
			return new ModelAndView("redirect:/login?update");
		}
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	@RequestMapping("/{id}/deleta")
	@CacheEvict(value="usuarios", allEntries=true)
	public ModelAndView deleta(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
		Usuario usuario = usuarioService.find(id);
		redirectAttributes.addFlashAttribute("success", messageSource.getMessage("usuario.deletado.sucesso",
				new String[] { usuario.getId().toString() }, locale));
		usuarioService.removeById(id);
		return new ModelAndView("redirect:/usuarios");
	}
	
	@RequestMapping("/minhaInfo")
	public ModelAndView minhaInfo(RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("usuarios/meusDetalhes");
		Usuario usuario = getPrincipal();
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@RequestMapping("/minhaInfo/atualiza")
	@CacheEvict(value="usuarios", allEntries=true)
	public ModelAndView minhaInfoAtualizar(RedirectAttributes redirectAttributes, Locale locale) {
		ModelAndView modelAndView = new ModelAndView("usuarios/atualiza");
		Usuario usuario = getPrincipal();
		Set<Funcao> funcoes = usuario.getFuncoes();
		modelAndView.addObject("funcoes", funcoes);
		modelAndView.addObject("estados", Estado.values());
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@ModelAttribute("roles")
	public List<Funcao> inicializaFuncoes() {
		return funcaoService.findAll();
	}
	
	private Usuario getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		Usuario usuario = usuarioService.findByLogin(userName);
		return usuario;
	}
}
