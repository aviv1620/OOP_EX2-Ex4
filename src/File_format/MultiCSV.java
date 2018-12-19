package File_format;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import File_format.kmlclass.Folder;
import GIS.GIS_layer;
import GIS.Meta_data;
import GIS.projectLayerModel.Layer;
import GIS.projectLayerModel.Project;

/**
 * Get folder name.
 * Scan Recursive and fine any csv file.
 * Build layer to any csv file.
 * Build project store all the layers.
 * 
 * Have also method to save it to kml.
 * 
 * @author aviv vexler.
 *
 */
public class MultiCSV {

	private Project project;
	private String pathFolder;

	/**@param pathFolder - folder dictionary*/
	public MultiCSV(String pathFolder) {
		this.pathFolder = pathFolder;
		project = new Project();
		load();
	}
	
	/**load csv file to project.*/
	private void load() {
		project = new Project();
		File folder = new File(pathFolder);
		
		//error if is not directory
		if(!folder.isDirectory())
			System.err.println("path is not directory");
		
		//Scan.
		File []files = folder.listFiles();
		recursiveScan(files, 0);
		
	}

	/**Scan all the file in folder.
	 * @param files - array of files.
	 * @param i - index.
	 */
	private void recursiveScan(File []files,int i) {
		//not have more file
		if(i >= files.length)
			return;
		
		//name file
		String fileName = files[i].getName();
		String format = fileName.substring(fileName.length()-3);
		
		//if is csv file parse it.
		if(format.equals("csv")) {
			try {
				Layer layer = Csv2kml.parseElements(files[i].getPath());
				project.add(layer);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		recursiveScan(files, ++i);
	}

	/**
	 * Save project to kml file.
	 * Possible to open this in Google Earth.
	 * hHave time line and color.
	 * 
	 * @throws IOException - Signals that an I/O exception of some sort has occurred.
	 * @throws ParseException - Signals that an error has been reached unexpectedly while parsing.
	 * @throws JAXBException - This is the root exception class for all JAXB exceptions.
	 */
	public void savekml() throws IOException, ParseException, JAXBException {
		
		
		//convert layer to folder.
		ArrayList< Folder > folders = new ArrayList< Folder >();		
		Iterator< GIS_layer > iterator = project.iterator();
		
		//every layer is one folder
		while( iterator.hasNext() ) {
			GIS_layer next = iterator.next();
			String foldarName = next.get_Meta_data().toString();
			folders.add( Csv2kml.kmlFolderCreate(next,foldarName) );						
		}
		
		//save to file
		String fileName = pathFolder + "/kmlAvivProject.kml";
		Csv2kml.writeKmlFile(folders, fileName);
	}
	
	
	/** @return project*/
	public Project getProject() {
		return project;
	}

	/**test this class
	 * @param args - not use
	 * @throws IOException - Signals that an I/O exception of some sort has occurred.
	 * @throws ParseException - Signals that an error has been reached unexpectedlywhile parsing.
	 * @throws JAXBException - This is the root exception class for all JAXB exceptions.*/

	public static void main(String[] args) throws IOException, ParseException, JAXBException {
		
		MultiCSV multiCSV = new MultiCSV("csvs");
		
		//save
		multiCSV.savekml();
		System.out.println("kml save in csvs/kmlAvivProject.kml");
		
		//test meta_data  a-v-i-v-v-e-x-l-e-r
		Meta_data projectData = multiCSV.getProject().get_Meta_data();
		Meta_data layerData =   multiCSV.getProject().get(0).get_Meta_data();
		Meta_data elementData = multiCSV.getProject().get(0).iterator().next().getData();
		
		System.out.println("\nproject meta_data:\n"+projectData);
		System.out.println("layer data meta_data:\n"+layerData);
		System.out.println("\nelement meta_data:\n"+elementData);
	}

}
