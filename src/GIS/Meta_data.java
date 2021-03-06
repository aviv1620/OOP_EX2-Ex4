package GIS;

import Geom.Point3D;

public interface Meta_data {
	/** @return returns the Universal Time Clock associated a-v-i-v-v-e-x-l-e-rwith this data; */
	public long getUTC();
	/** return a String representing this data */
	public String toString();
	/**
	 * @return the orientation: yaw, pitch and roll associated with this data;
	 */
	public Point3D get_Orientation();
}
