package com.jgr.alumnos.modelo.models;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Alumno.
 */
@Entity
@Table(name = "alumnos")

public class Alumno {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The nombre. */
	@NotBlank // no admite blancos en el nombre,y no puede estar vacio
	private String nombre;

	/** The email. */
	@NotEmpty(message = "Email erroneo")
	@Column(unique = true)
	@Email
	private String email;

	/** The password. */
	@NotBlank
	private String password;

	/** The create at. */
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	//saca mejor la fecha en el json de salida
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createAt;

	@Lob
	@JsonIgnore
	private byte[] foto;

	/**
	 * 
	 */
	public Alumno() {
		super();
	}

	/**
	 * @param id
	 * @param nombre
	 * @param email
	 * @param password
	 * @param createAt
	 */
	public Alumno(Long id, 
			@NotBlank String nombre, 
			@NotEmpty(message = "Email erroneo") @Email String email,
			@NotBlank String password, 
			Date createAt) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.createAt = createAt;
	}

	/**
	 * Pre persist.
	 */
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the createAt
	 */
	public Date getCreateAt() {
		return this.createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the foto
	 */
	public byte[] getFoto() {
		return this.foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Integer getFotoHashCode() {
		return (this.foto != null) ? this.foto.hashCode() : null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createAt, email, id, nombre, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Alumno)) {
			return false;
		}
		Alumno other = (Alumno) obj;
		return Objects.equals(this.id, other.id);
	}

	@Override
	public String toString() {
		return "Alumno [" + (this.id != null ? "id=" + this.id + ", " : "")
				+ (this.nombre != null ? "nombre=" + this.nombre + ", " : "")
				+ (this.email != null ? "email=" + this.email + ", " : "")
				+ (this.password != null ? "password=" + this.password + ", " : "")
				+ (this.createAt != null ? "createAt=" + this.createAt + ", " : "")
				+ (this.foto != null ? "foto=" + Arrays.toString(this.foto) : "") + "]";
	}

	

}
