package com.jgr.micro.cursos.service;

import java.util.Optional;

import com.jgr.micro.cursos.models.Curso;

public interface ICursoService {
	
	public Iterable<Curso> findAll();
	public Iterable <Curso> findAllSortedByNombreDesc();
	public Optional<Curso> findById(Long id);
	public Curso save(Curso curso);
	public void delete(Long id);
	

}
