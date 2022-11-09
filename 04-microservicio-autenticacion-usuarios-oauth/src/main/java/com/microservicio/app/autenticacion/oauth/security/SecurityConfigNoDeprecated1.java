/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The Class SecurityConfigNoDeprecated1.
 * COMO LA  WebSecurityConfigurerAdapter ESTA DEPRECATED se podria hacer asi
 * ASTERISCADOS TODOS LOS @ PARA QUE NO FALLE
 * https://www.udemy.com/course/microservicios-con-spring-boot-y-spring-cloud/learn/lecture/15373422#questions/17684622
 */

//@Configuration
//public class SpringSecurityConfig {
public class SecurityConfigNoDeprecated1 {

    //

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
     * Authentication manager.
     *
     * @param auth the auth
     * @return the authentication manager
     * @throws Exception the exception
     */
    //@Bean
    protected AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
	return auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder()).and().build();
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





}
