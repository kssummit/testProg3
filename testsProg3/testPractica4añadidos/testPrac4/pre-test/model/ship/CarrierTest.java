/**
 * @author Samuel Oliva
 */
package model.ship;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Coordinate;
import model.Orientation;

public class CarrierTest {
	Ship carrierN, carrierE, carrierS, carrierW;
	static List<Coordinate> north, east, south, west;
	static String sNorth, sEast, sSouth, sWest;
	final static int shape[][] = new int[][] {
		  { 0, 0, 1, 0, 0,
		   	0, 0, 1, 0, 0,	
		   	0, 0, 1, 0, 0,	
		   	0, 0, 1, 0, 0,
		   	0, 0, 1, 0, 0},
		  { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    1, 1, 1, 1, 1,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0},
		  { 0, 0, 1, 0, 0,
		   	0, 0, 1, 0, 0,	
		   	0, 0, 1, 0, 0,	
		   	0, 0, 1, 0, 0,
		   	0, 0, 1, 0, 0},
		  { 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0,	
			1, 1, 1, 1, 1,	
			0, 0, 0, 0, 0,
			0, 0, 0, 0, 0}};  
		    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			north = new ArrayList<Coordinate>();
			east = new ArrayList<Coordinate>();
			south = new ArrayList<Coordinate>();
			west = new ArrayList<Coordinate>();
			for (int i=0; i < 5; i++) {
				north.add(new Coordinate2D(2,i));
				east.add(new Coordinate2D(i,2));
				south.add(new Coordinate2D(2,i));
				west.add(new Coordinate2D(i,2));
			}
	        sEast = "Carrier (EAST)\n -----\n"
	        		+ "|     |\n"
	        		+ "|     |\n"
	        		+ "|®®®®®|\n"
	        		+ "|     |\n"
	        		+ "|     |\n"
	        		+ " -----";
			sNorth ="Carrier (NORTH)\n -----\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ " -----";
			sSouth ="Carrier (SOUTH)\n"
					+ " -----\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ "|  ®  |\n"
					+ " -----";
			sWest = "Carrier (WEST)\n"
					+ " -----\n"
					+ "|     |\n"
					+ "|     |\n"
					+ "|®®®®®|\n"
					+ "|     |\n"
					+ "|     |\n"
					+ " -----";
	}		    
		       
		    
	@Before
	public void setUp() throws Exception {
		carrierN = new Carrier(Orientation.NORTH);
		carrierE = new Carrier(Orientation.EAST);
		carrierS = new Carrier(Orientation.SOUTH);
		carrierW = new Carrier(Orientation.WEST);
		
	}

	@Test
	public void testGetShape() {
		int [][] shapeAux = carrierN.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	@Test
	public void testGetOrientation() {
		assertEquals (Orientation.NORTH, carrierN.getOrientation());
		assertEquals (Orientation.EAST, carrierE.getOrientation());
		assertEquals (Orientation.SOUTH, carrierS.getOrientation());
		assertEquals (Orientation.WEST, carrierW.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals('®', carrierN.getSymbol());
	}

	/* Se comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
			
			Coordinate c1 = new Coordinate2D(13,27);
			Set<Coordinate> pos = carrierN.getAbsolutePositions(c1);
			for (Coordinate c: north)
				assertTrue("Valores Absolutos posiciones c1+"+c, pos.contains(c.add(c1)));
	}
		
	/* Se comprueba que las posiciones absolutas para la orientación EARTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsEast() {
		Coordinate c1 = new Coordinate2D(0,0);
		Set<Coordinate> pos = carrierE.getAbsolutePositions(c1);
		for (Coordinate c: east) {
				assertTrue("Valores Absolutos posiciones East+c1", pos.contains(c.add(c1)));
		}
	}
		
	/* Se comprueba que las posiciones absolutas para la orientación SOUTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsSouth() {
		Coordinate c1 = new Coordinate2D(300,700);
		Set<Coordinate> pos = carrierS.getAbsolutePositions(c1);
		for (Coordinate c: south)
			assertTrue("Valores Absolutos posiciones South+c1", pos.contains(c.add(c1)));
	}

	/* Se comprueba que las posiciones absolutas para la orientación WEST a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsWest() {
		Coordinate c1 = new Coordinate2D(-11,-11);
		Set<Coordinate> pos = carrierW.getAbsolutePositions(c1);
		for (Coordinate c: east) {
				assertTrue("Valores Absolutos posiciones East+c1", pos.contains(c.add(c1)));
		}
	}


	@Test
	public void testToString() {
		compareLines(sNorth,carrierN.toString());
		compareLines(sSouth,carrierS.toString());
		compareLines(sEast,carrierE.toString());
		compareLines(sWest,carrierW.toString());
	}

	/* ************************************************
	 * FUNCIONES AUXILIARES
	 **************************************************/
	private void  compareLines(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length; i++) {
			 				 assertEquals("linea "+i, exp[i],res[i]);
		}
	}
}
