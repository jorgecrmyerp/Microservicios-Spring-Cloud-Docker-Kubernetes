package com.jgr.micro.cursos.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jgr.micro.cursos.models.Curso;

public interface ICursoRepository extends  PagingAndSortingRepository<Curso, Long>{

}
