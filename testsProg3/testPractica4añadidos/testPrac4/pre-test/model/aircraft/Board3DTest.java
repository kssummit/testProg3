/**
 * @author Samuel Oliva
 */
package model.aircraft;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Board;
import model.Coordinate;
import model.Orientation;
import model.exceptions.CoordinateAlreadyHitException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;
import model.ship.Battleship;
import model.ship.Board2D;
import model.ship.Carrier;
import model.ship.Coordinate2D;
import model.ship.Destroyer;
import model.ship.Ship;

public class Board3DTest {
	Aircraft bomberE, bomberS,fighterW,fighter1S, fighter2S, transportN;
	Board board;
	final int TAM=7;
	final int MAX_BOARD_SIZE = 20;
	final int MIN_BOARD_SIZE = 5;
	static String sboard00, sboard01, sboard10, sboard11, sboard20, sboard21, sboard02, sboard30;
	Coordinate3D b1, b2,f1,t1,f2,f3;
	final String strBoard0 = "Board 7; crafts: 0; destroyed: 0";
	final String strBoard1 = "Board 7; crafts: 6; destroyed: 0";
	final String strBoard2 = "Board 7; crafts: 6; destroyed: 1";
	final String strBoard3 = "Board 7; crafts: 6; destroyed: 2";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		sboard00 = "???????|???????|???????|???????|???????|???????|???????\n" + 
				  "???????|???????|???????|???????|???????|???????|???????\n" + 
			      "???????|???????|???????|???????|???????|???????|???????\n" + 
				  "???????|???????|???????|???????|???????|???????|???????\n" + 
				  "???????|???????|???????|???????|???????|???????|???????\n" + 
				  "???????|???????|???????|???????|???????|???????|???????\n" + 
				  "???????|???????|???????|???????|???????|???????|???????";
		
		sboard01 = "     ⇄ |       |       |       | ⇄     |       | ⇄     \n" + 
				   "     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     \n" + 
				   "    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  \n" + 
				   "     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  \n" + 
				   "       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ \n" + 
				   "       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋\n" + 
				   "       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  ";
		
		sboard02 = " ??????|???????| ??????| ??????|???? ??|???????|???????\n" + 
				   "? ?????|???????|? ?????| ??????|???? ??|???????|???????\n" + 
				   "?? ????|???????|?? ????| ??????|???? ??|???????|???????\n" + 
				   "??? ???|???????|??? ???| ??????|???? ??|???????|???????\n" + 
				   "???? ??|???????|???? ??| ??????|???? ??|???????|???????\n" + 
				   "       |???????|????? ?| ??????|???? ??|???????|???????\n" + 
				   "?????? |???????|?????? | ??????|???? ??|???????|???????";
		
		sboard10="???????|???????|???????|     ??| ⇄   ??|     ??|???????\n" + 
			 	 "???????|???????|???????|     ??|⇄⇄⇄⇄ ??|     ??|???????\n" + 
				 "???????|???????|???????|     ??| ⇄   ??|     ??|???????\n" + 
				 "???????|???????|???????|   ????|   ????|   ????|???????\n" + 
				 "???????|???????|???????|???????|???????|???????|???????\n" + 
				 "???????|???????|???????|???????|???????|???????|???????\n" + 
				 "???????|???????|???????|???????|???????|???????|???????";
		
		sboard11="     ⇄ |       |       |       | •     |       | ⇄     \n" + 
				 "     ⇄ |       |       |       |••••   |       | ⇄     \n" + 
				 "    ⇄⇄⇄| ⇶⇶    |       |       | •     |       |⇄⇄⇄ ⇋  \n" + 
				 "     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  \n" + 
				 "       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ \n" + 
				 "       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋\n" + 
				 "       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  ";
		
		
		sboard20="???????|???????|???????|     ??| ⇄   ??|     ??|???????\n" + 
				"???????|???????|???????|     ??|⇄⇄⇄⇄ ??|      ?|???   ?\n" + 
				"???????|??•????|???????|     ??| ⇄   ??|      ?|??? ⇋ ?\n" + 
				"???????|??•????|???????|   ????|   ????|       |??  ⇋  \n" + 
				"???????|??•????|???????|???????|???????|?      |?  ⇋⇋⇋ \n" + 
				"???????|??•????|???????|??•••??|???????|?      |? ⇋ ⇋ ⇋\n" + 
				"???????|??•????|???????|???????|???????|?      |?   ⇋  ";
		sboard21="     ⇄ |       |       |       | •     |       | ⇄     \n" + 
			 	 "     ⇄ |       |       |       |••••   |       | ⇄     \n" + 
				 "    ⇄⇄⇄| ⇶•    |       |       | •     |       |⇄⇄⇄ •  \n" + 
				 "     ⇄ |  •    |       |   ⇶   |       |       | ⇄  •  \n" + 
				 "       |⇶⇶•⇶   |       | ⇶ ⇶ ⇶ |       |       |   ••• \n" + 
				 "       |  •    |       | ⇶•••⇶ |       |       |  • • •\n" + 
				 "       | ⇶•    |       |   ⇶   |       |       |    •  ";
		
		sboard30 = " ??????|???????| ??????|     ??| ⇄   ??|     ??|???????\n" + 
			 	   "? ?????|???????|? ?????|     ??|⇄⇄⇄⇄ ??|      ?|???   ?\n" + 
				   "?? ????|??•????|?? ????|     ??| ⇄   ??|      ?|??? ⇋ ?\n" + 
				   "??? ???|??•????|??? ???|   ????|   ? ??|       |??  ⇋  \n" + 
				   "???? ??|??•????|???? ??| ??????|???? ??|?      |?  ⇋⇋⇋ \n" + 
				   "       |??•????|????? ?| ?•••??|???? ??|?      |? ⇋ ⇋ ⇋\n" + 
				   "?????? |??•????|?????? | ??????|???? ??|?      |?   ⇋  ";
		
	}

	@Before
	public void setUp() throws Exception {
		board = new Board3D(TAM);
		bomberE = new Bomber(Orientation.EAST);
		bomberS = new Bomber(Orientation.SOUTH);
		fighterW = new Fighter(Orientation.WEST);
		transportN = new Transport(Orientation.NORTH);
		fighter1S = new Fighter(Orientation.SOUTH);
		fighter2S = new Fighter(Orientation.SOUTH);
		f2 = new Coordinate3D(3,0,0);
		b1 = new Coordinate3D(0,2,1);
		b2 = new Coordinate3D(1,3,3);
		f1 = new Coordinate3D(-1,-1,4);
		t1 = new Coordinate3D(2,2,6);
		f3 = new Coordinate3D(-1,0,6);
	}

	/* Se comprueba que el constructor de Board3D crea los tableros en los límites 
	 * de tamaño máximos. Se comprueba que si nos excedemos en su tamaño se lanza
	 * la excepción IllegalArgumentException
	 */
	@Test
	public void testBoard3D() {
		Board board;
		board = new Board3D(MAX_BOARD_SIZE);
		assertEquals (MAX_BOARD_SIZE, board.getSize());
		board = new Board3D(MIN_BOARD_SIZE);
		assertEquals (MIN_BOARD_SIZE, board.getSize());
		try {
			new Board3D(MIN_BOARD_SIZE-1);
			fail("Error: No se produjo la excepción IllegalArgumentException");
		}catch (IllegalArgumentException e) {
		}
		try {
			new Board3D(MAX_BOARD_SIZE+1);
			fail("Error: No se produjo la excepción IllegalArgumentException");
		}catch (IllegalArgumentException e) {
		}
	}
	
	/* Se comprueba getSize() */
	@Test
	public void testGetSize( ) {
		 assertEquals(7,board.getSize());
		 board = new Board3D(17);
		 assertEquals(17, board.getSize());
	}
	
	/* Se comprueba que checkCoordinate para Coordinate3D en los límites 
	 * devuelve true */
	@Test
	public void testCheckCoordinateOk() {
		assertTrue(board.checkCoordinate(new Coordinate3D(0,0,0)));
		assertTrue(board.checkCoordinate(new Coordinate3D(0,0,TAM-1)));
		assertTrue(board.checkCoordinate(new Coordinate3D(0,TAM-1,0)));
		assertTrue(board.checkCoordinate(new Coordinate3D(0,TAM-1,TAM-1)));
		assertTrue(board.checkCoordinate(new Coordinate3D(TAM-1,0,0)));
		assertTrue(board.checkCoordinate(new Coordinate3D(TAM-1,0,TAM-1)));
		assertTrue(board.checkCoordinate(new Coordinate3D(TAM-1,TAM-1,0)));
		assertTrue(board.checkCoordinate(new Coordinate3D(TAM-1,TAM-1,TAM-1)));
	}
	
	/* Se comprueba que checkCoordinate(Coordinate) para Coordinate fuera de los límites
	 * devuelve false.
	 */
	@Test
	public void testCheckCoordinateOutOfLimits() {
		assertFalse(board.checkCoordinate(new Coordinate3D(-1,0,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(-1,0,TAM-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(-1,TAM-1,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(-1,TAM-1,TAM-1)));

		assertFalse(board.checkCoordinate(new Coordinate3D(0,-1,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(0,-1,TAM-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,-1,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,-1,TAM-1)));
		
		assertFalse(board.checkCoordinate(new Coordinate3D(0,0,-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(0,TAM-1,-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,0,-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,TAM-1,-1)));
		
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM,0,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM,0,TAM-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM,TAM-1,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM,TAM-1,TAM-1)));

		assertFalse(board.checkCoordinate(new Coordinate3D(0,TAM,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(0,TAM,TAM-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,TAM,0)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,TAM,TAM-1)));
		
		assertFalse(board.checkCoordinate(new Coordinate3D(0,0,TAM)));
		assertFalse(board.checkCoordinate(new Coordinate3D(0,TAM-1,TAM)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,0,TAM)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM-1,TAM-1,TAM)));
		
		assertFalse(board.checkCoordinate(new Coordinate3D(-1,-1,-1)));
		assertFalse(board.checkCoordinate(new Coordinate3D(TAM,TAM,TAM)));
		
	}
	
	/* Se comprueba que checkCoordinate para una Coordinate2D en un Board3D
	 * lanza IllegalArgumentException */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckCoordinateException() {
		assertTrue(board.checkCoordinate(new Coordinate2D(3,4)));
	}

	/* Se colocan los aircrafts correctamente en el board.
	 *     ⇄ |       |       |       | ⇄     |       | ⇄     
     *     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftOk()  {
		
		try {
			board.addCraft(bomberE,b1);
			board.addCraft(bomberS, b2);
			board.addCraft(fighterW, f1);
			board.addCraft(transportN,t1);
			board.addCraft(fighter1S, f2);
			board.addCraft(fighter2S, f3);
			
		} catch (InvalidCoordinateException | NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se produjo la excepcion "+(e.getClass()).getSimpleName());
		}
	}
	
	/* Tras poner los Craft como se indica en testAddCraftOk(), se comprueba que Board.show 
	 * para true y false coincide con los String sboard01 y sboard00 respectivamente.
	 */
	@Test
	public void testShow1() {
		
		assertEquals(sboard00, board.show(false));
		testAddCraftOk();
		assertEquals(sboard01, board.show(true));
		assertEquals(sboard00, board.show(false));
	}


	
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Transport, indicado con el símbolo 'T' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior (z=0)
	 * Gran parte queda fuera y solo una colisiona con el Fighter. Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Transport
	 * 3- que sigue existiendo el Fighter.
	 *     
		   T 
		   T
		  TTT
	 *   T T T       |       |       | ⇄     |       | ⇄     
           T |       |       |       |⇄⇄⇄⇄   |       | ⇄     
          ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
           ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
             |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
             |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
             | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftOutOccupied() {
		Coordinate3D c=new Coordinate3D(3,-3, 0); 
		Aircraft transportN = new Transport(Orientation.NORTH);
		testAddCraftOk();
		try {		
			board.addCraft(transportN, c);
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
		} catch (InvalidCoordinateException e) {
			//Comprobamos que todo sigue igual
			assertNotNull (board.getCraft(new Coordinate3D(5,0,0)));
			assertNotNull (board.getCraft(new Coordinate3D(5,1,0)));
			assertTrue(board.getCraft(new Coordinate3D(5,0,0)).getClass().getName()=="model.aircraft.Fighter");
			assertEquals(f2,board.getCraft(new Coordinate3D(5,0,0)).getPosition());
			assertEquals(f2,board.getCraft(new Coordinate3D(5,1,0)).getPosition());
			assertNull(transportN.getPosition());
			assertNull(board.getCraft(new Coordinate3D(3,0,0)));
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getSimpleName());
		} 
	}

	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Fighter, indicado con el símbolo 'F' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 3.
	 * Una parte colisiona con un Bomber y el resto queda fuera. Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Fighter
	 * 3- que sigue existiendo el Bomber.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *     ⇄ |       |       |       | ⇄     |       | ⇄     
     *     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   F   |       |       |    ⇋  
     *       				    FFFF
     *      					 F 
     *       
	 */
	@Test
	public void testAddCraftOccupiedOut(){
		Coordinate3D c=new Coordinate3D(1,5,3); 
		Aircraft fighterW = new Transport(Orientation.WEST);
		testAddCraftOk();
		try {		
			board.addCraft(fighterW, c);
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
		} catch (InvalidCoordinateException e) {
			//No ha cambiado nada
			assertNotNull (board.getCraft(new Coordinate3D(3,6,3)));
			assertTrue(board.getCraft(new Coordinate3D(3,6,3)).getClass().getName()=="model.aircraft.Bomber");
			assertEquals(b2,board.getCraft(new Coordinate3D(3,6,3)).getPosition());
			assertNull(fighterW.getPosition());

		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getSimpleName());
		} 
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Transport indicado con el símbolo 'T' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 2.
	 * Una parte queda fuera del tablero, otra parte queda próximo a un Bomber. Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Transport
	 * 
	 * 					 T
	 *     ⇄ |       |   T   |       | ⇄     |       | ⇄     
     *     ⇄ |       |  TTT  |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    | T T T |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |   T   |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftOutNextTo() {
		testAddCraftOk();
		Aircraft transportN = new Transport(Orientation.NORTH);
		try {
			board.addCraft(transportN, new Coordinate3D(1,-1,2));
			fail("Error: no se produjo la excepción InvalidCoordinateException ");	
			
		} catch (InvalidCoordinateException e) {
			assertNull (board.getCraft(new Coordinate3D(3,0,2))); //No hay nada en el board
			assertNull(transportN.getPosition()); //No se ha posicionado el aircraft
		
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}
	
		
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Bomber indicado con el símbolo 'B' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 5.
	 * Una parte queda próximo a un Bomber y otra fuera del tablero. Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Bomber
	 * 
	 *     ⇄ |       |       |       | ⇄     |       | ⇄     
     *     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   |       | ⇶⇶    | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |  ⇶    |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |⇶⇶⇶⇶   |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |  ⇶    |    ⇋  
     *       								   ⇶⇶
	 */
	@Test
	public void testAddCraftNextToOut() {
		testAddCraftOk();
		Aircraft bomberE = new Bomber(Orientation.EAST);
		try {
			board.addCraft(bomberE, new Coordinate3D(0,3,5));
			fail("Error: no se produjo la excepción InvalidCoordinateException ");	
		} catch (InvalidCoordinateException e) {
			assertNull(board.getCraft(new Coordinate3D(3,5,5))); //No hay nada en board
			assertNull(bomberE.getPosition()); //No se ha posicionado el aircraft
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}	
		
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Bomber indicado con el símbolo 'B' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 4.
	 * Una parte colisiona con un Fighter y otra queda próxima al Fighter y un Bomber. 
	 * Se comprueba que:
	 * 1- se lanza la excepción OccupiedCoordinateException y no otra.
	 * 2- que no se ha puesto el Bomber
	 * 3- que sigue existiendo el Fighter
	 * 
	 *     ⇄ |       |       |       | ⇄     |       | ⇄     
     *     ⇄ |       |       |       |⇄⇄⇄B   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    |       |       | B B B |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   | BBBBB |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |   B   |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
     *       
	 */
	@Test
	public void testAddCraftColisionNextTo() {
		testAddCraftOk();
		Aircraft bomberS = new Bomber(Orientation.SOUTH);
		try {
			board.addCraft(bomberS, new Coordinate3D(1,1,4));
			fail("Error: no se produjo la excepción OccupiedCoordinateException ");
		} catch (OccupiedCoordinateException e1) {
			//No ha cambiado nada
			assertNotNull (board.getCraft(new Coordinate3D(3,1,4)));
			assertNotNull (board.getCraft(new Coordinate3D(1,2,4)));
			assertTrue(board.getCraft(new Coordinate3D(3,1,4)).getClass().getName()=="model.aircraft.Fighter");
			assertTrue(board.getCraft(new Coordinate3D(1,2,4)).getClass().getName()=="model.aircraft.Fighter");
			assertEquals(f1,board.getCraft(new Coordinate3D(3,1,4)).getPosition());
			assertEquals(f1,board.getCraft(new Coordinate3D(1,2,4)).getPosition());
			assertNull(bomberS.getPosition());
			assertNull(board.getCraft(new Coordinate3D(3,3,4)));
		} catch (NextToAnotherCraftException | InvalidCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepción "+e2.getClass().getName());
		} 
	}
	
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta poner un Fighter indicado con el símbolo 'F' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 1.
	 * Una parte queda próxima a otro Fighter y una colisiona con un Bomber. 
	 * Se comprueba que:
	 * 1- se lanza la excepción OccupiedCoordinateException y no otra.
	 * 2- que no se ha puesto el Fighter
	 * 3- que sigue existiendo el Bomber
	 * 
	 *     ⇄ |  F    |       |       | ⇄     |       | ⇄     
     *     ⇄ | FFFF  |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶F    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftNextToColision() {
		testAddCraftOk();
		Aircraft fighterW = new Fighter(Orientation.WEST);
		try {
			board.addCraft(fighterW, new Coordinate3D(0,-1,1));
			fail("Error: no se produjo la excepción OccupiedCoordinateException ");
		} catch (OccupiedCoordinateException e1) {
			assertNotNull (board.getCraft(new Coordinate3D(2,2,1)));
			assertTrue(board.getCraft(new Coordinate3D(2,2,1)).getClass().getName()=="model.aircraft.Bomber");
			assertEquals(b1,board.getCraft(new Coordinate3D(2,2,1)).getPosition());
			assertNull(fighterW.getPosition());
			assertNull(board.getCraft(new Coordinate3D(2,0,1)));
		} catch (NextToAnotherCraftException | InvalidCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta poner un Transport indicado con el símbolo 'T' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 1.
	 * Una parte queda fuera del tablero, otra colisiona con un Bomber y otra próxima a un Fighter. 
	 * Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Transport
	 * 3- que sigue existiendo el Bomber
	 * 
	 * 		   T
	 *     ⇄ | T     |       |       | ⇄     |       | ⇄     
     *     ⇄ |TTT    |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄T T T   |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ | T⇶    |       |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftOutColisionNextTo() {
		testAddCraftOk();
		Aircraft transportN = new Transport(Orientation.NORTH);
		try {
			board.addCraft(transportN, new Coordinate3D(-1,-1, 0));
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
		} catch (InvalidCoordinateException e1) {
			assertNotNull (board.getCraft(new Coordinate3D(1,2,1)));
			assertTrue(board.getCraft(new Coordinate3D(1,2,1)).getClass().getName()=="model.aircraft.Bomber");
			assertEquals(b1,board.getCraft(new Coordinate3D(1,2,1)).getPosition());
			assertNull(transportN.getPosition());
			assertNull(board.getCraft(new Coordinate3D(1,0,1)));
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se intenta poner un Fighter indicado con el símbolo 'F' para diferenciarlo del resto,
	 * en la posición que aparece en la imagen inferior en la altura 6.
	 * Una parte colisiona con un Transport, otra próxima al propio Transport y otra queda 
	 * fuera del tablero. 
	 * Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Fighter
	 * 3- que sigue existiendo el Transport
	 * 
	 *     ⇄ |       |       |       | ⇄     |       | ⇄     
     *     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     
     *    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
     *     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
     *       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
     *       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  F ⇋ ⇋
     *       | ⇶⇶    |       |   ⇶   |       |       | FFFF  
     *       											F
	 */
	@Test
	public void testAddCraftColisionNextToOut() {
		testAddCraftOk();
		Aircraft fighterW = new Fighter(Orientation.WEST);
		try {
			board.addCraft(fighterW, new Coordinate3D(0,5,6));
		} catch (InvalidCoordinateException e1) {
			assertNotNull (board.getCraft(new Coordinate3D(2,5,6))); //Tiene aircraft
			assertNotNull (board.getCraft(new Coordinate3D(4,6,6))); //Tiene aircraft
			assertTrue(board.getCraft(new Coordinate3D(2,5,6)).getClass().getName()=="model.aircraft.Transport"); //Sigue siendo un Transport
			assertTrue(board.getCraft(new Coordinate3D(4,6,6)).getClass().getName()=="model.aircraft.Transport"); //Sigue siendo un Transport
			assertEquals(t1,board.getCraft(new Coordinate3D(2,5,6)).getPosition());
			assertEquals(t1,board.getCraft(new Coordinate3D(4,6,6)).getPosition());
			assertNull(fighterW.getPosition());
			assertNull(board.getCraft(new Coordinate3D(0,5,6)));
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * 1. Se comprueba que el Fighter de la altura 4 existe en sus coordenadas absolutas
	 * 2. Se destruye el Fighter
	 * 3. Que el Fighter sigue existiendo a pesar de ser hundido.
	 */
	@Test
	public void testGetCraft() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		//Para las coordenadas del fighterW en z=4
		for (int i=0; i<4; i++) assertEquals(fighterW, board.getCraft(new Coordinate3D(i,1,4)) );
		assertEquals(fighterW, board.getCraft(new Coordinate3D(1,0,4)) );
		assertEquals(fighterW, board.getCraft(new Coordinate3D(1,2,4)) );
		
		shotsAtFighterZ4(); //se destruye el fighterW
		//Debe serguir existiendo en el board.
		for (int i=0; i<4; i++) assertEquals(fighterW, board.getCraft(new Coordinate3D(i,1,4)) );
		assertEquals(fighterW, board.getCraft(new Coordinate3D(1,0,4)) );
		assertEquals(fighterW, board.getCraft(new Coordinate3D(1,2,4)) );
		
	}

	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se comprueba que isSeen es false si no se ha disparado sobre el Bomber de la altura 1
	 * Se dispara sobre el Bomber y se comprueba que en esas posiciones ahora isSeen devuelve
	 * true.
	 */
	@Test
	public void testIsSeenHits() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		for (int i=2; i<7; i++) 
			assertFalse(board.isSeen(new Coordinate3D(2,i,1)));
		shotsAtBomberZ1();
		for (int i=2; i<7; i++) 
			assertTrue(board.isSeen(new Coordinate3D(2,i,1)));
		
		
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se comprueba que isSeen es false sobre posiciones no ocupadas por Aircrafts
	 * Se dispara sobre esas mismas posiciones y se comprueba que en esas posiciones 
	 * ahora isSeen devuelve true.
	 */
	@Test
	public void testIsSeenWater() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		for (int i=0; i<7; i++) {
			assertFalse(board.isSeen(new Coordinate3D(i,i,2)));
			assertFalse(board.isSeen(new Coordinate3D(i,i,0)));
			assertFalse(board.isSeen(new Coordinate3D(i,5,0)));
			assertFalse(board.isSeen(new Coordinate3D(0,i,3)));
			assertFalse(board.isSeen(new Coordinate3D(4,i,4)));
		}
		shotsIntoWater();
		for (int i=0; i<7; i++) {
			assertTrue(board.isSeen(new Coordinate3D(i,i,2)));
			assertTrue(board.isSeen(new Coordinate3D(i,i,0)));
			assertTrue(board.isSeen(new Coordinate3D(i,5,0)));
			assertTrue(board.isSeen(new Coordinate3D(0,i,3)));
			assertTrue(board.isSeen(new Coordinate3D(4,i,4)));
		}
	}

	/* Se comprueba que para un Board3D de tamaño 10, si posicionáramos el bomberE, 
	 * fighter1S y transportN en la Coordinate3D(1,2,1) el método 
	 * board.getNeighborhood(Craft, new Coordinate(1,2,1)) devuelve sets con 92, 66 y 96 Coordinates 
	 * respectivamente.
	 */
	@Test
	public void testGetNeighborhoodCraftCoordinate1() throws InvalidCoordinateException, NextToAnotherCraftException, OccupiedCoordinateException {
		Board board = new Board3D(10);
		Set<Coordinate> neighborhood = board.getNeighborhood(bomberE, new Coordinate3D(1,2,1));
		assertEquals(92, neighborhood.size());
		neighborhood = board.getNeighborhood(fighter1S, new Coordinate3D(1,2,1));
		assertEquals(66, neighborhood.size());
		neighborhood = board.getNeighborhood(transportN, new Coordinate3D(1,2,1));
		assertEquals(96, neighborhood.size());
	}
	
	/* Se comprueban para un Board3D de tamaño 7, el número de vecinos indicados en los siguientes test, 
	 * que devolvería board.getNeighborhood al posicionar distintos Aircraft en las distintas posiciones
	 * indicadas.
	 */
	@Test
	public void testGetNeighborhoodCraftCoordinate2() throws InvalidCoordinateException, NextToAnotherCraftException, OccupiedCoordinateException {
		Board board = new Board3D(7);
		Set<Coordinate> neighborhood = board.getNeighborhood(bomberE, new Coordinate3D(0,2,1));
		assertEquals(71, neighborhood.size());
		neighborhood = board.getNeighborhood(fighter1S, new Coordinate3D(-1,2,1));
		assertEquals(57, neighborhood.size());
		neighborhood = board.getNeighborhood(transportN, new Coordinate3D(0,2,1));
		assertEquals(78, neighborhood.size());
	}
	
	@Test
	public void testGetNeighborhoodCraftCoordinate3() throws InvalidCoordinateException, NextToAnotherCraftException, OccupiedCoordinateException {
		Board board = new Board3D(7);
		Set<Coordinate> neighborhood = board.getNeighborhood(bomberE, new Coordinate3D(0,-4,1));
		assertEquals(22, neighborhood.size());
		neighborhood = board.getNeighborhood(fighterW, new Coordinate3D(-4,2,1));
		assertEquals(17, neighborhood.size());
		neighborhood = board.getNeighborhood(transportN, new Coordinate3D(1,5,6));
		assertEquals(20, neighborhood.size());
	}
	
	@Test
	public void testGetNeighborhoodCraftCoordinate4() throws InvalidCoordinateException, NextToAnotherCraftException, OccupiedCoordinateException {
		Board board = new Board3D(7);
		Set<Coordinate> neighborhood = board.getNeighborhood(bomberE, new Coordinate3D(0,-5,1));
		assertEquals(12, neighborhood.size());
		neighborhood = board.getNeighborhood(fighterW, new Coordinate3D(-5,2,1));
		assertEquals(9, neighborhood.size());
		neighborhood = board.getNeighborhood(transportN, new Coordinate3D(1,7,6));
		assertEquals(6, neighborhood.size());
		neighborhood = board.getNeighborhood(transportN, new Coordinate3D(1,8,6));
		assertEquals(0, neighborhood.size());
	}

	/* Se comprueba que getNeighborhood(Craft) para un Aircraft no posicionado debe lanzar NullPointerException
	 * Se posicionan los barcos según testAddCraftOk(). Se comprueba que getNeighborhood(Craft) devuelve el 
	 * número correcto de vecinos para todos los Aircraft.
	 */
	@Test
	public void testGetNeighborhoodCraft() {
		try {
			board.getNeighborhood(new Bomber(Orientation.WEST));
			fail ("Error: se debió lanzar la excepción NullPointerException");
		} catch (NullPointerException e) {
			testAddCraftOk();
			Set<Coordinate> neighborhood = board.getNeighborhood(bomberE);
			assertEquals(71, neighborhood.size());
			neighborhood = board.getNeighborhood(bomberS);
			assertEquals(83, neighborhood.size());
			neighborhood = board.getNeighborhood(fighterW);
			assertEquals(48, neighborhood.size());
			neighborhood = board.getNeighborhood(transportN);
			assertEquals(49, neighborhood.size());
			neighborhood = board.getNeighborhood(fighter1S);
			assertEquals(30, neighborhood.size());
			neighborhood = board.getNeighborhood(fighter2S);
			assertEquals(30, neighborhood.size());
		}
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se comprueba show antes y después de disparar sobre posiciones donde no hay Aircrafts
	 */
	@Test
	public void testHit1() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		compareLines(sboard00,board.show(false));
		shotsIntoWater();
		compareLines(sboard01,board.show(true));
		compareLines(sboard02,board.show(false));
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se comprueba show tras disparar sobre distintos hay Aircrafts y finalmente en
	 * el agua.
	 */
	@Test
	public void testHit2() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		shotsAtFighterZ4();
		compareLines(sboard10,board.show(false));
		compareLines(sboard11,board.show(true));
		shotsAtBomberZ3();
		shotsAtTransportZ6();
		shotsAtBomberZ1();
		compareLines(sboard20,board.show(false));
		compareLines(sboard21,board.show(true));
		shotsIntoWater();
		compareLines(sboard30,board.show(false));
		compareLines(sboard21,board.show(true));		
	}
	
	/* Se comprueba que hit sobre posiciones incorrectas, lanza InvalidCoordinateException
	 * 
	 */
	@Test
	public void testHitOutOfBoard() throws CoordinateAlreadyHitException{
		
		try {
			board.hit(new Coordinate3D(-1,3,0));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
		try {
			board.hit(new Coordinate3D(1,-1,0));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
		try {
			board.hit(new Coordinate3D(1,3,-1));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
		try {
			board.hit(new Coordinate3D(7,3,5));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
		try {
			board.hit(new Coordinate3D(2,7,4));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
		try {
			board.hit(new Coordinate3D(3,3,7));
			fail ("Error: debió lanzarse la excepción InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
		}
	}
	
	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se dispara sobre un Fighter y al agua. Se dispara sobre las mismas posiciones al agua.
	 * Se dispara sobre las mismas posiciones al Fighter y se comprueba que se lanza sucesivamente
	 * CoordinateAlreadyHitException.
	 */
	@Test
	public void testCoodinateAlreadyHitOnBoard() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		testAddCraftOk();
		shotsAtFighterZ4();
		shotsIntoWater();
		
		//Disparos repetidos al agua
		shotsIntoWater();
		
		//Disparos repetidos al Fighter en z=4
		for (int i=0; i<4; i++) 
		 try {
			board.hit(new Coordinate3D(i,1,4));
			fail ("Error: debió lanzarse la excepción CoordinateAlreadyHitException");
		 } catch (CoordinateAlreadyHitException e) {
		 }
	}
	
	/* Se comprueba que areAllCraftsDestroyed sobre un Board vacío es true
	 * Se añade un Fighter y se comprueba que ahora devuelve false.
	 * Se destruye el Fighter y se comprueba que ahora devuelve true.
	 * Se añade un Bomber y ahora devuelve false. Se dispara sobre él sin destruirlo
	 * y se comprueba que devuelve false.
	 */
	@Test
	public void testAreAllCraftsDestroyed() throws InvalidCoordinateException, 
				NextToAnotherCraftException, OccupiedCoordinateException, 
				CoordinateAlreadyHitException {
		assertTrue(board.areAllCraftsDestroyed());
		board.addCraft(fighterW,f1);
		assertFalse(board.areAllCraftsDestroyed());
		shotsAtFighterZ4();
		assertTrue(board.areAllCraftsDestroyed());
		board.addCraft(bomberE,b1);
		assertFalse(board.areAllCraftsDestroyed());
		shotsAtBomberZ1();
		assertFalse(board.areAllCraftsDestroyed());
	}

	/* Comprobación de board.toString tras añadir los Aircrafts y disparar sobre ellos */
	@Test
	public void testToString() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		assertEquals(strBoard0,board.toString());
		testAddCraftOk();
		assertEquals(strBoard1,board.toString());
		shotsAtFighterZ4();
		assertEquals(strBoard2,board.toString());
		shotsAtBomberZ3();
		assertEquals(strBoard2,board.toString());
		shotsAtTransportZ6();
		assertEquals(strBoard3,board.toString());
		shotsAtBomberZ1();
		assertEquals(strBoard3,board.toString());
		shotsIntoWater();
		assertEquals(strBoard3,board.toString());
	}
	
/***************************************************************/	
	//Funciones auxiliares
	void shotsAtFighterZ4() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		//Se destruye el fighter en z=4;
		for (int i=0; i<4; i++) board.hit(new Coordinate3D(i,1,4));
		board.hit(new Coordinate3D(1,0,4));
		board.hit(new Coordinate3D(1,2,4)); //Hundido
	}
	
	void shotsAtBomberZ3() throws InvalidCoordinateException, CoordinateAlreadyHitException{
	
		//Tocado Bomber en z=3
		for (int i=2; i<5; i++) board.hit(new Coordinate3D(i,5,3));
	}
	
	void shotsAtTransportZ6() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		//Se destruye Transport en z=6
		for (int i=2; i<7; i++) 
			board.hit(new Coordinate3D(4,i,6));
		board.hit(new Coordinate3D(3,4,6));
		board.hit(new Coordinate3D(5,4,6));
		board.hit(new Coordinate3D(2,5,6));
		board.hit(new Coordinate3D(6,5,6)); //Hundido
	}	
	void shotsAtBomberZ1() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		//Tocado
		for (int i=2; i<7; i++) 
			board.hit(new Coordinate3D(2,i,1));
	}
	
	void shotsIntoWater() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		for (int i=0; i<7; i++) {
			board.hit(new Coordinate3D(i,i,2));
			board.hit(new Coordinate3D(i,i,0));
			board.hit(new Coordinate3D(i,5,0));
			board.hit(new Coordinate3D(0,i,3));
			board.hit(new Coordinate3D(4,i,4));
		}
	}
	
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
