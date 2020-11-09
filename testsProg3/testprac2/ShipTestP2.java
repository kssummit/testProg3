package model;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.CoordinateAlreadyHitException;
import model.ship.Coordinate2D;
import model.ship.Cruiser;
import model.ship.Ship;

public class ShipTestP2 {
	final static int BOUNDING_SQUARE_SIZE = 5;
	static ArrayList<Coordinate> north, east, south, west;
    static String sNorth, sEast, sSouth, sWest;
	Ship bergantin, goleta, fragata, galeon;
    final static int shape[][] = new int[][] {
	      { 0, 0, 0, 0, 0,
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0, 0,
	    	0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0,	
		    0, 1, 1, 1, 0,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0},
	      { 0, 0, 0, 0, 0,
		    0, 0, 1, 0, 0,	
		    0, 0, 1, 0, 0,	
		    0, 0, 1, 0, 0,
		    0, 0, 0, 0, 0},
		  { 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0,	
		    0, 1, 1, 1, 0,	
		    0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0}}; 
		
		    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			north = new ArrayList<Coordinate>();
			east = new ArrayList<Coordinate>();
			south = new ArrayList<Coordinate>();
			west = new ArrayList<Coordinate>();
			for (int i=1; i < 4; i++) {
				north.add(new Coordinate2D(2,i));
				east.add(new Coordinate2D(i,2));
				south.add(new Coordinate2D(2,i));
				west.add(new Coordinate2D(i,2));
			}
			 sEast = "Cruiser (EAST)\n -----\n|     |\n|     |\n| ØØØ |\n|     |\n|     |\n -----";
			 sNorth ="Cruiser (NORTH)\n -----\n|     |\n|  Ø  |\n|  Ø  |\n|  Ø  |\n|     |\n -----";
			 sSouth ="Cruiser (SOUTH)\n -----\n|     |\n|  Ø  |\n|  Ø  |\n|  Ø  |\n|     |\n -----";
			 sWest = "Cruiser (WEST)\n -----\n|     |\n|     |\n| ØØØ |\n|     |\n|     |\n -----";
	}	    
		    
		    
		    
		    
	@Before
	public void setUp() throws Exception {
		bergantin = new Cruiser(Orientation.EAST);
		goleta = new Cruiser(Orientation.NORTH);
		fragata = new Cruiser(Orientation.WEST);
		galeon = new Cruiser(Orientation.SOUTH);
	}


	/* Se comprueba la composición entre Ship y Coordinate. Para ello se
	 * crea un objeto Coordinate, posicionamos un Ship a esa Coordinate.
	 * Comprobamos que esa Coordinate y la posición del Ship son iguales.
	 * Modificamos la Coordinate y comprobamos que ésta y la posición del
	 * Ship ya no son iguales 
	 */
	@Test
	public void testSetPosition() {
		Coordinate pos = new Coordinate2D(7,4);
	     
		//Comprobamos la composición entre Ship y Coordinate
		bergantin.setPosition(pos);
		assertEquals (pos, bergantin.getPosition());
		pos.set(0, -2);
		pos.set(1, -24);
		assertNotEquals(pos, bergantin.getPosition());
		
		
		//Modificamos posición y comprobamos de nuevo
		pos = new Coordinate2D(-17,-2);
		bergantin.setPosition(pos);	
		assertEquals(pos, bergantin.getPosition());
		pos.set(0, 12);
		pos.set(1, 34);
		assertNotEquals(pos, bergantin.getPosition());
	}

	/* Se comprueba que la posición inicial de un Ship es null.
	 * Comprobamos que getPosition hace copia defensiva: Para ello
	 * se posiciona el Ship en una Coordinate concreta. Se comprueba 
	 * que la posición del Ship y la Coordinate son iguales, pero no
	 * tienen la misma referencia.
	 */
	@Test
	public void testGetPosition() throws NullPointerException {
		assertEquals(null, bergantin.getPosition());
		assertNull(bergantin.getPosition());
		//Inicialmente la position del ship debe ser null 
		
		
		//Comprobamos que getPosition hace copia defensiva
		Coordinate pos1 = new Coordinate2D(7,4);
		bergantin.setPosition(pos1);
		Coordinate pos2 = bergantin.getPosition();
		assertNotSame (pos1, pos2);
		assertEquals(pos1, pos2);
	}

	@Test
	public void testGetName() {
		assertEquals ("Cruiser",bergantin.getName());
		assertEquals ("Cruiser",fragata.getName());
	}

	@Test
	public void testGetOrientation() {
		assertEquals (Orientation.NORTH, goleta.getOrientation());
		assertEquals (Orientation.EAST, bergantin.getOrientation());
		assertEquals (Orientation.SOUTH, galeon.getOrientation());
		assertEquals (Orientation.WEST, fragata.getOrientation());
	}

	@Test
	public void testGetSymbol() {
		assertEquals ('Ø',bergantin.getSymbol());
		assertEquals ('Ø',goleta.getSymbol());
		assertEquals ('Ø',galeon.getSymbol());
		assertEquals ('Ø',fragata.getSymbol());
	}

	/* Se comprueba que la matriz shape del alumno es correcta */
	@Test
	public void testGetShape() {
		int [][] shapeAux = goleta.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals(shape[i][j],shapeAux[i][j]);
	}

	/* Se comprueba, para todas las coordenadas relativas, que getShapeIndex(Coordinate):
	 * 1- Devuelve un valor entre 0 y 24 (ambos inclusives)
	 * 2- El correspondiente valor de x dentro de shape[][] para las distintas orientaciones, es correcto.
	 */
	@Test
	public void testGetShapeIndex() {
		Coordinate c;
		int x;
		for (int i=0; i<BOUNDING_SQUARE_SIZE; i++)
			for (int j=0; j<BOUNDING_SQUARE_SIZE; j++) {
				c = new Coordinate2D(i,j);
				x = goleta.getShapeIndex(c);
				assertTrue ("0<="+x+"<=24", (0<=x) && (x<=24));
				if ( (x==7)||(x==12)||(x==17) ) {
					assertTrue("Sape[NORTH]["+x+"]==1",goleta.getShape()[Orientation.NORTH.ordinal()][x]==1);
					assertTrue("Sape[SOUTH]["+x+"]==1",goleta.getShape()[Orientation.SOUTH.ordinal()][x]==1);
				}
				else {
					assertTrue("Sape[NORTH]["+x+"]==1",goleta.getShape()[Orientation.NORTH.ordinal()][x]==0);
					assertTrue("Sape[SOUTH]["+x+"]==1",goleta.getShape()[Orientation.SOUTH.ordinal()][x]==0);
				}
				if ( (x>10)&&(x<14) ) {
					assertTrue("Sape[EAST]["+x+"]==1",goleta.getShape()[Orientation.EAST.ordinal()][x]==1);
					assertTrue("Sape[WEST]["+x+"]==1",goleta.getShape()[Orientation.WEST.ordinal()][x]==1);
				}
				else {
					assertTrue("Sape[EAST]["+x+"]==1",goleta.getShape()[Orientation.EAST.ordinal()][x]==0);
					assertTrue("Sape[WEST]["+x+"]==1",goleta.getShape()[Orientation.WEST.ordinal()][x]==0);
				}
				
			}
	}

	/* Se comprueba que las posiciones absolutas para la orientación NORTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
		
		Coordinate c1 = new Coordinate2D(13,27);
		Set<Coordinate> pos = goleta.getAbsolutePositions(c1);
		for (Coordinate c: north)
			assertTrue("Valores Absolutos posiciones c1+"+c, pos.contains(c.add(c1)));
	}
	
	/* Se comprueba que las posiciones absolutas para la orientación EARTH a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsEast() {
		Coordinate c1 = new Coordinate2D(0,0);
		Set<Coordinate> pos = bergantin.getAbsolutePositions(c1);
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
		Set<Coordinate> pos = galeon.getAbsolutePositions(c1);
		for (Coordinate c: south)
			assertTrue("Valores Absolutos posiciones South+c1", pos.contains(c.add(c1)));
	}

	/* Se comprueba que las posiciones absolutas para la orientación WEST a partir de
	 * una Coordinate son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsWest() {
		Coordinate c1 = new Coordinate2D(-11,-11);
		Set<Coordinate> pos = fragata.getAbsolutePositions(c1);
		for (Coordinate c: east) {
				assertTrue("Valores Absolutos posiciones East+c1", pos.contains(c.add(c1)));
		}
	}

	/* Se posiciona varios Ship en una Coordinate.
	 * Se comprueba que sus posiciones absolutas son correctas.
	 */
	@Test
	public void testGetAbsolutePositionsShips() {
		
		Coordinate c1 = new Coordinate2D(119,-123);
		getAbsolutePositionsShip(c1,goleta,north);
		getAbsolutePositionsShip(c1,galeon,south);
		getAbsolutePositionsShip(c1,fragata,west);
		getAbsolutePositionsShip(c1,bergantin,east);
	}
	
	/* Se dispara a un Ship que todavía no ha sido posicionado. Se comprueba que
	 * hit devuelve false
	 */
	@Test
	public void testHitShipPositionNull() throws CoordinateAlreadyHitException {
		Coordinate c1 = new Coordinate2D(2,1);
		try {
			assertFalse(goleta.hit(c1));
			fail("Error: No lanzó NullPointerException");
		} catch (NullPointerException e) {
			e.getCause();
		}
	}
	
	/* Se posiciona un Ship en una Coordinate y se realizan disparos al agua.
	 * Se comprueba que hit devuelve siempre false
	 */
	@Test
	public void testHitWater() throws CoordinateAlreadyHitException {
		Coordinate c1 = new Coordinate2D(2,1);
		goleta.setPosition(c1);
		assertFalse(goleta.hit(c1));
		for (int i=3; i<7; i++) {
			for (int j=1; j<6; j++)
			  if ( (i==4) && ((j<2)||(j>4)) ) 
				  assertFalse(goleta.hit(new Coordinate2D(i,j)));
		}
	}
	
	/* Se posiciona un Ship en una Coordinate, y se realizan primeros disparos a 
	 * las posiciones del Ship y se comprueba que hit devuelve true. Se vuelve a 
	 * disparar a las mismas posiciones y se comprueba que hit ahora devuelve false.
	 */
	@Test
	public void testHitShip() throws CoordinateAlreadyHitException {
		Coordinate c1 = new Coordinate2D(2,1);
		goleta.setPosition(c1);
		for (int j=2; j<5; j++) {
		   assertTrue(goleta.hit(new Coordinate2D(4,j))); 
		   try {
				assertFalse("Coordinate(4,"+j+")",goleta.hit(new Coordinate2D(4,j)));
				fail ("Debió lanzar la excepción CoordinateAlreadyHitException");
			   } catch (CoordinateAlreadyHitException e) {

			   }
		}
	}

	/* Se comprueba que:
	 * 1- isShotDown() a un Ship sin posicionar devuelve false.
	 * 2- isShotDown() devuelve false tras posicionar un Ship en una Coordinate.
	 * */
	@Test
	public void testIsShotDown1() {
		Coordinate c1 = new Coordinate2D(2,1);
		assertFalse(bergantin.isShotDown());
		bergantin.setPosition(c1);
		assertFalse(bergantin.isShotDown());  
	}
	
	/* Se comprueba que:
	 * 1- isShotDown() devuelve false tras realizar disparos a todas las posiciones del
	 *    Ship excepto una. 
	 * 2- isShotDown() devuelve true tras disparar a la única posición no dañada.
	 * 
	 */
	@Test
	public void testIsShotDown2() throws CoordinateAlreadyHitException {
		Coordinate c1 = new Coordinate2D(2,1);
		bergantin.setPosition(c1);
		for (int j=3; j<6; j++) {
		   bergantin.hit(new Coordinate2D(j,3));
		   if (j!=5) assertFalse(bergantin.isShotDown());
		   else assertTrue(bergantin.isShotDown());
		}
		
	}

	/* Se comprueba que:
	 * 1- isHit en un Ship no posicionado devuelve false.
	 * 2- isHit sobre una Coordinate en una posición fuera
	 *    de un Ship ya posicionado, devuelve false.
	 */
	@Test
	public void testIsHit1() {
		Coordinate c = new Coordinate2D(2,1);
		//Ship no posicionado
		try {
		      assertFalse(bergantin.isHit(c));
		      fail ("Debió lanzar NullPointerException");
			} catch (NullPointerException e) {
					bergantin.setPosition(c);
					//Ship posicionado. Coordinate c en agua
					assertFalse(bergantin.isHit(c));
			} 
	}
	
	/* Se comprueba que:	
	 * 1- isHit sobre las Coordinates de un Ship devuelve false.
	 * 2- isHit sobre las Coordinates de un Ship devuelve true después 
	 *    de disparar sobre ellas (hit) 
	 *     
	 */
	@Test
	public void testIsHit() throws CoordinateAlreadyHitException {
		Coordinate c = new Coordinate2D(2,1);
		bergantin.setPosition(c);
		//Preguntamos en ship antes de disparar y despues de disparar
		for (int j=3; j<6; j++) {
		   c = new Coordinate2D(j,3);
		   assertFalse(bergantin.isHit(c));
		   bergantin.hit(c);
		   assertTrue(bergantin.isHit(c));
		}
	}

	/* Se comprueba que las salidas de los distintos Ships en distintas orientaciones
	 * son correctas
	 */
	@Test
	public void testToString() {
		assertEquals(sNorth,goleta.toString());
		assertEquals(sSouth,galeon.toString());
		assertEquals(sEast,bergantin.toString());
		assertEquals(sWest,fragata.toString());
	}
	
	
	//FUNCIONES DE APOYO
	
	void getAbsolutePositionsShip (Coordinate cpos, Ship ship, List<Coordinate> orient ) {
	   ship.setPosition(cpos);
	   Set<Coordinate> pos = ship.getAbsolutePositions();
	   for (Coordinate c: orient)
		  assertTrue("Valores Absolutos posiciones c1+"+c, pos.contains(c.add(cpos)));
	}
}
