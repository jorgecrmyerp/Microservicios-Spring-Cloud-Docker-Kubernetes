package com.jgr.micro.cursos.service.test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.cursos.error.IdNoEncontradoException;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.entity.CursoAlumno;
import com.jgr.micro.cursos.models.repository.ICursoRepository;

@DataJpaTest
class CursoServiceImplTest {
	@Autowired
	private ICursoRepository cursoRepository;

	@Test
	void testFindAll() {
		
		List<Curso> lista = new ArrayList<Curso>();
		lista= (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista,()->"lista de cursos vacia");		
		int longitud = lista.size();
		assertEquals(longitud,5,() -> "la longitud no es 5->"+ longitud);
		

	}

	@Test
	void testFindAllSortedByNombreDesc() {
		List<Curso> lista = new ArrayList<Curso>();
		lista= (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista,()->"lista de cursos vacia");		
		int longitud = lista.size();
		assertEquals(longitud,5,() -> "la longitud no es 5 en ordenados desc->"+ longitud);
	}

	@Test
	void testFindById() {

		Optional<Curso> curso = cursoRepository.findById(1L);
		assertTrue(curso.isPresent(), () -> "curso 1 no existe");
		assertEquals("Curso1", curso.get().getNombre(), () -> "no coincide el curso");
		List<Alumno> alumnos =new ArrayList<Alumno>();
		alumnos = curso.get().getAlumnos();		
		List<CursoAlumno> cursoAlumno = curso.get().getCursoAlumnos();
		int longi= cursoAlumno.size();
		assertEquals(1,longi,()->" no hay un cursoalumno en el curso"+ longi);
		int longi2 = curso.get().getAlumnos().size();
		
		assertNotEquals(1,longi2,()->"longidut de array alumno es 1->"+longi2);
		

	}

	@Test
	void testSave() {
		Curso cursoa = new Curso();
		//cursoa.setId(99L);
		cursoa.setNombre("NuevoCurso");
		CursoAlumno cursoAl = new CursoAlumno();
		cursoAl.setAlumnoId(2L);
		cursoAl.setId(6L);
		//cursoa.addCursoAlumno(cursoAl);
		cursoRepository.save(cursoa);
		cursoa.addCursoAlumno(cursoAl);
		cursoRepository.save(cursoa);
		
		
		List<Curso> lista = new ArrayList<Curso>();
		lista= (List<Curso>) cursoRepository.findAll();
		lista= (List<Curso>) cursoRepository.findAll();
		assertNotNull(lista,()->"lista de cursos vacia");		
		int longitud = lista.size();
		assertEquals(longitud,6,() -> "la longitud no es 6->"+ longitud);
		
		Optional<Curso> curso = cursoRepository.findById(6L);
		assertTrue(curso.isPresent(), () -> "curso 99 no existe");
		assertEquals("NuevoCurso", curso.get().getNombre(), () -> "no coincide el curso");
		List<CursoAlumno> cursoAlumnos =new ArrayList<CursoAlumno>();
		cursoAlumnos = curso.get().getCursoAlumnos();
		int longi = cursoAlumnos.size();
		
		assertEquals(1,longi,()->" no hay 1 alumnos en el curso"+ longi);
		
		long idAlumno = cursoAlumnos.get(0).getId();
		assertEquals(2L,idAlumno,()->"el id del alumno no es 99->"+idAlumno);
		
		

	}

	@Test
	void testDelete() {
		
		cursoRepository.deleteById(1L);
		 assertThrows(NoSuchElementException.class, () -> {
//           cuentaRepository.findByPersona("John").orElseThrow();
			 cursoRepository.findById(1L).orElseThrow();
       });
		 
		 assertEquals(4, ((List)cursoRepository.findAll()).size());
	}

	@Test
	void testAsignarAlumnoCurso() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	void testAltaAlumnoCurso() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarRelacionAlumnoCurso() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	
//este llama al microservicio alumnos...	
	void testAlumnosCursoporIdCurso() {
	//	fail("Not yet implemented"); // TODO
	}

	@Test
	void testEliminarCursoUsuarioPorId() {
		//fail("Not yet implemented"); // TODO
	}

}
