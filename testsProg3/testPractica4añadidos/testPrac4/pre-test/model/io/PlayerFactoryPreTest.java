/**
 * @author Samuel Oliva
 */
package model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import model.exceptions.io.BattleshipIOException;

public class PlayerFactoryPreTest {

	final String DIRFILES = "pre-test/files/";
	
	/* Se crea un player a partir de un fichero y se comprueba que createPlayer ha creado
	 * un PlayerFile.
	 */
	@Test
	public void testCreatePlayerFile1() throws BattleshipIOException {			
			IPlayer ip=PlayerFactory.createPlayer("Saul",DIRFILES+"testCreatePlayerFile1.in");
			assertEquals("PlayerFile",ip.getClass().getSimpleName());
	}
	
	
	/* Pasa a createPlayer un número como segundo parámetro y comprueba que se ha creado 
	 * un PlayerRandom 
	 */
	@Test
	public void testCreatePlayerRandom1() throws BattleshipIOException {
		IPlayer ip=PlayerFactory.createPlayer("Raul","12345");
		assertEquals("PlayerRandom",ip.getClass().getSimpleName());
	}
	
	
	/* Realiza el test que compruebe que createPlayer lanza la excepción BattleshipIOException,
	 * cuando se le pasa como segundo parámetro un fichero que no existe
	 */
	@Test(expected=BattleshipIOException.class)
	public void testCreatePlayerFileNotExist() throws BattleshipIOException {
		PlayerFactory.createPlayer("Laura","EsteArchivoNoDeberiaExistir.txt");
	}
	
	/* Cuando se pasa como segundo parámetro null, createPlayer debe lanzar NullPointerException
	 */
	@Test(expected=NullPointerException.class)
	public void testCreatePlayerNullPointerException() throws BattleshipIOException {
  
			PlayerFactory.createPlayer("Laura",null);
	
	}
	
	
	/* Comprueba que si como segundo parámetro, no pasamos ni números ni '.', '\', '/' 
	  debe devolver null*/
	@Test
	public void testCreatePlayerNull1() throws BattleshipIOException {
		assertEquals(null,PlayerFactory.createPlayer("Jose","EstoNoEsUnFichero"));
	}
	
}
