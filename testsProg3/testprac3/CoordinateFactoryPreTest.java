package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.aircraft.Coordinate3D;
import model.ship.Coordinate2D;

public class CoordinateFactoryPreTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	//TODO
	/* Crea coordenadas correctas con el método createCoordinate y comprueba 
	 * que se han creado bien
	 * 
	 */
	@Test
	public void testCreateCoordinateOk() {
		Coordinate2D c = new Coordinate2D(1,2);
		Coordinate3D c1 = new Coordinate3D(1,2,3);
		Coordinate c2d = CoordinateFactory.createCoordinate(1,2);
		assertTrue(c2d.equals(c));
		Coordinate c3d = CoordinateFactory.createCoordinate(1,2,3);
		assertTrue(c3d.equals(c1));
		//fail ("Realiza el test createCoordinateOk");
	}
	
	//TODO
	/* Comprueba que en los distintos casos de creación de coordenadas incorrectas
	 * createCoordinateException lanza la excepción IllegalArgument exception
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateCoordinateException() {
		try {
		   CoordinateFactory.createCoordinate(-1);
		   fail("Error: debió lanzarse la excepción IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			CoordinateFactory.createCoordinate(4,3,2,1);
			fail ("comprueba otro caso más");
		}
	}

}
