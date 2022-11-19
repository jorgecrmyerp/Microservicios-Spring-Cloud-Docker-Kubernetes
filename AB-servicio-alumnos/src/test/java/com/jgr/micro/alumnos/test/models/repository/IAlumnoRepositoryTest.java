/**
 * 
 */
package com.jgr.micro.alumnos.test.models.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.repository.IAlumnoRepository;
import com.jgr.micro.alumnos.test.Datos;

/**
 * @author JORGE
 * 
 * en esta no usamos el acceso a bbdd,usamos los de la clase Datos
 *
 */
@SpringBootTest
class IAlumnoRepositoryTest {

	
	

	@MockBean
	private IAlumnoRepository alumnoRepository;

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
		when(alumnoRepository.findById(1L)).thenReturn(Datos.crearAlumno001());
		when(alumnoRepository.findById(2L)).thenReturn(Datos.crearAlumno002());
		when(alumnoRepository.findById(3L)).thenReturn(Datos.crearAlumno003());
		when(alumnoRepository.findByEmail("email3@mail.com")).thenReturn(Datos.crearAlumno003());
		when(alumnoRepository.existsByEmail("email1@mail.com")).thenReturn(true);
		when(alumnoRepository.findAll()).thenReturn(Datos.listaAlumnos());

		when(alumnoRepository.save(any(Alumno.class))).then(new Answer<Alumno>() {

			Long secuencia = 4L;

			@Override
			public Alumno answer(InvocationOnMock invocation) throws Throwable {
				Alumno al = invocation.getArgument(0);
				al.setId(secuencia++);
				return al;
			}

		});

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.models.repository.IAlumnoRepository#findByEmail(java.lang.String)}.
	 */
	@Test
	void testFindByEmail() {

		Optional<Alumno> al = alumnoRepository.findByEmail("email3@mail.com");

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoRepository.findById(3L).get(), al.get(), () -> "no son iguales");

	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.models.repository.IAlumnoRepository#porEmail(java.lang.String)}.
	 */
	@Test
	void testPorEmail() {

		Optional<Alumno> al = alumnoRepository.findByEmail("email3@mail.com");

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoRepository.findById(3L).get(), al.get(), () -> "no son iguales");
	}

	/**
	 * Test method for
	 * {@link com.jgr.micro.alumnos.models.repository.IAlumnoRepository#existsByEmail(java.lang.String)}.
	 */
	@Test
	void testExistsByEmail() {

		boolean existe = alumnoRepository.existsByEmail("email1@mail.com");
		assertTrue(existe, () -> "no ha devuelvto qeu exista");

	}

	/**
	 * Test method for
	 * {@link org.springframework.data.repository.CrudRepository#save(S)}.
	 */
	@Test
	void testSave() {
		Alumno al = Datos.crearAlumno001().get();
		al.setId(null);
		List<Alumno> alumnos = (List<Alumno>) alumnoRepository.findAll();

		//me quedo con el ultimo
		Alumno alUlt = alumnos.get(alumnos.size()-1);
		
		Alumno alNuev=alumnoRepository.save(al);
		
		assertNotNull(alNuev,()->"no lo ha dado de alta");
		assertEquals(alUlt.getId()+1,alNuev.getId(),()->"no coincide el id");
		
		

	}

	/**
	 * Test method for
	 * {@link org.springframework.data.repository.CrudRepository#findById(java.lang.Object)}.
	 */
	@Test
	void testFindById() {

		Optional<Alumno> al = alumnoRepository.findById(1L);

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoRepository.findById(1L).get(), al.get(), () -> "no son iguales");
		assertSame(alumnoRepository.findById(1L).get(), al.get(), () -> "no son iguales");
	}

	/**
	 * Test method for
	 * {@link org.springframework.data.repository.CrudRepository#findAll()}.
	 */
	@Test
	void testFindAll() {

		List<Alumno> alumnos = (List<Alumno>) alumnoRepository.findAll();

		Alumno al = alumnos.get(0);
		Alumno alUno = alumnoRepository.findById(1L).get();
		

		verify(alumnoRepository, times(1)).findById(anyLong());

		assertAll(() -> assertEquals(3, alumnos.size(), () -> "no ha devuelto 3 ocurrencias"),
				() -> assertNotNull(alUno, () -> "no ha encontrado alumno"),
				() -> assertEquals(alUno.getId(), al.getId(), () -> "no son iguales"),
				() -> assertEquals(alUno.getNombre(), al.getNombre(), () -> "no son iguales"),
				() -> assertEquals(alUno.getId(), al.getId(), () -> "no son iguales")

		);

	}

}
