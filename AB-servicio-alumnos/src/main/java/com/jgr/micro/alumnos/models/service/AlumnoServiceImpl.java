package com.jgr.micro.alumnos.models.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgr.micro.alumnos.models.Alumno;
import com.jgr.micro.alumnos.models.repository.IAlumnoRepository;

/**
 * The Class AlumnoServiceImpl.
 */
@Service
public class AlumnoServiceImpl implements IAlumnoService {

	/** The alumno repository. */
	@Autowired
	IAlumnoRepository alumnoRepository;

	/**
	 * Find all.
	 *
	 * @return the iterable
	 */
	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll() {
		return alumnoRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Alumno> findById(Long id) {
		return alumnoRepository.findById(id);
	}

	/**
	 * Save.
	 *
	 * @param al the al
	 * @return the alumno
	 */
	@Override
	@Transactional
	public Alumno save(Alumno al) {

		return alumnoRepository.save(al);

	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		alumnoRepository.deleteById(id);

	}

	/**
	 * Delete.
	 *
	 * @param al the al
	 */
	@Override
	@Transactional
	public void delete(Alumno al) {
		alumnoRepository.delete(al);

	}

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	@Override
	public Optional<Alumno> findByEmail(String email) {
		return alumnoRepository.findByEmail(email);
	}

	@Override
	public Optional<Alumno> porEmail(String email) {
 
		return alumnoRepository.porEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return alumnoRepository.existsByEmail(email);
	}

}
