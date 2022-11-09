package com.app.eureka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Este es el servidor eureka,realiza el balanceo de carga,en el properties del
 * resto de aplicaciones ponemos el puerto que tenemos aqui definido para que se
 * registren.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

	/**
	 * Run.
	 *
	 * @param appContext the app context
	 * @return the command line runner
	 */
	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {

			String[] beans = appContext.getBeanDefinitionNames();

			// Arrays.stream(beans).sorted().forEach(System.out::println);
			/*
			 * System.out.println(appContext.getApplicationName());
			 * System.out.println(appContext.getClassLoader());
			 * System.out.println(appContext.getEnvironment());
			 * System.out.println(appContext.getId());
			 * System.out.println(appContext.getParent());
			 * System.out.println(appContext.getStartupDate());
			 */

		};
	}

}
