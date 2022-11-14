package com.jgr.alumnos.modelo.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class Alumno.
 */
@Entity
@Table(name="alumnos")


public class Alumno {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The nombre. */    
	@NotBlank//no admite blancos en el nombre,y no puede estar vacio
    private String nombre;

    /** The email. */
	@NotEmpty(message="Email erroneo")
	@Column(unique = true)
	@Email
    private String email;

    /** The password. */	
	@NotBlank
    private String password;
    
    /** The create at. */
    @Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
    
    
    
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
	public Alumno(Long id, @NotBlank String nombre, @NotEmpty(message = "Email erroneo") @Email String email,
			@NotBlank String password, Date createAt) {
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
		
		
		 String strDate ="";
		if (this.createAt != null) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		 strDate = dateFormat.format(this.createAt);  
		}
		 
		return "Alumno [" + (this.id != null ? "id=" + this.id + ", " : "")
				+ (this.nombre != null ? "nombre=" + this.nombre + ", " : "")
				+ (this.email != null ? "email=" + this.email + ", " : "")
				+ (this.password != null ? "password=" + this.password + ", " : "")
				+ (this.createAt != null ? "createAt=" + strDate  : "") + "]";
	}

	
}
