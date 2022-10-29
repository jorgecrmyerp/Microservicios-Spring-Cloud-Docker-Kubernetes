package com.jgr.micro.alumnos.models.service;

import java.util.Optional;

import com.jgr.micro.alumnos.models.Alumno;

// TODO: Auto-generated Javadoc
/**
 * The Interface IAlumnoService.
 */
public interface IAlumnoService {
	
	
	/**
	 * Find all.
	 *
	 * @return the iterable
	 */
	public Iterable <Alumno> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<Alumno> findById(Long id);
	
	/**
	 * Save.
	 *
	 * @param al the al
	 * @return the alumno
	 */
	public Alumno save(Alumno al);
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);
	
	
	/**
	 * Delete.
	 *
	 * @param al the al
	 */
	public void delete(Alumno al);
	
	

}
