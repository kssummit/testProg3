package model.aircraft;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Board;
import model.Coordinate;
import model.CoordinateFactory;
import model.Orientation;
import model.exceptions.CoordinateAlreadyHitException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;
import model.ship.*;

public class Board3DPreTest {
	Aircraft bomberE, bomberS,fighterW,fighter1S, fighter2S, transportN;
	Board3D board;
	final int TAM=7;
	final int MAX_BOARD_SIZE = 20;
	final int MIN_BOARD_SIZE = 5;
	static String sboard00,sboard01, sboard02, sboard03, sboard04;
	
	Coordinate3D b1, f2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		sboard00 ="???????|???????|???????|???????|???????|???????|???????\n" + 
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
		
		sboard03 = "     ⇄ |       |       |       | ⇄     |       | ⇄     \n" + 
				   "     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     \n" + 
				   "    ⇄⇄⇄| ••    |       |       | ⇄     |       |⇄⇄⇄ ⇋  \n" + 
				   "     ⇄ |  •    |       |   ⇶   |       |       | ⇄  ⇋  \n" + 
				   "       |••••   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ \n" + 
				   "       |  •    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋\n" + 
				   "       | ••    |       |   ⇶   |       |       |    ⇋  ";
		
		sboard04 ="???????|???????|???????|???????|???????|???????|???????\n" +
			      "    ???|    ???|    ???|???????|???????|???????|???????\n" +
			      "    ???| ⇶⇶ ???|    ???|???????|???????|???????|???????\n" +
			      "     ??|  ⇶  ??|     ??|???????|???????|???????|???????\n" +
			      "     ??|⇶⇶⇶⇶ ??|     ??|???????|???????|???????|???????\n" +
			      "     ??|  ⇶  ??|     ??|???????|???????|???????|???????\n" +
			      "    ???| ⇶⇶ ???|    ???|???????|???????|???????|???????";
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
	}

	//TODO
	/* Comprueba que al crear un Board3D con tamaño en los límites no produce excepción. 
	 * Y que si sales fuera de ellos se produce la excepción IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBoard3D() {
		Board board;
		board = new Board3D(MAX_BOARD_SIZE);
		assertEquals (MAX_BOARD_SIZE, board.getSize());
		board = new Board3D(MIN_BOARD_SIZE);
		assertEquals (MIN_BOARD_SIZE, board.getSize());
		try {
			board = new Board3D(MAX_BOARD_SIZE+1);
		} catch (IllegalArgumentException e) {
			board = new Board3D(MIN_BOARD_SIZE-1);
			e.getMessage();
		}
	}
	
	/* Se comprueba getSize() */
	@Test
	public void testGetSize( ) {
		 assertEquals(7,board.getSize());
		 board = new Board3D(17);
		 assertEquals(17, board.getSize());
	}
	
	/* Se comprueba checkCoordinate para Coordinate3D en los límites devuelve true */
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
	
	//TODO
	/* Comprueba que checkCoordinate(Coordinate) para Coordinate fuera de los límites
	 * devuelve false.
	 */
	@Test
	public void testCheckCoordinateOutOfLimits() {
		//fail("Realiza el test");
		assertFalse(board.checkCoordinate(CoordinateFactory.createCoordinate(TAM+1,TAM,TAM)));		
	}
	
	/* checkCoordinate para una Coordinate2D en un Board3D */
	@Test(expected=IllegalArgumentException.class)
	public void testCheckCoordinateException() {
		try {
			assertTrue(board.checkCoordinate(new Coordinate2D(3,4)));
			fail("no funciona");
		} catch (IllegalArgumentException e) {
			assertTrue(board.checkCoordinate(new Coordinate2D(2,4)));
			e.getMessage();
		}
		
	}
	
	//TODO
	/* Añade los Aircraft correctamente en el board, tal como aparecen a continuación:
	 *|     ⇄ |       |       |       | ⇄     |       | ⇄     |
     *|     ⇄ |       |       |       |⇄⇄⇄⇄   |       | ⇄     |
     *|    ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  |
     *|     ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  |
     *|       |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ |
     *|       |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋|
     *|       | ⇶⇶    |       |   ⇶   |       |       |    ⇋  |
	 */
	@Test
	public void testAddCraftOk() {
		try {
			board.addCraft(fighter1S, CoordinateFactory.createCoordinate(3,0,0));
			board.addCraft(bomberE, CoordinateFactory.createCoordinate(0,2,1));
			board.addCraft(bomberS, CoordinateFactory.createCoordinate(1,3,3));
			board.addCraft(fighterW, CoordinateFactory.createCoordinate(-1,-1,4));
			board.addCraft(fighter2S, CoordinateFactory.createCoordinate(-1,0,6));
			board.addCraft(transportN, CoordinateFactory.createCoordinate(2,2,6));
			System.out.println("Se han añadido todos los barcos correctamente");
			//fail("Completa el test añadiendo el resto de Aircrafts");
		} catch (InvalidCoordinateException | NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se produjo la excepcion "+(e.getClass()).getName());
		}		
	}
	
	
	//TODO
	/* Tras poner los Craft como se indica en testAddCraftOk(), comprueba que Board.show para true y false
	 * coincide con los String sboard01 y sboard00 respectivamente.
	 */
	@Test
	public void testShow1() {
		testAddCraftOk();
		assertEquals(sboard01, board.show(true));
		assertEquals(sboard00, board.show(false));
		//fail("Completa el test");
	}


	
	/* Posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
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
	     T T T       |       |       | ⇄     |       | ⇄     
           T |       |       |       |⇄⇄⇄⇄   |       | ⇄     
          ⇄⇄⇄| ⇶⇶    |       |       | ⇄     |       |⇄⇄⇄ ⇋  
           ⇄ |  ⇶    |       |   ⇶   |       |       | ⇄  ⇋  
             |⇶⇶⇶⇶   |       | ⇶ ⇶ ⇶ |       |       |   ⇋⇋⇋ 
             |  ⇶    |       | ⇶⇶⇶⇶⇶ |       |       |  ⇋ ⇋ ⇋
             | ⇶⇶    |       |   ⇶   |       |       |    ⇋  
	 */
	@Test
	public void testAddCraftOutOccupied() {
		Aircraft transportN = new Transport(Orientation.NORTH);
		f2 = new Coordinate3D(3,0,0);
		testAddCraftOk();
		try {		
			assertFalse(board.addCraft(transportN, new Coordinate3D(3,-3, 0)));
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
		} catch (InvalidCoordinateException e) {
			//Comprobamos que todo sigue igual
			assertNotNull (board.getCraft(new Coordinate3D(5,0,0)));
			assertNotNull(board.getCraft(new Coordinate3D(5,1,0)));
			assertTrue(board.getCraft(new Coordinate3D(5,0,0)).getClass().getName()=="model.aircraft.Fighter");
			assertEquals(f2,board.getCraft(new Coordinate3D(5,0,0)).getPosition());
			assertEquals(f2,board.getCraft(new Coordinate3D(5,1,0)).getPosition());
			assertNull(transportN.getPosition());
			assertNull(board.getCraft(new Coordinate3D(3,0,0)));
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e) {
			fail ("Error. Se esperaba InvalidCoordinateException "
					+ "pero se produjo la excepcion "+e.getClass().getName());
		} 
	}

	/* Posiciona los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Intenta poner un Aircraft en una posición en la que parte de ese Aircraft 
	 * queda fuera y parte queda próximo a otro Aircraft ya existente en el board. 
     * Comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra
	 * 2- que no se ha puesto el Aircraft 
	 */
	@Test
	public void testAddCraftOutNextTo() {
		Aircraft t1 = new Transport(Orientation.NORTH);
		f2 = new Coordinate3D(3,0,0);
		testAddCraftOk();
		try {
			assertFalse(board.addCraft(t1, new Coordinate3D(1,-3, 0)));
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
		} catch (InvalidCoordinateException e) {
			assertNull(t1.getPosition());
			e.getCause();
		} catch (OccupiedCoordinateException |NextToAnotherCraftException e) {
			fail("Error: no se produjo la excepción InvalidCoordinateException ");
			e.printStackTrace();
		}
	}
	
		
	
	/* Posiciona los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Intenta poner un Aircraft en una posición en la que parte de ese Aircraft
	 * colisiona con otro Aircraft ya existente y parte queda próximo a otro Aircraft
	 * también existente en el board. Comprueba que:
	 * 1- se lanza la excepción OccupiedCoordinateException y no otra.
	 * 2- que no se ha puesto el Aircraft
	 * 3- que sigue existiendo el Aircraft con el que colisionaba.*/
	@Test
	public void testAddCraftColisionNextTo() {
		testAddCraftOk();
        Fighter f = new Fighter(Orientation.WEST);
        Coordinate c = new Coordinate3D(1,0,0);
		try {
            assertFalse(board.addCraft(f, c));
			fail("Error: no se produjo la excepción OccupiedCoordinateException ");
		} catch (OccupiedCoordinateException e) {
			assertNull(f.getPosition());
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(4,2,0)));
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(5,0,0)));
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(5,1,0)));
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(5,2,0)));
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(5,3,0)));
            assertEquals(fighter1S, board.getCraft(new Coordinate3D(6,2,0)));
		} catch (InvalidCoordinateException | NextToAnotherCraftException e) {
			fail("Error: no se produjo la excepción OccupiedCoordinateException");
			e.printStackTrace();
		}
	}



	/* Se posicionan los Aircraft en el board tal como se indica en testAddCraftOk().
	/* Se intenta poner un Transport, indicado con el símbolo 'T' para diferenciarlo del resto,
	 * en una posición en la que parte de ese Transport queda fuera del board, parte colisiona con 
	 * un Bomber ya existente, y parte queda próximo a dos Aircraft (Bomber y Fighter) también 
	 * existentes en el board. 
	 * Se comprueba que:
	 * 1- se lanza la excepción InvalidCoordinateException y no otra.
	 * 2- que no se ha puesto el Aircraft
	 * 3- que sigue existiendo el Aircraft con el que colisionaba.
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
			assertNotEquals(b1,board.getCraft(new Coordinate3D(1,2,1)).getPosition());
			assertNull(transportN.getPosition());
			assertNull(board.getCraft(new Coordinate3D(1,0,1)));
		} catch (NextToAnotherCraftException | OccupiedCoordinateException e2) {
			fail ("Error. Se esperaba OccupiedCoordinateException "
					+ "pero se produjo la excepcion "+e2.getClass().getName());
		} 
	}
	
	
	//TODO
	/* Se añaden los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Comprueba para un Aicraft cualquiera del board que getCraft(Coordinate), en todas sus Coordinates, 
	 * devuelve dicho Aircraft. Dispara en todas sus posiciones hundiéndolo y vuelve a comprobar que
	 * dicho Aircraft sigue existiendo.
	 */
	@Test
	public void testGetCraft() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		assertNotNull(board.getCraft(new Coordinate3D(2,5,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,3,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,6,1)));
		assertNotNull(board.getCraft(new Coordinate3D(0,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,6,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,2,1)));
		assertNotNull(board.getCraft(new Coordinate3D(3,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,2,1)));

		this.shotsAtBomberZ1();
		assertNotNull(board.getCraft(new Coordinate3D(2,5,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,3,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,6,1)));
		assertNotNull(board.getCraft(new Coordinate3D(0,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,6,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(1,2,1)));
		assertNotNull(board.getCraft(new Coordinate3D(3,4,1)));
		assertNotNull(board.getCraft(new Coordinate3D(2,2,1)));
		//fail("Realiza el test");
		
	}

	/* Se añaden los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se comprueba que antes de disparar sobre uno de ellos, en sus posiciones, isSeen devuelve false
	 * Tras disparar sobre dichas posiciones, se comprueba que isSeen ahora devuelve true.
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
	
	
	/* Se comprueba que para un Board3D de tamaño 10, si posicionáramos el bomberE, fighter1S y transportN en la Coordinate3D(1,2,1)
	 * el método board.getNeighborhood(Craft, new Coordinate(1,2,1)) devuelve sets con 92, 66 y 96 Coordinates respectivamente.
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

	//TODO
	/* Comprueba que poniendo distintos Aircrafts en otras posiciones limítrofes, incluso fuera del tablero, getNeigborhood devuelve
	 * sets con un número de Coordinates correctas.
	 * Ten en cuenta que las posiciones fuera del tablero no se deben añadir al set.
	 */
	@Test
	public void testGetNeighborhoodCraftCoordinate2() throws InvalidCoordinateException, NextToAnotherCraftException, OccupiedCoordinateException {
		Board board2 = new Board3D(10);
		Aircraft fN = new Fighter(Orientation.NORTH);
		Set<Coordinate> neighborhood = board2.getNeighborhood(fN, new Coordinate3D(-1,-1,0));
		assertEquals(30, neighborhood.size());
		neighborhood = board2.getNeighborhood(fighter1S, new Coordinate3D(1,2,-1));
		assertEquals(24, neighborhood.size());
		neighborhood = board2.getNeighborhood(transportN, new Coordinate3D(1,2,10));
		assertEquals(35, neighborhood.size());
		neighborhood = board2.getNeighborhood(transportN, new Coordinate3D(1,2,10));
		assertEquals(47, board.getNeighborhood(bomberE, new Coordinate3D(0,4,1)).size());
		neighborhood = board2.getNeighborhood(transportN, new Coordinate3D(1,2,10));
		assertEquals(40, board.getNeighborhood(fN, new Coordinate3D(3,3,2)).size());
	}


	/* Se añaden los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Se hacen disparos al agua.
	 * Se comprueba que show(true) y show(false) genera los Strings sboard01 y sboard02 respectivamente.
	 */
	@Test
	public void testHit1() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		assertEquals(sboard00,board.show(false));
		shotsIntoWater();
		//EL true no va aún
		assertEquals(sboard01,board.show(true));
		assertEquals(sboard02,board.show(false));
	}
	
	//TODO
	/* Añade los Aircraft en el board tal como se indica en testAddCraftOk().
	 * Dispara sobre algunos de los Aircraft, destruyendo alguno; y comprueba que show(true) muestra los disparos 
	 * realizados a los Aircraft y que show(false) también los muestra excepto los desruídos que muestra el Aircraft
	 * y además marca como vistos sus coordenadas vecinas.
	 */
	@Test
	public void testHit2() throws InvalidCoordinateException, CoordinateAlreadyHitException  {
		testAddCraftOk();
		shotsDAtBomberZ1();
		assertEquals(sboard03, board.show(true));
		assertEquals(sboard04, board.show(false));
	}
	
	//TODO
	/* Realiza disparos fuera del Board y comprueba que se lanza la excepción InvalidCoordinateException
	 * 
	 */
	@Test
	public void testHitOutOfBoard() {
		try {
			board.hit(CoordinateFactory.createCoordinate(-1,-2,-3));
		} catch (CoordinateAlreadyHitException | IllegalArgumentException e) {
			fail("Deberia haber entrado en InvalidCoordinateException");
		} catch (InvalidCoordinateException e) {
			e.getCause();
		}
	}
	
	//TODO
	/* Realiza disparos repetidos a los Aircrafts y comprueba que se lanza la excepción CoordinateAlreadyHitException
	 * 
	 */
	@Test(expected = CoordinateAlreadyHitException.class)
	public void testCoodinateAlreadyHitOnBoard() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		testAddCraftOk();
		shotsDAtBomberZ1();
		try {
			board.hit(new Coordinate3D(2,2,1));
			fail("no se mete en la excepcion correspondiente");
		} catch (CoordinateAlreadyHitException e) {
			board.hit(new Coordinate3D(2,4,1));
			e.getCause();
		}
	}




/***************************************************************/	
	//Funciones auxiliares

	void shotsAtBomberZ1() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		//Tocado
		for (int i=2; i<7; i++) 
			board.hit(new Coordinate3D(2,i,1));
	}
	
	void shotsDAtBomberZ1() throws InvalidCoordinateException, CoordinateAlreadyHitException{
		//Tocado
		board.hit(new Coordinate3D(2,5,1));
		board.hit(new Coordinate3D(1,6,1));
		board.hit(new Coordinate3D(2,3,1));
		
		board.hit(new Coordinate3D(0,4,1));
		board.hit(new Coordinate3D(2,6,1));
		board.hit(new Coordinate3D(1,4,1));
		
		board.hit(new Coordinate3D(2,4,1));
		board.hit(new Coordinate3D(1,2,1));
		board.hit(new Coordinate3D(3,4,1));
		board.hit(new Coordinate3D(2,2,1));
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
	
}
