package File_format.kmlclass;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Kml {
	private Document document;
	private String xmlns;
	
	public Kml() {
		
	}


	public Kml(Document document, String xmlns) {
		super();
		this.document = document;
		this.xmlns = xmlns;
	}

	@XmlAttribute
	public String getXmlns() {
		return xmlns;
	}



	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}



	@XmlElement(name = "Document")
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	
}
