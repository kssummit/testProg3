package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.BattleshipException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;
import model.ship.Board2D;
import model.ship.Coordinate2D;
import model.ship.Cruiser;
import model.ship.Ship;

public class BoardTestP2 {

	static final int MAX_BOARD_SIZE = 20;
	static final int  MIN_BOARD_SIZE = 5;
	final static int DIM = 10;
	Board2D board;
	Ship fragata, galeon, bergantin, goleta;
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
	public void setUp() throws Exception {
		fragata = new Cruiser(Orientation.WEST);
		galeon = new Cruiser(Orientation.SOUTH);
		bergantin = new Cruiser(Orientation.EAST);
		goleta = new Cruiser(Orientation.NORTH);
		board = new Board2D(DIM);
		
	}
	

	/* Se comprueba los límites del tamaño en el constructor 
	 */
	@Test
	public void testBoardGetSize() {
		Board board;
		try {
		  board = new Board2D(MIN_BOARD_SIZE-1);
		  fail("Error. No se produjo la excepción IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		try {
		  board = new Board2D(MAX_BOARD_SIZE+1);
		  fail("Error. No se produjo la excepción IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		
		board = new Board2D(MIN_BOARD_SIZE+1);
		assertEquals(MIN_BOARD_SIZE+1,board.getSize());
		
		board = new Board2D(MAX_BOARD_SIZE-1);
		assertEquals(MAX_BOARD_SIZE-1,board.getSize());
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

	/* Posicionamientos correctos entre Ships. Se comprueba que ha posicionado
	 * los Ships en el Board.
	 */
	@Test
	public void testaddShipsOk() throws BattleshipException {
		board.addCraft(galeon, new Coordinate2D(0,1));
		for (int i=2; i<5; i++)	
			assertNotNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(2,i)));
		board.addCraft(fragata, new Coordinate2D(5,1));
		for (int i=6; i<9; i++)	
			assertNotNull("x,y = "+i+"3",board.getCraft(new Coordinate2D(i,3)));
		board.addCraft(goleta, new Coordinate2D(0,5));
		for (int i=6; i<9; i++)	
			assertNotNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(2,i)));
		board.addCraft(bergantin, new Coordinate2D(4,3));
		for (int i=5; i<8; i++)	
			assertNotNull("x,y = "+i+"5",board.getCraft(new Coordinate2D(i,5)));
	}

	/* Posicionamiento correcto Ships en los límites del Board. Se comprueba que 
	 * ha posicionado los Ships en el Board.
	 * 
	 */
	@Test
	public void testAddShipsOkLimits() throws BattleshipException {
		board.addCraft(galeon, new Coordinate2D(-2,-1));
		for (int i=0; i<3; i++)	
			assertNotNull("x,y = 0,"+i,board.getCraft(new Coordinate2D(0,i)));
		board.addCraft(fragata, new Coordinate2D(-1,7));
		for (int i=0; i<3; i++)	
			assertNotNull("x,y = "+i+"9",board.getCraft(new Coordinate2D(i,9)));
		board.addCraft(goleta, new Coordinate2D(7,6));
		for (int i=7; i<10; i++) 
			assertNotNull("x,y = 9,"+i,board.getCraft(new Coordinate2D(9,i)));
		board.addCraft(bergantin, new Coordinate2D(6,-2));
		for (int i=7; i<10; i++) 
			assertNotNull("x,y = "+i+"0",board.getCraft(new Coordinate2D(i,0)));
	}
	
	/* Posicionamiento Ships fuera del tablero. Se comprueba, además, que no añade
	 * Ship
	 */
	@Test
	public void testAddShipsOutOfBoard()  {
		try {
		  board.addCraft(galeon, new Coordinate2D(0,7));
		  fail ("Error no se produjo la excepción ");
		} catch (BattleshipException e) {
		    for (int i=8; i<11; i++) 
			   assertNull("x,y = 2,"+i,board.getCraft(new Coordinate2D(2,i)));
		}
		try {
		   board.addCraft(fragata, new Coordinate2D(7,3));
		   fail ("Error no se produjo la excepción");
		} catch (BattleshipException e) {
		    for (int i=8; i<11; i++) 
		       assertNull("x,y = "+i+"5",board.getCraft(new Coordinate2D(i,5)));
		}
		try {
		   board.addCraft(goleta, new Coordinate2D(-2,-2));
		   fail ("Error no se produjo la excepción ");
		} catch (BattleshipException e) {
			for (int i=-1; i<2; i++) 
				assertNull("x,y = 0,"+i,board.getCraft(new Coordinate2D(0,i)));
		}
		try {
			board.addCraft(bergantin, new Coordinate2D(-2,7));
			fail ("Error no se produjo la excepción ");
		} catch (BattleshipException e) {
			for (int i=8; i<11; i++) 
				assertNull("x,y = "+i+"0",board.getCraft(new Coordinate2D(i,0)));
		}
		
 	}
	
	/* Posicionamiento incorrecto Ship por proximidad con otro. Se comprueba, además, que 
	 * no añade Ship
	 */
	@Test
	public void testAddShipNextOther() {
		try {
		  board.addCraft(galeon, new Coordinate2D(0,1));
		  board.addCraft(fragata, new Coordinate2D(2,0));
		  fail ("No se produjo la excepcion");
		} catch (BattleshipException e) {
		  for (int i=3; i<6; i++) 
			assertNull("x,y = "+i+"2",board.getCraft(new Coordinate2D(i,2)));
		}
	}
	
	/* Posicionamiento incorrecto Ship por solapamento con otro. Se comprueba, además, que 
	 * no añade Ship
	 */
	@Test
	public void testAddShipOcuppied() {
		try {
		  board.addCraft(galeon, new Coordinate2D(0,1));
		  board.addCraft(fragata, new Coordinate2D(1,0));
		  fail ("Error no se produjo excepción");
		} catch (BattleshipException e) {
				for (int i=3; i<5; i++) 
					assertNull("x,y = "+i+"2",board.getCraft(new Coordinate2D(i,2)));
		}
	}
	
	/* Se posiciona un Ship en una Coordinate.
	 * 1- Se prueba getShip en una Coordinate que no contiene al Ship
	 * 2- Se prueba getShip en todas las posiciones que ocupa el Ship
	 */
	@Test
	public void testGetShip() throws BattleshipException {
		board.addCraft(fragata, new Coordinate2D(3,1));
		Coordinate c = new Coordinate2D(2,3);
		assertNull(board.getCraft(c));
		for (int i=4; i<7; i++) {
			c.set(0, i);
		    assertNotNull(board.getCraft(c));
		}
	}
	
	/* Se posiciona un Ship en una Coordinate y se comprueba la agregación
	 * entre Board y Ship. 
	 */
	@Test
	public void testAgregacionBoardShip() throws BattleshipException {
	    board.addCraft(fragata, new Coordinate2D(3,1));
		Coordinate c = new Coordinate2D(0,3);
		for (int i=4; i<7; i++) {
			c.set(0, i);
		    assertSame(fragata,board.getCraft(c));
		}
	}
	
	
    /* Se comprueba isSeen antes y después de disparar al agua
     * en un Board sin Ships */
	@Test
	public void testIsSeen1() throws BattleshipException {

		for (int i=0; i<board.getSize(); i++)
			for (int j=0; j<board.getSize(); j++) {
				assertFalse(board.isSeen(new Coordinate2D(i,j)));
				board.hit(new Coordinate2D(i,j));
				assertTrue(board.isSeen(new Coordinate2D(i,j)));
			}
				
	}

  /* Se posiciona un Ship en el Board y se comprueba isSeen 
   * antes y después de disparar a las distintas partes del Ship.
   * Cuando el Ship se ha hundido entonces se comprueba que las
   * Coordinates vecinas del Ship también se han marcado como
   * vistas */
	@Test
	public void testIsSeen2() throws BattleshipException{
		board.addCraft(galeon, new Coordinate2D(0,1));
		for (int i=2; i<5; i++) {
				assertFalse("x,y = 2,"+i,board.isSeen(new Coordinate2D(2,i)));
				board.hit(new Coordinate2D(2,i));
				assertTrue("x,y = 2,"+i,board.isSeen(new Coordinate2D(2,i)));
		}
		for (int i=1; i<4; i++)
			for (int j=1; j<6; j++) {
				assertTrue("x,y = "+i+","+j,board.isSeen(new Coordinate2D(i,j)));
			}
	}
	
	/* Se posiciona un Ship en el Board, se dispara a las distintas partes del Ship.
	 * Las coordenadas se guardan en un ArrayList. Se obtiene por reflexión el atributo
	 * privado 'seen'. Se comprueba que las coordenadas almacenadas en el ArrayList tienen
	 * las mismas direcciones que las correspondientes en 'seen'.
	 * Se comprueba la composición entre Board y Coordinate */
	    @SuppressWarnings("unchecked")
		@Test
		public void testCompositionBoardCoordinate() throws Exception {
	    	
			board.addCraft(galeon, new Coordinate2D(0,1));
			List<Coordinate> listHits=new ArrayList<Coordinate>();  //Para los disparos 
			Coordinate c;
			//Se dispara al ship
			for (int i=2; i<5; i++) {
					c=new Coordinate2D(2,i);
					listHits.add(c);
					board.hit(c);
			}		
			Set<Coordinate> boardSet = (Set<Coordinate>)getBoardField(board,"seen");	
			int i;
			for (Coordinate caux: boardSet) {
				  i = listHits.indexOf(caux);
				  if (i != -1)   assertNotSame("Composición", caux, listHits.get(i)); 
			}	
		}
	
	
	
	
	

	/* Se coloca un Ship en el Board en una Coordinate. Se comprueba que:
	 * 1- al disparar (hit) sobre las posiciones alrededor del Ship el 
	 *    resultado es WATER.
	 * 2- al disparar (hit) sobre las posiciones del Ship, excepto la última,
	 *    el resultado es HIT.
	 * 3- al disparar (hit) sobre la última posición del Ship, el resultado 
	 *    es DESTROYED
	 * 
	 */
	@Test
	public void testHit() throws BattleshipException {
		board.addCraft(galeon, new Coordinate2D(5,5));
		for (int i=5; i<board.getSize(); i++) 
		  for (int j=5; j<board.getSize(); j++) {
		     if ( (i!=7) || (j<6) ||(j>8) ){
		    	 
		    		 assertEquals("x,y = "+i+","+j,CellStatus.WATER,board.hit(new Coordinate2D(i,j)));
		     }
		     else 
		    	 if ((i==7)&&(j==8))
			        assertEquals("x,y = "+i+","+j,CellStatus.DESTROYED,board.hit(new Coordinate2D(i,j)));
		    	 else 
		    		 assertEquals("x,y = "+i+","+j,CellStatus.HIT,board.hit(new Coordinate2D(i,j)));
		} 		
	}

	/* Se comprueba que:
	 * 1- en un Board sin Ships, areAllCraftsDestroyed devuelve true.
	 * 2- al posicionar dos Ships en el Board, tras cada posicionamiento,
	 *    areAllCraftsDestroyed devuelve false.
	 * 3- tras cada disparo sobre el primer Ship, areAllCraftsDestroyed devuelve
	 *    false.
	 * 4- tras cada disparo sobre el segundo Ship, areAllCraftsDestroyed devuelve
	 *    false, excepto tras el último disparo que debe devolver true.
	 * 5- si añadimos un nuevo Ship, entonces areAllCraftsDestroyed debe devolver
	 *    false.
	 */
	@Test
	public void testAreAllCraftsDestroyed() throws BattleshipException{
		assertTrue("numCrafts=destroyedCrafts=0",board.areAllCraftsDestroyed());
		board.addCraft(galeon, new Coordinate2D(0,1));
		assertFalse("numCrafts=1; destroyedCrafts=0",board.areAllCraftsDestroyed());
		board.addCraft(fragata, new Coordinate2D(3,1));
		assertFalse("numCrafts=2; destroyedCrafts=0",board.areAllCraftsDestroyed());
		//Destruimos el Ship galeon
		for (int i=2; i<5; i++) {
			board.hit(new Coordinate2D(2,i));
			assertFalse(board.areAllCraftsDestroyed());
		}
		for (int i=4; i<6; i++) {
			board.hit(new Coordinate2D(i,3));
			assertFalse("numCrafts=2; destroyedCrafts=1",board.areAllCraftsDestroyed());
		}
		board.hit(new Coordinate2D(6,3));
		assertTrue("numCrafts=destroyedCrafts=2",board.areAllCraftsDestroyed());
		board.addCraft(galeon, new Coordinate2D(0,5));
		assertFalse("numCrafts=3; destroyedCrafts=2",board.areAllCraftsDestroyed());
	}

	/* Se comprueba:
	 * 1- getNeighborhood(Ship) para un Ship que no se ha puesto en el Board 
	 *    debe devolver un Set vacío.
	 * 
	 * 2- getNeighborhood(Ship, Coordinate) donde el Ship sale de los límites
	 *    del Board. El conjunto de Coordinate vecinas solo recoge aquellas
	 *    que están dentro del Board
	 */
	@Test
	public void testGetNeighborhoodShipOutOfBounds() {
		Set<Coordinate> neighborhood=new HashSet<Coordinate>();
		try {
		  neighborhood = board.getNeighborhood(galeon);
		  fail("No se produjo la excepcion NullPointerException");
		}catch (NullPointerException e) {
		  assertTrue(neighborhood.isEmpty());
		  neighborhood = board.getNeighborhood(galeon,new Coordinate2D(0,7));
		  assertEquals(7,neighborhood.size());
		  for (int i=1; i<4; i++) {
			for (int j=7; j<11; j++) {
				if ((j>9)||((j==8)||(j==9))&&(i==2))
					assertFalse("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));
				else 
					assertTrue("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));	
			}
		  }
		}
	}

	/* Se comprueba getNeighborhood(Ship) donde el Ship y todas sus 
	 * Coordinate vecinas están dentro de Board.
	 */
	@Test
	public void testGetNeighborhoodShipCompletelyIn1()throws BattleshipException {
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
	
	/* Se añade un Ship en un límite del tablero. Se comprueba que getNeighborhood(Ship) recoge
	 * solo aquellas posiciones vecinas que están dentro del Board.
	 */
	@Test
	public void testGetNeighborhoodShipCompletelyIn2() throws BattleshipException {
		board.addCraft(fragata, new Coordinate2D(6,-2));
		Set<Coordinate> neighborhood = board.getNeighborhood(fragata);
		assertEquals(5,neighborhood.size());
		for (int i=6; i<10; i++) {
			for (int j=-2; j<2; j++) {
				if ( ((j==0) && (i>=7)&&(i<=9))|| (j<0))
					assertFalse("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));
				else 
					assertTrue("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));	
			}
		}
	}
	/* Se comprueba que:
	 * 1- getNeighborhood(Ship, Coordinate) devuleve un Set vacío de Coordinates cuando el
	 *    Ship y sus vecinas están todas fuera del Board
	 * 2- getNeighborhood(Ship, Coordinate) devuelve sólo aquellas Coordinates vecinas que
	 *    están dentro del Board para un Ship completamente fuera del Board.
	 * 
	 */
	@Test
	public void testGetNeighborhoodShipCompletelyOutOfBounds() {
		Set<Coordinate> neighborhood = board.getNeighborhood(galeon,new Coordinate2D(0,10));
		assertTrue(neighborhood.isEmpty());
		neighborhood = board.getNeighborhood(galeon,new Coordinate2D(0,9));
		for (int i=1; i<4; i++) {
			for (int j=9; j<13; j++) {
				if ((j>9))
					assertFalse("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));
				else 
					assertTrue("x,y = "+i+","+j,neighborhood.contains(new Coordinate2D(i,j)));	
			}
		}
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
	
	/* Se crea un tablero de tamaño 5. Se añaden los 4 ships en las posiciones indicadas
	 * en la variable estática 'sboard' definida en setUp().
	 * Se comprueba que show(false) devuelve lo mismo que la variable estática sboardHide1
	 * y que show(true) lo mismo que el contenido de la variable estática 'sboard' 
	 * por show(true) y show(false) es correcto.
	 * 
	 */
	@Test
	public void testShowBoardWithShips() throws BattleshipException{
		board = new Board2D(5);
		board.addCraft(galeon, new Coordinate2D(-2,-1));
		board.addCraft(fragata, new Coordinate2D(1,-2));
		board.addCraft(goleta, new Coordinate2D(2,1));
		board.addCraft(bergantin, new Coordinate2D(-1,2));
		String hideShips = board.show(false);
		assertEquals(sboardHide1,hideShips);
		
		String showShips = board.show(true);
		assertEquals(sboard,showShips);
	}	
	
	/*Se crea un Board de tamaño 5. Sobre él se posicionan los 4 Ships del
	 * setUp() en las posiciones indicadas en la varable estática String 
	 * "sboard". Se dispara sobre las posiciones del 'galeon' hundiéndolo.
	 * El método show(true) debe devolver lo mismo que el String 'sboardHits1'
	 * Se dispara sobre posiciones de los Ships 'goleta' y 'fragata' sin 
	 * hundirlos y se comprueba show(true) con 'sboardHits2'
	 * Se dispara sobre el bergantín y varios disparos al agua. Se comprueba
	 * show(true) con 'sboardHits3'.
	 * Por último comparamos show(false) con 'sboardHide2'
	 */
	@Test
	public void testShowBoardWithShipsAndHits() throws BattleshipException{
		board = new Board2D(5);
		board.addCraft(galeon, new Coordinate2D(-2,-1));
		board.addCraft(fragata, new Coordinate2D(1,-2));
		board.addCraft(goleta, new Coordinate2D(2,1));
		board.addCraft(bergantin, new Coordinate2D(-1,2));
		//Se dispara sobre el galeón, hundiéndolo
		board.hit(new Coordinate2D(0,0));
		board.hit(new Coordinate2D(0,1));
		board.hit(new Coordinate2D(0,2));
		String showShips = board.show(true);
		assertEquals(sboardHits1,showShips);
		//Se dispara sobre la goleta y la fragata sin hundirlos
		board.hit(new Coordinate2D(4,0));
		board.hit(new Coordinate2D(4,3));
		board.hit(new Coordinate2D(4,4));
		showShips = board.show(true);
		assertEquals(sboardHits2,showShips);
		
		//Se dispara sobre el bergantín
		board.hit(new Coordinate2D(1,4));
		//Disparos al agua
		board.hit(new Coordinate2D(2,2));
		board.hit(new Coordinate2D(2,3));
		board.hit(new Coordinate2D(4,1));
		showShips = board.show(true);
		assertEquals(sboardHits3,showShips);
		
		showShips = board.show(false);
		assertEquals(sboardHide2,showShips);
	}

	/* Se añaden los 4 Ships del setUp() y se comprueba toString() con
	 * la salida correcta.
	 */
	@Test
	public void testToString1()throws BattleshipException {
		board.addCraft(galeon, new Coordinate2D(0,1));
		board.addCraft(fragata, new Coordinate2D(5,1));
		board.addCraft(goleta, new Coordinate2D(0,5));
		board.addCraft(bergantin, new Coordinate2D(4,3));
		assertEquals("Board 10; crafts: 4; destroyed: 0",board.toString());
	}
	
    /* Se toma el ejemplo del test testAreAllCraftsDestroyed() y se van
     * intercalando toString() comprobando que las salidas son correctas.
     */
	@Test
	public void testToString2()throws BattleshipException {		
		board.addCraft(galeon, new Coordinate2D(0,1));	
		board.addCraft(fragata, new Coordinate2D(3,1));	
		//Destruimos el Ship galeon
		for (int i=2; i<5; i++) {
			board.hit(new Coordinate2D(2,i));
		}
		assertEquals("Board 10; crafts: 2; destroyed: 1",board.toString());
		for (int i=4; i<6; i++) {
			board.hit(new Coordinate2D(i,3));
			assertEquals("Board 10; crafts: 2; destroyed: 1",board.toString());
		}
		
		board.hit(new Coordinate2D(6,3));
		assertEquals("Board 10; crafts: 2; destroyed: 2",board.toString());
		board.addCraft(galeon, new Coordinate2D(0,5));
		assertEquals("Board 10; crafts: 3; destroyed: 2",board.toString());
	}
	
	
	
	/* FUNCIONES AUXILIARES */
	@SuppressWarnings("unchecked")
	Object getBoardField(Board board, String atribute) throws Exception {

		// Sacamos el atributo privado (seen) de la clase (Board) 
        Field field = (board.getClass().getSuperclass()).getDeclaredField(atribute);
        // Indicamos que el atributo es accesible
        field.setAccessible(true);
        // Obtenemos el valor del atributo (seen)
        return (Object)field.get(board);
	}
	

}
