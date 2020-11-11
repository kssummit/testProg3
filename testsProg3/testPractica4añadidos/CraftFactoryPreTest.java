package model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.aircraft.Bomber;
import model.aircraft.Fighter;
import model.aircraft.Transport;
import model.ship.Battleship;
import model.ship.Carrier;
import model.ship.Cruiser;
import model.ship.Destroyer;

public class CraftFactoryPreTest {

	//TODO
	/* Comprueba la correcta creación de todos los Craft para todas las orientaciones */
	@Test
	public void testCreateCraftOk() {
		Craft craft;
		for (Orientation orient : Orientation.values()) {
			craft = CraftFactory.createCraft("Battleship", orient);
			assertTrue (craft instanceof Battleship );
			//fail("Completa el test");
			craft = CraftFactory.createCraft("Cruiser", orient);
			assertTrue (craft instanceof Cruiser );
			craft = CraftFactory.createCraft("Carrier", orient);
			assertTrue (craft instanceof Carrier );
			craft = CraftFactory.createCraft("Transport", orient);
			assertTrue (craft instanceof Transport );
			craft = CraftFactory.createCraft("Bomber", orient);
			assertTrue (craft instanceof Bomber );
			craft = CraftFactory.createCraft("Fighter", orient);
			assertTrue (craft instanceof Fighter );
			craft = CraftFactory.createCraft("Destroyer", orient);
			assertTrue (craft instanceof Destroyer );
			
		}
	}

	//TODO
	/* Comprueba que createCraft devuelve null si el Craft es desconocido */
	@Test
	public void testCreateCraftWrong() {
		//fail("Realiza el test");
		Craft craft;
		for (Orientation orient : Orientation.values()) {
			craft = CraftFactory.createCraft("MIblinderBARQUITO", orient);
			assertNull(craft);
		}
	}
}
