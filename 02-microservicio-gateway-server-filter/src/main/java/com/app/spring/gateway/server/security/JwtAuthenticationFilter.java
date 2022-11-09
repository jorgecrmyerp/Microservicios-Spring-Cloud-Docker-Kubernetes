package com.app.spring.gateway.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * The Class JwtAuthenticationFilter.
 */

//////@ComponenT
public class JwtAuthenticationFilter implements WebFilter{

    /** The authentication manager. */
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;


    /**
     * Http client. 
     * DA ESTE ERROR
     * "status": 500,
     *"error": "Internal Server Error",
     *"message": "failed to resolve 'DESKTOP-AH16A08.mshome.net' after 2 queries ",
     *"requestId": "0129b3c4-7",
     *"trace": "java.net.UnknownHostException: failed to resolve 'DESKTOP-AH16A08.mshome.net' after 2 queries
     *\r\n\tat io.netty.resolver.dns.DnsResolveContext.finishResolve(DnsResolveContext.java:1046)\r\n\tSuppressed: 
     *reactor.core.publisher.FluxOnAssembly$OnAssemblyException: \n
     *Error has been observed at the following site(s):\n\t|_ checkpoint ⇢ 
     *com.app.spring.gateway.server.security.JwtAuthenticationFilter [DefaultWebFilterChain]\n\t|_ checkpoint ⇢ 
     *org.springframework.cloud.gateway.filter.WeightCalculatorWebFilter [DefaultWebFilterChain]\n\t|_ checkpoint ⇢ 
     *org.springframework.security.web.server.authorization.AuthorizationWebFilter 

     *https://github.com/reactor/reactor-netty/issues/1534
     * @return the http client
     */
    @Bean
    HttpClient httpClient(){
	return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }



    /**
     * Filter.
     * verificamos que tiene la cabecera y quitamos el string bearer
     * devuelve un Mono, que es un flujo reactivo con un solo elemento.
     * @param exchange the exchange
     * @param chain the chain
     * @return the mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
	return Mono.justOrEmpty(exchange.getRequest()
		.getHeaders()
		.getFirst(HttpHeaders.AUTHORIZATION))
		.filter(authHeader -> authHeader.startsWith("Bearer "))
		//si esta vacio lo devuelve vacio
		.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
		//map devuelve un objeto normal
		.map(token -> token.replace("Bearer ", ""))
		//flatmap devuelve un stream
		.flatMap(token -> authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
		.flatMap(authentication -> chain.filter(exchange)
			.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }

}
