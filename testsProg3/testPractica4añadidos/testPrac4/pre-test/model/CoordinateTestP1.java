/**
 * @author Samuel Oliva
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.ship.Coordinate2D;

public class CoordinateTestP1{
	
    List<int[]> vcoordinates = new ArrayList<int[]>();
    int []vcoor= {0,0,-70,-2,20}; //Para crear coordenadas
    final int DIM = vcoor.length;
    List<Coordinate> lcoor;
    
	@Before
	public void setUp() throws Exception {
		lcoor = new ArrayList<Coordinate>();
		//Se crean las Coordinate (0,0),(0,-70), (-70,-2),(-2,20);
		for (int i=0; i<DIM-1; i++) {
			lcoor.add(new Coordinate2D(vcoor[i],vcoor[i+1]));
		}
		
	}

	@Test
	public void testHashCode() {
		Coordinate c1 = lcoor.get(2);
		Coordinate c2 = new Coordinate2D((Coordinate2D)c1);
		/* Se comprueba que cuando dos Coordinate son iguales, el resultado 
		 * del hash ha de ser el mismo.
		 * Si los Coordinate son distintos el hash puede ser igual o no.
		 */
		assertEquals (c1,c2);
		assertEquals (c1.hashCode(), c2.hashCode());
	}

	/* Se comprueba que el Constructor funciona bien. Para ello se analiza que 
	 * las componentes '0' y '1' de cada Coordinate creada en el setUp() son las 
	 * correctas.
	 */
	@Test
	public void testCoordinateConstructor() {
		int j=0;
		for (int i=0; i<DIM-1; i++) {			
			assertEquals("x",vcoor[i],lcoor.get(j).get(0));
			assertEquals("y",vcoor[i+1],lcoor.get(j).get(1));
			j++;
		}
	}

	/* Se comprueba que el constructor de copia crea una nueva Coordinate con
	 * los mismos valores que las componentes respectivas del Coordinate copiado.
	 * Y eso se hace para cada Coordinate creada en setUp();
	 */
	@Test
	public void testCoordinateConstructorCopy() {
		Coordinate ccopy;
		for (Coordinate caux: lcoor) {
			ccopy= new Coordinate2D((Coordinate2D)caux);
			assertEquals(caux.get(0),ccopy.get(0));
			assertEquals(caux.get(1),ccopy.get(1));	
		}
	}

	/* Se comprueba que el método get(int) para cada componente de una Coordinate 
	 * funciona correctamente.
	 * Se modifican los valores de las componentes de la Coordinate anterior con 
	 * el método set(int, int) y se comprueba con get(int) que los valores de sus 
	 * componentes han cambiado a dichos valores.
	 */
	@Test
	public void testGetSet() {
		Coordinate c = lcoor.get(2);
		assertEquals("x==-70", -70, c.get(0));
		assertEquals("x==-2", -2, c.get(1));
		
		c.set(1,-11); c.set(0, 33);
		assertEquals("x==33", 33, c.get(0));
		assertEquals("x==-11", -11, c.get(1));
	}

	/* Se suman las Coordinate creadas en el setUp() y se comprueba, conforme 
	 * se van sumando, que los valores de sus componentes van tomando los 
	 * valores correctos y que el Coordinate que devuelve no es el mismo que
	 * el Coordinate que invoca al método.
	 */
	@Test  //{0,0,-70,-2,20}
	public void testAdd() {
		Coordinate caux1 = lcoor.get(0);
		Coordinate caux2;
		
		int sumx = caux1.get(0);
		int sumy = caux1.get(1);
		for (int i=0; i<DIM-2; i++) {	
		   caux2 = caux1;
		   caux1 = caux1.add(lcoor.get(i+1));	  
		   sumx += (vcoor[i+1]);
		   sumy += (vcoor[i+2]);
		   
		   assertEquals(sumx,caux1.get(0)); 
		   assertEquals(sumy,caux1.get(1));
		   assertNotSame(caux2, caux1);
		}
	}

	/* Se van restando las Coordinate creadas en el setUp() y se comprueba, 
	 * conforme se van restando, que los valores de sus componentes van tomando 
	 * los valores correctos y que el Coordinate que devuelve no es el mismo que
	 * el Coordinate que invoca al método.
	 */
	@Test
	public void testSubtract() {
		Coordinate caux1 = lcoor.get(0);
		Coordinate caux2;
		int subx = caux1.get(0);
		int suby = caux1.get(1);
		for (int i=0; i<DIM-2; i++) {
		   caux2 = caux1;
		   caux1 = caux1.subtract(lcoor.get(i+1));
		   subx -= (vcoor[i+1]);
		   suby -= (vcoor[i+2]);
		   assertEquals(subx,caux1.get(0)); 
		   assertEquals(suby,caux1.get(1));
		   assertNotSame(caux2, caux1);
		}
	}

	
	
	/* Se comprueba, para el método toString(), que las Coordinate creadas en el setUp() 
	 * tienen el formato correcto.
	 */
	@Test
	public void testToString() {
		assertEquals ("(0, 0)",lcoor.get(0).toString());
		assertEquals ("(0, -70)",lcoor.get(1).toString());
		assertEquals ("(-70, -2)",lcoor.get(2).toString());
		assertEquals ("(-2, 20)",lcoor.get(3).toString());
	}

	/* Se toma una Coordinate y se comprueba todas las posibles condiciones bajo 
	 * las cuales nuestra función equals() devuelve true o false
	 */
	@Test
	public void testEqualsObject() {
		Object obj = new String("(0, 0)");
		Coordinate c = lcoor.get(0);
		assertFalse(c.equals(null));
		assertFalse(c.equals(obj));
		assertFalse(c.equals(lcoor.get(1)));
		assertFalse(c.equals(new Coordinate2D(24, 0)));
		assertTrue (c.equals(c));
		Coordinate d = new Coordinate2D(0,0);
		assertTrue((c.equals(d)));
	}

}
