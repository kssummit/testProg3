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

public class BattleshipPreTest {
	Ship battleshipN, battleshipE, battleshipS, battleshipW;
	static List<Coordinate> north;
	static String sNorth, sEast, sSouth, sWest;
	final static int shape[][] = new int[][] {
	      { 0, 0, 0, 0, 0,
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,
	    	0, 0, 1, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    0, 1, 1, 1, 1,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,
	    	0, 0, 1, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    0, 1, 1, 1, 1,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0}}; 
		    
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			north = new ArrayList<Coordinate>();
			for (int i=1; i < 5; i++) {
				north.add(new Coordinate2D(2,i));
			}
			 sEast = "Battleship (EAST)\n"
		        		+ " -----\n"
		        		+ "|     |\n"
		        		+ "|     |\n"
		        		+ "| OOOO|\n"
		        		+ "|     |\n"
		        		+ "|     |\n"
		        		+ " -----";
				sNorth ="Battleship (NORTH)\n"
						+ " -----\n"
						+ "|     |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ " -----";
				sSouth ="Battleship (SOUTH)\n"
						+ " -----\n"
						+ "|     |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ "|  O  |\n"
						+ " -----";
				sWest = "Battleship (WEST)\n"
						+ " -----\n"
						+ "|     |\n"
						+ "|     |\n"
						+ "| OOOO|\n"
						+ "|     |\n"
						+ "|     |\n"
						+ " -----";
	}		    
		    
		    
		    
	@Before
	public void setUp() throws Exception {
		battleshipN = new Battleship(Orientation.NORTH);
		battleshipE = new Battleship(Orientation.EAST);
		battleshipS = new Battleship(Orientation.SOUTH);
		battleshipW = new Battleship(Orientation.WEST);	
	}

	/* Comprobación de shape del alumno */
	@Test
	public void testGetShape() {
		int [][] shapeAux = battleshipN.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	//TODO
	/* Comprueba que las orientaciones de los Battleship creados en el setUp son 
	 * correctas.
	 */
	@Test
	public void testGetOrientation() {
		//fail ("Realiza el test");
		assertEquals (Orientation.NORTH, battleshipN.getOrientation());
		assertEquals (Orientation.EAST, battleshipE.getOrientation());
		assertEquals (Orientation.SOUTH, battleshipS.getOrientation());
		assertEquals (Orientation.WEST, battleshipW.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals('O', battleshipN.getSymbol());
		assertEquals('O', battleshipE.getSymbol());
		assertEquals('O', battleshipS.getSymbol());
		assertEquals('O', battleshipW.getSymbol());
	}

	//TODO
	/* Comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
		//fail ("Realiza el test");
		Coordinate c1 = CoordinateFactory.createCoordinate(3,3);
		Set<Coordinate> pos = new HashSet<Coordinate>();
		Set<Coordinate> posCopia = new HashSet<Coordinate>();
		posCopia = battleshipN.getAbsolutePositions(c1);
		pos.add(CoordinateFactory.createCoordinate(5,5));
		pos.add(CoordinateFactory.createCoordinate(5,6));
		pos.add(CoordinateFactory.createCoordinate(5,7));
		pos.add(CoordinateFactory.createCoordinate(5,4));
		assertEquals(pos, posCopia);
	}

	//TODO
	/* Comprueba que toString() para cada Battleship creado en el setUp coincide con 
	 * los correspondientes String creados en setUpBeforeClass()
	 */
	@Test
	public void testToString() {
		//fail ("Realiza el test");
		assertEquals(sNorth,battleshipN.toString());
		assertEquals(sEast,battleshipE.toString());
		assertEquals(sWest,battleshipW.toString());
		assertEquals(sSouth,battleshipS.toString());
	}

}
