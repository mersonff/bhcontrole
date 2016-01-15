package com.bhcontrole.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.model.Usuario;
import com.bhcontrole.service.UsuarioService;

@Controller
public class LoginController {

	@Autowired
	private UsuarioService userService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private Environment env;

	/*SMS com Twilo (meio caro pra usar, fica pra próxima)
	 * 
	 * public static final String ACCOUNT_SID = "AC03b56c651877946128e55996fed56305";
	public static final String AUTH_TOKEN = "235cf7f314d8f7f94c79de1de5e729f4";
	public static final String TWILIO_NUMBER = "+553139566192";
	
	*/

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){    
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/resetarSenha", method = RequestMethod.GET)
	public String resetarSenha() {
		return "resetarSenha";
	}

	@RequestMapping(value = "/resetarSenha", method = RequestMethod.POST)
	public String resetar(@ModelAttribute("userInfo") Usuario usuario, RedirectAttributes redirectAttributes,
			Locale locale) {
		
		Usuario usuarioSalvo = userService.findByLogin(usuario.getLogin());
		System.out.println(usuarioSalvo);
		
		if (usuarioSalvo == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("usuario.nao.existe", new String[] {}, locale));
			return "redirect:/resetarSenha";
		}
		
		if (!(usuario.getEmail().equals(usuarioSalvo.getEmail()))) {
			System.out.println(usuario.getEmail());
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("usuario.email.errado", new String[] {}, locale));
			return "redirect:/resetarSenha";
		}
		
		String novaSenha = gerarSenha();
		usuarioSalvo.setSenha(novaSenha);
		userService.update(usuarioSalvo);
		
		redirectAttributes.addFlashAttribute("success",
				messageSource.getMessage("novaSenha.enviada.sucesso", new String[] {}, locale));
		
		SimpleMailMessage mail = constroiMensagem(novaSenha, locale, usuarioSalvo);
		mailSender.send(mail);
		//String celular = criarStringTelefone(usuarioSalvo.getTelefones().getTelefoneCelular());
		//sendSMS(celular);
		
		return "redirect:/resetarSenha";
	}

	private static String gerarSenha() {
		int qtdeMaximaCaracteres = 8;
		String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return senha.toString();
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "403";
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

	private SimpleMailMessage constroiMensagem(String novaSenha, Locale locale, Usuario user) {
		String message = messageSource.getMessage("mensagem.resetarSenha", null, locale);
		String subject = messageSource.getMessage("subject.resetarSenha", null, locale);

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject(subject);
		email.setText(message + "\n\n " + novaSenha);
		email.setFrom(env.getProperty("mail.login"));
		return email;
	}
	
	/*
	 * Implentação do método para enviar SMS com Twilo
	 * 
	 * public void sendSMS(String celular) {
	    try {
	        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body", "Hello, World!"));
	        params.add(new BasicNameValuePair("To", "+55"+celular));
	        params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	 
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        Message message = messageFactory.create(params);
	        System.out.println(message.getSid());
	    } 
	    catch (TwilioRestException e) {
	        System.out.println(e.getErrorMessage());
	    }
	}
	

	private String criarStringTelefone(String telefone){
		String novoTelefone = telefone.replaceAll("\\(|\\)|\\-|\\ ", "");
		return novoTelefone;
	}
	*/
}
