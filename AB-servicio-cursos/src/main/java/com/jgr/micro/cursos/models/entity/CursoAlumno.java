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

// TODO: Auto-generated Javadoc
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the usuario id.
	 *
	 * @return the usuario id
	 */
	public Long getUsuarioId() {
		return alumnoId;
	}

	/**
	 * Sets the usuario id.
	 *
	 * @param alumnoId the new usuario id
	 */
	public void setUsuarioId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

}
