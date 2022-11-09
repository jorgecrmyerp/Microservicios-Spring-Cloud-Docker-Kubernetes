/*
 * Yo 24 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.validacion;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.microservicio.app.commons.usuario.seguridad.models.entity.Role;
import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

/**
 * The Class RepositoryConfig.
 * ESTA CLASE ES PARA CONFIGURAR LO RELACIONADO CON EL REPOSITORIO
 * COMO POR EJEMPLO PARA QUE SE VEA EL ID
 */
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer{



    /**
     * Configure repository rest configuration.
     *para que salga el id cuando hacemos la consulta por el postman
     * @param config the config
     * @param cors   the cors
     */
    //esto
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

	config.exposeIdsFor(Usuario.class,Role.class);
    }



}
