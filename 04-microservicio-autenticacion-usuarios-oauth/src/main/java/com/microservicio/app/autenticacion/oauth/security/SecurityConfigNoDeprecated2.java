/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The Class SecurityConfigNoDeprecated2.
 * COMO LA  WebSecurityConfigurerAdapter ESTA DEPRECATED se podria hacer asi
 * ASTERISCADOS TODOS LOS @ PARA QUE NO FALLE
 * https://www.udemy.com/course/microservicios-con-spring-boot-y-spring-cloud/learn/lecture/15373422#questions/17684622 
 */
//@Configuration
//public class SpringSecurityConfig {
public class SecurityConfigNoDeprecated2 {


    /** The usuario service. */
    //@Autowired
    private UserDetailsService usuarioService;

    /**
     * Password encoder.
     *
     * @return the b crypt password encoder
     */
    //@Bean
    public static BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }


    /**
     * Filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    //@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeRequests()
	.anyRequest().authenticated()
	.and()
	.csrf().disable()
	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	return http.build();
    }


    /**
     * Configurer global.
     *
     * @param build the build
     * @throws Exception the exception
     */
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
    {
	build.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }


    /**
     * Authentication manager.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception the exception
     */
    ////@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

	return authenticationConfiguration.getAuthenticationManager();

    }
}


