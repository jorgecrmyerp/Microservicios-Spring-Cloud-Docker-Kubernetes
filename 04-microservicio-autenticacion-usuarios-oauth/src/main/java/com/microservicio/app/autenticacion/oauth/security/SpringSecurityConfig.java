/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class SpringSecurityConfig. Clase para la configuracion de la seguridad
 * con autowired userDetailsService incluimos la clase servicio de usuario con
 * la que obtenemos los datos del usuario guadados en bbdd
 * 
 * con bean BCryptPasswordEncoder registramos el retorno del metodo
 * passwordEncoder(), un BCryptPasswordEncoder
 * 
 * el endpoint es localhost:8090/api/autenticacion/oauth/token
 * 
 */

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /** The user details service. */
    @Autowired
    private UserDetailsService userDetailsService;
    
    
    /** The autent event publisher. 
     * implementado en AuthenticacionSuccessErrorHandler, lo injectamos y lo usamos en el metodo
     * configure*/
    @Autowired
    private AuthenticationEventPublisher autentEventPublisher;
    

    /**
     * Password encoder.
     * los declaramos aqui y los usamos en la clase AuthorizationServerConfig
     * @return the b crypt password encoder
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager.
     * los declaramos aqui y los usamos en la clase AuthorizationServerConfig
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
	return super.authenticationManager();
    }

    /**
     * Configure.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder()).
	and().authenticationEventPublisher(autentEventPublisher);

    }

}
