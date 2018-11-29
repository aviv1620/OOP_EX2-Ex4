package File_format.kmlclass;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Folder {
	private ArrayList< Placemark > placemark;
	private String name;

	public Folder() {
		super();
	}

	public Folder(ArrayList< Placemark > placemark,String name) {
		this.placemark = placemark;
		this.name = name;
	}
	
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "Placemark")
	public ArrayList< Placemark > getPlacemark() {
		return placemark;
	}

	public void setPlacemark(ArrayList< Placemark > placemark) {
		this.placemark = placemark;
	}

}
