/**
 * 
 */
package com.jgr.alumnos.modelo.models.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





import com.jgr.alumnos.modelo.models.Alumno;


/**
 * @author JORGE
 *
 */
class AlumnoTest {
	

  
   
   
    
    private Alumno al1;
    private Alumno al2;
    private Alumno al3;
    private Alumno al4;
    private Alumno al5;


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	
		  //System.out.println("inicializando el test @BeforeAll");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		  //System.out.println("tearDownAfterClass()@AfterAll");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		//logger.debug("setUp() @BeforeEach ");
		al1 = new Alumno();
		al2 = new Alumno();
		al3 = new Alumno();
		al4 = new Alumno();
		al5 = new Alumno();
		al1.setId(1L);
		al1.setNombre("nombre1");
		al1.setEmail("email1@mail.com");
		al1.setPassword("password1");
		al1.setCreateAt(new Date());
		al2.setId(1L);
		al2.setNombre("nombre1");
		al2.setEmail("email1@mail.com");
		al2.setPassword("password1");
		al2.setCreateAt(new Date());
		al2.setId(3L);
		al3.setNombre("nombre3");
		al3.setEmail("email3@mail.com");
		al3.setPassword("password3");
		al3.setCreateAt(new Date());
		
		
//		 testReporter.publishEntry(" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName()
//	                + " con las etiquetas " + testInfo.getTags());
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		//logger.debug("finalizando el metodo de prueba.");
		 
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#hashCode()}.
	 */
	@Test
	@DisplayName("Probando hashcode")
	void testHashCode() {
		 assertNotNull(al1,()->"alumno1 es nulo");
		 al4= new Alumno();
		 al4=al1;
		 assertEquals(al1,al4,()->"no son iguales al1 y al4");
		 assertNotEquals(al1,al2,()->"Son iguales al1 y al2");
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#prePersist()}.
	 */
	@Test
	void testPrePersist() {
		//fail("Not yet implemented"); // TODO
	}

	

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#Alumno()}.
	 */
	@Test
	void testAlumno() {
		
		Alumno alumno8 =null;
		assertNull(alumno8,()->"no es nulo");
		

	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#Alumno(Long, String, String, String, Date)}.
	 */
	@Test
	void testAlumnoLongStringStringStringDate() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#getId()}.
	 */
	@Test
	void testGetId() {
		 assertNotNull(al1);
		 assertTrue(al1.getId().equals(1L),()->"no coincide el id");
		 assertEquals(al1.getId().intValue(),1,()->"no coincide el id");
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#getNombre()}.
	 */
	@Test
	void testGetNombre() {
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("nombre5", al5.getNombre(),()->"no coincide el nombre5");
		assertEquals(al1.getNombre(),al2.getNombre(),()->"no coincide el nombre1 "+ al1.getEmail() + " nombre2"+al2.getNombre());
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#getEmail()}.
	 */
	@Test
	void testGetEmail() {
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("email5@mail.com", al5.getEmail(),()->"no coincide el email5");
		assertEquals(al1.getEmail(),al2.getEmail(),()->"no coincide el email1 "+ al1.getEmail() + " email2"+al2.getEmail());
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#getPassword()}.
	 */
	@Test
	void testGetPassword() {
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("password5", al5.getPassword(),()->"no coincide la password");

	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#getCreateAt()}.
	 */
	@Test
	void testGetCreateAt() {
		Date fecha = new Date();
		
		al5.setCreateAt(fecha);
		Date fecha2 = fecha;
		assertNotNull(al5,()->"al5 es nulo");
		
		assertEquals(al5.getCreateAt(),fecha2,()->"no coincide la fecha");
		assertTrue(al5.getCreateAt().equals(fecha2),()->"no coincide la fecha True");
		
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#setId(Long)}.
	 */
	@Test
	void testSetId() {
		 assertNotNull(al5);
		 al5.setId(5L);		 
		 assertTrue(al5.getId().equals(5L),()->"no coincide el id");
		 assertEquals(al1.getId().intValue(),1,()->"no coincide el id");
		 
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#setNombre(String)}.
	 */
	@Test
	void testSetNombre() {
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("nombre5", al5.getNombre(),()->"no coincide el nombre5");
		assertEquals(al1.getNombre(),al2.getNombre(),()->"no coincide el nombre1 "+ al1.getEmail() + " nombre2"+al2.getNombre());
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#setEmail(String)}.
	 */
	@Test
	void testSetEmail() {
		
		al4= new Alumno();
		al4.setNombre("nombre4");
		al4.setEmail("email4@mail.com");
		al4.setPassword("password4");
		al4.setCreateAt(new Date());
		assertNotNull(al4,()->"alumno4 es nulo");
		assertEquals(al4.getEmail(),"email4@mail.com",()->"no es igual el email");
		
		
		
		

	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#setPassword(String)}.
	 */
	@Test
	void testSetPassword() {
		
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("password5", al5.getPassword(),()->"no coincide la password");
		
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#setCreateAt(Date)}.
	 */
	@Test
	void testSetCreateAt() {
		//fail("Not yet implemented"); // TODO
		
		Date fecha = new Date();
		al5.setCreateAt(fecha);
		assertNotNull(al5,()->"al5 es nulo");
		assertEquals(al5.getCreateAt(),fecha,()->"no coincide la fecha");
		assertTrue(al5.getCreateAt().equals(fecha),()->"no coincide la fecha True");
		
	}

	/**
	 * Test method for {@link com.jgr.alumnos.modelo.models.test.Alumno#toString()}.
	 */
	@Test
	void testToString() {
//		fail("Not yet implemented"); // TODO
		
		
		
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	void testObject() {
		

		assertTrue(al1.getClass().getCanonicalName().equalsIgnoreCase("com.jgr.alumnos.modelo.models.Alumno"),()->"no son iguales");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	void testGetClass() {
		al5.setNombre("nombre5");
		al5.setEmail("email5@mail.com");
		al5.setPassword("password5");
		al5.setCreateAt(new Date());
		assertEquals("class com.jgr.alumnos.modelo.models.Alumno", al5.getClass().toString(),()->"no es la misma clase");

	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {

		al5=al1;
		assertEquals(al1,al5, ()->"no son iguales");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	void testClone() {
//		fail("Not yet implemented"); // TODO
	}

	

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	void testNotify() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	void testNotifyAll() {

	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	void testWait() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	void testWaitLong() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	void testWaitLongInt() {
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	void testFinalize() {
//		fail("Not yet implemented"); // TODO
	}
}

