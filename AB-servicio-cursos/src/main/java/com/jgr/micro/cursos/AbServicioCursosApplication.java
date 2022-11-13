package com.jgr.micro.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Class AbServicioCursosApplication.
 * para swagger http://localhost:8002/swagger-ui/index.html 
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
//@EnableWebMvc//para que funcione swagger
@EntityScan({"com.jgr.alumnos.modelo.models",
	"com.jgr.micro.cursos.models.entity"
})

public class AbServicioCursosApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AbServicioCursosApplication.class, args);
	}

}
