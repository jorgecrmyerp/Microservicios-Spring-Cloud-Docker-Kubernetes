package com.jgr.micro.alumnos.client.rest.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The Interface CursoClienteFeign.
 * Relacion con cursos
 */
@FeignClient(name="servicio-cursos",url="localhost:8002")
public interface CursoClienteFeign {
	
	/**
	 * Eliminar la relacion en curso_alumno por id alumno.
	 *
	 * @param id the id
	 */
	@DeleteMapping("eliminar-curso-alumno/{id}")
	public void eliminarCursoAlumnoId(@PathVariable Long id);
	
	
	
	

}
