/*
 * Yo 27 sept 2022
 * Jorge
 * 
 */
package com.app.spring.gateway.server.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/*
 * IMPORTANTE: TIENE QUE TENER GatewayFilterFactory en el nombre
 * 
 * hereda de AbstractGatewayFilterFactory y tiene como parametro una clase, que este caso llamamos 
 * Configuracion y que creamos aqui mismo.
 * 
 * */

/**
 * A factory for creating EjemploGatewayFilter objects.
 */
@Component
public class EjemploGatewayFilterFactory
	extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

    /**
     * Instantiates a new ejemplo gateway filter factory.
     */
    public EjemploGatewayFilterFactory() {
	super(Configuracion.class);
    }

    /**
     * Apply.
     *
     * @param config the config
     * @return the gateway filter
     */
    // interfaz funcional¿?,como parametro de entrada va a tener la clase
    // configuracion que creo aqui mismo
    @Override
    public GatewayFilter apply(Configuracion config) {
	return (exchange, chain) -> {

	    logger.info("PRE EjemploGatewayFilterFactory->" + config.mensaje);
	    return chain.filter(exchange)// este seria el pre
		    .then(Mono.fromRunnable(() -> {

			Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
			    exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
			});
			logger.info("POST EjemploGatewayFilterFactory->" + config.mensaje);

		    }));//
	};
    }

    /**
     * Shortcut field order.
     *
     * @return the list
     */
    // PARA OBTENER LOS PARAMETROS
    @Override
    public List<String> shortcutFieldOrder() {

	return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
    }

    /**
     * Name.
     *
     * @return the string
     */
    // para obtenerl el parametro por nombre,este no existe
    @Override
    public String name() {
	return "Ejemplo";
    }

    /**
     * The Class Configuracion.
     */
    public static class Configuracion {

	/** The mensaje. */
	private String mensaje;

	/** The cookie valor. */
	private String cookieValor;

	/** The cookie nombre. */
	private String cookieNombre;

	/**
	 * Gets the mensaje.
	 *
	 * @return the mensaje
	 */
	public String getMensaje() {
	    return this.mensaje;
	}

	/**
	 * Sets the mensaje.
	 *
	 * @param mensaje the new mensaje
	 */
	public void setMensaje(String mensaje) {
	    this.mensaje = mensaje;
	}

	/**
	 * Gets the cookie valor.
	 *
	 * @return the cookie valor
	 */
	public String getCookieValor() {
	    return this.cookieValor;
	}

	/**
	 * Sets the cookie valor.
	 *
	 * @param cookieValor the new cookie valor
	 */
	public void setCookieValor(String cookieValor) {
	    this.cookieValor = cookieValor;
	}

	/**
	 * Gets the cookie nombre.
	 *
	 * @return the cookie nombre
	 */
	public String getCookieNombre() {
	    return this.cookieNombre;
	}

	/**
	 * Sets the cookie nombre.
	 *
	 * @param cookieNombre the new cookie nombre
	 */
	public void setCookieNombre(String cookieNombre) {
	    this.cookieNombre = cookieNombre;
	}

    }

}
