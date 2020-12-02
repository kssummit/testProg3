/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.aircraft.Board3D;
import model.exceptions.io.BattleshipIOException;
import model.io.IPlayer;
import model.io.IVisualiser;
import model.io.PlayerFactory;
import model.io.VisualiserFactory;
import model.ship.Board2D;

public class GamePreTest {

	final String DIRFILES ="pre-test/files/";
	Game game;
	IPlayer player1, player2; 
	Board board1, board2;
	
	@Before
	public void setUp() throws Exception {
		player1= PlayerFactory.createPlayer("Julia", DIRFILES+"ShipsFile1.in");
		player2= PlayerFactory.createPlayer("Raul", "21");
		board1 = new Board2D(7);
		board2 = new Board2D(7);
		game = new Game(board1, board2, player1, player2);
	}

	
	/* Comprueba que al pasar null a alguno de los parámetros del constructor de
	 * Game se lanza la excepción NullPointerException
	 */
	@Test
	public void testGameNullPointerException() throws BattleshipIOException {
		try {
		  new Game(null, board2, player1, player2);
		  fail ("Error: se debió lanzar NullPointerException");
		} catch (NullPointerException e) {
		} 
		
		try {
			  new Game(board1, null, player1, player2);
			  fail ("Error: se debió lanzar NullPointerException");
		} catch (NullPointerException e) {
		} 
		
		try {
			  new Game(board1, board2, null, player2);
			  fail ("Error: se debió lanzar NullPointerException");
		} catch (NullPointerException e) {
		} 
		
		try {
			  new Game(board1, board2, player1, null);
			  fail ("Error: se debió lanzar NullPointerException");
		} catch (NullPointerException e) {
		}
	}

	/*Test de los getPlayers y su relación de asociación con Game */
	@Test
	public void testGetPlayers() {
		assertSame(player1,game.getPlayer1());
		assertSame(player2,game.getPlayer2());
	}

	/*Prueba de los getBoards y su relación de asociación con Game */
	@Test
	public void testGetBoards() {
		assertSame(board1, game.getBoard1());
		assertSame(board2, game.getBoard2());
	}


	/* Se comprueba game.toString() en sus tres estados: Not Started, Ongoing, Ended
	 */
	@Test
	public void testToString() throws BattleshipIOException {
		
			player1= PlayerFactory.createPlayer("Julia", DIRFILES+"GameFile1.in");
			player2= PlayerFactory.createPlayer("Raul", DIRFILES+"GameFile2.in");
			game = new Game(board1, board2, player1, player2);
			IVisualiser iv = VisualiserFactory.createVisualiser("Console", game);
			
			compareLines (readFromFile(DIRFILES+"GameNotStarted.sol").toString(),game.toString()+"\n");
			
			game.start();
			game.playNext();
			game.playNext();
			compareLines (readFromFile(DIRFILES+"OngoingGame.sol").toString(),game.toString()+"\n");
			
			for (int i=0; i<13; i++) game.playNext();
			System.out.println(game.toString());
			compareLines (readFromFile(DIRFILES+"GameEnded.sol").toString(),game.toString()+"\n");
			assertTrue(game.gameEnded());
		
	}
		
	
	/* Partida 1. Ambos jugadores colocan sus Ships a partir de la misma semilla. Deben generar tableros 2D
	 * iguales. Partida aleatoria.
	 */
	@Test
	public void testPlayGame1() throws BattleshipIOException {
		
		final String outFile = DIRFILES+"testPlayGame1.alu";
		player1= PlayerFactory.createPlayer("Mary", "15");
		player2= PlayerFactory.createPlayer("Raul", "15");
		board1 = new Board2D(5);
		board2 = new Board2D(5);
		game = new Game(board1, board2, player1, player2);
		IVisualiser iv = VisualiserFactory.createVisualiser("Console", game);
		PrintStream ps = standardIO2File(outFile);
		if (ps!=null) {
			game.playGame(iv);
			assertTrue(game.gameEnded());
			System.setOut(System.out); //Reestablecemos la salida standard
			ps.close();
		} 
		else fail("Error: no se pudo crear el fichero "+outFile);
		
		//Se compara salida del alumno con la solución
		StringBuilder sbSolution=readFromFile(DIRFILES+"testPlayGame1.sol");
		StringBuilder sbStudent=readFromFile(outFile);
		System.out.println(sbStudent.toString());
		compareLines(sbSolution.toString(),sbStudent.toString());	
	}
	
	
	/* Realiza más partidas combinando distinto tipos de Player. Puedes usar el esquema de testPlayGame1 */
	 
	
	/*************************
	 * FUNCIONES AUXILIARES
	 *************************/
	//Redirección de la salida estandard a un fichero	
	public static PrintStream standardIO2File(String fileName){

        if(fileName.equals("")){//Si viene vacío usamos este por defecto
            fileName="C:\\javalog.txt";
        }
        PrintStream ps=null;
        try {
            //Creamos un printstream sobre el archivo.
            ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))),true);
            //Redirigimos salida estandar
            System.setOut(ps);
           // System.setErr(ps);
        } catch (FileNotFoundException ex) {
            System.err.println("Se ha producido una excepción FileNotFoundException");
        }
        return ps;
    }

	//Lee la solución de un fichero y la devuelve en un StringBuilder	
	private StringBuilder readFromFile(String file) {
		Scanner sc=null;
		try {
				sc = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()) 
			sb.append(sc.nextLine()+"\n");			
		sc.close();
		return (sb);
	}
	
	private void  compareLines(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		boolean iguales = true;
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length && iguales; i++) {
			 if (! exp[i].contains("Action by")) {
				 assertEquals("linea "+i, exp[i].trim(),res[i].trim());
			 }
		}
	}
	
}
