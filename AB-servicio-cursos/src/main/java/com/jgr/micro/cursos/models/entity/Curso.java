package com.jgr.micro.cursos.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * The Class Curso.
 */
@Entity
@Table(name = "cursos")
//@NoArgsConstructor


@AllArgsConstructor
//@Getter
//@Setter
@Data
public class Curso {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The nombre. */
	@NotEmpty(message = "El nombre del curso no puede estar vacio")
	private String nombre;

	/** The create at. */
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	/** The curso alumnos. */
	// @JsonIgnoreProperties(value = {"padre", "handler",
	// "hibernateLazyInitializer"}, allowSetters = true)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curso_id")
	private List<CursoAlumno> cursoAlumnos;

	/** The alumnos. */
	// este no lo guardamos en la bbdd
	@Transient
	private List<Alumno> alumnos;

	/**
	 * Instantiates a new curso.
	 */
	public Curso() {
		cursoAlumnos = new ArrayList<>();
		alumnos = new ArrayList<>();
	}

	/**
	 * Pre persist.
	 */
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	/**
	 * Adds the curso usuario.
	 *
	 * @param cursoUsuario the curso usuario
	 */
	public void addCursoUsuario(CursoAlumno cursoUsuario) {
		cursoAlumnos.add(cursoUsuario);
	}

	/**
	 * Removes the curso usuario.
	 *
	 * @param cursoUsuario the curso usuario
	 */
	public void removeCursoUsuario(CursoAlumno cursoUsuario) {
		cursoAlumnos.remove(cursoUsuario);
	}

	/**
	 * Gets the curso usuarios.
	 *
	 * @return the curso usuarios
	 */
	public List<CursoAlumno> getCursoUsuarios() {
		return cursoAlumnos;
	}

	/**
	 * Sets the curso alumnoss.
	 *
	 * @param cursoAlumnos the new curso alumnoss
	 */
	public void setCursoAlumnoss(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}

	/**
	 * Gets the alumnos.
	 *
	 * @return the alumnos
	 */
	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	/**
	 * Sets the alumnos.
	 *
	 * @param alumnos the new alumnos
	 */
	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return Objects.equals(this.id, other.id);
	}

}
