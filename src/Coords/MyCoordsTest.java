/**
 * 
 */
package Coords;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Geom.Point3D;

/**
 * test MyCoords class.
 * @author Aviv Vexler
 *
 */
class MyCoordsTest {
		
	private Point3D p0 = new Point3D(32.103315,35.209039,670);
	private Point3D p1 = new Point3D(32.106352,35.205225,650);
	private Point3D metar = new Point3D(337.6989921,-359.2492069,-20);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		myCoords = new MyCoords();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	private static MyCoords myCoords;
	private static final double DELTA = 10;
	
	/**Compare 2 Point3D whit Approximation delta.
	 * @param expected - some Point3D.
	 * @param actual - some Point3D.*/
	private void assertEqualsPoint3DDelta(Point3D expected,Point3D actual) {
		assertEquals(expected.x(), actual.x(),DELTA);
		assertEquals(expected.y(), actual.y(),DELTA);
		assertEquals(expected.z(), actual.z(),DELTA);
	}
	
	/**
	 * Test method for {@link Coords.MyCoords#add(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	void testAdd() {
		Point3D expected = p0;
		Point3D actual = myCoords.add(p1, metar);
		assertEqualsPoint3DDelta(expected, actual);		
	}

	/**
	 * Test method for {@link Coords.MyCoords#distance3d(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	void testDistance3d() {
		double expected = 493.0523318;
		double actual= myCoords.distance3d(p0, p1);
		assertEquals(expected, actual,DELTA);
	}

	/**
	 * Test method for {@link Coords.MyCoords#vector3D(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	void testVector3D() {
		Point3D expected = new Point3D(32.1048335,35.207132,660);
		Point3D actual = myCoords.vector3D(p0, p1);
		assertEqualsPoint3DDelta(expected, actual);
	}

	/**
	 * Test method for {@link Coords.MyCoords#azimuth_elevation_dist(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	void testAzimuth_elevation_dist1() {
		double []expected = {313.2299449,-2.324763517,493.0523318};
		double []actual = myCoords.azimuth_elevation_dist(p0, p1);
		assertArrayEquals(expected, actual,DELTA);
	}
	
	/**
	 * Test method for {@link Coords.MyCoords#azimuth_elevation_dist(Geom.Point3D, Geom.Point3D)}.
	 * other point.
	 */
	@Test
	void testAzimuth_elevation_dist2() {
		Point3D p0 = new Point3D(32.111,33.111,100);
		Point3D p1 = new Point3D(32.1111,33.1111,90);
		
		double []expected = {39.7183,-34.4787,18};
		double []actual = myCoords.azimuth_elevation_dist(p0, p1);
		assertArrayEquals(expected, actual,DELTA);
	}
	
	/**
	 * Test method for {@link Coords.MyCoords#isValid_GPS_Point(Geom.Point3D)}.
	 */
	@Test
	void testIsValid_GPS_Point() {
		Point3D noValid = new Point3D(32.106352,35.205225,-650);
		assertTrue(myCoords.isValid_GPS_Point(p0));
		assertFalse(myCoords.isValid_GPS_Point(noValid));
	}

	//a-v-i-v-v-e-x-l-e-r
}
