package com.app.spring.gateway.server.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

/**
 * The Class AuthenticationManagerJwt.
 * valida la firma,el token que no haya expirado,su estructura...
 * luego se utiliza en el filtro
 */
//@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

    /** The llave jwt. guardada en el application.config del config-server
     * 
     *  @Value("${config.security.autenticacion.jwt.key}")
     *  ESTO SOLO SE USA CUANDO TOMAMOS DATOS DEL FICHERO EN LOCAL
     *  */
    
    private String llaveJwt;
    
    @Autowired
    private Environment entorno;
    

    /**
     * Authenticate.
     * devuelve un Mono, que es un flujo reactivo con un solo elemento.
     *
     * @param authentication the authentication
     * @return the mono
     */
    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
	
	
	llaveJwt = entorno.getProperty("config.security.autenticacion.jwt.key");
	
	//con just convierte un objeto a reactivo
	//con esto obtenemos el token y se valida
	return Mono.just(authentication.getCredentials().toString()).map(token -> {
	    //esta codificacion la usamos tambien en la autenticacion en AuthorizationServerConfig de la autenticacion
	    //de usuarios para que coincida
	    SecretKey llave = Keys.hmacShaKeyFor(Base64.getEncoder().encode(llaveJwt.getBytes()));	    
	    return Jwts.parserBuilder().setSigningKey(llave).build().parseClaimsJws(token).getBody();
	    //convertimos el token a authentication claims(informacion como el username,roles...)
	}).map(claims -> {
	    String username = claims.get("user_name", String.class);
	    List<String> roles = claims.get("authorities", List.class);
	    
	    Collection<GrantedAuthority> authorities = roles.stream().map(
		    
		    role->new SimpleGrantedAuthority(role))
		    //tb se puede poner asi
		    //SimpleGrantedAuthority::new)
		    .collect(Collectors.toList());
	    //solo necesitamos pasar el username y roles pero no el password, necesario para validar el token, el login con el password lo 
	    //realiza el servicio oauth, pero acá solo validamos el token nada que ver con la contraseña
	    return new UsernamePasswordAuthenticationToken(username, null, authorities);

	});
    }

}
