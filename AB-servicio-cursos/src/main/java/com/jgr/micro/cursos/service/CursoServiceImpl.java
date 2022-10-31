package com.jgr.micro.cursos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jgr.micro.cursos.client.rest.feign.AlumnoFeign;
import com.jgr.micro.cursos.models.entity.Alumno;
import com.jgr.micro.cursos.models.entity.Curso;
import com.jgr.micro.cursos.models.entity.CursoAlumno;
import com.jgr.micro.cursos.models.repository.ICursoRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class CursoServiceImpl.
 */
@Service
public class CursoServiceImpl implements ICursoService {

	/** The i curso repository. */
	@Autowired
	private ICursoRepository iCursoRepository;

	/** The alumno feign. RELACION CON EL CLIENTE FEIGN */
	@Autowired
	private AlumnoFeign alumnoFeign;

	/**
	 * Find all.
	 *
	 * @return the iterable
	 */
	@Override
	public Iterable<Curso> findAll() {
		return iCursoRepository.findAll();
	}

	/**
	 * Find all sorted by nombre desc.
	 *
	 * @return the iterable
	 */
	@Override
	public Iterable<Curso> findAllSortedByNombreDesc() {
		return iCursoRepository.findAll(Sort.by("nombre").descending());

	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	public Optional<Curso> findById(Long id) {
		return iCursoRepository.findById(id);
	}

	/**
	 * Save.
	 *
	 * @param curso the curso
	 * @return the curso
	 */
	@Override
	public Curso save(Curso curso) {
		return iCursoRepository.save(curso);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		iCursoRepository.deleteById(id);

	}

	/**
	 * Asignar alumno. asignamos el alumno al curso con id que pasamos por parametro
	 * obtenemos el alumno con feign, lo relacionamos con el curso y lo guardamos en
	 * bbdd
	 * 
	 * @param alumno  the alumno
	 * @param cursoid the cursoid
	 * @return the optional
	 */
	@Override
	public Optional<Alumno> asignarAlumno(Alumno alumno, Long cursoid) {

		// buscamos el curso
		Optional<Curso> cursoOp = iCursoRepository.findById(cursoid);

		if (cursoOp.isPresent()) {
			Alumno alClient = alumnoFeign.detalleAlumno(alumno.getId());
			Curso curso = cursoOp.get();
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alClient.getId());
			curso.addCursoUsuario(cursoAlumno);
			iCursoRepository.save(curso);
			return Optional.of(alClient);
		}

		return Optional.empty();

	}

	/**
	 * Alta alumno.
	 *
	 * @param alumno  the alumno
	 * @param cursoid the cursoid
	 * @return the optional
	 */
	@Override
	public Optional<Alumno> altaAlumno(Alumno alumno, Long cursoid) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	/**
	 * Eliminar relacion alumno curso.
	 *
	 * @param alumno  the alumno
	 * @param cursoid the cursoid
	 * @return the optional
	 */
	@Override
	public Optional<Alumno> eliminarRelacionAlumnoCurso(Alumno alumno, Long cursoid) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
