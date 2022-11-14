package com.jgr.micro.alumnos.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.service.IAlumnoService;





@WebMvcTest(AlumnoController.class)
class AlumnoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IAlumnoService alumnoService;

	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		when(alumnoService.findById(1L)).thenReturn(Datos.crearAlumno001());
		when(alumnoService.findById(2L)).thenReturn(Datos.crearAlumno002());
		when(alumnoService.findById(3L)).thenReturn(Datos.crearAlumno002());
		List<Alumno> alumnos = new ArrayList<>();
		alumnos.add(Datos.crearAlumno001().orElseThrow());
		alumnos.add(Datos.crearAlumno002().orElseThrow());
		alumnos.add(Datos.crearAlumno003().orElseThrow());
		when(alumnoService.findAll()).thenReturn(alumnos);
		
		
		
			
	}

	@Test
	void testObtenerAlumnoPorIdPathVariable() throws Exception {
		
		mvc.perform(get("/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.nombre").value("Alumno1"));

		// 1 vez aqui y otra vez dentro del controlador
		verify(alumnoService, times(1)).findById(1L);
		
		mvc.perform(get("/999").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		
		// 1 vez aqui y otra vez dentro del controlador
		verify(alumnoService, times(1)).findById(1L);
		
		
		

	}

	@Test
	void testListarAlumnos() throws JsonProcessingException, Exception {

		List<Alumno> alumnos = (List<Alumno>) alumnoService.findAll();

		mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
		// Then
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].nombre").value("Alumno1"))
		.andExpect(jsonPath("$[0].password").value("Password1")).andExpect(jsonPath("$[0].id").value(1L))
		.andExpect(jsonPath("$[1].email").value("email2@mail.com")).andExpect(jsonPath("$", hasSize(3)))
		.andExpect(content().json(objectMapper.writeValueAsString(alumnos)));
		assertEquals(3, ((List) alumnoService.findAll()).size());
		verify(alumnoService, times(3)).findAll();

	}

	

	@Test
	void testActualizaAlumno() throws JsonProcessingException, Exception {
		
		Alumno al = alumnoService.findById(1L).get();
		
		when(alumnoService.save(any())).then(invocation -> {
			Alumno al2 = invocation.getArgument(0);
			al2.setNombre("NombreModificado");
			return al2;
		});
		
		
		mvc.perform(put("/1").contentType(MediaType.APPLICATION_JSON)
				 .content(objectMapper.writeValueAsString(al)))
		  .andExpect(status().isCreated())
		  .andExpect(jsonPath("$.nombre", is("NombreModificado")));
		
		
		
	}

	@Test
	void testAltaAlumno() throws JsonProcessingException, Exception {
		
		Alumno al = new Alumno(null,"Nombre4","email4@mail.com","Password4",new Date());
		
		when(alumnoService.save(any())).then(invocation -> {
			Alumno al2 = invocation.getArgument(0);
			al2.setId(4L);
			return al2;
		});
		
		 mvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(al)))
	        // Then
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.nombre", is("Nombre4")))
	                .andExpect(jsonPath("$.id", is(4)));
		  verify(alumnoService).save(any());
	        

	}

	@Test
	void testBorraAlumnoId() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testBorraAlumnoAlumno() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAlumnosCursoRequestParam() {
		fail("Not yet implemented"); // TODO
	}

}
