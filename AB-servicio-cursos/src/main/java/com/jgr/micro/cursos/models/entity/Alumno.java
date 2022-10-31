package com.jgr.micro.cursos.models.entity;

import java.util.Date;


import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Instantiates a new alumno.
 * Este NO se guarda en la tabla, la necesitamos para tratar el tipo alumno, pero el que va a guardarse
 * es el del microservicio alumnos
 */

/**
 * Instantiates a new alumno.
 */
@NoArgsConstructor

/**
 * Instantiates a new alumno.
 *
 * @param id the id
 * @param nombre the nombre
 * @param email the email
 * @param password the password
 * @param createAt the create at
 */

/**
 * Instantiates a new alumno.
 *
 * @param id the id
 * @param nombre the nombre
 * @param email the email
 * @param password the password
 * @param createAt the create at
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data
public class Alumno {

    /** The id. */
   
    private Long id;

    /** The nombre. */    
	
    private String nombre;

    /** The email. */
	
    private String email;

    /** The password. */	
	
    private String password;
    
    /** The create at. */
    
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