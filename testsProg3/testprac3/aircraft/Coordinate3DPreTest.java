package model.aircraft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.Coordinate;
import model.CoordinateFactory;
import model.ship.Coordinate2D;

public class Coordinate3DPreTest{
	
    List<int[]> vcoordinates = new ArrayList<int[]>();
    int []vcoor= {0,0,-70,-2,20,75}; //Para crear coordenadas
    final int DIM = vcoor.length;
    List<Coordinate> lcoor;
    
	@Before
	public void setUp() throws Exception {
		lcoor = new ArrayList<Coordinate>();
		//Se crean las Coordinate (0,0,-70),(0,-70, -2), (-70,-2,20),(-2,20,75);
				for (int i=0; i<DIM-2; i++) {
					lcoor.add(new Coordinate3D(vcoor[i],vcoor[i+1],vcoor[i+2]));
				}
		
	}
    /* Adapta los test de Coordinate2D (CoordinateP1Test y CoordinateP2Test) para Coordinate3D */
	
	//TODO
	@Test
	public void testHashCode() {
		//fail ("Realiza el test");
		Coordinate c1 = lcoor.get(2);
		Coordinate c2 = CoordinateFactory.createCoordinate(c1.get(0), c1.get(1), c1.get(2));
		/* Se comprueba que cuando dos Coordinate son iguales, el resultado 
		 * del hash ha de ser el mismo.
		 * Si los Coordinate son distintos el hash puede ser igual o no.
		 */
		assertEquals (c1,c2);
		assertEquals (c1.hashCode(), c2.hashCode());
	}

	//TODO
	/* Se comprueba que el Constructor funciona bien. Para ello se analiza que 
	 * las componentes '0', '1' y '2' de cada Coordinate3D creada en el setUp() son las 
	 * correctas.
	 */
	@Test
	public void testCoordinateConstructor() {
		//fail ("Realiza el test");
		int j=0;
		for (int i=0; i<DIM-2; i++) {			
			assertEquals("x",vcoor[i],lcoor.get(j).get(0));
			assertEquals("y",vcoor[i+1],lcoor.get(j).get(1));
			assertEquals("z",vcoor[i+2],lcoor.get(j).get(2));
			j++;
		}
	}

	//TODO
	/* Se comprueba que el constructor de copia crea una nueva Coordinate3D con
	 * los mismos valores que las componentes respectivas del Coordinate3D copiado.
	 * Y eso se hace para cada Coordinate3D creada en setUp();
	 */
	@Test
	public void testCoordinateConstructorCopy() {
		//fail ("Realiza el test");
		Coordinate ccopy;
		for (Coordinate caux: lcoor) {
			ccopy= ((Coordinate3D)caux);
			assertEquals(caux.get(0),ccopy.get(0));
			assertEquals(caux.get(1),ccopy.get(1));
			assertEquals(caux.get(2),ccopy.get(2));	
		}
	}

	//TODO
	/* Se comprueba que el método get(int) para cada componente de una Coordinate 
	 * funciona correctamente.
	 * Se modifican los valores de las componentes de la Coordinate anterior con 
	 * el método set(int, int) y se comprueba con get(int) que los valores de sus 
	 * componentes han cambiado a dichos valores.
	 * OBSERVACION: No podrás usar el método set de Coordinate, ANALIZA POR QUÉ NO PUEDES.
	 * Al final del fichero encontrarás una clase auxiliar con la que podrás modificar los
	 * valores.
	 */
	@Test
	public void testGetSet() {
		//fail ("Realiza el test");
		Coordinate c = lcoor.get(2);
		assertEquals("x==-70", -70, c.get(0));
		assertEquals("x==-2", -2, c.get(1));
		assertEquals("x==20", 20, c.get(2));
		//aRREGLAR ESTO
		Coordinate3DAux aux = new Coordinate3DAux((Coordinate3D)CoordinateFactory.createCoordinate(0,0,0));
		//Coordinate3DAux.set(0,-11); 
		aux.set(0, -11);
		aux.set(1, 33);
		aux.set(2, 4);
		c = aux;
		assertEquals("x==-11", -11, aux.get(0));
		assertEquals("x==33", 33, aux.get(1));
		assertEquals("x==4", 4, aux.get(2));
	}

	//TODO
	/* Se suman las Coordinate creadas en el setUp() y se comprueba, conforme 
	 * se van sumando, que los valores de sus componentes van tomando los 
	 * valores correctos y que el Coordinate3D que devuelve no es el mismo que
	 * el Coordinate3D que invoca al método.
	 */
	@Test  //{0,0,-70,-2,20, 75}
	public void testAdd() {
		//fail ("Realiza el test");
		Coordinate caux1 = lcoor.get(0);
		Coordinate caux2 = lcoor.get(1);
		Coordinate caux3 = CoordinateFactory.createCoordinate(2,3);
		
		Coordinate sumx = caux1.add(caux2);
		Coordinate sumy = caux1.add(CoordinateFactory.createCoordinate(5,2));
		Coordinate sumz = caux3.add(caux2);
		Coordinate x = CoordinateFactory.createCoordinate(0,-70,-72);
		Coordinate y = CoordinateFactory.createCoordinate(5,2,-70);
		Coordinate z = CoordinateFactory.createCoordinate(2,-67);
		
		assertEquals(sumx,x);
		assertEquals(sumy,y);
		assertEquals(sumz,z);
		assertNotSame(caux2, caux1);
	}

	//TODO
	/* Se van restando las Coordinate3D creadas en el setUp() y se comprueba, 
	 * conforme se van restando, que los valores de sus componentes van tomando 
	 * los valores correctos y que el Coordinate3D que devuelve no es el mismo que
	 * el Coordinate3D que invoca al método.
	 */
	@Test
	public void testSubtract() {
		//fail ("Realiza el test");
		Coordinate caux1 = lcoor.get(0);
		Coordinate caux2 = lcoor.get(1);
		Coordinate caux3 = CoordinateFactory.createCoordinate(2,3);
		
		Coordinate resx = caux1.subtract(caux2);
		Coordinate resy = caux1.subtract(CoordinateFactory.createCoordinate(5,2));
		Coordinate resz = caux3.subtract(caux2);
		Coordinate x = CoordinateFactory.createCoordinate(0,70,-68);
		Coordinate y = CoordinateFactory.createCoordinate(-5,-2,-70);
		Coordinate z = CoordinateFactory.createCoordinate(2,73);
		
		assertEquals(resx,x);
		assertEquals(resy,y);
		assertEquals(resz,z);
		assertNotSame(caux2, caux1);
	}
	
	//TODO
	/* Se comprueba, para el método toString(), que las Coordinate creadas en el setUp() 
	 * tienen el formato correcto.
	 */
	@Test
	public void testToString() {
		//fail ("Realiza el test");
		assertEquals ("(0, 0, -70)",lcoor.get(0).toString());
		assertEquals ("(0, -70, -2)",lcoor.get(1).toString());
		assertEquals ("(-70, -2, 20)",lcoor.get(2).toString());
		assertEquals ("(-2, 20, 75)",lcoor.get(3).toString());
		
	}
	
	//TODO
	/* Creamos copias de las Coordinates3D creadas en el setUp() y comprobamos que:
	 * 1 - La copia y el original no son la misma.
	 * 2 - La copia tiene los mismos valores en las componentes respectivas que el objeto copiado.
	 */
	@Test
	public void testCopy() {
		//fail ("Realiza el test");
		lcoor = new ArrayList<Coordinate>();
		for (Coordinate c : lcoor) {
			assertFalse(c.copy().equals(c));
			assertEquals(c.get(0), c.copy().get(0));
			assertEquals(c.get(1), c.copy().get(1));
			assertEquals(c.get(2), c.copy().get(2));
		}	
	}

	//TODO
	/* Se crea una Coordinate3D y a partir de ella se obtienen las Coordinate3D adyacentes 
	 * que se guardan en un Set<Coordinate>. Para cada una de las posiciones adyacentes
	 * a la Coordinate3D inicial se crea una Coordinate3D, y se va comprobando están
	 * contenidas en el Set.
	 *
	 */
	@Test
	public void testAdjacentCoordinates() {
		//fail ("Realiza el test");
		Coordinate c2 = CoordinateFactory.createCoordinate(5,3,2);
		Set<Coordinate> coord = c2.adjacentCoordinates();
		Set<Coordinate> adjs = new HashSet<Coordinate>();
		for (int ib = -1; ib <= 1; ib++) {
			for (int jb = -1; jb <= 1; jb++) {
				for (int kb = -1; kb <= 1; kb++) {
					if (ib == 0 && jb == 0 && kb == 0)	continue;
					else adjs.add(CoordinateFactory.createCoordinate(c2.get(0)+ib, c2.get(1)+jb, c2.get(2)+kb));
				}
			}
		}
		
		for (Coordinate c3 : adjs) {
			assertTrue(coord.contains(c3));
		}
		assertEquals(coord.size(), 27-1);
	}
	
	//TODO
	/* Se toma una Coordinate3D y se comprueba todas las posibles condiciones bajo 
	 * las cuales nuestra función equals() devuelve true o false
	 */
	@Test
	public void testEqualsObject() {
		//fail ("Realiza el test");
		Object obj = new String("(0, 0)");
		Coordinate c = CoordinateFactory.createCoordinate(lcoor.get(0).get(0),lcoor.get(0).get(1),lcoor.get(0).get(2));
		assertFalse(c.equals(null));
		assertFalse(c.equals(obj));
		assertFalse(c.equals(lcoor.get(1)));
		assertFalse(c.equals(CoordinateFactory.createCoordinate(24, 0, 3)));
		assertTrue (c.equals(c));
		Coordinate d = CoordinateFactory.createCoordinate(0,0,-70);
		assertTrue((c.equals(d)));
	}
	
/********************************************/
	//FUNCIÓN DE APOYO
		String removeSpaces (String str) {
			String exp[]=str.split(" ");
			String nstr=new String("");
			for (String s: exp) {
				if (! s.equals(" ") ) nstr+=s; 
			}
			return nstr;
		}

	//CLASE AUXILIAR PARA USAR set DE Coordinate
	class Coordinate3DAux extends Coordinate3D {
		protected Coordinate3DAux(Coordinate3D c) {
			super(c);
		}
		
		public void set(int component, int value) {
			super.set(component,value);
		}
		
		public int get(int component) {
			return super.get(component);
		}		
	}
}
