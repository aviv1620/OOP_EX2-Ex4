/**
 * 
 */
package GIS.packmanModel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Geom.Point3D;

/**
 * @author avivasus
 *
 */
class TestDataStructure {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGameAndPatch() {



		Game game = simpleGame();;


		String expected = "Game [packmens=[Packman [id=0, location=32.1045513,35.2035022,10.0, angale=0.0, speed=1, radius=1], Packman [id=1, location=32.10451344,35.21019738,0.0, angale=0.0, speed=1, radius=1]], fruits=[Fruit [location=32.10462702,35.20573393,10.0, Weight=10, id=0]]]";
		String actual = game.toString();
		System.out.println(actual);
		assertEquals(expected, actual);

	/*	//patch
		Patch patch1 = simplePatch();
		expected = "Patch [utc=1544183030947, location=32.10451344,35.21019738,0.0]";
		actual = patch1.toString();
		System.out.println(actual);
//		assertEquals(expected, actual);
*/


	}

//	public static Patch simplePatch() {
//		
//		/*Point3D loacation = new Point3D(32.10451344,35.21019738,0);
//		Patch patch = new Patch(1544183030947l, loacation);
//		return patch;*/
//	}

	public static Game simpleGame() {
		//game
		Point3D loacationPackmen1 = new Point3D(32.1045513,35.2035022,10);
		Point3D loacationPackmen2 = new Point3D(32.10451344,35.21019738,0);
		Point3D loacationFruit1 = new Point3D(32.10462702,35.20573393,10);

		Packman packman1 = new Packman(0,loacationPackmen1 , 0, 1, 1);
		Packman packman2 = new Packman(1,loacationPackmen2 , 0, 1, 1);
		Fruit fruit1 = new Fruit(2,loacationFruit1, 10);


		Game game = new Game();
		game.addPackman(packman1);
		game.addPackman(packman2);
		game.addFruit(fruit1);
		return game;
	}

}
