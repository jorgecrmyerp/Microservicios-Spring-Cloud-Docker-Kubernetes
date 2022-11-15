package com.jgr.micro.alumnos.test.models.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.repository.IAlumnoRepository;

//@DataJpaTest->da error de configuracion¿????

//
///**
// * en esta usamos la bbdd en memoria
// * si da error asteriscar @EnableWebMvc en la clase MicroAlumnosApplication
// * https://stackoverflow.com/questions/56248954/spring-boot-datajpatest-causing-no-servletcontext-set-error
// * *


@EnableAutoConfiguration
@DataJpaTest

//para ejecutarlo en Coverage as->CoverageConfiguration->Include and Exclude Tags ponerlo o quitarlo
@Tag("integracion-jpa")
class IAlumnoRepositoryJPATestIntegracion {

	@Autowired
	private IAlumnoRepository alumnoRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindByEmail() {

		Optional<Alumno> al = alumnoRepository.findByEmail(alumnoRepository.findById(1L).get().getEmail());
		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoRepository.findById(1L).get(), al.get(), () -> "no son iguales");
	}

	@Test
	void testPorEmail() {
		
		Optional<Alumno> al = alumnoRepository.findById(1L);
		String email =al.get().getEmail();
		Optional<Alumno> al2 = alumnoRepository.findByEmail(email);		
		assertEquals(al,al2,()->"no son iguales");
		
		
	}

	@Test
	void testExistsByEmail() {
		
		Optional<Alumno> al = alumnoRepository.findById(1L);
		assertNotNull(al,()->"no existe el id 1");
		
		boolean existe = alumnoRepository.existsByEmail(al.get().getEmail());
		assertTrue(existe,()->"no encontrado por email");
		
		
	}

	@Test
	void testSave() {
		
		
		List<Alumno> alumnos = (List<Alumno>) alumnoRepository.findAll();
		
		
		String mail = "Emailnuevo@mail.com";
		Alumno al = new Alumno();
		al.setNombre("NOMBREALUMNOALTA1");
		al.setPassword("PASSWORDALUMNOALTA1");
		al.setEmail(mail);
		
		int longitud = alumnos.size();
		Alumno ultAlumno = alumnos.get(longitud-1);
		
		alumnoRepository.save(al);
		alumnos = (List<Alumno>) alumnoRepository.findAll();
		
		assertEquals(longitud+1,alumnos.size(),()->"no ha recuperado uno mas");
		assertEquals(ultAlumno.getId()+1,alumnos.get(alumnos.size()-1).getId(),()->"no coincide el id");
		
		
	}

	@Test
	void testFindById() {
		Optional<Alumno> al = alumnoRepository.findById(1L);
		assertNotNull(al.get(),()->"alumno es nulo");
	}

	@Test
	void testExistsById() {
		
		Optional<Alumno> al = alumnoRepository.findById(1L);
		boolean existe = alumnoRepository.existsById(1L);
		assertTrue(existe,()->"deberia existir");
		
	}

	@Test
	void testFindAll() {
		
		List<Alumno> alumnos = (List<Alumno>) alumnoRepository.findAll();
		
		//List<Integer> numeros = new ArrayList<>();
		
		List<Long> numeros = alumnos
				.stream()
				.map(al->al.getId()).collect(Collectors.toList());
		
		
		List<Alumno> alumnos2 = (List<Alumno>) alumnoRepository.findAllById(numeros);
		
		assertEquals(alumnos, alumnos2,()->"no son iguales las listas");
		
		
		
	}

}
