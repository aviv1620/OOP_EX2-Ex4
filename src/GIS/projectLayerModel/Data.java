package GIS.projectLayerModel;

import GIS.Meta_data;
import Geom.Point3D;
/**
 * store time,string and orientation.
 * @author aviv vexler
 */
public class Data implements Meta_data {
	private Point3D orientation;
	private long utc;
	private String data;
	
	/**
	 * store orientation, UTC and data.
	 * @param orientation - null
	 * @param utc - time
	 * @param data - String representing this data
	 */
	public Data(Point3D orientation, long utc,String data) {
		this.orientation = orientation;
		this.utc = utc;
		this.data = data;
	}

	/**
	 * @see Meta_data#getUTC().
	 */
	@Override
	public long getUTC() {
		return utc;
	}

	/**
	 * @see Meta_data#get_Orientation().
	 */
	@Override
	public Point3D get_Orientation() {
		return orientation;
	}

	/**
	 * @see Meta_data#toString().
	 */
	@Override
	public String toString() {
		return data;
	}
	
	//a-v-i-v-v-e-x-l-e-r

}
