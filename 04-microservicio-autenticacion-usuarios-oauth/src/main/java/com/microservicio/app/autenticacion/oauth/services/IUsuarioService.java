/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.services;

import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

/**
 * The Interface IUsuarioService.
 * necesaria para que en InfoAdicionalToken podamos guardar los datos
 */
public interface IUsuarioService {
	
	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the usuario
	 */
	public Usuario findByUsername(String username);
	
	
	
 	/**
	  * Update del usuario.
	  *
	  * @param usuario the usuario
	  * @param id the id
	  * @return the usuario
	  */
	 public Usuario update(Usuario usuario, Long id);

}
