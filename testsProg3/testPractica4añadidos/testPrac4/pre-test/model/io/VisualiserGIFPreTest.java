/**
 * @author Samuel Oliva
 */
package model.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Craft;
import model.Game;
import model.Orientation;
import model.aircraft.Board3D;
import model.exceptions.CoordinateAlreadyHitException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;
import model.ship.Battleship;
import model.ship.Board2D;
import model.ship.Carrier;
import model.ship.Coordinate2D;
import model.ship.Cruiser;
import model.ship.Destroyer;


public class VisualiserGIFPreTest {

	final String DIRFILES = "pre-test/files/";
	Game game;
	IPlayer player1, player2; 
	Board board1, board2;
	Craft destroyer, carrier, battleship, cruiser;

	@Before
	public void setUp() throws Exception {
		board1 = new Board2D(10);
		board2 = new Board2D(10);
		player1 = PlayerFactory.createPlayer("Raul","46");
		player2 = PlayerFactory.createPlayer("Carol","21");
		destroyer = new Destroyer(Orientation.EAST);
		carrier = new Carrier(Orientation.SOUTH);
		battleship = new Battleship(Orientation.NORTH);
		cruiser = new Cruiser(Orientation.WEST);
	}

	
	/* Comprueba que pasándo un game null al constructor visualserGIF se
	 * lanza NullPointerException. 
	 * */
	@Test(expected=NullPointerException.class)
	public void testVisualiserGIF() throws Exception {
		new VisualiserGIF(null);
	}

	/* Se aplica show a un Game con 2 Board2D con el juego sin empezar.
	 * Se compara el fichero GIF que genera show, con el GIF que debe salir */
	@Test
	public void testShowEmpty2D() throws IOException {
		game = new Game(board1, board2, player1, player2);
		VisualiserGIF vg = new VisualiserGIF(game);
		vg.show();
		vg.close();
		/* IMPORTANTE: Si el siguiente assert fuera erróneo, para comprobar ficheros de salida 
		 * con la solución, ejecutar solo este test por separado y comprobar luego las diferencias.
		 */
		
		//He comparado los GIF manualmente y están bien, si te da error comprueba manualmente los gif
		assertTrue ("Comprueba este test de forma aislada",compareFiles(DIRFILES+"emptyBoard2D.gif","files/output.gif"));
		
	}

	
	/* Realiza lo mismo que el test anterior, con los mismos players, pero ahora con board1 y board2 
	 * como Board3D de tamaño 10.
	 * Comprueba que el gif obtenido es igual que el gif solución: 'emptyBoard3D.gif'
	 */
	@Test
	public void testShowEmpty3D() throws IOException {
		
		Board tabla1 = new Board3D(10);
		Board tabla2 = new Board3D(10);
		game = new Game(tabla1, tabla2, player1, player2);
		VisualiserGIF vg = new VisualiserGIF(game);
		vg.show();
		vg.close();
		
		//La correción manual está bien
		assertTrue("Comprueba este test de forma aislada",compareFiles(DIRFILES+"emptyBoard3D.gif","files/output.gif"));
		
	}


	/* Se crean 2 tableros 2D y 2 PlayerRandom (hecho ya en setUp).
	 * Crea un Game con dichos tableros y los PlayerRandom. Inicia el juego y 
	 * dispara sobre ambos tableros con las funciones creadas HitsInBoard1_2D y HitsInBoard2_2D
	 * Crea un visualiserGIF para ese Game, muéstralo y guarda el gif.
	 * Comprueba que el gif obtenido es igual al gif solución 'Show2D.gif'.
	 * 
	 * Tableros iniciales.
	 * Board1		Board2
	  ----------   ----------
	 |  ®®®®®   | |          |
     |          | |          |
     |          | |  ®®®®® Ω |
     |          | |        Ω |
     |     ØØØ  | |          |
     |          | |          |
     |     ΩΩ  O| |       O  |
     |         O| |       O  |
     |         O| |   ØØØ O  |
     |         O| |       O  |
      ----------   ----------
      * Tableros tras los disparos:
     Board1				Board2
     ? ®®®®® ??			?????????
	 ?       ??			??????????
	 ??????????			??•••• •? 
	 ? ????  ??			??????????
	 ?? ???•???			????????? 
	 ??? ?? ?  			?????   ??
	 ???? ?•? O			????? O ? 		
	 ?????  ? O			?     O ??	
	 ?????? ? O			  ØØØ O ? 
              O			?     O ??
	 */
	@Test
	public void testShow2DWithShipsAndHits() throws IOException, InvalidCoordinateException, 
	    NextToAnotherCraftException, OccupiedCoordinateException, CoordinateAlreadyHitException {
		Game juego = new Game(board1, board2, player1, player2);
		juego.start();
		HitsInBoard1_2D();
		HitsInBoard2_2D();
		VisualiserGIF vg = new VisualiserGIF(juego);
		vg.show();
		vg.close();
		
		//La correción manual está bien, si te da error comprueba manualmente los gif
		assertTrue("Comprueba este test de forma aislada",compareFiles(DIRFILES+"Show2D.gif","files/output.gif"));
	}

	
	
	
	/*************************
	 * FUNCIONES AUXILIARES
	 *************************/
	// Dispara sobre el tablero board1 2D
	void HitsInBoard1_2D() throws InvalidCoordinateException, NextToAnotherCraftException, 
	   OccupiedCoordinateException, CoordinateAlreadyHitException   {
		
		for (int i=2;i<7;i++) board1.hit(new Coordinate2D(i,0));
		for (int i=0;i<10; i++) board1.hit(new Coordinate2D(i,9));
		for (int i=3;i<8; i++) board1.hit(new Coordinate2D(6,i));
		for (int i=6;i<9; i++) board1.hit(new Coordinate2D(9,i));
		for (int i=1;i<7; i++) board1.hit(new Coordinate2D(i,i+2));	
	}

	// Dispara sobre el tablero board2 2D
	void HitsInBoard2_2D() throws InvalidCoordinateException, NextToAnotherCraftException, 
	   OccupiedCoordinateException, CoordinateAlreadyHitException   {
		for (int i=6;i<10;i++) board2.hit(new Coordinate2D(6,i));
		for (int i=0;i<5; i++) board2.hit(new Coordinate2D(i,8));
		for (int i=2;i<8; i++) board2.hit(new Coordinate2D(i,2));
		board1.hit(new Coordinate2D(7,3));
		for (int i=0;i<5; i++) board2.hit(new Coordinate2D(9,i*2));
	}
	
	 String getFileExtension(String name) {
        int extIndex = name.lastIndexOf(".");

        if (extIndex == -1) {
            return "";
        } else {
            return name.substring(extIndex + 1);
        }
    }
	 
	//Compara ficheros gifs
	 boolean compareFiles(String f1, String f2) throws IOException {
		String comando = null;
		if (! getFileExtension(f1).equals("gif"))
			throw new IOException("Error: No es un fichero gif");
		
		if (!new File(f1).exists())  {
			System.out.println("El fichero "+f1+ "no existe.");
			return false;
		}
		if (!new File(f2).exists())  {
			System.out.println("El fichero "+f2+ "no existe.");
			return false;
		}
		try {
			comando = new String("cmp -b "+f1+" "+f2);
			// Ejecutamos el comando definido
			Process p = Runtime.getRuntime().exec(comando);
      
			// Instanciamos un lector del buffer para mostrar resultado
			BufferedReader resultado = new BufferedReader(new InputStreamReader(p.getInputStream()));
			// System.out.println("Resultado del comando:");
			String diferencias = resultado.readLine();
			if (diferencias!=null && diferencias.length()!=0) {
				while (diferencias!= null){
					System.out.println(diferencias);
					diferencias=resultado.readLine();
				}
				return false;
			} 
			else return true;
		} catch (IOException ex) {
        return false;
		}
	}

}
