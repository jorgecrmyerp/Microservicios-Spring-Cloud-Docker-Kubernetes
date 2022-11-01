package com.jgr.micro.cursos.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jgr.micro.cursos.models.entity.Curso;

public interface ICursoRepository extends  PagingAndSortingRepository<Curso, Long>{
	
    @Modifying
    @Query("delete from CursoAlumno cu where cu.alumnoId=?1")
    void eliminarCursoUsuarioPorId(Long id);

}
