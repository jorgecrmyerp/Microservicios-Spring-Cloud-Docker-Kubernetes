package com.jgr.micro.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Class MicroAlumnosApplication.
 *  * para swagger http://localhost:8001/swagger-ui/index.html
 */
@SpringBootApplication
@EnableFeignClients
@EnableWebMvc//para que funcione swagger
public class MicroAlumnosApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MicroAlumnosApplication.class, args);
	}

}
