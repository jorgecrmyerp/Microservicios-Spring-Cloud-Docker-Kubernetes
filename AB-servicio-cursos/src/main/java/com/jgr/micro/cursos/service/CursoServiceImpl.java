package com.jgr.micro.cursos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jgr.micro.cursos.models.Curso;
import com.jgr.micro.cursos.models.repository.ICursoRepository;

@Service
public class CursoServiceImpl implements ICursoService {
	
	@Autowired
	private ICursoRepository iCursoRepository;
	

	@Override
	public Iterable<Curso> findAll() {
		return iCursoRepository.findAll();
	}

	@Override
	public Iterable<Curso> findAllSortedByNombreDesc() {
		return iCursoRepository.findAll(Sort.by("nombre").descending());

	}

	@Override
	public Optional<Curso> findById(Long id) {
		return iCursoRepository.findById(id);
	}

	@Override
	public Curso save(Curso curso) {
		return iCursoRepository.save(curso);
	}

	@Override
	public void delete(Long id) {
		iCursoRepository.deleteById(id);

	}

}
