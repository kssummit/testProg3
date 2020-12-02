/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.ship.Coordinate2D;

public class CoordinateTestP2 {
	
    int []vcoor= {0,0,-70,-2,20}; //Para crear coordenadas
    final int DIM = vcoor.length;
    List<Coordinate> lcoor;
    
	@Before
	public void setUp() throws Exception {
		lcoor = new ArrayList<Coordinate>();
		//Se crean las Coordinate (0,0),(0,-70), (-70,-2),(-2,20);
		for (int i=0; i<DIM-1; i++) {
			lcoor.add(new Coordinate2D(vcoor[i],vcoor[i+1]));
		}
		
	}

	/* Creamos copias de las Coordinates creadas en el setUp() y comprobamos que:
	 * 1 - La copia y el original no son la misma.
	 * 2 - La copia tiene los mismos valores en las componentes respectivas que el objeto copiado.
	 */
	@Test
	public void testCopy() {
		Coordinate ccopy;
		
		for (Coordinate caux: lcoor) {
			ccopy= caux.copy();
			assertNotSame(caux, ccopy);
			assertEquals(caux.get(0),ccopy.get(0));
			assertEquals(caux.get(1),ccopy.get(1));	
		}
	}

	/* Se crea una Coordinate y a partir de ella se obtienen las Coordinate adyacentes 
	 * que se guardan en un Set<Coordinate>. Para cada una de las posiciones adyacentes
	 * a la Coordinate inicial se crea una Coordinate, y se va comprobando están
	 * contenidas en el Set.
	 * 
	 */
	@Test
	public void testAdjacentCoordinates() {
		
        Coordinate c = new Coordinate2D(-3,5);
		Set<Coordinate> setcoord = c.adjacentCoordinates();
		//Set<Coordinate> setcoord = new HashSet<Coordinate>();
		for (int i=-1; i<2; i++) 
			for (int j=-1; j<2; j++) 
		      if ((i==0)&&(j==0))
		    	  assertFalse(setcoord.contains(new Coordinate2D(c.get(0)+i,c.get(1)+j)));
		      else
		    	  assertTrue(setcoord.contains(new Coordinate2D(c.get(0)+i,c.get(1)+j)));
	}
	
	
}
