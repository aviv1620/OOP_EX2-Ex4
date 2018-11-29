package File_format.kmlclass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Style {
	private String id;
	private IconStyle iconStyle;
	public Style(String id, IconStyle iconStyle) {
		super();
		this.id = id;
		this.iconStyle = iconStyle;
	}
	public Style() {
		super();
	}
	@XmlAttribute
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlElement(name = "IconStyle")
	public IconStyle getIconStyle() {
		return iconStyle;
	}
	public void setIconStyle(IconStyle iconStyle) {
		this.iconStyle = iconStyle;
	}
	
	
}
