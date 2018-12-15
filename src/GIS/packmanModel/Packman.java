package GIS.packmanModel;

import Geom.Point3D;

public class Packman {
	
	
	public final int id;
	public Point3D location;
	public double angle;
	public int speed;
	public int radius;
	
	
	public Packman(int id,Point3D location, double angale, int speed, int radius) {
		super();
		this.id = id;
		this.location = location;
		this.angle = angale;
		this.speed = speed;
		this.radius = radius;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Packman [id=" + id + ", location=" + location + ", angale=" + angle + ", speed=" + speed + ", radius="
				+ radius + "]";
	}
	
	
	
}
