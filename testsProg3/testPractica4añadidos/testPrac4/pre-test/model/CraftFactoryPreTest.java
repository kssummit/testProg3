/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import model.ship.*;
import model.aircraft.*;

public class CraftFactoryPreTest {

	//TODO
	/* Comprueba la correcta creación de todos los Craft para todas las orientaciones */
	@Test
	public void testCreateCraftOk() {
		Craft craft;
		for (Orientation orient : Orientation.values()) {
			//Comprueba los ships
			craft = CraftFactory.createCraft("Battleship", orient);
			assertTrue (craft instanceof Battleship );
			craft = CraftFactory.createCraft("Carrier", orient);
			assertTrue (craft instanceof Carrier );
			craft = CraftFactory.createCraft("Cruiser", orient);
			assertTrue (craft instanceof Cruiser );
			craft = CraftFactory.createCraft("Destroyer", orient);
			assertTrue (craft instanceof Destroyer );
			
			//Comprueba los aircrafts
			craft = CraftFactory.createCraft("Bomber", orient);
			assertTrue (craft instanceof Bomber );
			craft = CraftFactory.createCraft("Fighter", orient);
			assertTrue (craft instanceof Fighter );
			craft = CraftFactory.createCraft("Transport", orient);
			assertTrue (craft instanceof Transport );
		}
	}

	
	/* Comprueba que createCraft devuelve null si el Craft es desconocido */
	@Test
	public void testCreateCraftWrong() {
		assertEquals(null,CraftFactory.createCraft("aSaberQueNavioEsEste", Orientation.NORTH));
	}
}
