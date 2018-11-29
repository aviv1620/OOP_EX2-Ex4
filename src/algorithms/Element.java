package algorithms;

import Coords.MyCoords;
import GIS.GIS_element;
import GIS.Meta_data;
import Geom.Geom_element;
import Geom.Point3D;
/**
 * store geom and data.
 * @author aviv vexler
 */
public class Element implements GIS_element {
	private Point3D geom;
	private Data data;
	
	public Element(Point3D geom, Data data) {
		this.geom = geom;
		this.data = data;
	}

	/** return geom*/
	@Override
	public Geom_element getGeom() {
		return geom;
	}

	/** return meta_data*/
	@Override
	public Meta_data getData() {
		return data;
	}

	/** rcomputes a new point which is the gps point transformed by a 3D vector (in meters*/
	@Override
	public void translate(Point3D vec) {
		geom = new MyCoords().add(geom,vec);
		
	}

	@Override
	public String toString() {
		return "Element [geom=" + geom + ", data=" + data + "]";
	}
	
	

}
