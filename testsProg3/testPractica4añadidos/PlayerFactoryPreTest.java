package model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import model.exceptions.io.BattleshipIOException;

public class PlayerFactoryPreTest {

	final String DIRFILES = "test/files/";
	
	/* Se crea un player a partir de un fichero y se comprueba que createPlayer ha creado
	 * un PlayerFile.
	 */
	@Test
	public void testCreatePlayerFile1() throws BattleshipIOException, NullPointerException, FileNotFoundException {			
		IPlayer ip=PlayerFactory.createPlayer("Saul",DIRFILES+"testCreatePlayerFile1.in");
		assertEquals("PlayerFile",ip.getClass().getSimpleName());
	}

    //TODO
	/* Pasa a createPlayer un número como segundo parámetro y comprueba que se ha creado 
	 * un PlayerRandom 
	 */
	@Test
	public void testCreatePlayerRandom1() throws BattleshipIOException {
		//fail("Realiza el test");
		try {
			IPlayer ip=PlayerFactory.createPlayer("MM", "12");
			assertEquals("PlayerRandom", ip.getClass().getSimpleName());
		} catch (BattleshipIOException | NullPointerException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//TODO
	/* Realiza el test que compruebe que createPlayer lanza la excepción BattleshipIOException,
	 * cuando se le pasa como segundo parámetro un fichero que no existe
	 */
	@Test(expected=BattleshipIOException.class)
	public void testCreatePlayerFileNotExist() throws BattleshipIOException, NullPointerException, FileNotFoundException {
		//fail("Realiza el test");
		try {
			IPlayer ip=PlayerFactory.createPlayer("Saul",DIRFILES+"ElPutoBlinder.in");
			fail("deberia lanzar la excepcion ");
			assertEquals("PlayerRandom", ip.getClass().getSimpleName());
		} catch (BattleshipIOException | NullPointerException | FileNotFoundException e) {
			PlayerFactory.createPlayer("Saul",DIRFILES+"ElPutoBlinder.in");
		}

	}
	
	/* Cuando se pasa como segundo parámetro null, createPlayer debe lanzar NullPointerException
	 */
	@Test(expected=NullPointerException.class)
	public void testCreatePlayerNullPointerException() throws BattleshipIOException, NullPointerException, FileNotFoundException {
		//fail("Realiza el test");		     
		try {
			PlayerFactory.createPlayer("Laura",null);
		} catch (NullPointerException e) {
			PlayerFactory.createPlayer("Pepa",null);
		} catch (BattleshipIOException | FileNotFoundException e) {
			fail("Aqui no debería entrar");
		}
	}
	
	
	//TODO
	/* Comprueba que si como segundo parámetro, no pasamos ni números ni '.', '\', '/' 
	  debe devolver null*/
	@Test
	public void testCreatePlayerNull1() throws BattleshipIOException {
		//fail("Realiza el test");
		try {
			assertEquals(null, PlayerFactory.createPlayer("Laura","fgs"));
		} catch (NullPointerException | BattleshipIOException | FileNotFoundException e) {
			fail("Aqui no debería entrar, debería solo devolver un nulo");
		}

	}
	
}
