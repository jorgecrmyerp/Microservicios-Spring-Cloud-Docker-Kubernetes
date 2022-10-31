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
	
	
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	public Optional<Alumno> findByEmail(String email);
	
	/**
	 * Por email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	public Optional <Alumno> porEmail(String email);
	
	
	/**
	 * Exist by email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean existsByEmail(String email);

}
