package GIS.packmanModel;

import Geom.Point3D;

public class Fruit {
	public Point3D location;
	public int Weight;
	public final int id;
	
	
	public Fruit(int id,Point3D location, int weight) {
		super();
		this.location = location;
		Weight = weight;
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fruit [location=" + location + ", Weight=" + Weight + ", id=" + id + "]";
	}
	
	
}
