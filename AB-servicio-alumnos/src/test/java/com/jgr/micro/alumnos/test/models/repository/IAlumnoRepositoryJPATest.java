package com.jgr.micro.alumnos.test.models.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.repository.IAlumnoRepository;

//@DataJpaTest

@SpringBootTest
class IAlumnoRepositoryJPATest {

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

		Optional<Alumno> al = alumnoRepository.findByEmail("email1@asdf.com");

		assertNotNull(al, () -> "alumno es nulo");
		assertEquals(alumnoRepository.findById(1L).get(), al.get(), () -> "no son iguales");
	}

	@Test
	void testPorEmail() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testExistsByEmail() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSave() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFindById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testExistsById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented"); // TODO
	}

}
