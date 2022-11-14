package com.jgr.micro.alumnos.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.service.IAlumnoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;





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
	void testListarAlumnos() throws JsonProcessingException, Exception {
		
		List<Alumno> alumnos = (List<Alumno>) alumnoService.findAll();
		
		mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].persona").value("Andrés"))
                .andExpect(jsonPath("$[1].persona").value("Jhon"))
                .andExpect(jsonPath("$[0].saldo").value("1000"))
                .andExpect(jsonPath("$[1].saldo").value("2000"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(alumnos)));

        verify(alumnoService).findAll();
		
	}

	@Test
	void testObtenerAlumnoPorIdPathVariable() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testObtenerAlumnoPorIdRequestParam() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testActualizaAlumno() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAltaAlumno() {
		fail("Not yet implemented"); // TODO
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
