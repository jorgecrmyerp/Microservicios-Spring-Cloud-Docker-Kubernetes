/*
 * Yo 24 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservicio.app.autenticacion.oauth.clients.UsuarioFeignClient;
import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

import brave.Tracer;
import feign.FeignException;

/**
 * The Class UsuarioService.
 * SE VALIDA QUE EL USUARIO QUE OBTENEMOS EN USUARIOFEIGN EXISTE,SI NO ES ASI DEVOLVEMOS ERROR
 * Y ADEMAS QUE EL ROL QUE TIENE ES VALIDO
 * añadimos IUsuarioService para recuperar el usuario
 */
@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{


    /** The usuario feign client. */
    //le paso el usuario que nos ha devuelto en usuariofeign
    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    /** The log. */
    private Logger log = LoggerFactory.getLogger(UsuarioService.class);
    
    /** The trace zipkin. */
    @Autowired
    private Tracer traceZipkin;


    /**
     * Load user by username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	try {
	    //capturamos el username
	    Usuario usuario=usuarioFeignClient.findByUsername(username);
	    //para guardar los roles, esta clase es propia de spring security
	    //el peek es para que lo imprima
	    List<GrantedAuthority> perfiles = usuario.getRoles().
		    stream().map(role->new SimpleGrantedAuthority(role.getNombreRol()))
		    .peek(authority->log.info("Role->".concat(authority.getAuthority().concat(" existe"))))				
		    .collect(Collectors.toList());

	    log.info("UsuarioService autenticado->".concat(username));
	    return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),
		    true,true,true,perfiles);
	} catch(FeignException e) {//404 usuario no existe

	    log.error("UsuarioService usuario->".concat(" no existe/no login"));
	    traceZipkin.currentSpan().tag("microservicio.autenticacion.usuarios.UsuarioService.loadUserByUsername",
			"usuario NO existe".concat(username));
	    throw new UsernameNotFoundException("Usuario->".concat(username).concat(" no existe,error en login"));
	}
    }


    /**
     * Find by username.
     *
     * @param username the username
     * @return the usuario
     */
    @Override
    public Usuario findByUsername(String username) {
	return usuarioFeignClient.findByUsername(username);
    }


    /**
     * Update.
     *
     * @param usuario the usuario
     * @param id the id
     * @return the usuario
     */
    @Override
    public Usuario update(Usuario usuario, Long id) {
	return usuarioFeignClient.update(usuario, id);
    }


}
