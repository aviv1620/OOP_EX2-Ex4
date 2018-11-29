package File_format.kmlclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IconStyle {
	private Icon icon;

	public IconStyle(Icon icon) {
		super();
		this.icon = icon;
	}

	public IconStyle() {
		super();
	}

	@XmlElement(name = "Icon")
	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	
}
