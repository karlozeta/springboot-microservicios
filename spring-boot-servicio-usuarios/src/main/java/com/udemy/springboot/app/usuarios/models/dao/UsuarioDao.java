package com.udemy.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.udemy.springboot.app.usuarios.models.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	//usar findBy construye la consulta: WHERE USERNAME = ? y en la ruta se utiliza en nombre del metodo
	//Para personalizar rutas usamos @RestResource
	@RestResource(path = "buscar_username")
	public Usuario findByUsername(@Param("username") String username);
	
	//Buscar con sql en @Query. El parametro ?1 quiere decir que es el primer parametro
	//Si hubiera otro parametro seria ?2 y se coloca igualmente en los argumentos del metodo
	@Query("select u from Usuario u where u.username = ?1")
	public Usuario buscarPorUsername(String username);

}
