package com.jgr.micro.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class CursoAlumno.
 */
@Entity
@Table(name = "cursos_alumnos")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CursoAlumno {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The alumno id. */
	@Column(name = "alumno_id", unique = true)
	private Long alumnoId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CursoAlumno)) {
			return false;
		}
		CursoAlumno o = (CursoAlumno) obj;
		return this.alumnoId != null && this.alumnoId.equals(o.alumnoId);
	}

}
