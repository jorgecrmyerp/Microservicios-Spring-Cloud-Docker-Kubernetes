package com.microservicio.app.autenticacion.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.microservicio.app.autenticacion.oauth.services.IUsuarioService;
import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

import brave.Tracer;
import feign.FeignException;

/**
 * The Class AuthenticacionSuccessErrorHandler.
 * 
 * Gestiona los eventos de autenticacion correcta/incorrecta
 * 
 */
@Component
public class AuthenticacionSuccessErrorHandler implements AuthenticationEventPublisher {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(AuthenticacionSuccessErrorHandler.class);

    /**
     * The i usuario service. para actualizar el numero de intentos de login
     * fallidos
     */
    @Autowired
    private IUsuarioService iUsuarioService;
    
    /** The trace zipkin. */
    @Autowired
    private Tracer traceZipkin;

    /**
     * Publish authentication success. obtengo el usuario que se ha logado ok como
     * intenta validarlo con el clientid de la web, en ese caso no hago nada solo
     * valido el que exista en la bbdd
     *
     * @param authentication the authentication
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

	// si es el de la web no hago nada
	if (authentication.getDetails() instanceof WebAuthenticationDetails) {
	    return;
	}
	// esta es otra manera de hacerlo
	if (authentication.getName().equalsIgnoreCase("frontendadd")) {
	    return;
	}

	// obtengo el usuario
	UserDetails usuario = (UserDetails) authentication.getPrincipal();
	logger.info("obtengo datos del usuario OK->" + usuario.getUsername());

	Usuario usu = iUsuarioService.findByUsername(authentication.getName());
	if (usu.getIntentos() != null && usu.getIntentos() > 0) {
	    usu.setIntentos(0);
	    // usu.setEnabled(true);
	    iUsuarioService.update(usu, usu.getId());
	}
	authentication.getAuthorities().forEach(
		a -> logger.info("AuthenticacionSuccessErrorHandle authorities->".concat(a.getAuthority().toString())));
	
	
	traceZipkin.currentSpan().tag("microservicio.autenticacion.usuarios.AuthenticacionSuccessErrorHandler.publishAuthenticationSuccess",
		"autenticado ok->".concat(usu.getNombre()));

    }

    /**
     * Publish authentication failure. ko en el login,actualizamos el numero de
     * intentos ko para bloquearlo
     * 
     * @param exception      the exception
     * @param authentication the authentication
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
	
	logger.info("obtengo datos del usuario KO getLocalizedMessage->"
		+ exception.getLocalizedMessage().concat(exception.getLocalizedMessage()));
	logger.info("obtengo datos del usuario KO exception.getMessage()->"
		+ exception.getLocalizedMessage().concat(exception.getMessage()));

	// obtengo el usuario, voy sumando uno al contador hasta que cuando llegue a
	// tres lo bloqueo

	try {
	    Usuario usu = iUsuarioService.findByUsername(authentication.getName());
	    if (usu.getIntentos() == null) {
		usu.setIntentos(0);
	    }
	    usu.setIntentos(usu.getIntentos() + 1);
	    // al tercer intento lo bloqueamos
	    usu.setEnabled(usu.getIntentos() >= 3 ? false : true);
	    iUsuarioService.update(usu, usu.getId());

	    if (!usu.getEnabled()) {
		 traceZipkin.currentSpan().tag("microservicio.autenticacion.usuarios.AuthenticacionSuccessErrorHandler.publishAuthenticationFailure",
				"usuario bloqueado->".concat(usu.getNombre()));
		logger.error(String.format("Usuario %s bloqueado por intentos erroneos", usu.getNombre()));

	    }

	} // si el usuario no existe
	catch (FeignException e) {
	    traceZipkin.currentSpan().tag("microservicio.autenticacion.usuarios.AuthenticacionSuccessErrorHandler.publishAuthenticationFailure",
			"usuario NO existe".concat(authentication.getName()));
	    logger.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
	}

    }

}
