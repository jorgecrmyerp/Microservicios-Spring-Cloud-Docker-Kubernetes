package com.jgr.micro.alumnos.test.models.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.repository.IAlumnoRepository;
import com.jgr.micro.alumnos.models.service.IAlumnoService;
import com.jgr.micro.alumnos.test.Datos;


@SpringBootTest
class AlumnoServiceImplTest {
	

	@Autowired
	private IAlumnoService alumnoService;
	
	@MockBean
	private IAlumnoRepository alumnoRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		when(alumnoService.findById(1L)).thenReturn(Datos.crearAlumno001());
		when(alumnoService.findById(2L)).thenReturn(Datos.crearAlumno002());
		when(alumnoService.findById(3L)).thenReturn(Datos.crearAlumno003());
		when(alumnoService.findByEmail("email3@mail.com")).thenReturn(Datos.crearAlumno003());
		when(alumnoService.existsByEmail("email1@mail.com")).thenReturn(true);
		when(alumnoService.findAll()).thenReturn(Datos.listaAlumnos());

		when(alumnoService.save(any(Alumno.class))).then(new Answer<Alumno>() {

			Long secuencia = 4L;

			@Override
			public Alumno answer(InvocationOnMock invocation) throws Throwable {
				Alumno al = invocation.getArgument(0);
				al.setId(secuencia++);
				return al;
			}

		});
	}

	@AfterEach
	void tearDown() throws Exception {
		
		
	}

	@Test
	void testFindAll() {

		List<Alumno> alumnos = (List<Alumno>) alumnoService.findAll();

		Alumno al = alumnos.get(0);
		Alumno alUno = alumnoService.findById(1L).get();
		
		//CURIOSO,NO DEJA HACERLO CON alumnoService,da error¿?????
		verify(alumnoRepository, times(1)).findById(anyLong());

		assertAll(() -> assertEquals(3, alumnos.size(), () -> "no ha devuelto 3 ocurrencias"),
				() -> assertNotNull(alUno, () -> "no ha encontrado alumno"),
				() -> assertEquals(alUno.getId(), al.getId(), () -> "no son iguales"),
				() -> assertEquals(alUno.getNombre(), al.getNombre(), () -> "no son iguales"),
				() -> assertEquals(alUno.getId(), al.getId(), () -> "no son iguales")

		);
	}

	@Test
	void testFindById() {

		Optional<Alumno> al = alumnoService.findById(1L);

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoService.findById(1L).get(), al.get(), () -> "no son iguales");
		assertSame(alumnoService.findById(1L).get(), al.get(), () -> "no son iguales");
	}

	@Test
	void testSave() {
		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		List<Alumno> alumnos = (List<Alumno>) alumnoService.findAll();

		//me quedo con el ultimo
		Alumno alUlt = alumnos.get(alumnos.size()-1);
		
		Alumno alNuev=alumnoService.save(al);
		
		assertNotNull(alNuev,()->"no lo ha dado de alta");
		assertEquals(alUlt.getId()+1,alNuev.getId(),()->"no coincide el id");
		
	}

	@Test
	void testDeleteLong() {
	//	fail("Not yet implemented"); // TODO
	}

	@Test
	void testDeleteAlumno() {
	//	fail("Not yet implemented"); // TODO
	}

	@Test
	void testFindByEmail() {

		Optional<Alumno> al = alumnoService.findByEmail("email3@mail.com");

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoService.findById(3L).get(), al.get(), () -> "no son iguales");
	}

	@Test
	void testPorEmail() {

		Optional<Alumno> al = alumnoService.findByEmail("email3@mail.com");

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoService.findById(3L).get(), al.get(), () -> "no son iguales");
	}

	@Test
	void testExistsByEmail() {
		boolean existe = alumnoService.existsByEmail("email1@mail.com");
		assertTrue(existe, () -> "no ha devuelvto qeu exista");

	}



}
