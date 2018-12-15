package GIS.packmanModel;

import Geom.Point3D;

public class PatchPoint {
	private Point3D location;
	private long time;
	public PatchPoint(Point3D location, long time) {
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
	public long getTime() {
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
