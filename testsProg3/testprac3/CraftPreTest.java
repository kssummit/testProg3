package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.aircraft.Board3D;
import model.aircraft.Bomber;
import model.ship.Carrier;

@SuppressWarnings("unused")
public class CraftPreTest {

	Craft carrier, bomber;
	
	@Before
	public void setUp() throws Exception {
		carrier = new Carrier(Orientation.EAST);
		bomber = new Bomber(Orientation.SOUTH);
	}
	
	@Test
	public void testGetShapeIndex() {
		System.out.println("Test GetShapeIndex");
		try {
			Coordinate c = CoordinateFactory.createCoordinate(null);
			carrier.setPosition(c);
			carrier.getShapeIndex(c);
			fail("No va el try catch");
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.getMessage();
		}
	}
	
	@Test
	public void testGetAbsolutePositionsNorth() {
		System.out.println("Test AbsolutePositions");
		try {
			Coordinate c = CoordinateFactory.createCoordinate(null);
			bomber.getAbsolutePositions(c);
		} catch (NullPointerException e) {
			e.getMessage();
		}
		try {
			Coordinate c = CoordinateFactory.createCoordinate(null);
			bomber.setPosition(c);
			bomber.getAbsolutePositions();
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}
	//TODO 
	/* Realiza los tests en los que se comprueba el lanzamiento de
	 * la excepción NullPointerException en los métodos getShapeIndex,
	 * getAbsolutePositions(Coordinate) y getAbsolutePositions
	 */
}
