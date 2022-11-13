/*
 * Yo 19 sept 2022
 * Jorge
 * 
 */
package com.app.spring.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * The Class ConfigServerApplication para guardar la configuracion de los
 * microservicios. Se puede hacer en git o en local
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		SpringApplication.run(ConfigServerApplication.class, args);

	}

}
