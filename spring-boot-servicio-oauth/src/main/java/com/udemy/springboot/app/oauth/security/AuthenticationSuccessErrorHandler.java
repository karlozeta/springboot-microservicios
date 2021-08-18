package com.udemy.springboot.app.oauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.udemy.springboot.app.commons.usuarios.models.entity.Usuario;
import com.udemy.springboot.app.oauth.services.IUsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private Tracer tracer;

	private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	// Metodo para manejar el error
	@Override
	public void publishAuthenticationFailure(AuthenticationException authenticationException,
			Authentication authentication) {
		logger.error("Error en el login: " + authenticationException.getMessage());

		try {
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Error en el login: " + authenticationException.getMessage());
			
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if (usuario.getIntentosLogin() == null) {
				usuario.setIntentosLogin(0);
			}

			logger.info("Intentos actuales es de: " + usuario.getIntentosLogin());
			usuario.setIntentosLogin(usuario.getIntentosLogin() + 1);
			logger.info("Intentos despues es de: " + usuario.getIntentosLogin());
			
			stringBuilder.append(" - Intentos de login: " + usuario.getIntentosLogin());

			if (usuario.getIntentosLogin() >= 3) {
				String errorMaxIntentos = String.format("El usuario %s deshabilitado por maximo de intentos", usuario.getUsername());
				logger.error(errorMaxIntentos);
				stringBuilder.append(" - " + errorMaxIntentos);
				usuario.setEnabled(false);
			}

			usuarioService.update(usuario, usuario.getId());
			tracer.currentSpan().tag("error.mensaje", stringBuilder.toString());

		} catch (FeignException e) {
			logger.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}

	// Metodo para manejar el exito
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		// Se valida si esta haciendo login o el cliente. Si es el cliente se sale
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		logger.info("Login Exitoso: " + userDetails.getUsername());

		Usuario usuario = usuarioService.findByUsername(authentication.getName());

		if (usuario.getIntentosLogin() != null && usuario.getIntentosLogin() > 0) {
			usuario.setIntentosLogin(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

}
