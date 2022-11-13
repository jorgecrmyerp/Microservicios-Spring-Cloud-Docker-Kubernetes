package com.jgr.micro.cursos.models.repository.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.entity.CursoAlumno;

public class Datos {
	
	public static Optional<Curso> crearCurso001() {
		CursoAlumno cursoAlumno = new CursoAlumno();
		List<CursoAlumno> cursoAlumnos = new ArrayList<CursoAlumno>();

		cursoAlumno.setAlumnoId(1L);
		cursoAlumno.setId(1L);

		Alumno alumno = new Alumno();
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumno.setId(1L);
		alumno.setNombre("Alumno1");
		alumno.setPassword("Password1");
		alumno.setEmail("email1@mail.com");
		alumno.setCreateAt(new Date());

		cursoAlumno.setAlumnoId(1L);
		cursoAlumno.setId(1L);

		cursoAlumnos.add(cursoAlumno);
		alumnos.add(alumno);

		return Optional.ofNullable(new Curso(1L, "Curso1", new Date(), cursoAlumnos, alumnos));
	}
	
	public static Optional<Curso> crearCurso002() {
		CursoAlumno cursoAlumno = new CursoAlumno();
		List<CursoAlumno> cursoAlumnos = new ArrayList<CursoAlumno>();
		
		cursoAlumno.setAlumnoId(1L);
		cursoAlumno.setId(1L);
		
		Alumno alumno = new Alumno();
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumno.setId(1L);
		alumno.setNombre("Alumno2");
		alumno.setPassword("Password2");
		alumno.setEmail("email2@mail.com");
		alumno.setCreateAt(new Date());
		
		cursoAlumno.setAlumnoId(2L);
		cursoAlumno.setId(2L);
		
		cursoAlumnos.add(cursoAlumno);
		alumnos.add(alumno);
		
		return Optional.of(new Curso(2L, "Curso2", new Date(), cursoAlumnos, alumnos));
	}
	public static Optional<Curso> crearCurso003() {
		CursoAlumno cursoAlumno = new CursoAlumno();
		List<CursoAlumno> cursoAlumnos = new ArrayList<CursoAlumno>();
		
		cursoAlumno.setAlumnoId(3L);
		cursoAlumno.setId(3L);
		
		Alumno alumno = new Alumno();
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumno.setId(3L);
		alumno.setNombre("Alumno3");
		alumno.setPassword("Password3");
		alumno.setEmail("email3@mail.com");
		alumno.setCreateAt(new Date());
		
		cursoAlumno.setAlumnoId(3L);
		cursoAlumno.setId(3L);
		
		cursoAlumnos.add(cursoAlumno);
		alumnos.add(alumno);
		
		return Optional.ofNullable(new Curso(3L, "Curso3", new Date(), cursoAlumnos, alumnos));
	}

}
