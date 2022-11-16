package com.jgr.micro.cursos.service.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.entity.CursoAlumno;
import com.jgr.micro.cursos.models.repository.ICursoRepository;

//si falla @DataJpaTest asteriscar @EnableWebMvc//para que funcione swagger en el application
//si falla por lazyload en el properties test ver que no este asteriscado
//spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
//@DataJpaTest
@SpringBootTest
class CursoServiceImplJPATestIntegracion {
	
	@Autowired
	private ICursoRepository cursoRepository;

	@Test
	void testFindAll() {

		List<Curso> lista = new ArrayList<Curso>();
		lista = (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista, () -> "lista de cursos vacia");
		int longitud = lista.size();
		assertEquals(longitud, 5, () -> "la longitud no es 5->" + longitud);

	}

	@Test
	void testFindAllSortedByNombreDesc() {
		List<Curso> lista = new ArrayList<Curso>();
		lista = (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista, () -> "lista de cursos vacia");
		int longitud = lista.size();
		assertEquals(longitud, 5, () -> "la longitud no es 5 en ordenados desc->" + longitud);
	}

	@Test
	void testFindById() {

		Optional<Curso> curso = cursoRepository.findById(1L);
		assertTrue(curso.isPresent(), () -> "curso 1 no existe");
		assertEquals("Curso1", curso.get().getNombre(), () -> "no coincide el curso");
		List<Alumno> alumnos = curso.get().getAlumnos();

		List<CursoAlumno> cursoAlumno = curso.get().getCursoAlumnos();
		int longi = cursoAlumno.size();
		assertEquals(1, longi, () -> " no hay un cursoalumno en el curso" + longi);
		int longi2 = curso.get().getAlumnos().size();

		assertNotEquals(1, longi2, () -> "longidut de array alumno es 1->" + longi2);

	}

	@Test
	void testSave() {
		Curso cursoa = new Curso();
		// cursoa.setId(99L);
		cursoa.setNombre("NuevoCurso");
		CursoAlumno cursoAl = new CursoAlumno();
		cursoAl.setAlumnoId(2L);
		cursoAl.setId(6L);
		// cursoa.addCursoAlumno(cursoAl);
		cursoRepository.save(cursoa);
		cursoa.addCursoAlumno(cursoAl);
		cursoRepository.save(cursoa);

		List<Curso> lista = new ArrayList<Curso>();
		lista = (List<Curso>) cursoRepository.findAll();
		lista = (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista, () -> "lista de cursos vacia");
		int longitud = lista.size();
		assertEquals(longitud, 6, () -> "la longitud no es 6->" + longitud);

		Optional<Curso> curso = cursoRepository.findById(6L);
		assertTrue(curso.isPresent(), () -> "curso 99 no existe");
		assertEquals("NuevoCurso", curso.get().getNombre(), () -> "no coincide el curso");
		List<CursoAlumno> cursoAlumnos = new ArrayList<CursoAlumno>();
		cursoAlumnos = curso.get().getCursoAlumnos();
		int longi = cursoAlumnos.size();

		assertEquals(1, longi, () -> " no hay 1 alumnos en el curso" + longi);

		long idAlumno = cursoAlumnos.get(0).getId();
		assertEquals(2L, idAlumno, () -> "el id del alumno no es 99->" + idAlumno);

	}

	@Test
	void testDelete() {

		int longitudA = ((List<Curso>) cursoRepository.findAll()).size();
		List<Curso> cursoAntes = ((List<Curso>) cursoRepository.findAll());

		cursoAntes.forEach(System.out::println);

		cursoRepository.deleteById(1L);
		// lanza error de que no lo encuentra
		assertThrows(NoSuchElementException.class, () -> {
			cursoRepository.findById(1L).orElseThrow();
		});

		List<Curso> cursoDespues = ((List<Curso>) cursoRepository.findAll());

		cursoDespues.forEach(System.out::println);

		int longitudB = ((List<Curso>) cursoRepository.findAll()).size();
		assertEquals(longitudA - 1, longitudB, () -> "no ha ido bien el delete");
		//

	}

	@Test
	void testAsignarAlumnoCurso() {
		// fail("Not yet implemented"); // TODO
	}

	@Test
	void testAltaAlumnoCurso() {
		// fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarRelacionAlumnoCurso() {
		// fail("Not yet implemented"); // TODO
	}

	@Test

	// este llama al microservicio alumnos...
	void testAlumnosCursoporIdCurso() {
		// fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarCursoUsuarioPorId() {
		// fail("Not yet implemented"); // TODO
	}

}
