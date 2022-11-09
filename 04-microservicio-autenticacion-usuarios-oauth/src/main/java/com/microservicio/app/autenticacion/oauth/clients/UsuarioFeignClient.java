/*
 * Yo 24 sept 2022
 * Jorge
 * 
 */
package com.microservicio.app.autenticacion.oauth.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicio.app.commons.usuario.seguridad.models.entity.Usuario;

/**
 * The Class UsuarioFeignClient. COMUNICACION con el microservicio validacion
 * usuarios FeignClient lo usamos para que se conecte con este
 * microservicio,usamos sus endpoint
 */
@FeignClient(name = "servicio-validacion")
public interface UsuarioFeignClient {

    /**
     * Find by username. se conecta con el microservicio validacion-usuarios ponemos
     * el endpoint que tiene en el microservicio destino como ahi usamosel
     * repositorio que genera automaticamente tiene que tener el search
     * 
     * @param username the username
     * @return the usuario
     */
    @GetMapping("/usuarios/search/findByUsername")
    public Usuario findByUsername(@RequestParam String username);

    /**
     * Find by username and email.
     *
     * @param username the username
     * @param email    the email
     * @return the usuario
     */
    @GetMapping("/usuarios/search/buscar-username-email")
    public Usuario findByUsernameAndEmail(@RequestParam String username, @RequestParam String email);

    /**
     * Obtener por user name.
     *
     * @param username the username
     * @return the usuario
     */
    @GetMapping("/usuarios/search/buscar-username")
    public Usuario obtenerPorUserName(@RequestParam String username);

    /**
     * Busca usuarios por nombre rol.
     *
     * @param role the role
     * @return the list
     */
    @GetMapping("/usuarios/search/buscaUsuariosPorNombreRol")
    public List<Usuario> buscaUsuariosPorNombreRol(@RequestParam String role);

    /**
     * Update del usuario.
     *
     * @param usuario the usuario
     * @param id      the id
     * @return the usuario
     */
    @PutMapping("/usuarios/{id}")
    public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id);

}
