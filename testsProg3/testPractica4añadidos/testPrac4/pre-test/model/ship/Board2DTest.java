/**
 * @author Samuel Oliva
 */
package model.ship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Board;
import model.Orientation;
import model.aircraft.Coordinate3D;
import model.exceptions.CoordinateAlreadyHitException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;

public class Board2DTest {
	Ship destroyer, carrier, battleship;
	Board board;
	
	static String sboard0, sboard1, sboard2, sboard3, sboard4, sboard5;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sboard0 = "??????\n" + 
				  "??????\n" + 
				  "??????\n" + 
				  "??????\n" + 
				  "??????\n" + 
				  "??????";
		
		sboard1 = "O    ®\n"
				+ "O    ®\n"
				+ "O    ®\n"
				+ "O    ®\n"
				+ "     ®\n"
				+ "  ΩΩ  "; 
		
		sboard2 = "•    •\n" + 
				  "•    •\n" + 
				  "•    •\n" + 
				  "•    •\n" + 
				  "     •\n" + 
				  "  ••  ";
		
		sboard3 = "O ?? ®\n" + 
				  "O ?? ®\n" + 
				  "O ?? ®\n" + 
				  "O ?? ®\n" + 
				  "     ®\n" + 
				  "? ΩΩ  ";
		
		sboard4 = "O    ®\n" + 
				  "O    ®\n" + 
				  "•    •\n" + 
				  "•    ®\n" + 
				  "     ®\n" + 
				  "  ••  ";
		
		sboard5 = "??????\n" + 
				  "??????\n" + 
				  "•? ??•\n" + 
				  "•?????\n" + 
				  "     ?\n" + 
				  "? ΩΩ ?";
	}

	@Before
	public void setUp() throws Exception {
		board = new Board2D(6);
		destroyer = new Destroyer(Orientation.EAST);
		carrier = new Carrier(Orientation.SOUTH);
		battleship = new Battleship(Orientation.NORTH);
	}
	
	@Test
	public void testGetSize( ) {
		 assertEquals(6,board.getSize());
		 board = new Board2D(17);
		 assertEquals(17, board.getSize());
	}

	/* Se comprueba que checkCoordinate para Coordinate2D en los límites 
	 * devuelve true */
	@Test
	public void testCheckCoordinateOk() {
		assertTrue(board.checkCoordinate(new Coordinate2D(0,0)));
		assertTrue(board.checkCoordinate(new Coordinate2D(0,5)));
		assertTrue(board.checkCoordinate(new Coordinate2D(5,0)));
		assertTrue(board.checkCoordinate(new Coordinate2D(5,5)));
	}
	
	/* Se comprueba que checkCoordinate(Coordinate) para Coordinate fuera de los límites
	 * devuelve false.
	 */
	@Test
	public void testCheckCoordinateOutOfLimits() {
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,0)));
		assertFalse(board.checkCoordinate(new Coordinate2D(0,-1)));
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,-1)));
		
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,6)));
		assertFalse(board.checkCoordinate(new Coordinate2D(0,6)));
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,5)));
		
		assertFalse(board.checkCoordinate(new Coordinate2D(6,-1)));
		assertFalse(board.checkCoordinate(new Coordinate2D(6,0)));
		assertFalse(board.checkCoordinate(new Coordinate2D(5,-1)));
		
		assertFalse(board.checkCoordinate(new Coordinate2D(6,6)));
		assertFalse(board.checkCoordinate(new Coordinate2D(5,6)));
		assertFalse(board.checkCoordinate(new Coordinate2D(6,5)));
	}
	
	/* Se comprueba que checkCoordinate para una Coordinate3D en un Board2D
	 * lanza IllegalArgumentException */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckCoordinateException() {
		assertTrue(board.checkCoordinate(new Coordinate3D(3,4,0)));
		
	}
	
	/* Se colocan los ships correctamente en el board.
	 *  ------
	 * |O	 ®|
	 * |O    ®|
	 * |O	 ®|
	 * |O	 ®|
	 * |	 ®|
	 * |  ΩΩ  |
	 *  ------
	 */
	@Test
	public void testAddCraftOk()  {
		
		try {
			board.addCraft(destroyer,new Coordinate2D(1,3));
			board.addCraft(carrier, new Coordinate2D(3,0));
			board.addCraft(battleship, new Coordinate2D(-2,-1));
		} catch (InvalidCoordinateException | NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se produjo la excepcion "+(e.getClass()).getName());
		}
		
	}
	
	/* Se añaden los Ship según testAddCraftOk() y se comprueba que lo que devuelve show(true) y show(false) coincide
	 * con sboard1 y sboard0 respectivamente.
	 */
	@Test
	public void testShow1() {
		
		compareLines(sboard0, board.show(false));
		testAddCraftOk();
		compareLines(sboard1, board.show(true));
		compareLines(sboard0, board.show(false));
	}
	
	/* Se añaden los Ship según testAddCraftOk() y se dispara sobre todos los Ship hundíendolos. 
	 * Se comprueba que lo que devuelve show(true) y show(false) coincide con sboard2 y sboard3 respectivamente.
	 */
	@Test
	public void testShow2() throws InvalidCoordinateException, CoordinateAlreadyHitException {
				
		testAddCraftOk();
		for (int i=0; i<5; i++) {
			if (i<4) board.hit(new Coordinate2D(0,i));
			board.hit(new Coordinate2D(5,i));
			if ((i==2)||(i==3)) board.hit(new Coordinate2D(i,5));			
		}
		assertTrue(board.areAllCraftsDestroyed());
		compareLines(sboard2, board.show(true));
		compareLines(sboard3, board.show(false));
	}
	
	/* Se dispara sucesivamente sobre posiciones no válidas en el Board. Se comprueba que
	 * en todas ellas se lanza InvalidCoordinateException y no otra.
	 */
	@Test(expected=InvalidCoordinateException.class)
	public void testHitInvalidCoordinate() throws CoordinateAlreadyHitException, InvalidCoordinateException {
		
			try {
				board.hit(new Coordinate2D(3,-1));
				fail ("Error. Debió producirse la excepción InvalidCoordinateException");
			} catch (InvalidCoordinateException e) {
				try {
					assertNotNull(e.getMessage());
					assertTrue(e.getMessage().length()>1);
					board.hit(new Coordinate2D(6,2));
					fail ("Error. Debió producirse la excepción InvalidCoordinateException");
				} catch (InvalidCoordinateException e1) {
					try {
						board.hit(new Coordinate2D(4,6));
						fail ("Error. Debió producirse la excepción InvalidCoordinateException");
					} catch (InvalidCoordinateException e2) {
						board.hit(new Coordinate2D(-1, 3));
					} 
				}
			}
	}

	/* Se intenta disparar sobre un Board2D a una Coordinate3D. Se debe lanzar IllegalArgumentException */
	@Test(expected=IllegalArgumentException.class)
	public void testHitIllegalArgument() throws CoordinateAlreadyHitException, InvalidCoordinateException {
				board.hit(new Coordinate3D(3,-1, 7));
	}
	
	/* Se añaden los Ship al Board2D según testAddCraftOK(). Se dispara sucesivamente sobre él en distintas
	 * posiciones. Se comprueba que cuando se vuleve a disparar sobre una posición de un Ship previamente dañada
	 * se lanza CoordinateAlreadyHitException. Se comprueba otros métodos como isShotDown(), show(...), toString().
	 */
	@Test
	public void testHitsShowAndOthers() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		board.hit(new Coordinate2D(2,2));
		board.hit(new Coordinate2D(5,2));
		board.hit(new Coordinate2D(2,4));
		board.hit(new Coordinate2D(2,2));
		try {
		  board.hit(new Coordinate2D(5,2));
		  fail("Error: debió de lanzarse CoordinateAlreadyHitException");
		} catch (CoordinateAlreadyHitException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().length()>1);
		}
		board.hit(new Coordinate2D(0,2));
		board.hit(new Coordinate2D(0,3));
		board.hit(new Coordinate2D(0,4));
		try {
			  board.hit(new Coordinate2D(0,2));
			  fail("Error: debió de lanzarse CoordinateAlreadyHitException");
		} catch (CoordinateAlreadyHitException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().length()>1);
		}
		board.hit(new Coordinate2D(2,5));
		board.hit(new Coordinate2D(3,5));
		board.hit(new Coordinate2D(2,2)); //Se repite disparo pero no hay Craft
		assertFalse(battleship.isShotDown());
	    assertTrue(destroyer.isShotDown());
		assertEquals(sboard4, board.show(true));
		assertEquals(sboard5, board.show(false));
		assertTrue(destroyer.isShotDown());
		assertEquals("Board 6; crafts: 3; destroyed: 1",board.toString());
	}
		
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte queda fuera del tablero
	 * y parte colisiona con un Battleship. Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Destroyer
	 * 3. que sigue existiendo el Battleship.
	 * 
	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   ΩΩ    ®|
	 *   |	   ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOutOccupied() {
		Coordinate2D c=new Coordinate2D(-2,1); 
		testAddCraftOk();
		try {		
			board.addCraft(destroyer, c);
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNotNull (board.getCraft(new Coordinate2D(0,3)));
			assertTrue(board.getCraft(new Coordinate2D(0,3)).getClass().getName()=="model.ship.Battleship");
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte colisiona con un Carrier y
	 * parte queda fuera del tablero. Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Destroyer
	 * 3. que sigue existiendo el Carrier.
	 * 
	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |O    ®|
	 *   |	   ΩΩ
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOccupiedOut(){
		Coordinate2D c=new Coordinate2D(4,2); 
		testAddCraftOk();
		try {		
			board.addCraft(destroyer, c);
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().length()>1);
			assertNotNull (board.getCraft(new Coordinate2D(5,4)));
			assertTrue(board.getCraft(new Coordinate2D(5,4)).getClass().getName()=="model.ship.Carrier");

		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			board.getCraft(c);
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte queda fuera del tablero
	 * y parte está colindante con un Battleship. Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Destroyer
	 * 
	 *   ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |O    ®|
	 *   ΩΩ    ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOutNextTo() {
		testAddCraftOk();
		try {
			board.addCraft(destroyer, new Coordinate2D(-2,2));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNull (board.getCraft(new Coordinate2D(0,4)));
		
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte queda colindante con un
	 * Carrier y parte queda fuera del tablero. . Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Destroyer
	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |O    ®|
	 *   |	   ®|
	 *   |  ΩΩ ΩΩ
	 *    ------
	 */
	@Test
	public void testAddCraftNextToOut() {
		testAddCraftOk();
		try {
			board.addCraft(destroyer, new Coordinate2D(4,3));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNull (board.getCraft(new Coordinate2D(5,5)));	
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte colisiona con un Battleship
	 * y parte queda colindante con el propio Battleship. Se comprueba que:
	 * 1. se lanza OccupiedCoordinateException
	 * 2. no se ha puesto el Destroyer
	 * 3. sigue existiendo el Battleship
	 * 
	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |ΩΩ   ®|
	 *   |	   ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftColisionNextTo() {
		testAddCraftOk();
		try {
			board.addCraft(destroyer, new Coordinate2D(-1,1));
			fail ("Error: debió lanzarse la excepción OccupiedCoordinateException");
		} catch (OccupiedCoordinateException e1) {
			assertNull (board.getCraft(new Coordinate2D(1,3)));
			assertTrue(board.getCraft(new Coordinate2D(0,3)).getClass().getName()=="model.ship.Battleship");
		} catch (NextToAnotherCraftException | InvalidCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Destroyer en una posición donde parte queda colindante con
	 * un Carrier y parte colisiona con el propio Carrier. Se comprueba que:
	 * 1. se lanza OccupiedCoordinateException
	 * 2. no se ha puesto el Destroyer
	 * 3. sigue existiendo el Carrier
	 * 
	 *  ------
	 * |O	 ®|
	 * |O    ®|
	 * |O	 ®|
	 * |O	ΩΩ|
	 * |	 ®|
	 * |  ΩΩ  |
	 *  ------
	 */
	@Test
	public void testAddCraftNextToColision() {
		testAddCraftOk();
		try {
			board.addCraft(carrier, new Coordinate2D(3,1));
			fail ("Error: debió lanzarse la excepción OccupiedCoordinateException");
		} catch (OccupiedCoordinateException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().length()>1);
			assertNull (board.getCraft(new Coordinate2D(4,3)));
			assertTrue(board.getCraft(new Coordinate2D(5,3)).getClass().getName()=="model.ship.Carrier");

		} catch (NextToAnotherCraftException | InvalidCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Battleship en una posición donde parte queda fuera del tablero,
	 * parte colisiona con un Carrier y parte queda próximo al propio Carrier.
	 * Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Battleship y sigue existiendo el Carrier
	 * 
	 *  -----O
	 * |O	 O|
	 * |O    O|
	 * |O	 O|
	 * |O	 ®|
	 * |	 ®|
	 * |  ΩΩ  |
	 *  ------
	 */
	@Test
	public void testAddCraftOutColisionNextTo() {
		testAddCraftOk();
		try {
			board.addCraft(battleship, new Coordinate2D(3,-2));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e1) {
			assertNotNull (board.getCraft(new Coordinate2D(5,0))); //Tiene Ship
			assertTrue(board.getCraft(new Coordinate2D(5,0)).getClass().getName()=="model.ship.Carrier"); //Sigue siendo un carrier
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta añadir un Battleship en una posición donde parte queda próximo a un Carrier,
	 * parte colisiona con el propio Carrier y parte queda fuera del tablero.
	 * Se comprueba que:
	 * 1. se lanza InvalidCoordinateException
	 * 2. no se ha puesto el Battleship y sigue existiendo el Carrier
	 * 
	 *  ------
	 * |O	 ®|
	 * |O    ®|
	 * |O	 ®|
	 * |O	 O|
	 * |	 O|
	 * |  ΩΩ O|
	 *  -----O
	 */
	@Test
	public void testAddCraftColisionNextToOut() {
		testAddCraftOk();
		try {
			board.addCraft(battleship, new Coordinate2D(3,2));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e1) {
			assertNotNull (board.getCraft(new Coordinate2D(5,4))); //Tiene Ship
			assertTrue(board.getCraft(new Coordinate2D(5,4)).getClass().getName()=="model.ship.Carrier"); //Sigue siendo un carrier
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
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
