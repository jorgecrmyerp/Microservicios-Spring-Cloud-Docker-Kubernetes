/*
 * Yo 23 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.validacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


/**
 * The Class MicroservicioSeguridadApplication.
 * Gestion de usuarios/roles,VALIDA si existen o no
 * Aqui en vez de crear la capa de servicio añadimos el starter Rest Repositories
 * importo el commons usuarios con el entityscan
 *  *FUNCIONAMIENTO LOGICO DE AUTENTICACION/VALIDACION
 *https://www.udemy.com/course/microservicios-con-spring-boot-y-spring-cloud/learn/lecture/15373422#questions/8743796
 */
@SpringBootApplication
@EntityScan({"com.microservicio.app.commons.usuario.seguridad.models.entity"})
public class MicroservicioSeguridadApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MicroservicioSeguridadApplication.class, args);
	}

}
