package model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.*;
import model.ship.Board2D;
import model.ship.Coordinate2D;
import model.ship.Cruiser;

public class BoardPreTest {

	static final int MAX_BOARD_SIZE = 20;
	static final int  MIN_BOARD_SIZE = 5;
	static final int DIM = 10;
	static final int minimo = MIN_BOARD_SIZE;
	Board2D board;
	Craft fragata, galeon, bergantin, goleta;
	static String sboardEmpty,sboard, sboardHide1, sboardHits1,
				sboardHits2,sboardHits3, sboardHide2; //= new String();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sboardHide1 = "?????\n?????\n?????\n?????\n?????";
		
		sboardHide2 = "Ø ??•\n" + 
			      "Ø ?? \n" + 
			      "Ø  ??\n" + 
			      "   ?•\n" + 
			      "?•??•";
		
		sboardEmpty = "     \n     \n     \n     \n     ";
		
		sboard="Ø ØØØ\n" + 
			   "Ø    \n" + 
			   "Ø   Ø\n" + 
			   "    Ø\n" + 
			   "ØØØ Ø";
		
		sboardHits1 ="• ØØØ\n" + 
				     "•    \n" + 
				     "•   Ø\n" + 
				     "    Ø\n" + 
				     "ØØØ Ø";
		
		sboardHits2 ="• ØØ•\n" + 
				     "•    \n" + 
				     "•   Ø\n" + 
				     "    •\n" + 
				     "ØØØ •";
		
		sboardHits3 = "• ØØ•\n" + 
				      "•    \n" + 
				      "•   Ø\n" + 
				      "    •\n" + 
				      "Ø•Ø •";
		
	}

	@Before
	public void setUp() {
		fragata = new Cruiser(Orientation.WEST);
		galeon = new Cruiser(Orientation.SOUTH);
		bergantin = new Cruiser(Orientation.EAST);
		goleta = new Cruiser(Orientation.NORTH);
		board = new Board2D(DIM);
	}

	//TODO testBoardGetSize
	/* Comprueba los límites del tamaño en el constructor,
	 * tanto dentro como justo fuera. Comprueba que al superarlos
	 * el tamaño que toma el Board es el mínimo 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBoardGetSize()throws IllegalArgumentException {
		//Dentro de los límites
		board = new Board2D(MIN_BOARD_SIZE+1);
		assertEquals(MIN_BOARD_SIZE+1,board.getSize());
		board = new Board2D(MAX_BOARD_SIZE-1);
		assertEquals(MAX_BOARD_SIZE-1,board.getSize());
		//fuera de los limites
		try {
			board = new Board2D(MIN_BOARD_SIZE-1);
			assertEquals(board.getSize(),MIN_BOARD_SIZE);
			fail(" no va, debería lanzar la excepcion");
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			board = new Board2D(MAX_BOARD_SIZE+3);
			assertEquals(board.getSize(),MIN_BOARD_SIZE);
			e.getCause();
		}

		//fail ("Termina el test superando los límites en 1");		
	}
	
	/* Se comprueba checkCoordinate en los límites del tamaño 
	 * del Board 
	 */
	@Test
	public void testCheckCoordinate() {
		final int SIZE = 15;
		Board board = new Board2D(SIZE);
		assertFalse(board.checkCoordinate(new Coordinate2D(0,SIZE)));
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,SIZE-1)));
		assertFalse(board.checkCoordinate(new Coordinate2D(-1,SIZE)));
		assertFalse(board.checkCoordinate(new Coordinate2D(SIZE,0)));
		assertFalse(board.checkCoordinate(new Coordinate2D(SIZE-1,-1)));
		assertFalse(board.checkCoordinate(new Coordinate2D(SIZE,-1)));
		assertTrue(board.checkCoordinate(new Coordinate2D(0,SIZE-1)));
		assertTrue(board.checkCoordinate(new Coordinate2D(SIZE-1,0)));
		
	}

	//TODO testAddShipsOk()
	/* Posicionamientos correctos entre Ships. Posiciona de forma correcta 
	 * los 4 ships galeon, fragata, goleta y bergantín y comprueba que  se
	 * han posicionado los Ships en el Board.
	 */
	@Test
	public void testAddShipsOk() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		assertTrue(board.addCraft(galeon, new Coordinate2D(0,1)));
		for (int i=2; i<5; i++)	
			assertNotNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(2,i)));
		
		assertTrue(board.addCraft(goleta, new Coordinate2D(2,1)));
		for(int i=2; i<5; i++) {
			assertNotNull("x,y = 4,"+i,board.getCraft(new Coordinate2D(4,i)));
		}
		assertTrue(board.addCraft(fragata, new Coordinate2D(5,1)));
		for(int i=6; i<9; i++) {
			assertNotNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(i,3)));
		}
		assertTrue(board.addCraft(bergantin, new Coordinate2D(0,6)));
		for(int i=1; i<4; i++) {
			assertNotNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(i,8)));
		}
		//TODO fail("Sigue comprobando addShip igualmente para fragata, goleta y bergantín");
	}

	//TODO testAddShipsOutOfBoard
	/* Posiciona los 4 Ships fuera del tablero y comprueba que
	 * addShip devuelve false y que además no se han posicionado
	 * los Ships en el Board
	 */
	@Test(expected = InvalidCoordinateException.class)
	public void testAddShipsOutOfBoard() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		try {
			assertFalse(board.addCraft(bergantin, new Coordinate2D(MIN_BOARD_SIZE+1,MAX_BOARD_SIZE+1)));
		} catch (InvalidCoordinateException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			assertFalse(board.addCraft(fragata, new Coordinate2D(MAX_BOARD_SIZE+1,MIN_BOARD_SIZE-1)));
		} catch (OccupiedCoordinateException | NextToAnotherCraftException e) {
			// TODO Auto-generated catch block
			fail("Deberia haber entrado en InvalidCoordinateException");
		}
		
		//TODO fail("Realiza el test testAddShipsOutOfBoard()");
	}
	
	//TODO testAddShipNextOther
	/* Posiciona un Ship junto a otro y comprueba que addShip devuelve
	 * false y que además no se ha posicionado el Ship en el Board
	 */
	@Test
	public void testAddShipNextOther() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		//TODO fail("Realiza el test testAddShipNextOther()");
		try {
			assertTrue(board.addCraft(galeon, new Coordinate2D(1,1)));
			assertFalse(board.addCraft(fragata, new Coordinate2D(0,1)));
		} catch (OccupiedCoordinateException e) {
			e.getCause();
			assertNull(board.getCraft(new Coordinate2D(0,1)));
		}
		
		
	}
	
	//TODO testGetShip
	/* Se posiciona un Ship en una Coordinate.
	 * 1- Prueba getShip en una Coordinate que no contiene al Ship
	 * 2- Prueba getShip en todas las posiciones que ocupa el Ship
	 */
	@Test
	public void testGetShip() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		assertTrue(board.addCraft(fragata, new Coordinate2D(3,1)));
		assertNull(board.getCraft(new Coordinate2D(7,9)));
		Set<Coordinate> cord = fragata.getAbsolutePositions();
		for (Coordinate c1 : cord) {
			assertEquals(fragata, board.getCraft(c1));
		}
		
		//TODO fail ("Termina el test testGetShip()");
	}
	
	
	
    /* Se comprueba isSeen antes y después de disparar al agua
     * en un Board sin Ships */
	@Test
	public void testIsSeen1() throws InvalidCoordinateException, CoordinateAlreadyHitException {
		for (int i=0; i<board.getSize(); i++) {
			for (int j=0; j<board.getSize(); j++) {
				assertFalse(board.isSeen(new Coordinate2D(i,j)));
				board.hit(new Coordinate2D(i,j));
				assertTrue(board.isSeen(new Coordinate2D(i,j)));
			}
		}
				
	}

	//TODO testIsSeen2
  /* Posiciona un Ship en el Board y comprueba isSeen 
   * antes y después de disparar a las distintas partes del Ship.
   * Cuando el Ship se ha hundido entonces comprueba que las
   * Coordinates vecinas del Ship también se han marcado como
   * vistas */
	@Test
	public void testIsSeen2() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException, CoordinateAlreadyHitException {
		assertTrue(board.addCraft(galeon, new Coordinate2D(2,3)));
		assertFalse(board.isSeen(new Coordinate2D(3,3)));
		for (int i = 4; i <= 6; i++) {
			assertFalse("F",board.isSeen(new Coordinate2D(4,i)));
			board.hit(new Coordinate2D(4,i));
			assertTrue("V",board.isSeen(new Coordinate2D(4,i)));
		}
		assertTrue(board.isSeen(new Coordinate2D(3,5)));
		//TODO fail ("Realiza el test testIsSeen2()");
	}

	//TODO testHit
	/* Coloca un Ship en el Board en una Coordinate. Comprueba que:
	 * 1- al disparar (hit) sobre las posiciones alrededor del Ship el 
	 *    resultado es WATER.
	 * 2- al disparar (hit) sobre las posiciones del Ship, excepto la última,
	 *    el resultado es HIT.
	 * 3- al disparar (hit) sobre la última posición del Ship, el resultado 
	 *    es DESTROYED
	 * 
	 */
	@Test
	public void testHit() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException, CoordinateAlreadyHitException {
		//TODO fail("Realiza el test testHit()");
		board.addCraft(goleta, new Coordinate2D(2,1));
		assertEquals(CellStatus.WATER, board.hit(new Coordinate2D(3,1)));
		assertEquals(CellStatus.HIT, board.hit(new Coordinate2D(4,3)));
		assertEquals(CellStatus.HIT, board.hit(new Coordinate2D(4,4)));
		assertEquals(CellStatus.WATER, board.hit(new Coordinate2D(3,4)));
		assertEquals(CellStatus.DESTROYED, board.hit(new Coordinate2D(4,2)));
		
	}

	//TODO testAreAllCraftsDestroyed
	/* Comprueba que:
	 * 1- en un Board sin Ships, areAllCraftsDestroyed devuelve true.
	 * 2- al posicionar dos Ships en el Board, tras cada posicionamiento,
	 *    areAllCraftsDestroyed devuelve false.
	 * 3- tras cada disparo sobre el primer Ship, hundiéndolo, areAllCraftsDestroyed 
	 *    devuelve false.
	 * 4- tras cada disparo sobre el segundo Ship, areAllCraftsDestroyed devuelve
	 *    false, excepto tras el último disparo que debe devolver true.
	 * 5- añade un nuevo Ship, entonces areAllCraftsDestroyed debe devolver
	 *    false.
	 */
	@Test
	public void testAreAllCraftsDestroyed() throws InvalidCoordinateException, CoordinateAlreadyHitException, OccupiedCoordinateException, NextToAnotherCraftException {
		board = new Board2D(10);
		assertTrue("numCrafts=destroyedCrafts=0",board.areAllCraftsDestroyed());
		board.addCraft(galeon, new Coordinate2D(0,1));
		assertFalse("numCrafts=1; destroyedCrafts=0",board.areAllCraftsDestroyed());
		board.addCraft(fragata, new Coordinate2D(3,1));
		assertFalse("numCrafts=2; destroyedCrafts=0",board.areAllCraftsDestroyed());
		//TODO fail ("Termina las pruebas 3, 4 y 5");
		board.hit(new Coordinate2D(2,2));
		board.hit(new Coordinate2D(2,3));
		board.hit(new Coordinate2D(2,4));
		assertFalse(board.areAllCraftsDestroyed());
		board.hit(new Coordinate2D(4,3));
		board.hit(new Coordinate2D(5,3));
		assertFalse(board.areAllCraftsDestroyed());
		board.hit(new Coordinate2D(6,3));
		assertTrue(board.areAllCraftsDestroyed());
		assertNull(board.getCraft(new Coordinate2D(5,2)));
		assertTrue(board.areAllCraftsDestroyed());
	}

	
	/* Se comprueba getNeighborhood(Ship) donde el Ship y todas sus 
	 * Coordinate vecinas están dentro de Board.
	 */
	@Test
	public void testGetNeighborhoodShipCompletelyIn1() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		board.addCraft(fragata, new Coordinate2D(5,1));
		Set<Coordinate> neighborhood = board.getNeighborhood(fragata);
		assertEquals(12,neighborhood.size());
		for (int i=5; i<10; i++) {
			for (int j=2; j<4; j++) {
				if ((j==3) && (i>=6)&&(i<=8))
					assertFalse("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));
				else 
					assertTrue("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));	
			}
		}
	}
	
	//TODO testGetNeighborhoodShipOutOfBounds
	/* Comprueba:
	 * 1- getNeighborhood(Ship) para un Ship que no se ha puesto en el Board 
	 *    debe devolver un Set vacío.
	 * 
	 * 2- getNeighborhood(Ship, Coordinate) donde el Ship sale de los límites
	 *    del Board. El conjunto de Coordinate vecinas solo recoge aquellas
	 *    que están dentro del Board
	 */
	@Test
	public void testGetNeighborhoodShipOutOfBounds() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		//TODO fail ("Realiza el test testGetNeighborhoodShipOutOfBounds() ");
		Set<Coordinate> vacio = new HashSet<Coordinate>();
		board = new Board2D(7);
		
		try {
			assertEquals(vacio, this.board.getNeighborhood(galeon));
		} catch (NullPointerException e) {
			e.getMessage();
			assertTrue(board.addCraft(galeon, new Coordinate2D(2,3)));
			vacio.add(new Coordinate2D(3,3));
			vacio.add(new Coordinate2D(3,4));
			vacio.add(new Coordinate2D(3,5));
			vacio.add(new Coordinate2D(3,6));
			vacio.add(new Coordinate2D(4,3));
			vacio.add(new Coordinate2D(5,3));
			vacio.add(new Coordinate2D(5,4));
			vacio.add(new Coordinate2D(5,5));
			vacio.add(new Coordinate2D(5,6));
			assertEquals(vacio, board.getNeighborhood(galeon));
		}
		//a partir de aquí falla y hay que ver el porque
	}
	
	
	/* Se crea un tablero de tamaño 5 sin Ships. Se comprueba que lo devuelto
	 * por show(true) y show(false) es correcto.
	 * 
	 */
	@Test
	public void testShowBoardEmty() {
		board = new Board2D(5);
		String hideShips = board.show(false);
		assertEquals(sboardHide1,hideShips);
		String showShips = board.show(true);
		assertEquals(sboardEmpty,showShips);
		
	}
	
	//TODO testShowBoardWithShips
	/* Se crea un tablero de tamaño 5.
	 * 1- Se añaden los 4 ships en las posiciones indicadas en la variable estática 'board'
	 *    definida en setUp().
	 * 2- Se comprueba que show(false) devuelve lo mismo que la variable estática sboardHide1
	 *    y que show(true) lo mismo que el contenido de la variable estática 'sboard' 
	 */
	@Test
	public void testShowBoardWithShips() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		board = new Board2D(5);
		assertTrue(board.addCraft(galeon, new Coordinate2D(-2,-1)));//A South
		assertTrue(board.addCraft(fragata, new Coordinate2D(1,-2)));//F West
		assertTrue(board.addCraft(bergantin, new Coordinate2D(-1,2)));//B East
		assertTrue(board.addCraft(goleta, new Coordinate2D(2,1)));//G North
		//TODO fail ("Sigue añadiendo la goleta y el bergantín en sus posiciones y haz las comprobaciones indicadas");
		assertEquals(sboardHide1, this.board.show(false));
		assertEquals(sboard, this.board.show(true));
	}	

	//TODO testToString1
	/* Añade los 4 Ships del setUp() en el Board y comprueba toString() con
	 * la salida correcta.
	 */
	@Test
	public void testToString1() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException {
		//TODO fail("Añade los 4 Ships en el Board");
		board.addCraft(galeon, new Coordinate2D(-2,-1));
		board.addCraft(fragata, new Coordinate2D(1,-2));
		board.addCraft(bergantin, new Coordinate2D(-1,2));
		board.addCraft(goleta, new Coordinate2D(2,1));
		assertEquals("Board 10; crafts: 4; destroyed: 0",board.toString());
	}
	
	//TODO testToString2
    /* Se toma el ejemplo del test testAreAllCraftsDestroyed().
     * 1- Destruye el galeón y comprueba que la salida que debe dar es correcta.
     * 2- Realiza disparos sobre la fragata comprobando que las salidas que debe
     *    dar son correctas.
    */
	@Test
	public void testToString2() throws InvalidCoordinateException, OccupiedCoordinateException, NextToAnotherCraftException, CoordinateAlreadyHitException {
		board.addCraft(galeon,new Coordinate2D(0,1));
		board.addCraft(fragata,new Coordinate2D(3,1));
		assertEquals("Board 10; crafts: 2; destroyed: 0", board.toString());
		board.hit(new Coordinate2D(2,2));
		board.hit(new Coordinate2D(2,3));
		board.hit(new Coordinate2D(2,4));
		assertEquals("Board 10; crafts: 2; destroyed: 1", board.toString());
		board.hit(new Coordinate2D(4,3));
		board.hit(new Coordinate2D(5,3));
		board.hit(new Coordinate2D(6,3));
		assertEquals("Board 10; crafts: 2; destroyed: 2", board.toString());
		//TODO fail ("Realiza el test testToString2()");
	}

}
