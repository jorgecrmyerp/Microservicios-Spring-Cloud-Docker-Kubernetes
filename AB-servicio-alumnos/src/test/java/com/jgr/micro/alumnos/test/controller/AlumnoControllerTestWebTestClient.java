/**
 * 
 */
package com.jgr.micro.alumnos.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.test.Datos;

/**
 * @author JORGE incluido en el pom spring-boot-starter-webflux
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AlumnoControllerTestWebTestClient {

	@Autowired
	private WebTestClient webTestClient;

	private ObjectMapper objectMapper;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		objectMapper = new ObjectMapper();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {

	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.test.controller.AlumnoController#listarAlumnos()}.
	 */
	@Test
	@Order(1)
	void testListarAlumnos() {

		webTestClient.get().uri("").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBody().jsonPath("$[0].nombre").isEqualTo("ALUMNO1")
				.jsonPath("$[1].id").isEqualTo(2).jsonPath("$[2].password").isEqualTo("PASSWORD3").jsonPath("$")
				.isArray().jsonPath("$").value(hasSize(8));
	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.test.controller.AlumnoController#obtenerAlumnoPorIdPathVariable(java.lang.Long)}.
	 * 
	 * @param respuesta
	 * @throws JsonProcessingException
	 */
	@Test
	void testObtenerAlumnoPorIdPathVariable() throws JsonProcessingException {

		// aqui no se le envia un json,en el path enviamos el id del alumno

		webTestClient.get().uri("/2").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBody(Alumno.class).consumeWith(responde -> {
					Alumno al2 = responde.getResponseBody();
					assertNotNull(al2);
					assertEquals("ALUMNO2", al2.getNombre());
					assertEquals("PASSWORD2", al2.getPassword());
				});

	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.test.controller.AlumnoController#actualizaAlumno(com.jgr.alumnos.modelo.models.Alumno, org.springframework.validation.BindingResult, java.lang.Long)}.
	 */
	@Test
	void testActualizaAlumno() {
		 // given
        Alumno al = Datos.crearAlumno002().get();
        al.setNombre("NombreModificado");

        // when
        webTestClient.put().uri("/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(al)
                .exchange()
        // then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("NombreModificado");
    
	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.test.controller.AlumnoController#altaAlumno(com.jgr.alumnos.modelo.models.Alumno, org.springframework.validation.BindingResult)}.
	 */
	@Test
	void testAltaAlumno() {

		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		al.setEmail("otromail@mail.com");

		// valida con jsonpath
		webTestClient.post().uri("/").contentType(MediaType.APPLICATION_JSON).bodyValue(al).exchange().expectStatus()
				.isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody().jsonPath("$.id")
				.isEqualTo(9) // hace el insert en tabla,por eso el id es 9
		;

		al = Datos.crearAlumno002().get();
		al.setId(null);
		al.setEmail("otromail2@mail.com");

		// valida convirtiendo a objeto alumno
		webTestClient.post().uri("/").contentType(MediaType.APPLICATION_JSON).bodyValue(al).exchange().expectStatus()
				.isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody(Alumno.class)
				.consumeWith(responde -> {
					Alumno a = responde.getResponseBody();
					assertNotNull(a, () -> "alumno es nulo");
					assertEquals(10L, a.getId(), () -> "no coincide el id");
				});

	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.test.controller.AlumnoController#borraAlumnoId(java.lang.Long)}.
	 */
	@Test
	@Order(2)
	void testBorraAlumnoId() {

		// antes de borrar hay 8
		webTestClient.get().uri("/").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Alumno.class).hasSize(8);
		// borramos
		webTestClient.delete().uri("/1").exchange().expectStatus().
		is5xxServerError() //da error porque habria que levantar el clientefeign de curso para borrar
		;

/*esto no se puede probar porque no se puede borrar
		// ahora tiene que haber 7
		webTestClient.get().uri("/api/cuentas").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Alumno.class).hasSize(3);

		//el borrado no debe existir
		webTestClient.get().uri("/1").exchange()
//        .expectStatus().is5xxServerError();
				.expectStatus().isNotFound().expectBody().isEmpty();
*/
		
		
	}

	

}
