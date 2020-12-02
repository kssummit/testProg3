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

public class DestroyerTest {
	Ship destroyerN, destroyerE, destroyerS, destroyerW;
	static List<Coordinate> north, east, south, west;
	static String sNorth, sEast, sSouth, sWest;
	final static int shape[][] = new int[][] {
	      { 0, 0, 0, 0, 0,
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,	
	    	0, 0, 0, 0, 0,
	    	0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    0, 1, 1, 0, 0,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,	
	    	0, 0, 0, 0, 0,
	    	0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    0, 1, 1, 0, 0,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0}}; 
		    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			north = new ArrayList<Coordinate>();
			east = new ArrayList<Coordinate>();
			south = new ArrayList<Coordinate>();
			west = new ArrayList<Coordinate>();
			for (int i=1; i < 3; i++) {
				north.add(new Coordinate2D(2,i));
				east.add(new Coordinate2D(i,2));
				south.add(new Coordinate2D(2,i));
				west.add(new Coordinate2D(i,2));
			}
	       
			sNorth ="Destroyer (NORTH)\n"
					+ " -----\n"
					+ "|     |\n"
					+ "|  Ω  |\n"
					+ "|  Ω  |\n"
					+ "|     |\n"
					+ "|     |\n"
					+ " -----";
			sEast = "Destroyer (EAST)\n"
		       		+ " -----\n"
		       		+ "|     |\n"
		       		+ "|     |\n"
		       		+ "| ΩΩ  |\n"
		       		+ "|     |\n"
		       		+ "|     |\n"
		       		+ " -----";
			sSouth ="Destroyer (SOUTH)\n"
					+ " -----\n"
					+ "|     |\n"
					+ "|  Ω  |\n"
					+ "|  Ω  |\n"
					+ "|     |\n"
					+ "|     |\n"
					+ " -----";
			sWest = "Destroyer (WEST)\n"
					+ " -----\n"
					+ "|     |\n"
					+ "|     |\n"
					+ "| ΩΩ  |\n"
					+ "|     |\n"
					+ "|     |\n"
					+ " -----";
	}		    
		    
		    
		    
	@Before
	public void setUp() throws Exception {
		destroyerN = new Destroyer(Orientation.NORTH);
		destroyerE = new Destroyer(Orientation.EAST);
		destroyerS = new Destroyer(Orientation.SOUTH);
		destroyerW = new Destroyer(Orientation.WEST);
		
	}

	@Test
	public void testGetShape() {
		int [][] shapeAux = destroyerN.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	@Test
	public void testGetOrientation() {
		assertEquals (Orientation.NORTH, destroyerN.getOrientation());
		assertEquals (Orientation.EAST, destroyerE.getOrientation());
		assertEquals (Orientation.SOUTH, destroyerS.getOrientation());
		assertEquals (Orientation.WEST, destroyerW.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals('Ω', destroyerN.getSymbol());
	}

	/* Se comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
			
			Coordinate c1 = new Coordinate2D(13,27);
			Set<Coordinate> pos = destroyerN.getAbsolutePositions(c1);
			for (Coordinate c: north)
				assertTrue("Valores Absolutos posiciones c1+"+c, pos.contains(c.add(c1)));
	}
		
	/* Se comprueba que las posiciones absolutas para la orientación EARTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsEast() {
		Coordinate c1 = new Coordinate2D(0,0);
		Set<Coordinate> pos = destroyerE.getAbsolutePositions(c1);
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
		Set<Coordinate> pos = destroyerS.getAbsolutePositions(c1);
		for (Coordinate c: south)
			assertTrue("Valores Absolutos posiciones South+c1", pos.contains(c.add(c1)));
	}

	/* Se comprueba que las posiciones absolutas para la orientación WEST a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsWest() {
		Coordinate c1 = new Coordinate2D(-11,-11);
		Set<Coordinate> pos = destroyerW.getAbsolutePositions(c1);
		for (Coordinate c: east) {
				assertTrue("Valores Absolutos posiciones East+c1", pos.contains(c.add(c1)));
		}
	}


	@Test
	public void testToString() {
		compareLines(sNorth,destroyerN.toString());
		compareLines(sSouth,destroyerS.toString());
		compareLines(sEast,destroyerE.toString());
		compareLines(sWest,destroyerW.toString());
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
