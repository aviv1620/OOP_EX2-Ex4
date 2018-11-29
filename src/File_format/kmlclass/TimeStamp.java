package File_format.kmlclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimeStamp {

	private String when;

	@XmlElement(name = "when")
	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public TimeStamp() {
		super();
	}

	public TimeStamp(String when) {
		super();
		this.when = when;
	}
}
