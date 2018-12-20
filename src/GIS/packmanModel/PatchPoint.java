package GIS.packmanModel;

import Geom.Point3D;
/**
 * point in patch.
 * @author Aviv Vexler
 *
 */
public class PatchPoint {
	private Point3D location;
	private int time;
	private int weight;
	
	public PatchPoint(Point3D location, int time,int weight) {
		this.location = location;
		this.time = time;
		this.weight = weight;
	}
	/**
	 * @return the location
	 */
	public Point3D getLocation() {
		return location;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * 
	 * @return weight
	 */
	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "PatchPoint [location=" + location + ", time=" + time + "]";
	}
	
	
	
}
