/*
 * Yo 23 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.validacion.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

/**
 * The Interface IUsuarioDao. AÑADIDO EL STARTER REST REPOSITORY,con esto no
 * hace falta crear la clase de servicio para que acceda a la BBDD, tampoco es
 * necesario crear el controller lo que se hace es poner en la url el path:
 * 
 * localhost:8090/api/autenticacion/usuarios/search/findByUsername?username=user
 * se pone search/nombre de metodo?nombreparametro=valor
 */

@RepositoryRestResource(path = "usuarios")
//public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

    /**
     * Find by username. ruta de acceso
     * localhost:8090/api/autenticacion/usuarios/search/findByUsername?username=user
     * se pone search/nombre de metodo?nombreparametro=valor
     *
     * @param username the username
     * @return the usuario
     */
    public Usuario findByUsername(String username);

    /**
     * Find by username and email.
     * usando el RestResource para personalizar el path
     * path
     * localhost:8090/api/autenticacion/usuarios/search/buscar-username-email?username=user&email=usuario@email
     * @param username the username
     * @param email    the email
     * @return the usuario
     */
    @RestResource(path="buscar-username-email")
    public Usuario findByUsernameAndEmail(String username, String email);

    /**
     * Obtener por user name.
     *
     * @param username the username
     * @return the usuario
     */
    @RestResource(path="buscar-username")
    @Query("select u from Usuario u where u.username=?1")
    public Usuario obtenerPorUserName(String username);

    /**
     * Obtener por user name ignorando mayusculas/minusculas.
     *
     * @param role the role
     * @return lista usuarios por rol
     */	

    @Query("select u from Usuario u ,Role r where u.id =r.id AND r.nombreRol LIKE UPPER(concat('%', ?1,'%'))")
    public List<Usuario> buscaUsuariosPorNombreRol( String role);

}
