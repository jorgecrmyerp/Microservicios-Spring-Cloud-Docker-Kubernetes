package com.jgr.micro.cursos.models.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.service.ICursoService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class CursoController.
 */
@RestController
public class CursoController {

	/** The service. */
	@Autowired
	private ICursoService service;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(CursoController.class);

	@GetMapping("/api/cursos")
	@Operation(summary = "Lista de los cursos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de cursos con alumnos asignados y su detalle", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "No hay cursos", content = @Content) })
	public ResponseEntity<Iterable<Curso>> listarCursos() {
		
		List<Curso> cursos = new ArrayList<>();
		cursos = (List<Curso>) service.findAll();
		if (cursos.size() > 0) {
			return ResponseEntity.ok(cursos);

		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@GetMapping
	@Operation(summary = "Lista de los cursos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de cursos con alumnos asignados y su detalle", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "No hay cursos", content = @Content) })
	public ResponseEntity<Iterable<Curso>> listar() {
		List<Curso> cursos = new ArrayList<>();
		cursos = (List<Curso>) service.findAll();
		if (cursos.size() > 0) {
			return ResponseEntity.ok(cursos);

		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@GetMapping("/por-nombre/")
	@Operation(summary = "Lista ordenada por nombre de los cursos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de cursos con alumnos asignados y su detalle", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "No hay cursos", content = @Content) })
	public ResponseEntity<Iterable<Curso>> listarPorNombre() {
		List<Curso> cursos = new ArrayList<>();

		cursos = (List<Curso>) service.findAllSortedByNombreDesc();

		if (cursos.size() > 0) {
			return ResponseEntity.ok(cursos);

		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Detalle de un curso")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Datos del curso", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "No existe el curso", content = @Content) })
	public ResponseEntity<?> detalle(@PathVariable Long id) {

		// Optional<Curso> o = service.findById(id); //solo saca el curso
		// curso y el detalle de los alumnos relacionados
		Optional<Curso> o = service.alumnosCursoporIdCurso(id);
		logger.info("En detalle de un curso->" + id);
		if (o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/")
	@Operation(summary = "Alta de un curso")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Datos del curso dado de alta", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Error en validacion de los datos", content = @Content) })
	public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {

		if (result.hasErrors()) {
			return validar(result);
		}

		Curso cursoDb = service.save(curso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Modificacion de un curso")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Datos del curso modificado", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso no existe", content = @Content) })
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

		if (result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> o = service.findById(id);

		if (o.isPresent()) {
			Curso cursoDb = o.get();
			cursoDb.setNombre(curso.getNombre());
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrado de un curso")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Curso borrado correctamente", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso no existe/errores de validacion", content = @Content) })

	public ResponseEntity<?> eliminar(@PathVariable Long id) {

		Optional<Curso> o = service.findById(id);
		if (o.isPresent()) {
			service.delete(o.get().getId());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/asignar-alumno/{cursoId}")
	@Operation(summary = "Asignar alumno YA EXISTENTE a un curso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Operacion realizada correctamente", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso/Alumno no existe/errores de validacion", content = @Content) })

	public ResponseEntity<?> asignarAlumnoCurso(@Valid @RequestBody Alumno alumno, BindingResult result,
			@PathVariable Long cursoId) {

		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Alumno> alumnoAlta = null;

		// si hay error en la comunicacion con feign
		try {
			alumnoAlta = service.asignarAlumnoCurso(alumno, cursoId);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					Collections.singletonMap("mensaje", "error comunicacion o no existe curso " + e.getMessage()));

		}

		if (alumnoAlta.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoAlta.get());
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/borrar-alumno/{cursoId}")
	@Operation(summary = "Quitar alumno de un curso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operacion realizada correctamente", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso/Alumno no existe/errores de validacion", content = @Content) })
	public ResponseEntity<?> eliminarRelacionAlumnoCurso(@RequestBody Alumno alumno, BindingResult result,
			@PathVariable Long cursoId) {

		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Alumno> alumnoBaja;

		// si hay error en la comunicacion con feign
		try {
			alumnoBaja = service.eliminarRelacionAlumnoCurso(alumno, cursoId);
			logger.debug("borrar alumno id" + alumno.getId() + "-" + cursoId);

			logger.debug("alumnoBaja" + alumnoBaja.get().getId());
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					Collections.singletonMap("mensaje", "error comunicacion o no existe curso " + e.getMessage()));

		}

		if (alumnoBaja.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(alumnoBaja.get());
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping("/alta-alumno/{cursoId}")
	@Operation(summary = "Alta de alumno No existente en curso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operacion realizada correctamente", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso/Alumno no existe/errores de validacion", content = @Content) })
	public ResponseEntity<?> altaAlumnoCurso(@Valid @RequestBody Alumno alumno, BindingResult result,
			@PathVariable Long cursoId) {

		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Alumno> alumnoAlta;

		// si hay error en la comunicacion con feign
		try {
			alumnoAlta = service.altaAlumnoCurso(alumno, cursoId);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje",
					"error comunicacion o no se pudo crear alumno " + e.getMessage()));

		}

		if (alumnoAlta.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoAlta.get());
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("eliminar-curso-alumno/{id}")
	@Operation(summary = "Elimina alumno de un curso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operacion realizada correctamente", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Curso.class))) }),
			@ApiResponse(responseCode = "404", description = "Curso/Alumno no existe/errores de validacion", content = @Content) })
	public ResponseEntity<?> eliminarCursoAlumnoId(@PathVariable Long id) {

		Optional<Curso> o = service.findById(id);

		if (o.isPresent()) {
			service.eliminarCursoUsuarioPorId(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();

	}

	/**
	 * Validar.
	 *
	 * @param result the result
	 * @return the response entity
	 */
	private ResponseEntity<Map<String, String>> validar(BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
			errores.put("DefaultMessage", err.getDefaultMessage());
			errores.put("Code", err.getCode());
			errores.put("Name", err.getObjectName());

		});
		return ResponseEntity.badRequest().body(errores);
	}

}
