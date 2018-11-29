package File_format.kmlclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Point {
	private String coordinates;

	public Point() {
		super();
	}

	public Point(String coordinates) {
		this.coordinates = coordinates;
	}

	@XmlElement(name = "coordinates")
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
}
