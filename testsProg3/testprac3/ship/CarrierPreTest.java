package model.ship;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Coordinate;
import model.CoordinateFactory;
import model.Orientation;

public class CarrierPreTest {
	Ship carrierN, carrierE, carrierS, carrierW;
	static List<Coordinate> north;
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
			for (int i=0; i < 5; i++) {
				north.add(new Coordinate2D(2,i));
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

	/* Comprobación de shape del alumno */
	@Test
	public void testGetShape() {
		int [][] shapeAux = carrierN.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	//TODO
	/* Comprueba que las orientaciones de los Carrier creados en el setUp son 
	 * correctas.
	 */
	@Test
	public void testGetOrientation() {
		//fail("Realiza el test");
		assertEquals (Orientation.NORTH, carrierN.getOrientation());
		assertEquals (Orientation.EAST, carrierE.getOrientation());
		assertEquals (Orientation.SOUTH, carrierS.getOrientation());
		assertEquals (Orientation.WEST, carrierW.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals('®', carrierN.getSymbol());
		assertEquals('®', carrierE.getSymbol());
		assertEquals('®', carrierW.getSymbol());
		assertEquals('®', carrierS.getSymbol());
	}

	//TODO
	/* Comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
			//fail("Realiza el test");
		Coordinate c1 = CoordinateFactory.createCoordinate(3,3);
		Set<Coordinate> pos = new HashSet<Coordinate>();
		Set<Coordinate> posCopia = new HashSet<Coordinate>();
		posCopia = carrierN.getAbsolutePositions(c1);
		pos.add(CoordinateFactory.createCoordinate(5,3));
		pos.add(CoordinateFactory.createCoordinate(5,5));
		pos.add(CoordinateFactory.createCoordinate(5,6));
		pos.add(CoordinateFactory.createCoordinate(5,7));
		pos.add(CoordinateFactory.createCoordinate(5,4));
		assertEquals(pos, posCopia);
	}
		

	//TODO
	/* Comprueba que toString() para cada Carrier creado en el setUp coincide con 
	 * los correspondientes String creados en setUpBeforeClass()
	 */
	@Test
	public void testToString() {
		//fail("Realiza el test");
		assertEquals(sNorth,carrierN.toString());
		assertEquals(sEast,carrierE.toString());
		assertEquals(sWest,carrierW.toString());
		assertEquals(sSouth,carrierS.toString());
	}

}
