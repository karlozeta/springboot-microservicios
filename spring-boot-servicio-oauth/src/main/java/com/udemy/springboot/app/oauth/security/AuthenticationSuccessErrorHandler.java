package com.udemy.springboot.app.oauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	//Metodo para manejar el error
	@Override
	public void publishAuthenticationFailure(AuthenticationException authenticationException, Authentication authentication) {
		logger.error("Error en el login: " + authenticationException.getMessage());
		
	}

	//Metodo para manejar el exito
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
		logger.info("Login Exitoso: " + userDetails.getUsername());
	}

}
