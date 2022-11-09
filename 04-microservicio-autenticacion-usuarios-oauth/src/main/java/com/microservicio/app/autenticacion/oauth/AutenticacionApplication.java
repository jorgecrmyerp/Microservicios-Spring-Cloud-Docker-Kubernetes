/*
 * Yo 24 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class AutenticacionApplication.
 *  AUTENTICACION DE USUARIOS,verificamos su identidad.
 *  GENERA EL TOKEN poner estas dependencias para oauth y jwp importante la version 2.3.8 
 * porque desde la 2.4 se quita el componente para generar el token la otra opcion es usar una
 * libreria externa a spring. estas son las que se incluyen a mano. 
 * Jaxb solo si
 * se usa java >=11 
 * <dependency>
 * <groupId>org.springframework.security.oauth</groupId>
 * <artifactId>spring-security-oauth2</artifactId>
 * <version>2.3.8.RELEASE</version> </dependency> <dependency>
 * <groupId>org.springframework.security</groupId>
 * <artifactId>spring-security-jwt</artifactId>
 *  <version>1.1.1.RELEASE</version>
 * </dependency>
 *
 * <dependency> <groupId>org.glassfish.jaxb</groupId>
 * <artifactId>jaxb-runtime</artifactId> </dependency> <!-- FIN DE LAS INCLUIDAS
 * MANUALMENTE--> incluimos el commons de usuarios como accede a bbdd el
 * commons, tenemos que deshabilitarlo como en items pero lo quitamos en el pom
 * <dependency>
 *			<groupId>com.microservicio.app.commons.usuario.validacion</groupId>
 *			<artifactId>00-spring-servicio-commons-usuario-validacion</artifactId>
 *			<version>V1</version>
 *			<exclusions>
 *				<exclusion>
 *					<groupId>org.springframework.boot</groupId>
 *					<artifactId>spring-boot-starter-data-jpa</artifactId>
 *				</exclusion>
 *			</exclusions>
 *		</dependency>
 *EurekaClient lo incluimos de manera explicita porque ya esta en el pom
 *EnableFeignClients para comunicar este microservicio con el de validacion de usuarios
 *
 *FUNCIONAMIENTO LOGICO DE AUTENTICACION/VALIDACION
 *https://www.udemy.com/course/microservicios-con-spring-boot-y-spring-cloud/learn/lecture/15373422#questions/8743796
 *
 *el token que genera lo podemos verificar en https://jwt.io/
 *
 *el endpoint es 
 *localhost:8090/api/autenticacion/oauth/token
 *
 *ver pagina https://www.adictosaltrabajo.com/2020/05/21/introduccion-a-spring-security/
 *
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AutenticacionApplication implements CommandLineRunner{

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	SpringApplication.run(AutenticacionApplication.class, args);
    }


    /** The b crypt password encoder. */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Run.
     *
     * @param args the args
     * @throws Exception the exception
     */
    //creamos este metodo para encriptar las password de ejemplo
    @Override
    public void run(String... args) throws Exception {
	String password ="password";

	for (int i = 0; i < 4; i++) {
	    String passwordCreada = bCryptPasswordEncoder.encode(password);
	    System.out.println("contraseña 'password' encriptada->".concat(passwordCreada));

	}

    }

}
