/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import model.ship.Coordinate2D;

public class CoordinateFactoryPreTest {

	   
	
	/* Crea una Coordinate2D con su constructor. Crea la misma Coordinate2D con el 
	 * método createCoordinate. Comprueba que ambas Coordinate2D creadas son iguales.
	 * Realiza lo mismo para Coordinate3D
	 */
	@Test
	public void testCreateCoordinateOk() {
		Coordinate2D pepe = new Coordinate2D(1, 3);
		assertEquals(pepe, CoordinateFactory.createCoordinate(1, 3));
	}
	
	/* Comprueba que si a createCoordinate se le pasa una cantidad de argumentos enteros 
	 * incorrecto, lanza la excepción IllegalArgumentException
	 */
	@Test
	public void testCreateCoordinateException() {
		try {
			CoordinateFactory.createCoordinate(1);
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			CoordinateFactory.createCoordinate(1, 2, 3, 4);
			fail();
		} catch (IllegalArgumentException e) { }
	}

}
