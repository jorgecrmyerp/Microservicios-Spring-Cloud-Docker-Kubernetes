package com.app.spring.gateway.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The Class MicroservicioGatewayApplication.
 * Sustituye a Zuul como punto de entrada, en este se definen filtros para los microservicios y para acceder
 * a ellos se pone la ip que definimos en el properties
 * Se conecta a Eureka como si fuera otro microservicio.
 * implementamos la seguridad con https://github.com/jwtk/jjwt,OAuth2 y JWT solo funcionan en zuul,estan deprecated
 */
@EnableEurekaClient
@SpringBootApplication
public class MicroservicioGatewayApplication {
	
	

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	SpringApplication.run(MicroservicioGatewayApplication.class, args);
	
	
    }

}
