package com.udemy.springboot.app.oauth.services;

import com.udemy.springboot.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);

}
