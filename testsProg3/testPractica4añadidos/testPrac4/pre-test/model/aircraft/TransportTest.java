/**
 * @author Samuel Oliva
 */
package model.aircraft;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Coordinate;
import model.Orientation;
import model.ship.Coordinate2D;

public class TransportTest {
	Aircraft transportN, transportE, transportS, transportW;
	static List<Coordinate> north, east, south, west;
	static String sNorth, sEast, sSouth, sWest;
	final static int shape[][] = new int[][] {
		 { 0, 0, 1, 0, 0,
		   0, 0, 1, 0, 0,	
		   0, 1, 1, 1, 0,	
		   1, 0, 1, 0, 1,
		   0, 0, 1, 0, 0},
		 { 0, 1, 0, 0, 0,
		   0, 0, 1, 0, 0,	
		   1, 1, 1, 1, 1,	
		   0, 0, 1, 0, 0,
		   0, 1, 0, 0, 0},
		 { 0, 0, 1, 0, 0,
		   1, 0, 1, 0, 1,	
		   0, 1, 1, 1, 0,	
		   0, 0, 1, 0, 0,
		   0, 0, 1, 0, 0},
		 { 0, 0, 0, 1, 0,
		   0, 0, 1, 0, 0,	
		   1, 1, 1, 1, 1,	
		   0, 0, 1, 0, 0,
		   0, 0, 0, 1, 0}}; 
		    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			north = new ArrayList<Coordinate>();
			east = new ArrayList<Coordinate>();
			south = new ArrayList<Coordinate>();
			west = new ArrayList<Coordinate>();
			for (int i=0; i < 5; i++) {
				north.add(new Coordinate3D(2,i,0));	
				east.add(new Coordinate3D(i,2,0));
				south.add(new Coordinate3D(2,i,0));
				west.add(new Coordinate3D(i,2,0));
				if (i<2) {
					north.add(new Coordinate3D(i,3-i,0));
					north.add(new Coordinate3D(i+3,i+2,0));
					east.add(new Coordinate3D(i+1,i,0));
					east.add(new Coordinate3D(i+1,4-i,0));
					south.add(new Coordinate3D(i,i+1,0));
					south.add(new Coordinate3D(4-i,i+1,0));
					west.add(new Coordinate3D(i+2,i+3,0));
					west.add(new Coordinate3D(3-i,i,0));
				}
			}
			
			sNorth ="Transport (NORTH)\n"
					+ " -----\n"
					+ "|  ⇋  |\n"
					+ "|  ⇋  |\n"
					+ "| ⇋⇋⇋ |\n"
					+ "|⇋ ⇋ ⇋|\n"
					+ "|  ⇋  |\n"
					+ " -----";
			sEast = "Transport (EAST)\n"
					+ " -----\n"
					+ "| ⇋   |\n"
					+ "|  ⇋  |\n"
					+ "|⇋⇋⇋⇋⇋|\n"
					+ "|  ⇋  |\n"
					+ "| ⇋   |\n"
					+ " -----";
			sSouth ="Transport (SOUTH)\n"
					+ " -----\n"
					+ "|  ⇋  |\n"
					+ "|⇋ ⇋ ⇋|\n"
					+ "| ⇋⇋⇋ |\n"
					+ "|  ⇋  |\n"
					+ "|  ⇋  |\n"
					+ " -----";
			sWest = "Transport (WEST)\n"
					+ " -----\n"
					+ "|   ⇋ |\n"
					+ "|  ⇋  |\n"
					+ "|⇋⇋⇋⇋⇋|\n"
					+ "|  ⇋  |\n"
					+ "|   ⇋ |\n"
					+ " -----";
			}		    
		    
		    
		    
	@Before
	public void setUp() throws Exception {
		transportN = new Transport(Orientation.NORTH);
		transportE = new Transport(Orientation.EAST);
		transportS = new Transport(Orientation.SOUTH);
		transportW = new Transport(Orientation.WEST);
		
	}

	@Test
	public void testGetShape() {
		int [][] shapeAux = transportN.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	@Test
	public void testGetOrientation() {
		assertEquals (Orientation.NORTH, transportN.getOrientation());
		assertEquals (Orientation.EAST, transportE.getOrientation());
		assertEquals (Orientation.SOUTH, transportS.getOrientation());
		assertEquals (Orientation.WEST, transportW.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals('⇋', transportN.getSymbol());
	}

	/* Se comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
			
			Coordinate c1 = new Coordinate3D(13,27,5);
			Set<Coordinate> pos = transportN.getAbsolutePositions(c1);
			for (Coordinate c: north)
				assertTrue("Valores Absolutos posiciones c1+"+c, pos.contains(c.add(c1)));
	}
		
	/* Se comprueba que las posiciones absolutas para la orientación EARTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsEast() {
		Coordinate c1 = new Coordinate3D(0,0,5);
		Set<Coordinate> pos = transportE.getAbsolutePositions(c1);
		for (Coordinate c: east) {
				assertTrue("Valores Absolutos posiciones East c1+"+c, pos.contains(c.add(c1)));
		}
	}
		
	/* Se comprueba que las posiciones absolutas para la orientación SOUTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsSouth() {
		Coordinate c1 = new Coordinate3D(300,700,5);
		Set<Coordinate> pos = transportS.getAbsolutePositions(c1);
		for (Coordinate c: south)
			assertTrue("Valores Absolutos posiciones South c1+"+c, pos.contains(c.add(c1)));
	}

	/* Se comprueba que las posiciones absolutas para la orientación WEST a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsWest() {
		Coordinate c1 = new Coordinate3D(-11,-11,5);
		Set<Coordinate> pos = transportW.getAbsolutePositions(c1);
		for (Coordinate c: west) {
				assertTrue("Valores Absolutos posiciones West c1+"+c, pos.contains(c.add(c1)));
		}
	}


	@Test
	public void testToString() {
		compareLines(sNorth,transportN.toString());
		compareLines(sSouth,transportS.toString());
		compareLines(sEast,transportE.toString());
		compareLines(sWest,transportW.toString());
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
