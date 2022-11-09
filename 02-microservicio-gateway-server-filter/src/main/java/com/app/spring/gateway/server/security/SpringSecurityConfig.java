package com.app.spring.gateway.server.security;

import org.springframework.web.server.WebFilter;

import reactor.core.publisher.Flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * The Class SpringSecurityConfig.
 * Configurar seguridad
 * Ambos tienen la misma semántica de seguridad, pero EnableWebSecurityse usan en una pila MVC 
 * mientras que EnableWebFluxSecurityse usan en la pila reactiva (habilitando Spring Security WebFlux)
 */
@EnableWebFluxSecurity
public class SpringSecurityConfig {
    
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);
    
    
    /** The authentication filter. */
    @Autowired
	private JwtAuthenticationFilter authenticationFilter;
    
    
    /**
     * Configurar seguridad. para todos pedimos que este autenticado 
     * desactivamos csrf que es para formularios y html como thymeleaf,jsp porque el nuestro es
     * servicio rest 
     * si devuelve un error de que jaxb-api no se encuentra incluir :
     * <dependency> <groupId>org.glassfish.jaxb</groupId>
     * <artifactId>jaxb-runtime</artifactId> </dependency>
     * 
     * @param http the http
     * @return the security web filter chain
     */

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
	
	logger.info("************************entro en SecurityWebFilterChain configure*******************************");
	
	 /*
	  * NO FUNCIONA NI DE COÑA,COPIO EL DEL PROFESOR
	 
	return http.authorizeExchange()
		.pathMatchers("/api/security/oauth/**").permitAll()
		.pathMatchers("/api/autenticacion/oauth/**").permitAll()
		.pathMatchers(HttpMethod.GET, "/api/productos/listar",
				"/api/items/listar",
				"/api/usuarios/usuarios",
				"/api/items/ver/{id}/cantidad/{cantidad}",
				"/api/productos/ver/{id}").permitAll()
		.pathMatchers(HttpMethod.GET, "/api/usuarios/usuarios/{id}")
		//.hasAnyRole("ADMIN", "USER")
		.hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
		//.pathMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/usuarios/**").hasRole("ADMIN")
		.pathMatchers(HttpMethod.POST,"/api/productos/**", "/api/items/**", "/api/usuarios/usuarios/**",
			"/api/usuarios/validacion/**").
		//hasRole("ADMIN")
		hasAuthority("ROLE_ADMIN")
		.pathMatchers(HttpMethod.PUT,"/api/productos/**", "/api/items/**", "/api/usuarios/usuarios/**",
			"/api/usuarios/validacion/**").
		//hasRole("ADMIN")
		hasAuthority("ROLE_ADMIN")
		.pathMatchers(HttpMethod.DELETE,"/api/productos/**",
			"/api/productos/borra/**",
			"/api/productos/borra/{id}",
			"/api/items/**", "/api/usuarios/usuarios/**",
			"/api/usuarios/validacion/**").
		//hasRole("ADMIN")
		hasAuthority("ROLE_ADMIN")
		.anyExchange().authenticated()
		.and()
		//.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
		.csrf().disable()
		.build();
	 */
	return http.authorizeExchange()
		.pathMatchers("/api/security/oauth/**").permitAll()
		.pathMatchers("/api/autenticacion/oauth/**").permitAll()
		.pathMatchers(HttpMethod.GET, "/api/productos/listar",
				"/api/items/listar",
				"/api/usuarios/usuarios",
				"/api/items/ver/{id}/cantidad/{cantidad}",
				"/api/productos/ver/{id}").permitAll()
		.pathMatchers(HttpMethod.GET,
			"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		.pathMatchers(HttpMethod.GET,
			"/api/validacion/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		.pathMatchers("/api/productos/**", "/api/items/**", 
			"/api/usuarios/usuarios/**",
			"/api/validacion/usuarios/**",
			"/api/autenticacion/usuarios/**",
			"/servicio-items/default/**"
			).hasRole("ADMIN")
		.anyExchange().authenticated()
		//añadimos el filtro de configuracion
		.and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
		.csrf().disable()
		.build();
		
    }



}
