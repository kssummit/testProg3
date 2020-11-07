package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/* Para realizar los test se sugiere usar métodos de la librería de junit como:
 * assertEquals; assertSame; assertNotSame, assertTrue; assertFalse
 */
public class CoordinatePreTestP2 {
	
    int []vcoor= {0,0,-70,-2,20}; //Para crear coordenadas
    final int DIM = vcoor.length;
    List<Coordinate> lcoor;
    
	@Before
	public void setUp() throws Exception {
		lcoor = new ArrayList<Coordinate>();
		//Se crean las Coordinates (0,0),(0,-70), (-70,-2),(-2,20);
		for (int i=0; i<DIM-1; i++) {
			lcoor.add(CoordinateFactory.createCoordinate(vcoor[i],vcoor[i+1]));
		}
	}

	
	//TODO testCopy
	/* Crea copias de las Coordinates creadas en el setUp() y comprueba que:
	 * 1 - La copia y el original no son la misma.
	 * 2 - La copia tiene los mismos valores, en las componentes respectivas, que el objeto copiado.
	 */
	@Test
	public void testCopy() {
		lcoor = new ArrayList<Coordinate>();
		for (Coordinate c : lcoor) {
			assertFalse(c.copy().equals(c));
			assertEquals(c.get(0), c.copy().get(0));
			assertEquals(c.get(1), c.copy().get(1));
		}
		//TODO fail ("Realizar el test propuesto");
	}

	//TODO testAdjacentCoordinates
	/* Crea una Coordinate y a partir de ella obten las Coordinate adyacentes 
	 * con tu método adjacentCoordinates(). Guárdalas en un Set. 
	 * Para cada una de las posiciones adyacentes a la Coordinate inicial, crea una 
	 * Coordinate, y comprueba que están contenidas en el Set.
	 */
	@Test
	public void testAdjacentCoordinates() {
		Coordinate c = CoordinateFactory.createCoordinate(5,3);
		Set<Coordinate> coordinates = c.adjacentCoordinates();
		Set<Coordinate> adjacents = new HashSet<Coordinate>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) { //00 se refiere a la coordenada origen que en este caso es la 5,3
					continue;
				}
				else {
					adjacents.add(CoordinateFactory.createCoordinate(c.get(0)+i, c.get(1)+j));
				}
			}
		}
		
		for (Coordinate c1 : adjacents) {
			assertTrue(coordinates.contains(c1));
		}
		assertEquals(coordinates.size(), 9-1);
		
	}
}
