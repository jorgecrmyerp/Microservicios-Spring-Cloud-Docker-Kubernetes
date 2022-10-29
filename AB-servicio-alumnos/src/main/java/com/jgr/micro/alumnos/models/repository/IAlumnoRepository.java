
package com.jgr.micro.alumnos.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jgr.micro.alumnos.models.Alumno;


/**
 * The Interface IAlumnosRepository.
 */

public interface IAlumnoRepository extends CrudRepository<Alumno, Long> {

}
