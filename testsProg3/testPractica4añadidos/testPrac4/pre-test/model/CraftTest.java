/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.aircraft.Bomber;
import model.aircraft.Coordinate3D;
import model.exceptions.CoordinateAlreadyHitException;
import model.ship.Carrier;
import model.ship.Coordinate2D;

public class CraftTest {

	Craft carrier, bomber;
	static final int HIT_VALUE = -1;
	static final int CRAFT_VALUE = 1;
	static String scarrier, sbomber;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		scarrier = "Carrier (EAST)\n -----\n"
        		+ "|     |\n"
        		+ "|     |\n"
        		+ "|•••••|\n"
        		+ "|     |\n"
        		+ "|     |\n"
        		+ " -----";
		
		sbomber ="Bomber (SOUTH)\n"
				+ " -----\n"
				+ "|  •  |\n"
				+ "|⇶ • ⇶|\n"
				+ "|•••••|\n"
				+ "|  •  |\n"
				+ "|     |\n"
				+ " -----";
	}
	
	@Before
	public void setUp() throws Exception {
		carrier = new Carrier(Orientation.EAST);
		bomber = new Bomber(Orientation.SOUTH);
	}

	/* Se prueban los casos de lanzamiento de NullPointerException para
	 * getShapeIndex y getAbsolutePositions.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetShapeIndexNullPointerException() {
		carrier.getShapeIndex(null);
	}

	@Test(expected=NullPointerException.class)
	public void testGetAbsolutePositionsCoordinate() {
		bomber.getAbsolutePositions(null);
	}

	@Test(expected=NullPointerException.class)
	public void testGetAbsolutePositions() {
		carrier.getAbsolutePositions();
	}
	
	/* Se posiciona un Carrier en un hipotético tablero. Se dispara completamente sobre él.
	 * Se comprueba que en su Shape ahora donde había CRAFT_VALUE, ahora hay HIT_VALUE
	 */
	@Test
	public void testHitCarrier() throws CoordinateAlreadyHitException {
		carrier.setPosition(new Coordinate2D(7,8));
		for (int i=7; i<12; i++) carrier.hit(new Coordinate2D(i,10));
		int aux[] = carrier.getShape()[carrier.getOrientation().ordinal()];
		for (int i=10; i<15; i++)  {
			assertEquals (HIT_VALUE, aux[i]);
		}
	}
		
	/* Se posiciona un Bomber en un hipotético tablero. Se dispara sobre parte de él.
	 * Se comprueba que en su Shape, donde se ha disparado, ahora hay HIT_VALUE y
	 * donde no se ha disparado y había un CRAFT_VALUE, sigue habiéndolo.
	 */
	@Test
	public void testHitBomber() throws CoordinateAlreadyHitException {
		bomber.setPosition(new Coordinate3D(1,3,10000));
		for (int i=3; i<7; i++) bomber.hit(new Coordinate3D(3,i,10000));
	    for (int i=1; i<6; i++) 
			 if (i!=3) bomber.hit(new Coordinate3D (i,5,10000));
		int aux[] = bomber.getShape()[bomber.getOrientation().ordinal()];
		for (int i=0; i<4; i++)  {
			assertEquals ("aux["+(i*5+2)+"]",HIT_VALUE, aux[i*5+2]);
			if ( ((i*5+2)>9) && ((i*5+2)<15) ) assertEquals ("aux["+i*5+2+"]",HIT_VALUE, aux[i*5+2]);
		}
		assertEquals (CRAFT_VALUE, aux[5]);
		assertEquals (CRAFT_VALUE, aux[9]);
	}

	/* Se posiciona un Carrier en un hipotético tablero. Se dispara completamente sobre él.
	 * Se comprueba que toString devuelve un String igual que scarrier creado en setUpBeforeClass()
	 */
	@Test
	public void testToStringCarrierHited() throws CoordinateAlreadyHitException {
		carrier.setPosition(new Coordinate2D(7,8));
		for (int i=7; i<12; i++) carrier.hit(new Coordinate2D(i,10));
		assertEquals(scarrier, carrier.toString());
	}
	
	/* Se posiciona un Bomber en un hipotético tablero. Se dispara sobre parte de él.
	 * Se comprueba que toString devuelve un String igual que sbomber creado en setUpBeforeClass() .
	 */
	@Test
	public void testToStringBomberHited() throws CoordinateAlreadyHitException {
		bomber.setPosition(new Coordinate3D(1,3,10000));
		for (int i=3; i<7; i++) bomber.hit(new Coordinate3D(3,i,10000));
	    for (int i=1; i<6; i++) 
			 if (i!=3) bomber.hit(new Coordinate3D (i,5,10000));
		assertEquals(sbomber, bomber.toString());
	}
}
