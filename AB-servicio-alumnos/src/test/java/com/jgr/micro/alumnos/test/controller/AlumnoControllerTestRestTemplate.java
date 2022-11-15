package com.jgr.micro.alumnos.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.test.Datos;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlumnoControllerTestRestTemplate {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ObjectMapper objectMapper;

	@LocalServerPort
	private int puerto;

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
		
		assertEquals(8, longitudAntes);
		assertEquals(1L, listaAlumnos.get(0).getId(), () -> "no coincide el id");
		JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(listaAlumnos));
		assertEquals("ALUMNO1", json.get(0).path("nombre").asText());

	}

	@Test
	void testAltaAlumno() {

		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		al.setEmail("emailnuevo@mail.com");

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
		assertTrue(jsonString.contains("emailnuevo@mail.com"));
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
		assertEquals(HttpStatus.OK,alumnoGets.getStatusCode(),()->"no na devuelto ok");
		assertEquals(al,alumnoGets.getBody(),()->"El alumno es distinto");

		alumnoGets = testRestTemplate.getForEntity("/9999999", Alumno.class);		
		assertEquals(HttpStatus.NOT_FOUND,alumnoGets.getStatusCode(),()->"no na devuelto not found");

	}

	@Test
	void testActualizaAlumno() {

		ResponseEntity<Alumno[]> lista = testRestTemplate.getForEntity("/", Alumno[].class);

		List<Alumno> listaAlumnos = Arrays.asList(lista.getBody());
		int longitudA = listaAlumnos.size();

		Alumno al = listaAlumnos.get(0);
		al.setEmail("modificadoEmail@mailnuevo.com");

		// el put no devuelve nada¿??????
		testRestTemplate.put("/1", Alumno[].class, al, Alumno.class);

		// vuelvo a obtener la lista de alumnos

		lista = testRestTemplate.getForEntity("/", Alumno[].class);
		listaAlumnos=null;
		listaAlumnos = Arrays.asList(lista.getBody());
		Alumno mod = listaAlumnos.get(0);
		int longitudD = listaAlumnos.size();		
		assertEquals(longitudA,longitudD,()->"no concide longitud array");
		assertEquals(al.getId(),mod.getId(),()->"no coincide el id");
		assertEquals(al.getNombre(),mod.getNombre(),()->"no coincide el nombre");
		assertEquals("modificadoEmail@mailnuevo.com",mod.getEmail(),()->"no se ha modificado el email");
		

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
