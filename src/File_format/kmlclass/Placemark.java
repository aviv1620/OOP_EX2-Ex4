package File_format.kmlclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Placemark {
	private String name;
	private String description;
	private Point point;
	private String styleUrl;
	private TimeStamp timeStamp;
	
	public Placemark(String name, String description, Point point,String styleUrl,TimeStamp timeStamp) {
		this.name = name;
		this.description = description;
		this.point = point;
		this.styleUrl = styleUrl;
		this.timeStamp = timeStamp;
	}
	
	public Placemark() {
	}
	
	
	@XmlElement(name = "TimeStamp")
	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@XmlElement(name = "styleUrl")
	public String getStyleUrl() {
		return styleUrl;
	}

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "Point")
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
}
