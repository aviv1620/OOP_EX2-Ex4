package File_format;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

import File_format.kmlclass.*;
import GIS.packmanModel.Patch;
import GIS.packmanModel.PatchPackman;
import GIS.packmanModel.PatchPoint;
/**
 * Save patch moment in KML file.
 * Possible to see in google earth timeline.
 * @author Aviv Vexler
 * ms=seconds in my kml.
 */
public class Path2KML {
	
	/**time format to Date class*/
	private static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	/**
	 * seve file.
	 * @param patch - patch
	 * @param fileDictory dictory to csv file. Change the extension to kml and dsace file.
	 */
	public static void save(Patch patch,String fileDictory) {
		ArrayList<Folder> folders = new ArrayList<>(); 
		for(int pi=0;pi < patch.getNumPackmens();pi++) {
			PatchPackman patchPackman =  patch.getPatchPackman(pi);
			ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
			
			for(int pointI=0;pointI < patchPackman.getSize();pointI++) {
				PatchPoint patchPoint = patchPackman.get(pointI);
				
				//#kml
				//Point
				String coordinates = patchPoint.getLocation().y() + "," + patchPoint.getLocation().x();
				Point kmlPoint = new Point(coordinates);
				
				//time
				SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);        
				Date date = new Date(patchPoint.getTime()*1000);//ms to seconds
				String when = formatter.format(date);				
				TimeStamp timeStamp = new TimeStamp(when);
				
				//Placemark
				String name = "fruit index: "+pointI;
				String description = "aviv vexler";	
				String styleUrl = getRandomStyleUrl(pi);
				Placemark placemark = new Placemark(name, description, kmlPoint, styleUrl, timeStamp);				
				
				//add placemark to folder
				placemarks.add(placemark);
			}
			
			folders.add( new Folder(placemarks, "patch packmen index: "+pi) );
		}
		
		//write kml.				
		try {
			Csv2kml.writeKmlFile(folders, fileDictory);
			System.out.println("patch save in: "+fileDictory);
		} catch (JAXBException e) {			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * return some color.same color to same index.
	 * @param i index
	 * @return style URL.
	 */
	private static String getRandomStyleUrl(int i) {
		switch (i % 3) {
		case 1:
			return "#yellow";
		case 2:
			return "#green";
		default:
			return "#red";
			
		}
	}
}
