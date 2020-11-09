package model;

import static org.junit.Assert.*;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.CoordinateAlreadyHitException;
import model.exceptions.InvalidCoordinateException;
import model.exceptions.NextToAnotherCraftException;
import model.exceptions.OccupiedCoordinateException;
import model.ship.Board2D;
import model.ship.Coordinate2D;
import model.ship.Cruiser;
import model.ship.Ship;

@SuppressWarnings("unused")
public class TestAngyMain {
	static final int MAX_BOARD_SIZE = 20;
	static final int  MIN_BOARD_SIZE = 5;
	final static int DIM = 10;
	Board2D board;
	Ship Manolo, R, Francisco_del_Moral, RaulSinPutas;
	static String player1, player1HITT, player1HITF, sboard4, sboardEmpty;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		player1 = "       Ø  \n" + 
			      "       Ø  \n" + 
			      " ØØØ   Ø  \n" + 
			      "          \n" +
			      "          \n" +
			      "   ØØØ    \n          \n          \n          \n          ";
  
		player1HITT = "       Ø  \n"
					+ "       Ø  \n"
					+ " ØØØ   Ø  \n"
					+ "          \n"
					+ "          \n"
					+ "   ØØ•    \n"
					+ "          \n"
					+ "          \n"
					+ "          \n"
					+ "          ";
		
		player1HITF = "??????????\n"
					+ "? ????????\n"
					+ "??????????\n"
					+ "??????????\n"
					+ "??????????\n"
					+ "?????•????\n"
					+ "??????????\n"
					+ "??????????\n"
					+ "??????????\n"
					+ "??????????";
		
		sboard4 = "• ØØ•\n" + 
			     "•    \n" + 
			     "•   Ø\n" + 
			     "    •\n" + 
			     "ØØØ •";
		sboardEmpty = "     \n     \n     \n     \n     ";
		
	}
	
	@Before
	public void setUP() throws Exception {
		Manolo = new Cruiser(Orientation.EAST);
		R = new Cruiser(Orientation.NORTH);
		Francisco_del_Moral = new Cruiser(Orientation.EAST);
		RaulSinPutas= new Cruiser(Orientation.WEST);
		board = new Board2D(DIM);
	}
	
	@Test
	public void testShowParte1() throws InvalidCoordinateException, CoordinateAlreadyHitException, OccupiedCoordinateException, NextToAnotherCraftException {
		System.out.println("Test Show Parte 1");
		assertTrue(board.addCraft(Manolo, new Coordinate2D(0,0)));
		assertTrue(board.addCraft(R, new Coordinate2D(5,-1)));
		assertTrue(board.addCraft(Francisco_del_Moral, new Coordinate2D(2,3)));
		assertEquals("(1) Player 1:Board 10; crafts: 3; destroyed: 0", player1, board.show(true));
		board.hit(new Coordinate2D(1,1));
		board.hit(new Coordinate2D(5,5));
		assertEquals("Player 1:Board 10; crafts: 3; destroyed: 0", player1HITT, board.show(true));
		assertEquals(player1HITF, board.show(false));
		assertEquals(player1HITT, board.show(true));
	}

}
