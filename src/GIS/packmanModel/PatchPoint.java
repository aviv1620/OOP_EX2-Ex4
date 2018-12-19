package GIS.packmanModel;

import Geom.Point3D;

public class PatchPoint {
	private Point3D location;
	private int time;
	public PatchPoint(Point3D location, int time) {
		this.location = location;
		this.time = time;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PatchPoint [location=" + location + ", time=" + time + "]";
	}
	
	
	
}
