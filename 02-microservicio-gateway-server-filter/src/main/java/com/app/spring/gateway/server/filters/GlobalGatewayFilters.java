
package com.app.spring.gateway.server.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import brave.Tracer;
import reactor.core.publisher.Mono;


/**
 * The Class GlobalGatewayFilters.
 * Definimos los filtros para los microservicios que se van a conectar a traves de el
 */
@Component
public class GlobalGatewayFilters implements GlobalFilter,Ordered{

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(GlobalGatewayFilters.class);
    
    
    
    @Autowired
    private Tracer tracer;
    

    /**
     * Filter.
     *
     * @param exchange the exchange
     * @param chain the chain
     * @return the mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

	logger.info("ejecutando filtro PRE GlobalGatewayFilters");

	//MODIFICACION REQUEST(peticion) EN EL FILTRO PRE
	exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));

	//reactivo¿?
	return chain.filter(exchange).//esto es lo que se ejecuta en el prefiltro
		then(Mono.fromRunnable(()->{               //esto es lo que se ejecuta en el postfiltro
		    logger.info("ejecutando filtro POST GlobalGatewayFilters");
		    logger.info("exchange.getResponse()->"+ exchange.getResponse().toString());
		    logger.info("exchange..getRequest()METODO->"+ exchange.getRequest().getMethodValue());
		    logger.info("exchange...getRequest()RUTA->"+ exchange.getRequest().getPath());


		    //modificamos el header de la respuesta y le metemos el valor 123456

		    Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
			exchange.getResponse().getHeaders().add("token", valor);
		    });
		    //le pasamos una cookie a la respuesta
		    exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());

		    //convierte a texto plano la respuesta
		    //exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
		}));
    }


    /**
     * Gets the order.
     *orden de ejecucion, en este caso es el primero.
     *si se le pone otro valor puede dar error,porque intenta escribir en la respuesta
     *el pgm se queda pillado si le cambiamos el valor
     * @return the order
     */
    //orden de ejecucion del filtro
    @Override
    public int getOrder() {
	return 100;
    }





}
