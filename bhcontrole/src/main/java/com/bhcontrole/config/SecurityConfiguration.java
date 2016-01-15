package com.bhcontrole.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

		http.authorizeRequests()
			.antMatchers("/login*").permitAll()
			.antMatchers("/resetarSenha").permitAll()
			.antMatchers(HttpMethod.POST, "/resetarSenha").permitAll()
			.antMatchers("/","/home").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/relatorios").hasAnyRole("ADMIN")
			.antMatchers("/relatorios/hospedagem*").hasAnyRole("ADMIN","FUNCIONARIO")
			.antMatchers("/relatorios/**").hasAnyRole("ADMIN")
			.antMatchers("/hospedagens").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/hospedagens/*/deleta").hasAnyRole("ADMIN")
			.antMatchers("/hospedagens/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/reservas").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/reservas/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/apartamentos").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/apartamentos/*/deleta").hasAnyRole("ADMIN")
			.antMatchers("/apartamentos/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/clientes").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/clientes/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/usuarios").hasRole("ADMIN")
			.antMatchers("/usuarios/minhaInfo").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/usuarios/minhaInfo/**").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers(HttpMethod.POST, "/usuarios/atualiza").hasAnyRole("ADMIN", "FUNCIONARIO")
			.antMatchers("/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").usernameParameter("login").passwordParameter("senha").permitAll()
			.and().logout().logoutSuccessUrl("/login").deleteCookies("JSESSIONID").invalidateHttpSession( true )
			.and().exceptionHandling().accessDeniedPage("/403")
			.and().sessionManagement().maximumSessions(1).expiredUrl("/login?expired").sessionRegistry(sessionRegistry());
	}	
	
	@Bean
	public SessionRegistry sessionRegistry() {
	  return new SessionRegistryImpl();
	}
	
}
