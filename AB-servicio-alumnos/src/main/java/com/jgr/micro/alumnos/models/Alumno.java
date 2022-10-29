package com.jgr.micro.alumnos.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Alumno.
 */
@Entity
@Table(name="alumnos")
@NoArgsConstructor
@AllArgsConstructor

@Data
public class Alumno {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The nombre. */
	@NotEmpty(message="Error en el nombre")
    private String nombre;

    /** The email. */
	@NotEmpty(message="Email erroneo")
	@Email
    private String email;

    /** The password. */	
	@NotEmpty(message="La password no puede estar vacia")
    private String password;
    
    /** The create at. */
    @Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
    
	/**
	 * Pre persist.
	 */
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

}
