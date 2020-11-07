package model.ship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.*;
import model.aircraft.Coordinate3D;
import model.exceptions.*;

public class Board2DPreTest {
	Ship destroyer, carrier, battleship, b1, d1;
	Board board;
	static String sboard0, sboard1, sboard2, sboard3, sboard4, sboard5, sboard6, sboard7;
	
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
		
		sboard4 = "•    ®\n" +
				  "•    ®\n" +
				  "•    ®\n" +
				  "•    ®\n" +
				  "     ®\n" +
				  "  ΩΩ  ";
		
		sboard5 = "O ????\n" +
				  "O ????\n" +
				  "O ????\n" +
				  "O ????\n" +
				  "  ????\n" +
				  "??????";
		
		sboard6 = "•    •\n" +
				  "•    •\n" +
				  "•    ®\n" +
				  "•    ®\n" +
				  "     ®\n" +
				  "  ••  ";
		
		sboard7 = "O ???•\n" +
				  "O ???•\n" +
				  "O ????\n" +
				  "O ????\n" +
				  "     ?\n" +
				  "? ΩΩ ?";
	}

	@Before
	public void setUp() throws Exception {
		board = new Board2D(6);
		destroyer = new Destroyer(Orientation.EAST);
		carrier = new Carrier(Orientation.SOUTH);
		battleship = new Battleship(Orientation.NORTH);
		b1 = new Destroyer(Orientation.EAST);
	}
	
	@Test
	public void testGetSize( ) {
		 assertEquals(6,board.getSize());
		 board = new Board2D(17);
		 assertEquals(17, board.getSize());
	}

	@Test
	public void testCheckCoordinateOk() {
		assertTrue(board.checkCoordinate(new Coordinate2D(0,0)));
		assertTrue(board.checkCoordinate(new Coordinate2D(0,5)));
		assertTrue(board.checkCoordinate(new Coordinate2D(5,0)));
		assertTrue(board.checkCoordinate(new Coordinate2D(5,5)));		
	}
	
	//TODO
	/* Añade los Crafts correctamente en el board tal como aparecen:
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
	  //fail("Realiza el test");
		try {
			assertTrue(board.addCraft(battleship, new Coordinate2D(-2,-1)));
			assertTrue(board.addCraft(destroyer, new Coordinate2D(1,3)));
			assertTrue(board.addCraft(carrier, new Coordinate2D(3,0)));
			assertEquals(sboard1, board.show(true));
			//fail("esto va");
		} catch (InvalidCoordinateException | OccupiedCoordinateException | NextToAnotherCraftException | IllegalArgumentException e) {
			fail("todo va mal");
			e.printStackTrace();
		}
	}
	
	//TODO
	/* Comprueba checkCoordinate con Coordinates fuera de los límites del
	 * tablero.
	 */
	@Test
	public void testCheckCoordinateOutOfLimits() {
		//fail("Realiza el test");
		assertFalse(board.checkCoordinate(new Coordinate2D(0,-1)));
		assertFalse(board.checkCoordinate(new Coordinate2D(6,6)));
		assertFalse(board.checkCoordinate(new Coordinate2D(6,-2)));
		assertTrue(board.checkCoordinate(new Coordinate2D(1,2)));
	}
	
	/* Se comprueba que al pasarle una Coordinate3D a checkCoordinate aplicado sobre
	 * un Board2D, se lanza la excepción IllegalArgumentException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckCoordinateException() {
		try {
			this.board.checkCoordinate(new Coordinate3D(3,4,0));
			fail("no va benne");
		} catch (IllegalArgumentException e) {
			this.board.checkCoordinate(new Coordinate3D(3,2,0));
			e.getCause();
		}
		
		
	}

	//TODO
	/* Posiciona los Crafts en el tablero según aparecen en testAddCraftOk. 
	 * Compara que board.show(true) genera lo mismo que sboard1 y
	 * board.show(false) lo mismo que sboard0
	 */
	@Test
	public void testShow1() {
		testAddCraftOk();
		assertEquals(sboard1, board.show(true));
		assertEquals(sboard0, board.show(false));
		//fail("Realiza el test");		
	}
	
	//TODO
	/* Posiciona los Crafts según testAddCraftOk y después dispara sobre ellos 
	 * hundiéndolos todos. Comprueba que show(true) y show(false) coincide
	 * con sboard2 y sboard3 respectivamente.
	 */
	@Test
	public void testShow2() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		//hundimos todos los barcos a ver que pasa
		board.hit(CoordinateFactory.createCoordinate(0,0));
		board.hit(CoordinateFactory.createCoordinate(0,1));
		board.hit(CoordinateFactory.createCoordinate(0,2));
		board.hit(CoordinateFactory.createCoordinate(0,3));
		assertEquals(sboard4, board.show(true));
		assertEquals(sboard5, board.show(false));
		board.hit(CoordinateFactory.createCoordinate(2,5));
		board.hit(CoordinateFactory.createCoordinate(3,5));
		
		board.hit(CoordinateFactory.createCoordinate(5,0));
		board.hit(CoordinateFactory.createCoordinate(5,1));
		assertEquals(sboard6, board.show(true));
		assertEquals(sboard7, board.show(false));
		board.hit(CoordinateFactory.createCoordinate(5,2));
		board.hit(CoordinateFactory.createCoordinate(5,3));
		board.hit(CoordinateFactory.createCoordinate(5,4));
		assertEquals(sboard2, board.show(true));
		assertEquals(sboard3, board.show(false));
	}
	
	//TODO
	/* Realiza disparos fuera del board y comprueba que se lanza la excepción
	 * InvalidCoordinateException
	 */
	@Test(expected = InvalidCoordinateException.class)
	public void testHitInvalidCoordinate() throws CoordinateAlreadyHitException, InvalidCoordinateException {
		try {
			board.hit(CoordinateFactory.createCoordinate(-2,0));
			fail("no va");
		} catch (InvalidCoordinateException e) {
			board.hit(CoordinateFactory.createCoordinate(6,-3));
			fail("aqui falla");
			e.getCause();
		}
	}

	
	/* Se comprueba que al disparar sobre una Coordenada3D se lanza la excepción 
	 * IllegalArgumentException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testHitIllegalArgument() throws CoordinateAlreadyHitException, InvalidCoordinateException {
		try {
			board.hit(new Coordinate3D(3,-1, 7));
			fail("no va");
		} catch (IllegalArgumentException e) {
			board.hit(new Coordinate3D(3, 2, 1));
			fail("si que va");
			e.getCause();
		}
	}
		
	
	//TODO
	/* Posiciona los Crafts según testAddCraftOk().
	 * Se intenta añadir un Craft Destroyer (en la imagen inferior aparecen con 'DD' para
	 * indicar dónde se intenta poner) en la que, parte está fuera del tablero y
	 * parte en una posición ocupada por un  Battleship. Se comprueba que se lanza la
	 * excepción InvalidCoordinateException
	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   DD    ®|
	 *   |	   ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOutOccupied() {
		testAddCraftOk();
		Coordinate2D c=new Coordinate2D(-2,1);
		d1 = new Destroyer(Orientation.EAST);
		try {
			board.addCraft(d1, c);
			fail("Error: debería haber lanzado la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNotNull(board.getCraft(new Coordinate2D(0,3)));
			assertTrue(board.getCraft(new Coordinate2D(0,3)).getClass().getName()=="model.ship.Battleship");
		} catch (OccupiedCoordinateException | NextToAnotherCraftException e) {
			fail("Error. Se esperaba InvalidCoordinateException "+ "pero se produjo la " +e.getClass().getName());
		}
	}
	
	
	//TODO
	/* Posiciona los Crafts según testAddCraftOk().
	 * Intenta añadir un Craft Destroyer en la que, parte está fuera del tablero y
	 * parte en una posición próxima a otro Craft del tablero. 
	 * Comprueba que se lanza la excepción InvalidCoordinateException 
	 * 	 *    ------
	 *   |O	   ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |O    ®|
	 *   DD	   ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOutNextTo() {
		testAddCraftOk();
		try {//2.5 3.5 --> 1.3
			//-2.1
			//	   --> -3.2
			assertTrue(board.addCraft(destroyer, CoordinateFactory.createCoordinate(-3,2)));
			fail("Error: debería haber lanzado la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			assertNotNull(board.getCraft(CoordinateFactory.createCoordinate(0,3)));
			assertTrue(board.getCraft(CoordinateFactory.createCoordinate(0,3)).getClass().getName()=="model.ship.Battleship");
		} catch (NextToAnotherCraftException e) {
			fail ("Error. Se esperaba InvalidCoordinateException " + "pero se produjo la excepcion "+e.getClass().getName());
		} catch (OccupiedCoordinateException e) {
			fail ("Deberia haber entrado en nextoanother");
		}
	}
	
	

	//TODO
	/* Posiciona los Crafts según testAddCraftOk().
	 * Intenta añadir un Craft Destroyer dentro del tablero en la que, parte está colisionando y
	 * parte en una posición próxima a otro Craft del tablero. 
	 * Comprueba que se lanza la excepción OccupiedCoordinateException
	 * |O	 ®|
	 * |O    ®|
	 * |O	 ®|
	 * |O	 ®|
	 * |	DD ®|
	 * |  ΩΩ  |
	 *  ------
	 */
	@Test
	public void testAddCraftColisionNextTo() {
		testAddCraftOk();
		//fail("Completa el test");
		//3,2 -> 2,5 3,5
		try {
			assertFalse(board.addCraft(b1, CoordinateFactory.createCoordinate(3,2)));
			fail("Error: debería haber lanzado la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException | NextToAnotherCraftException e) {
			fail ("Error. Se esperaba OccupiedCoordinateException pero se produjo la excepcion "+e.getClass().getName());
		} catch (OccupiedCoordinateException e) {
			e.getMessage();
		}
	}

	//TODO
	/* Posiciona los Crafts según testAddCraftOk().
	 * Intenta añadir un Craft Battleship en la que, parte está fuera del tablero, parte está
	 * colisionando y parte en una posición próxima a otro Craft del tablero. 
	 * Comprueba que se lanza la excepción InvalidCoordinateException
	 *    ------
	 *  9|O999 ®|
	 *   |O    ®|
	 *   |O	   ®|
	 *   |O    ®|
	 *   |	   ®|
	 *   |  ΩΩ  |
	 *    ------
	 */
	@Test
	public void testAddCraftOutColisionNextTo() {
		testAddCraftOk();
		//
		try {
			assertTrue(board.addCraft(b1, CoordinateFactory.createCoordinate(-1,-3)));
			fail("no va el test");
		} catch (InvalidCoordinateException e) {
			e.getCause();
		} catch (OccupiedCoordinateException | NextToAnotherCraftException | IllegalArgumentException e) {
			fail("se debería meter en el catch anterior");
		}
		
	}
	
}
