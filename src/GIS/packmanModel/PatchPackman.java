/**
 * 
 */
package GIS.packmanModel;

import java.util.ArrayList;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * @author avivasus
 * route for packman.
 * store list point on the map in start PatchPoint.
 * data structure logic is stack.the totalTime calculate in this stack.
 * another programmer can't interrupt the total time just get it. 
 *
 */
public class PatchPackman {
	private ArrayList<PatchPoint> patchPoints = new ArrayList<PatchPoint>();

	/** lest point */
	public Point3D cournetLocation;
	
	private MyCoords myCoords = new MyCoords();
	private Packman packmanPointer;
	
	private int TotalTime = 0;
	
	/**
	 * @param packmanPointer first point is packman it self.
	 */
	public PatchPackman(Packman packmanPointer) {
		this.cournetLocation = packmanPointer.location;
		this.packmanPointer = packmanPointer;
		
		//packman it self is the first point.
		PatchPoint patchPoint = new PatchPoint(cournetLocation, TotalTime);
		patchPoints.add(patchPoint);
	}

	/**
	 * like push in stack data structure add new point.
	 * calculate the time and cournet location is the lest point.
	 * @param fruit
	 * @return - time to go to new point.
	 */
	public long push(Point3D newLocation) {
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
	 * like pop in stack data structure remove an element from the stack
	 * and return it.
	 * @return the point that remove or null if the stack is empty.
	 */
	public PatchPoint pop() {
		
		if(getSize() == 0)
			return null;
		
		PatchPoint removeP =  patchPoints.get(getSize() - 1);
		
		//now is empty
		if(getSize() == 1) {
			TotalTime = 0;
			cournetLocation = null;
			patchPoints.remove(removeP);
			return removeP;
		}
		
		
		PatchPoint cournetP =  patchPoints.get(getSize() - 2);
		//time 
		TotalTime = cournetP.getTime();
		patchPoints.remove(removeP);
		
		//now have new corrnet location.
		cournetLocation = cournetP.getLocation();
				
		return removeP;
	}
	
	/**
	 * like top in stack data structure get the top point in the stack.
	 * @return top PatchPoint.
	 */
	public PatchPoint top() {
		if(getSize() == 0)
			return null;
		
		return patchPoints.get(getSize() - 1);
	}

	@Override
	public String toString() {
		return "\nPatchPackman [TotalTime=" + TotalTime + ", packmanPointer=" + packmanPointer + "]";
	}

	/**
	 * @return the totalTime
	 */
	public int getTotalTime() {
		return TotalTime;
	}
	
	/**
	 * 
	 * @return packman speed.-1 if not set pointer.
	 */
	public int getSpeed() {
		if(packmanPointer == null)
			return -1;
		else
			return packmanPointer.speed;
	}
	
	/**
	 * get specific point in the patch.
	 * @param index - index point.
	 * @return - the point.
	 */
	public PatchPoint get(int index) {
		return patchPoints.get(index);
	}
	
	/**
	 * 
	 * @return count point.
	 */
	public int getSize() {
		return patchPoints.size();
	}
	
	
	
}
