package com.jgr.micro.cursos.models.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.service.ICursoService;

@RestController
public class CursoController {

    @Autowired
    private ICursoService service;

    @GetMapping
    public ResponseEntity<Iterable<Curso>> listar() {
    	
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/por-nombre/")
    public ResponseEntity<Iterable<Curso>> listarPorNombre() {
    	return ResponseEntity.ok(service.findAllSortedByNombreDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso,BindingResult result ) {
        Curso cursoDb = service.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<?> eliminar(@Valid @PathVariable Long id, BindingResult result) {
    	
    	if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> o = service.findById(id);
        if (o.isPresent()) {
            service.delete(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    

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
