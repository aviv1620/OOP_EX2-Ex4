package File_format.kmlclass;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Document {
	private Style []style;
	private ArrayList<Folder> folder;
	
	
	
	@XmlElement(name = "Style")
	public Style[] getStyle() {
		return style;
	}

	public void setStyle(Style[] style) {
		this.style = style;
	}
	
	@XmlElement(name = "Folder")
	public ArrayList<Folder> getFolder() {
		return folder;
	}

	public void setFolder(ArrayList<Folder> folder) {
		this.folder = folder;
	}

	public Document() {
		
	}

	public Document(Style[] style, ArrayList<Folder> folder) {
		super();
		this.style = style;
		this.folder = folder;
	}


	
	

	

}
