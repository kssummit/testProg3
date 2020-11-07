package model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import model.aircraft.Coordinate3D;
import model.ship.Coordinate2D;

@SuppressWarnings("unused")
public class CoordinatePreTest {
	Coordinate cd2, cd3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cd2 = new Coordinate2D(-10,7);
		cd3 = new Coordinate3D(15, 8, -2);
	}

	/* Comprueba set y get modificando algunas de las Coordinates creadas en setUp() 
	 */
	//TODO
	@Test
	public void testSetGet() {
		//fail("Realiza la comprobación del correcto funcionamiento de set y get");
		Coordinate aux2d = new Coordinate2D (5,15);
		Coordinate aux3d = new Coordinate3D (5,15,-2);
		assertNotEquals ("aux2d!=cd2", aux2d, cd2);
		assertNotEquals ("aux3d!=cd3", aux3d, cd3);
		cd2.set(0, 5);
		cd2.set(1, 15);
		cd3.set(0, 5);
		cd3.set(1, 15);
		cd3.set(2, -2);
		assertEquals ("set1", aux2d, cd2);
		assertEquals ("set2", aux3d, cd3);
	}
	
	//TODO
	/* Comprueba que set lanza IllegalArgumentException cuando el componente
	 * no es correcto
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetIllegalArgumentException() {
		//fail("Realiza el test SetIllegalArgumentException");
		try {
			cd2.set(-1, -5);
			fail ("Error: No se lanzó la excepción SetIllegalArgumentException");
		} catch (IllegalArgumentException e) {
			cd3.set(4, 5);
		}
	}


	
	//TODO
	/* Comprueba que get lanza IllegalArgumentException cuando el componente
	 * no es correcto
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetIllegalArgumentException() {
		//fail("Realiza el test SetIllegalArgumentException");
		try {
			   cd2.get(-1);
			   fail ("Error: No se lanzó la excepción IllegalArgumentException");
			} catch (IllegalArgumentException e) {
				cd3.get(5);
			}
	}
	
	/* Comprobación de la sumas entre coordenadas de dimensiones distintas*/
	@Test
	public void testAdd2Dand3D() {
		Coordinate aux2d = new Coordinate2D (5,15);
		Coordinate aux3d = new Coordinate3D (5,15,-2);
		assertEquals ("c2+c3", aux2d, cd2.add(cd3));
		assertEquals ("c3+c2", aux3d, cd3.add(cd2));
		assertNotEquals ("aux2d!=cd2", aux2d, cd2);
		assertNotEquals ("aux3d!=cd3", aux2d, cd3);
	}
	
	//TODO add
	@Test(expected=NullPointerException.class)
	public void testAddNullPointerException() {
		try {
		   cd2.add(null);
		   fail ("Error: No se lanzó la excepción NullPointerException");
		} catch (NullPointerException e) {
			cd3.add(null);
		}
	}
	
	//TODO subtract
	/* Comprueba la correcta resta entre Coordinates de distinta dimensión */
	@Test
	public void testsubtract2Dand3D() {
		//fail("Realiza el test");
		Coordinate aux2d = new Coordinate2D (-25,-1);
		Coordinate aux3d = new Coordinate3D (25,1,-2);
		assertEquals ("c2-c3", aux2d, cd2.subtract(cd3));
		assertEquals ("c3-c2", aux3d, cd3.subtract(cd2));
		assertNotEquals ("aux2d!=cd2", aux2d, cd2);
		assertNotEquals ("aux3d!=cd3", aux2d, cd3);
	}

	//TODO
	/* Comprueba que al intentar restar a una Coordinate el valor null, se lanza
	 * la excepción NullPointerException 
	 */
	@Test(expected = NullPointerException.class)
	public void testSubtractNullPointerException() {
		//fail("Realiza el test");
		try {
			cd2.subtract(null);
			fail ("Error: No se lanzó la excepción NullPointerException");
		} catch (NullPointerException e) {
			cd3.subtract(null);
			fail("no funciona");
		}
	}


}
