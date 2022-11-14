package com.jgr.micro.cursos.models.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jgr.micro.cursos.models.controller.CursoController;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.repository.test.Datos;
import com.jgr.micro.cursos.service.ICursoService;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(CursoController.class)
class CursoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ICursoService cursoService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		when(cursoService.findById(1L)).thenReturn(Datos.crearCurso001());
		when(cursoService.findById(2L)).thenReturn(Datos.crearCurso002());
		when(cursoService.findById(3L)).thenReturn(Datos.crearCurso003());
		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(cursoService.findById(1L).get());
		cursos.add(cursoService.findById(2L).get());
		cursos.add(cursoService.findById(3L).get());
		when(cursoService.findAll()).thenReturn(cursos);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testDetalle() throws Exception {
		mockMvc.perform(get("/1").contentType(MediaType.APPLICATION_JSON))
	        // Then
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.nombre").value("Curso1"));

	        verify(cursoService).findById(1L);
	}
	@Test
	void testListar() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testListarPorNombre() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCrear() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEditar() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminar() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAsignarAlumnoCurso() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarRelacionAlumnoCurso() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAltaAlumnoCurso() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarCursoAlumnoId() {
//		fail("Not yet implemented"); // TODO
	}

}
