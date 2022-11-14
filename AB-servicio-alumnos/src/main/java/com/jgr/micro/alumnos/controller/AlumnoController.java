package com.jgr.micro.alumnos.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.alumnos.models.service.IAlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class AlumnoController.
 */
@RestController
public class AlumnoController {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(AlumnoController.class);

	/** The i alumno service. */
	@Autowired
	private IAlumnoService iAlumnoService;

	
	@GetMapping
	@Operation(summary = "Lista de todos los alumnos")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Alumnos existentes", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "No hay alumnos", content = @Content) })
	public ResponseEntity<?> listarAlumnos() {

		return ResponseEntity.ok(iAlumnoService.findAll());

	}

	
	@GetMapping("/{id}")
	@Operation(summary = "Busqueda de alumnos por path variable")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "existe", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "No existe", content = @Content) })
	public ResponseEntity<?> obtenerAlumnoPorIdPathVariable(@PathVariable Long id) {
	
		Optional<Alumno> al = iAlumnoService.findById(id);

		if (al.isPresent()) {
			return ResponseEntity.ok(al.get());
		}
		return ResponseEntity.notFound().build();

	}

	
	@GetMapping("/request-param/")	
	@Operation(summary = "Busqueda de alumnos por id pero con request param")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "existe", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "No existe", content = @Content) })
	
	public ResponseEntity<?> obtenerAlumnoPorIdRequestParam(@RequestParam Long id) {

		Optional<Alumno> al = iAlumnoService.findById(id);

		if (al.isPresent()) {
			return ResponseEntity.ok(iAlumnoService.findById(id));
		}
		return ResponseEntity.notFound().build();
	}

	
	@PutMapping("/{id}")
	@Operation(summary = "Actualiza alumno,hay que pasar el id del alumno y en formato alumno todos los datos")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "existe", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "No existe", content = @Content) })
	public ResponseEntity<?> actualizaAlumno(@Valid @RequestBody Alumno al, BindingResult result,
			@PathVariable Long id) {

		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Alumno> o = iAlumnoService.findById(id);

		if (!o.isPresent()) {
			logger.debug("Microservicio Alumno->actualizaAlumno");
			return ResponseEntity.notFound().build();
		}

		Alumno alDb = o.get();

		if (!al.getEmail().isEmpty() && !al.getEmail().equalsIgnoreCase(alDb.getEmail())
				&& iAlumnoService.porEmail(al.getEmail()).isPresent()) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonMap("mensaje", "Ya existe un alumno con ese email"));

		}

		alDb.setNombre(al.getNombre());
		alDb.setEmail(al.getEmail());
		alDb.setPassword(al.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body(iAlumnoService.save(alDb));

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

	
	@PostMapping
	@Operation(summary = "Alta de alumno")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "alta correcta", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "400", description = "Existe un alumno con ese email", content = @Content) })
	public ResponseEntity<?> altaAlumno(@Valid @RequestBody Alumno al, BindingResult result) {

		System.out.println("en alta alumnos" + al.toString());

		if (result.hasErrors()) {
			return validar(result);
		}

		if (!al.getEmail().isEmpty() && iAlumnoService.findByEmail(al.getEmail()).isPresent()) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonMap("mensaje", "Ya existe un alumno con ese email"));

		}

		Alumno alDb = new Alumno();
		alDb.setNombre(al.getNombre());
		alDb.setEmail(al.getEmail());
		alDb.setPassword(al.getPassword());

		return ResponseEntity.status(HttpStatus.CREATED).body(iAlumnoService.save(alDb));

	}

	
	@DeleteMapping("/{id}")
	@Operation(summary = "Borrado de alumno por id,borra tambien en cursos")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "borrado correcto", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "Ese alumno no existe", content = @Content) })
	
	public ResponseEntity<?> borraAlumnoId(@PathVariable Long id) {

		Optional<Alumno> o = iAlumnoService.findById(id);

		if (!o.isPresent()) {
			logger.debug("Microservicio Alumno->actualizaAlumno");

			return ResponseEntity.notFound().build();
		}
		iAlumnoService.delete(id);

		return ResponseEntity.noContent().build();

	}

	
	@DeleteMapping("/borra-alumno")
	@Operation(summary = "Borrado de alumno por alumno,borra tambien en cursos")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "borrado correcto", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "Ese alumno no existe", content = @Content) })
	public ResponseEntity<?> borraAlumnoAlumno(@Valid @RequestBody Alumno al) {

		Optional<Alumno> o = iAlumnoService.findById(al.getId());

		if (!o.isPresent()) {
			logger.debug("Microservicio Alumno->actualizaAlumno");

			return ResponseEntity.notFound().build();
		}
		iAlumnoService.delete(al);
		return ResponseEntity.noContent().build();

	}

	
	@GetMapping("/alumnos-por-curso")	
	@Operation(summary = "A partir de una lista de alumnos devuelve el curso en el que estan")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "lista de alumnos", content = {
			@Content(mediaType = "application/json", 
					array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "204", description = "No hay alumnos", content = @Content) })
	public ResponseEntity<?> alumnosCursoRequestParam(@RequestParam List<Long> ids) {

		logger.debug("alumnoz-AlumnosCursoREquest->" + ids.get(0));
		List<Alumno> lista = new ArrayList<>();
		lista = (List<Alumno>) iAlumnoService.findAllById(ids);
		
		if (lista.size()>0) {
			return ResponseEntity.ok(iAlumnoService.findAllById(ids));			
		}
		else {
			return ResponseEntity.notFound().build();
			
		}



	}

}
