package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.CoordinateAlreadyHitException;
import model.ship.Ship;

public class ShipPreTest {
	final static int BOUNDING_SQUARE_SIZE = 5;
	static ArrayList<Coordinate> north, east, south, west;
    static String sNorth, sEast, sSouth, sWest;
	Craft bergantin, goleta, fragata, galeon;
	Fighter b1;
    final static int shape[][] = new int[][] {
	      { 0, 0, 0, 0, 0,
	    	0, 0, 1, 0, 0,	
	    	0, 0, 1, 0,ula. Si fuera nula el método lanzará una NullPoin 0,	
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
		    //Coordinates relativas de las distintas orientaciones de un Ship
			north = new ArrayList<Coordinate>();
			east = new ArrayList<Coordinate>();
			south = new ArrayList<Coordinate>();
			west = new ArrayList<Coordinate>();
			for (int i=1; i < 4; i++) {
				north.add(CoordinateFactory.createCoordinate(2,i));
				east.add(CoordinateFactory.createCoordinate(i,2));
				south.add(CoordinateFactory.createCoordinate(2,i));
				west.add(CoordinateFactory.createCoordinate(i,2));
			}
			
			 sEast = "Bergantín (EAST)\n -----\n|     |\n|     |\n| BBB |\n|     |\n|     |\n -----";
			 sNorth ="Goleta (NORTH)\n -----\n|     |\n|  G  |\n|  G  |\n|  G  |\n|     |\n -----";
			 sSouth ="Galeón (SOUTH)\n -----\n|     |\n|  A  |\n|  A  |\n|  A  |\n|     |\n -----";
			 sWest = "Fragata (WEST)\n -----\n|     |\n|     |\n| FFF |\n|     |\n|     |\n -----";
	}	    
		    
		    
		    
		    
	@Before
	public void setUp() throws Exception {
		bergantin = new Craft(Orientation.EAST,'B',"Bergantín");
		goleta = new Ship(Orientation.NORTH,'G',"Goleta");
		fragata = new Ship(Orientation.WEST,'F',"Fragata");
		galeon = new Ship(Orientation.SOUTH,'A',"Galeón");
	}

 
	//testGetPosition hecho
	/* Comprueba que la posición inicial de un Ship es null.
	 * Comprueba también que getPosition hace copia defensiva. Para ello:
	 * 1- Posiciona el Ship en una Coordinate concreta. 
	 * 2- Comprueba que la posición del Ship y la Coordinate son iguales, pero no
	 *    tienen la misma referencia.
	 */
	@Test
	public void testGetPosition() {
		assertEquals(null, bergantin.getPosition());
		Coordinate c = CoordinateFactory.createCoordinate(5,3);
		bergantin.setPosition(c);
		assertEquals(c, bergantin.getPosition());
	}

	/* Se comprueba que getName() es correcto. 
	 */
	@Test
	public void testGetName() {
		assertEquals ("Bergantín",bergantin.getName());
		assertEquals ("Fragata",fragata.getName());
	}

	/* Se comprueba que getOrientation() es correcto.
	 */
	@Test
	public void testGetOrientation() {
		assertEquals (Orientation.NORTH, goleta.getOrientation());
		assertEquals (Orientation.EAST, bergantin.getOrientation());
		assertEquals (Orientation.SOUTH, galeon.getOrientation());
		assertEquals (Orientation.WEST, fragata.getOrientation());
	}

	/* Se comprueba que getSymbol() es correcto.
	 */
	@Test
	public void testGetSymbol() {
		assertEquals ('B',bergantin.getSymbol());
		assertEquals ('G',goleta.getSymbol());
		assertEquals ('A',galeon.getSymbol());
		assertEquals ('F',fragata.getSymbol());
	}

	/* Se comprueba que la matriz shape del alumno es correcta 
	 */
	@Test
	public void testGetShape() {
		int [][] shapeAux = goleta.getShape();
		for (int i=0; i< shape.length; i++) 
			for (int j=0; j<shape[i].length; j++)
				assertEquals("shape["+i+"]["+j+"]==shapeAux["+i+"]["+j+"]", shape[i][j],shapeAux[i][j]);
	}

	//testGetShapeIndex hecho
	/* Comprueba, para todas las coordenadas relativas, que getShapeIndex(Coordinate c):
	 * 1- Devuelve un valor entre 0 y 24 (ambos inclusives)
	 * 2- Los correspondientes valores dentro de shape[][] para las distintas orientaciones y 
	 *    los distintos valores devueltos por getShapeIndex, son correctos.
	 */
	@Test
	public void testGetShapeIndex() {
		Coordinate c = CoordinateFactory.createCoordinate(3,4);
		goleta.setPosition(c);
		int pos =0;
		for (int i = 0; i < BOUNDING_SQUARE_SIZE; i++) {
			for (int j = 0; j < BOUNDING_SQUARE_SIZE ;j++) {
				pos = goleta.getShapeIndex(CoordinateFactory.createCoordinate(i,j));
				assertEquals(pos,j*BOUNDING_SQUARE_SIZE+i);
			}
		}		
	}

	//hecho testGetAbsolutePositionsNorth
	/* Comprueba que las posiciones absolutas que devuelve el método 
	 * getAbsolutePositions(Coordinate) de un barco con orientación NORTH 
	 * a partir de una Coordinate son correctas. Ten en cuenta que el 
	 * ArrayList estático 'north', declarado al principio, contiene 
	 * las Coordinates relativas del barco para la orientación NORTH.
	 */
	@Test
	public void testGetAbsolutePositionsNorth() {
		
		Coordinate c1 = CoordinateFactory.createCoordinate(3,3);
		Coordinate aux = c1.copy();
		Set<Coordinate> pos = new HashSet<Coordinate>();
		Set<Coordinate> posCopia = new HashSet<Coordinate>();
		posCopia = goleta.getAbsolutePositions(c1);
		int k = 1;
		do {
			aux.set(0, aux.get(0)+2);
			aux.set(1, aux.get(1)+k);
			pos.add(aux);
			aux = c1.copy();
			k++;
		} while (pos.size()<3);
		assertEquals(pos, posCopia);
	}

	/* Se dispara a un Ship que todavía no ha sido posicionado. 
	 * Se comprueba que hit devuelve false
	 */
	@Test
	public void testHitShipPositionNull() {
		try {
			assertFalse(goleta.hit(bergantin.getPosition()));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertFalse(galeon.hit(goleta.getPosition()));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Posiciona un Ship en una Coordinate y realiza disparos al agua.
	 * Comprueba que hit devuelve siempre false
	 */
	@Test
	public void testHitWater() {
		
		goleta.setPosition(CoordinateFactory.createCoordinate(2, 3));
        try {
			assertFalse(goleta.hit(CoordinateFactory.createCoordinate(2, 3)));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			assertFalse(goleta.hit(CoordinateFactory.createCoordinate(3, 3)));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TODO testHitShip
	/* Posiciona un Ship en una Coordinate, y realiza los primeros disparos a 
	 * las posiciones del Ship y comprueba que hit devuelve true. 
	 * Vuelve a disparar a las mismas posiciones y comprueba que hit ahora 
	 * devuelve false.
	 */
	@Test
	public void testHitShip() {
		Coordinate generatriz = CoordinateFactory.createCoordinate(2,3);
		Coordinate golpeo = CoordinateFactory.createCoordinate(4,5);
		goleta.setPosition(generatriz);
		bergantin.setPosition(generatriz);
		try {
			assertTrue(goleta.hit(golpeo));
		} catch (CoordinateAlreadyHitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			assertFalse(goleta.hit(golpeo));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertTrue(bergantin.hit(golpeo));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertFalse(bergantin.hit(golpeo));
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//TODO testIsShotDown2
	/* Comprueba que:
	 * 1- isShotDown() devuelve false tras realizar disparos a todas las posiciones del
	 *    Ship excepto una. 
	 * 2- isShotDown() devuelve true tras disparar a la única posición no dañada.
	 */
	@Test
	public void testIsShotDown2() {
		//TODO fail ("Realiza el test IsShotDown2");
		Coordinate generatriz = CoordinateFactory.createCoordinate(2,3);
		Coordinate golpeo = CoordinateFactory.createCoordinate(4,4);
		Coordinate golpeo2 = CoordinateFactory.createCoordinate(4,5);
		Coordinate golpeo3 = CoordinateFactory.createCoordinate(4,6);
		goleta.setPosition(generatriz);
		try {
			goleta.hit(golpeo);
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			goleta.hit(golpeo2);
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(goleta.isShotDown());
		try {
			goleta.hit(golpeo3);
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(goleta.isShotDown());
	}
	
	//TODO testIsHit
	/* Comprueba que:	
	 * 1- isHit sobre las Coordinates de un Ship devuelve false.
	 * 2- isHit sobre las Coordinates de un Ship devuelve true después 
	 *    de disparar sobre ellas (hit) 
	 *     
	 */
	@Test(expected = CoordinateAlreadyHitException.class)
	public void testIsHit() {
		//TODO fail ("Realiza el test IsHit");
		Coordinate generatriz = CoordinateFactory.createCoordinate(2,3);
		Coordinate golpeo = CoordinateFactory.createCoordinate(4,4);
		Coordinate golpeo2 = CoordinateFactory.createCoordinate(4,5);
		goleta.setPosition(generatriz);
		assertFalse(goleta.isHit(golpeo));
		try {
			goleta.hit(golpeo);
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			goleta.hit(golpeo2);
		} catch (CoordinateAlreadyHitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(goleta.isHit(golpeo));
		assertTrue(goleta.isHit(golpeo2));
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

}
