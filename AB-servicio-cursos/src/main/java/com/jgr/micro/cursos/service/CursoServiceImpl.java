package com.jgr.micro.cursos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.jgr.alumnos.modelo.models.Alumno;
import com.jgr.micro.cursos.client.rest.feign.AlumnoFeign;
import com.jgr.micro.cursos.error.IdNoEncontradoException;
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
	@Transactional(readOnly = true)
	//si le quitamos esto en junit da error
	//org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
	//com.jgr.micro.cursos.models.entity.Curso.cursoAlumnos, could not initialize proxy - no Session	
	//@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	public Iterable<Curso> findAll() {
		return iCursoRepository.findAll();
	}

	/**
	 * Find all sorted by nombre desc.
	 *
	 * @return the iterable
	 */
	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return iCursoRepository.findById(id);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return curso or IdNoEncontradoException
	 */
	@Override
	@Transactional(readOnly = true)
	public Curso findByIdThrowError(Long id) {

		Optional<Curso> curso = iCursoRepository.findById(id);
		if (curso.isPresent()) {
			return curso.get();
		}
		throw new IdNoEncontradoException(id.toString());

	}

	/**
	 * Save.
	 *
	 * @param curso the curso
	 * @return the curso
	 */
	@Override
	@Transactional
	public Curso save(Curso curso) {
		return iCursoRepository.save(curso);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		iCursoRepository.deleteById(id);

	}

	/**
	 * Asignar alumno. asignamos el alumno que SI existe al curso con id que pasamos
	 * por parametro obtenemos el alumno con feign, lo relacionamos con el curso y
	 * lo guardamos en bbdd
	 * 
	 * @param alumno  the alumno
	 * @param cursoid the cursoid
	 * @return the optional alumno obtenido con feign y guardado en bbdd curso
	 */
	@Override
	@Transactional
	public Optional<Alumno> asignarAlumnoCurso(Alumno alumno, Long cursoid) {

		// buscamos el curso
		Optional<Curso> cursoOp = iCursoRepository.findById(cursoid);

		System.out.println("asignar alumno curso,curso->" + cursoOp.get().getNombre());

		if (cursoOp.isPresent()) {
			// alumno de bbdd obtenido con feign
			Alumno alClient = alumnoFeign.detalleAlumno(alumno.getId());
			Curso curso = cursoOp.get();
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alClient.getId());
			curso.addCursoAlumno(cursoAlumno);
			iCursoRepository.save(curso);
			return Optional.of(alClient);
		}

		return Optional.empty();

	}

	/**
	 * Alta alumno en curso. Damos de alta un alumno que no existe y lo guardamos en
	 * bbdd
	 *
	 * @param alumno  the alumno
	 * @param cursoid the cursoid
	 * @return the optional
	 */
	@Override
	@Transactional
	public Optional<Alumno> altaAlumnoCurso(Alumno alumno, Long cursoid) {
		// buscamos el curso
		Optional<Curso> cursoOp = iCursoRepository.findById(cursoid);

		if (cursoOp.isPresent()) {
			// alumno de bbdd obtenido con feign
			Alumno alClient = alumnoFeign.altaAlumno(alumno);
			Curso curso = cursoOp.get();
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(alClient.getId());
			curso.addCursoAlumno(cursoAlumno);
			iCursoRepository.save(curso);
			return Optional.of(alClient);
		}

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
	@Transactional
	public Optional<Alumno> eliminarRelacionAlumnoCurso(Alumno alumno, Long cursoid) {
		// buscamos el curso
		Optional<Curso> cursoOp = iCursoRepository.findById(cursoid);

		if (cursoOp.isPresent()) {
			Alumno al = alumnoFeign.detalleAlumno(alumno.getId());
			Curso curso = cursoOp.get();
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(al.getId());
			cursoAlumno.setId(cursoid);
			curso.removeCursoAlumno(cursoAlumno);
			iCursoRepository.save(curso);
			return Optional.of(al);
		}

		return Optional.empty();
	}

	/**
	 * Le pasamos el id de un curso y nos devuelve el curso con los alumnos
	 * relacionados.
	 *
	 * @param cursoId the curso id
	 * @return the optional curso con los alumnos relacionados
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> alumnosCursoporIdCurso(Long cursoId) {

		Optional<Curso> cursoOp = iCursoRepository.findById(cursoId);

		if (cursoOp.isPresent()) {
			Curso curso = cursoOp.get();

			if (!curso.getCursoAlumnos().isEmpty()) {

				// del curso obtenemos el id del alumno relacionado en alumno_cursos
				List<Long> ids = curso.getCursoAlumnos().stream(). // lo convierto a stream
						map(cursoAlumno -> cursoAlumno.getAlumnoId()). // lo paso al formato cursoAlumno
						collect(Collectors.toList());// lo convierto a lista
				// esta seria otra manera de hacerlo igual
				/*
				 * List<Long> ids2 = curso.getCursoAlumnos().stream().
				 * map(CursoAlumno::getAlumnoId).collect(Collectors.toList());
				 */
				// obtenemos el detalle de los alumnos por lista de idalumnos

				List<Alumno> alumnos = (List<Alumno>) alumnoFeign.alumnosCursoRequestParam(ids);

				curso.setAlumnos(alumnos);

			}
			return Optional.of(curso);

		}
		return Optional.empty();

	}

	/**
	 * Eliminar curso usuario por id.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	public void eliminarCursoUsuarioPorId(Long id) {
		iCursoRepository.eliminarCursoUsuarioPorId(id);

	}

}
