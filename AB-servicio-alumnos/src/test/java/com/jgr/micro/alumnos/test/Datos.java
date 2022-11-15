package com.jgr.micro.alumnos.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.jgr.alumnos.modelo.models.Alumno;


public class Datos {
	
	public static Optional<Alumno> crearAlumno001() {		
		
		Alumno alumno = new Alumno();		
		alumno.setId(1L);
		alumno.setNombre("Alumno1");
		alumno.setPassword("Password1");
		alumno.setEmail("email1@mail.com");
		alumno.setCreateAt(new Date());

		return Optional.ofNullable(alumno);
	}
	
	public static Optional<Alumno> crearAlumno002() {
	
	
		Alumno alumno = new Alumno();
	
		alumno.setId(2L);
		alumno.setNombre("Alumno2");
		alumno.setPassword("Password2");
		alumno.setEmail("email2@mail.com");
		alumno.setCreateAt(new Date());
		
		return Optional.of(alumno);
	}
	public static Optional<Alumno> crearAlumno003() {
		
		Alumno alumno = new Alumno();
		
		alumno.setId(3L);
		alumno.setNombre("Alumno3");
		alumno.setPassword("Password3");
		alumno.setEmail("email3@mail.com");
		alumno.setCreateAt(new Date());
		return Optional.ofNullable(alumno);
	}
	
	public static Iterable<Alumno> listaAlumnos(){
		
		List<Alumno> alumnos = new ArrayList<>();
		
		alumnos.add(crearAlumno001().get());
		alumnos.add(crearAlumno002().get());
		alumnos.add(crearAlumno003().get());
		return alumnos;
		
	}

}
