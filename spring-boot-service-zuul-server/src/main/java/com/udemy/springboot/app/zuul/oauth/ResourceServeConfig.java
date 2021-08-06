package com.udemy.springboot.app.zuul.oauth;

import org.apache.http.client.methods.HttpPost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServeConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, 
				"/api/productos/listar", 
				"/api/items/listar", 
				"/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, 
				"/api/productos/buscar/{id}",
				"/api/items/buscar/{id}/cantidad/{cantidad}", 
				"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(
				"/api/productos/**",
				"/api/items/**",
				"/api/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated();
	}
	/* Agregando las rutas de manera mas especificas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, 
				"/api/productos/listar", 
				"/api/items/listar", 
				"/api/usuarios/usuarios").permitAll()
		.antMatchers(HttpMethod.GET, 
				"/api/productos/buscar/{id}",
				"/api/items/buscar/{id}/cantidad/{cantidad}", 
				"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.POST,
				"/api/productos/crear",
				"/api/items/crear",
				"/api/usuarios/usuarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,
				"/api/productos/editar",
				"/api/items/editar",
				"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,
				"/api/productos/eliminar",
				"/api/items/eliminar",
				"/api/usuarios/usuarios/{id}").hasRole("ADMIN");
	}*/
	
	@Bean
	public JwtTokenStore tokenStore() {		
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algun_codigo_secreto_123456");
		return accessTokenConverter;
	}
	

}