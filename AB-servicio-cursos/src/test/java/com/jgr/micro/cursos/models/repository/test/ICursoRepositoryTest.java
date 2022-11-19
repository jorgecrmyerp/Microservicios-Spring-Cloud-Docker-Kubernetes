package com.jgr.micro.cursos.models.repository.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.repository.ICursoRepository;


@SpringBootTest
class ICursoRepositoryTest {
	
	
	@MockBean
	private ICursoRepository cursoRepository;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		when(cursoRepository.findById(1L)).thenReturn(Datos.crearCurso001());
		when(cursoRepository.findById(2L)).thenReturn(Datos.crearCurso002());
		when(cursoRepository.findById(3L)).thenReturn(Datos.crearCurso003());
		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(cursoRepository.findById(1L).get());
		cursos.add(cursoRepository.findById(2L).get());
		cursos.add(cursoRepository.findById(3L).get());
		when(cursoRepository.findAll()).thenReturn(cursos);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindAllSort() {
		List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
		assertNotNull(cursos,()->"PUES ES NULO");
		assertEquals(cursos.size(),3,()->"no coincide longitud");
	}

	@Test
	void testFindAllPageable() {
		List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
		assertNotNull(cursos,()->"PUES ES NULO");
		assertEquals(cursos.size(),3,()->"no coincide longitud");
	}

	@Test
	void testSave() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	void testFindById() {
		assertNotNull(cursoRepository.findById(1L),()->"PUES ES NULO");
		String nombre = cursoRepository.findById(1L).get().getNombre();
		assertTrue(nombre.equals("Curso1"),
				()->"Pues no es el mismo nombre->" + nombre);
		verify(cursoRepository, times(3)).findById(1L);
		//son el mismo objeto
		Curso curso = (Curso) ((List<Curso>) cursoRepository.findAll()).get(0);
		
		assertSame(cursoRepository.findById(1L).get(),curso,()->"pues no son iguales");
	}

	@Test
	void testFindAll() {
		
		List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
		assertNotNull(cursos,()->"PUES ES NULO");
		assertEquals(cursos.size(),3,()->"no coincide longitud");
		
	}

	@Test
	void testDelete() {
//		fail("Not yet implemented"); // TODO
	}

}
