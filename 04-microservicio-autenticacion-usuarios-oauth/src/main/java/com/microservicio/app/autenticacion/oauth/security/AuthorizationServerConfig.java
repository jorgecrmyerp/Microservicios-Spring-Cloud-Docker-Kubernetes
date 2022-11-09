/*
 * Yo 26 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.security;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * The Class AuthorizationServerConfig. La añadimos como clase de configuracion
 * y ademas indicamos con el EnableAuthorizationServer que va a ser un servidor
 * de autorizacion inyecatamos el passwordencoder y el authenticationmanager de
 * la clase springsecurityconfig
 * la clave SigningKey del metodo accessTokenConverter() tiene que ser la misma que la que usamos en zuul
 * 
 * el endpoint es localhost:8090/api/autenticacion/oauth/token
 */

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /** The b crypt password encoder. */
    // de la clase sepringsecurityconfig
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The authentication manager. de la clase sepringsecurityconfig ,lo registramos
     * en el authorizationmanager con estos metodos
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** The info adicional token. obtenemos los datos del token que recibimos */
    @Autowired
    private InfoAdicionalToken infoAdicionalToken;


    /** The env. para recuperar los parametros que tenemos en el application.properties e inyectarlos quitando el hardcode */
    @Autowired
    private Environment env;


    /**
     * Configure. permisos de los endpoint de nuestro servidor de autorizacion para
     * generar y validar el token
     * 
     * @param security the security
     * @throws Exception the exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	// este es el endpoint para generar el token
	// POST:/oauth/token
	// cualquiera puede acceder
	security.tokenKeyAccess("permitAll").
	// valida el token y que el cliente esté autenticado
	checkTokenAccess("isAuthenticated()")
	// estos endpoint estan protegidos con proteccion basica http, que tiene el
	// client id y el client secret
	// que se envian en la cabecera de la peticion
	;
    }

    /**
     * Configure. aqui configuramos los clientes
     * 
     * @param clients the clients
     * @throws Exception the exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

	// Lo almacenamos en memoria,podria ser en bbdd
	//		clients.inMemory().withClient("frontendadd").
	clients.inMemory().withClient(env.getProperty("config.security.autenticacion.client.id")).
	// encriptamos la contraseña con el Bcryptpasswordencode de springsecurityconfig
	//				secret(bCryptPasswordEncoder.encode("12345")).
	secret(bCryptPasswordEncoder.encode(env.getProperty("config.security.autenticacion.secret"))).
	// operaciones que puede realizar
	scopes("read", "write").
	// tipo de autenticacion,como la vamos a obtener, en nuestro caso con password
	// porque es con credenciales , el usuario existe en backend y para autenticar
	// necesita usuario
	// y password. Enviamos la del cliente frontendadpp-12345 y las del usuario a
	// traves del token
	// el refresh es para que lo actualice antes dee que caduque
	authorizedGrantTypes("password", "refresh_token").
	// cuando va a refrescar el token, 1 hora
	accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600).
	// esto es por si queremos crear oto cliente distinto cuando entra por un
	// telefono
	and().withClient("android").
	// encriptamos la contraseña con el Bcryptpasswordencode de springsecurityconfig
	secret(bCryptPasswordEncoder.encode("12345")).
	// operaciones que puede realizar
	scopes("read", "write").
	// tipo de autenticacion,como la vamos a obtener, en nuestro caso con password
	// porque es con credenciales , el usuario existe en backend y para autenticar
	// necesita usuario
	// y password. Enviamos la del cliente frontendadpp-12345 y las del usuario a
	// traves del token
	// el refresh es para que lo actualice antes dee que caduque
	authorizedGrantTypes("password", "refresh_token").
	// cuando va a refrescar el token, 1 hora
	accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600)
	;

    }

    /**
     * Configure. esta relacionado al endpoint de oaut2 del servidor que se encarga
     * de generar el token configuramos el authenticationmanger ,el tokenstorage del
     * tipo jwt y el converter que guarda los datos en el token
     * 
     * @param endpoints the endpoints
     * @throws Exception the exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

	// cadena para unir los datos del token
	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

	// le añadimos los datos que obtenemos de la clase InfoAdicionalToken, y el
	// metodo accessTokenConverter()
	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
	// registramos el autentication manager
	endpoints.authenticationManager(authenticationManager)
	.tokenStore(tokenStore())
	.accessTokenConverter(accessTokenConverter()).
	// le añadimos la cadena
	tokenEnhancer(tokenEnhancerChain);

    }

    /**
     * Token store. creamos el token store que esw donde vamos a guardar los datos
     * como parametro necesita la clave que creamos en accessTokenConverter()
     * 
     * @return the token store
     */
    @Bean
    public JwtTokenStore tokenStore() {
	return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * Access token converter. asignamos la clave para crear el token y encriptarlo
     * la clave SigningKey tiene que ser la misma que la que usamos en zuul
     * @return the jwt access token converter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

	// creamos el token converter en el que viene la clave que vamos a usar para
	// encriptar
	JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
	//		jwtAccessTokenConverter.setSigningKey("codigo_secreto_aeiou");
	
	    //esta codificacion la usamos tambien en la autenticacion enAuthenticationManagerJwt del 
	    //gateway para que coincida
	
	jwtAccessTokenConverter.setSigningKey(Base64.getEncoder().encodeToString(env.getProperty("config.security.autenticacion.jwt.key").getBytes()));

	
	return jwtAccessTokenConverter;

    }

}
