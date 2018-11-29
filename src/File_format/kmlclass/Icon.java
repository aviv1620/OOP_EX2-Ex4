package File_format.kmlclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Icon {
	private String href;

	public Icon(String href) {
		super();
		this.href = href;
	}

	public Icon() {
		super();
	}

	@XmlElement(name = "href")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}
