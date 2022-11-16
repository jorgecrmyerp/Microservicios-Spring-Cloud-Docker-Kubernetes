package com.jgr.micro.alumnos.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.test.Datos;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class AlumnoControllerTestRestTemplate {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		objectMapper = new ObjectMapper();
	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	@Order(1)
	void testListarAlumnos() throws JsonMappingException, JsonProcessingException {
		// miro a ver cuantos alumnos hay antes y despues para asegurarme de que lo ha
		// dado de alta
		ResponseEntity<Alumno[]> listaAntes = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnos = Arrays.asList(listaAntes.getBody());
		int longitudAntes = listaAlumnos.size();
		assertEquals(MediaType.APPLICATION_JSON, listaAntes.getHeaders().getContentType());
		assertEquals(HttpStatus.OK, listaAntes.getStatusCode());

		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		al.setEmail("emailnuevo@mail.com");
		// es un post,no envio url,le paso el objeto alumno y nos devuelve un json en
		// formato string
		ResponseEntity<Alumno> respuesta = testRestTemplate.postForEntity("/", al, Alumno.class);

		String jsonString = respuesta.getBody().toString();
		Alumno alumnoCreado = respuesta.getBody();
		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		assertNotNull(jsonString, () -> "la respuesta es nulo");
		assertTrue(jsonString.contains("emailnuevo@mail.com"));
		assertEquals("Alumno1", alumnoCreado.getNombre(), () -> "No es el mismo nombre");

		ResponseEntity<Alumno[]> listaDespues = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnosDespues = Arrays.asList(listaDespues.getBody());
		int longitudDespues = listaAlumnosDespues.size();
		assertEquals(longitudAntes + 1, longitudDespues, () -> "parece que no se ha dado de alta");
	
		assertEquals(1L, listaAlumnos.get(0).getId(), () -> "no coincide el id");
		// para pasar lo recibido a formato json y poder comparar como string
		JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(listaAlumnos));
		assertEquals("ALUMNO1", json.get(0).path("nombre").asText());

	}

	@Test
	void testAltaAlumno() {

		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		al.setEmail("email1nuevo@mail.com");

		// miro a ver cuantos alumnos hay antes y despues para asegurarme de que lo ha
		// dado de alta
		ResponseEntity<Alumno[]> listaAntes = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnos = Arrays.asList(listaAntes.getBody());
		int longitudAntes = listaAlumnos.size();

		// es un post,no envio url,le paso el objeto alumno y nos devuelve un json en
		// formato string
		ResponseEntity<Alumno> respuesta = testRestTemplate.postForEntity("/", al, Alumno.class);

		String jsonString = respuesta.getBody().toString();
		Alumno alumnoCreado = respuesta.getBody();
		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		assertNotNull(jsonString, () -> "la respuesta es nulo");
		assertTrue(jsonString.contains("email1nuevo@mail.com"));
		assertEquals("Alumno1", alumnoCreado.getNombre(), () -> "No es el mismo nombre");

		ResponseEntity<Alumno[]> listaDespues = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnosDespues = Arrays.asList(listaDespues.getBody());
		int longitudDespues = listaAlumnosDespues.size();
		assertEquals(longitudAntes + 1, longitudDespues, () -> "parece que no se ha dado de alta");

	}

	@Test

	void testObtenerAlumnoPorIdPathVariable() {

		ResponseEntity<Alumno[]> listaAntes = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnos = Arrays.asList(listaAntes.getBody());
		Alumno al = listaAlumnos.get(0);

		ResponseEntity<Alumno> alumnoGets = testRestTemplate.getForEntity("/1", Alumno.class);
		assertNotNull(alumnoGets);
		assertEquals(MediaType.APPLICATION_JSON, alumnoGets.getHeaders().getContentType());
		assertEquals(HttpStatus.OK, alumnoGets.getStatusCode(), () -> "no na devuelto ok");
		assertEquals(al, alumnoGets.getBody(), () -> "El alumno es distinto");

		alumnoGets = testRestTemplate.getForEntity("/9999999", Alumno.class);
		assertEquals(HttpStatus.NOT_FOUND, alumnoGets.getStatusCode(), () -> "no na devuelto not found");

	}

	@Test
	void testActualizaAlumno() {

		ResponseEntity<Alumno[]> lista = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnos = Arrays.asList(lista.getBody());
		int longitudA = listaAlumnos.size();

		Alumno al = listaAlumnos.get(0);
		al.setEmail("modificadoEmail@mailnuevo.com");

		// el put no devuelve nada¿??????
		testRestTemplate.put("/1", al, Alumno.class);

		lista = testRestTemplate.getForEntity("/", Alumno[].class);
		listaAlumnos = null;
		listaAlumnos = Arrays.asList(lista.getBody());
		Alumno mod = listaAlumnos.get(0);
		int longitudD = listaAlumnos.size();
		assertEquals(longitudA, longitudD, () -> "no concide longitud array");
		assertEquals(al.getId(), mod.getId(), () -> "no coincide el id");
		assertEquals(al.getNombre(), mod.getNombre(), () -> "no coincide el nombre");
		assertEquals("modificadoEmail@mailnuevo.com", mod.getEmail(), () -> "no se ha modificado el email");

	}

	@Test
	void testBorraAlumnoId() {

	}

	@Test
	void testBorraAlumnoAlumno() {

	}

	@Test
	void testAlumnosCursoRequestParam() {

	}

}
