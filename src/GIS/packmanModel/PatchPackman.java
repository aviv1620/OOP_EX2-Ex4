/**
 * 
 */
package GIS.packmanModel;

import java.util.ArrayList;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * @author avivasus
 *
 */
public class PatchPackman {
	public ArrayList<PatchPoint> patchPoints = new ArrayList<PatchPoint>();

	public Point3D cournetLocation;
	
	private MyCoords myCoords = new MyCoords();
	private Packman packmanPointer;
	
	private long TotalTime = 0;
	
	public PatchPackman(Packman packmanPointer) {
		this.cournetLocation = packmanPointer.location;
		this.packmanPointer = packmanPointer;
		
		//packman it self is the first point.
		PatchPoint patchPoint = new PatchPoint(cournetLocation, TotalTime);
		patchPoints.add(patchPoint);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PatchPackman [patchPoints=" + patchPoints + "]";
	}



	/**
	 * 
	 * @param fruit
	 * @return - time to go to new point.
	 */
	public long add(Point3D newLocation) {
		//calculate time
		double distance = myCoords.distance3d(cournetLocation, newLocation);
		long time = (long) (distance/packmanPointer.speed);
		
		//add to total
		TotalTime += time;
		
		//add point
		PatchPoint patchPoint = new PatchPoint(newLocation, TotalTime);
		patchPoints.add(patchPoint);
		
		//now have new corrnet location.
		cournetLocation = newLocation;
		
		return time;
	}



	/**
	 * @return the totalTime
	 */
	public long getTotalTime() {
		return TotalTime;
	}
	
	
	
	
}
