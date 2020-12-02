/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.aircraft.Coordinate3D;
import model.ship.Coordinate2D;

public class CoordinateTest {
	Coordinate cd2, cd3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cd2 = new Coordinate2D(-10,7);
		cd3 = new Coordinate3D(15, 8, -2);
	}

	/* Se prueba set y get con las Coordinates 2D y 3D creadas en 
	 * setUp() */
	@Test
	public void testSetGet() {
		assertEquals (-10, cd2.get(0));
		assertEquals (7, cd2.get(1));
		cd2.set(0, -15);
		cd2.set(1, 19);
		assertEquals (-15, cd2.get(0));
		assertEquals (19, cd2.get(1));
	}
	
	/* Se prueba set en caso de pasar una componente fuera de rango
	 * en una Coordinate2D. En ese caso se debe lanzar IllegalArgumentException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetIllegalArgumentException2D() {
		try {
			cd2.set(-1, 12);
			fail ("Error: No se lanzó la excepción IllegalArgumentException");
		}catch (IllegalArgumentException e) {
			   assertEquals(cd2, cd2.copy());
			   cd2.set(2, 5);		
		}
	}

	/* Se prueba set en caso de pasar una componente fuera de rango
	 * en una Coordinate3D. En ese caso se debe lanzar IllegalArgumentException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetIllegalArgumentException3D() {
		try {
			cd3.set(-1, 12);
			fail ("Error: No se lanzó la excepción IllegalArgumentException");
		}catch (IllegalArgumentException e) {
			   assertEquals(cd3, cd3.copy());
			   cd3.set(3, 5);		
		}
	}
	
	/* Se prueba get en caso de pasar una componente fuera de rango
	 * en una Coordinate2D. En ese caso se debe lanzar IllegalArgumentException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetIllegalArgumentException() {
		try {
			cd2.get(-1);
			fail ("Error: No se lanzó la excepción IllegalArgumentException");
		}catch (IllegalArgumentException e1) {
			try {
			   cd2.get(2);	
			   fail ("Error: No se lanzó la excepción IllegalArgumentException");
			} catch (IllegalArgumentException e2) {
				cd3.get(3);
			}
		}
	}
	
	/* Se suman una Coordinate2D y una Coordinate3D. La dimensión de la Coordinate
	 * depende de quién invoque a add.  */
	@Test
	public void testAdd2Dand3D() {
		Coordinate aux2d = new Coordinate2D (5,15);
		Coordinate aux3d = new Coordinate3D (5,15,-2);
		assertEquals ("c2+c3", aux2d, cd2.add(cd3));
		assertEquals ("c3+c2", aux3d, cd3.add(cd2));
		assertNotEquals ("aux2d!=cd2", aux2d, cd2);
		assertNotEquals ("aux3d!=cd3", aux2d, cd3);
	}
	
	/* Si se pasa null como parámetro a add, se comprueba que se lanza NullPointerException
	 * tanto en Coordinate2D como Coordinate3D.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddNullPointerException() {
		try {
		   cd2.add(null);
		   fail ("Error: No se lanzó la excepción NullPointerException");
		} catch (NullPointerException e) {
			cd3.add(null);
		}
	}
	
	/* Si se pasa null como parámetro a add, se comprueba que se lanza NullPointerException
	 * tanto en Coordinate2D como Coordinate3D.
	 */
	@Test
	public void testsubtract2Dand3D() {
		Coordinate aux2d = new Coordinate2D (-25, -1);
		Coordinate aux3d = new Coordinate3D (25,1,-2);
		assertEquals ("c2-c3", aux2d, cd2.subtract(cd3));
		assertEquals ("c3-c2", aux3d, cd3.subtract(cd2));
		assertNotEquals ("aux2d!=cd2", aux2d, cd2);
		assertNotEquals ("aux3d!=cd3", aux2d, cd3);
	}

	/* Si se pasa null como parámetro a subtract, se comprueba que se lanza NullPointerException
	 * tanto en Coordinate2D como Coordinate3D.
	 */
	@Test(expected=NullPointerException.class)
	public void testSubtractNullPointerException() {
		try {
			   cd2.subtract(null);
			   fail ("Error: No se lanzó la excepción NullPointerException");
			} catch (NullPointerException e) {
				cd3.subtract(null);
			}
	}


}
