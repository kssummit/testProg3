/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import model.aircraft.Coordinate3D;
import model.ship.Coordinate2D;

public class CoordinateFactoryTest {

	/* Creación correcta de Coordinate */
	@Test
	public void testCreateCoordinateOk() {
		Coordinate aux = new Coordinate2D(-1, 23);
		Coordinate c = CoordinateFactory.createCoordinate(-1, 23);
		assertEquals(aux, c);
		aux = new Coordinate3D(15, -7, 18);
		c = CoordinateFactory.createCoordinate(15, -7, 18);
		assertEquals(aux, c);
	}
	
	/* Se pasa un número incorrecto de enteros a createCoordinate. Se lanzan 
	 * IllegalArgumentException en cada caso.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateCoordinateException() {
		try {
		   CoordinateFactory.createCoordinate(-1);
		   fail("Error: debió lanzarse la excepción IllegalArgumentException");
		} catch (IllegalArgumentException e1) {
			try {
			  CoordinateFactory.createCoordinate(-1, 3, 4, 0);
			  fail("Error: debió lanzarse la excepción IllegalArgumentException");
			} catch (IllegalArgumentException e2) {
			     CoordinateFactory.createCoordinate();
			}
		}
	}

}
