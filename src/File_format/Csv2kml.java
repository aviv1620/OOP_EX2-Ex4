package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import File_format.kmlclass.*;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.projectLayerModel.Data;
import GIS.projectLayerModel.Element;
import GIS.projectLayerModel.Layer;
import Geom.Point3D;
/**
 * convert csv file to kml file
 * @author aviv vexler
 *
 */
public class Csv2kml {
	/**time format to Date class*/
	private static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static void main(String[] args) throws JAXBException, IOException, ParseException {
		convert("WigleWifi_20171203085618.csv");
	}

	/**convert csv to kml
	 * @param csvFile - file patch.
	 * @throws JAXBException - This is the root exception class for all JAXB exceptions.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred
	 * @throws ParseException - Signals that an error has been reached unexpectedlywhile parsing.
	 */
	private static void convert(String csvFile) throws JAXBException, IOException, ParseException {
		//same name with .kml
		String fileName = csvFile.substring(0, csvFile.length() - 4) + ".kml" ;

		//Layer
		Layer layer = parseElements(csvFile);
		Folder folder = kmlFolderCreate(layer,csvFile);

		//add one folder(no more).
		ArrayList< Folder > folders = new ArrayList< Folder >();
		folders.add(folder);

		//write
		writeKmlFile(folders, fileName);

	}

	/**parse csv to GIS_Layer.
	 * Element implements GIS_element.
	 * 
	 * @param csvFile - file patch.
	 * @return - Layer of elements.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred.
	 * @throws ParseException - Signals that an error has been reached unexpectedlywhile parsing.
	 */
	public static Layer parseElements(String csvFile) throws IOException, ParseException {
		Layer elements = new Layer(csvFile);
		String line = "";
		String cvsSplitBy = ",";

		//read csv
		BufferedReader br = new BufferedReader(new FileReader(csvFile));

		br.readLine(); //skip device data.
		br.readLine(); //skip colom data.

		while ((line = br.readLine()) != null) 
		{
			/*index by csv
			 * userInfo[6] - CurrentLatitude
			 * userInfo[7] - CurrentLongitude
			 * userInfo[8] - AltitudeMeters
			 * userInfo[3] - FirstSeen
			 */
			String[] userInfo = line.split(cvsSplitBy);

			//geom
			double lat = Double.parseDouble( userInfo[6] );
			double lon = Double.parseDouble( userInfo[7] );
			double AltitudeMeters = Double.parseDouble( userInfo[7] );

			Point3D geom = new Point3D(lon ,lat,AltitudeMeters);

			//utc a-v-i-v-v-e-x-l-e-r
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");						
			Date utc = simpleDateFormat.parse(userInfo[3]);

			//add to element
			Data data = new Data(null, utc.getTime(), line);
			elements.add( new Element(geom, data) );
		}

		br.close();
		return elements;
	}

	/**
	 * write file
	 * @param kml - kml
	 * @param fileName - file name
	 * @throws JAXBException
	 */
	private static void kmlToFile(Kml kml,String fileName) throws JAXBException {
		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(Kml.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to File
		m.marshal(kml, new File(fileName));
	}

	/**
	 * write kml file
	 * @param folders - folder(kml)
	 * @param fileName - file name
	 * @throws JAXBException
	 */
	public static void writeKmlFile(ArrayList< Folder > folders,String fileName) throws JAXBException {
		Kml kml = kmlCreate(folders);
		kmlToFile(kml,fileName);
	}
	/**
	 * create kml class
	 * @param folders - array of folders
	 * @return
	 */
	private static Kml kmlCreate(ArrayList< Folder > folders) {
		Style style[] = kmlStyleCreate();
		Document document = new Document(style,folders);
		return new Kml(document,"http://www.opengis.net/kml/2.2");
	}

	/**
	 * style in color.
	 * @return Style(kml)
	 */
	private static Style[] kmlStyleCreate() {
		Icon icon1 = new Icon("http://maps.google.com/mapfiles/ms/icons/red-dot.png");
		IconStyle iconStyle1 = new IconStyle(icon1);
		Style style1 = new Style("red", iconStyle1);
		Icon icon2 = new Icon("http://maps.google.com/mapfiles/ms/icons/yellow-dot.png");
		IconStyle iconStyle2 = new IconStyle(icon2);
		Style style2 = new Style("yellow", iconStyle2);
		Icon icon3 = new Icon("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
		IconStyle iconStyle3 = new IconStyle(icon3);
		Style style3 = new Style("green", iconStyle3);	

		Style style[] = {style1,style2,style3};
		return style;
	}

	/**convert GIS_Layer to Placemarks(kml) in kml and return Folder(kml).
	 * 
	 * @param csvFile - file patch.
	 * @return placemarks - Placemarks XmlRootElement.
	 * @throws IOException - Signals that an I/O exception of some sort has occurred. 
	 * @throws ParseException - Signals that an error has been reached unexpected while parsing.
	 */
	public static Folder kmlFolderCreate(GIS_layer layer,String folderName) throws IOException, ParseException {

		ArrayList< Placemark > placemarks = new ArrayList<>();
		Iterator<GIS_element> element = layer.iterator();

		//to all element.
		while(element.hasNext()) {
			String cvsSplitBy = ",";
			GIS_element next = element.next();
			String line = next.getData().toString();
			/*index by csv
			 * userInfo[1] - SSID;
			 * userInfo[2] - AuthMode
			 * userInfo[6] - CurrentLatitude
			 * userInfo[7] - CurrentLongitude*/
			String[] userInfo = line.split(cvsSplitBy);

			//color green=open wifi, yellow=gsm, red=else.
			String styleUrl;

			if(userInfo[2].equals("[ESS][BLE]"))
				styleUrl = "#green";
			else if(userInfo[2].equals("UNKNOWN;il"))
				styleUrl = "#yellow";
			else
				styleUrl = "#red";


			//name
			String name = userInfo[1];

			//description
			String description = "";
			for (int i = 0; i < userInfo.length; i++) {
				description += userInfo[i] + "<br/>";
			}

			//geom
			double lat = Double.parseDouble( userInfo[6] );
			double lon = Double.parseDouble( userInfo[7] );
			Point point = new Point(lon+","+lat);

			//time
			SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);        
			Date date = new Date(next.getData().getUTC());   
			String TimeStamp = formatter.format(date);
			TimeStamp += "Z";

			TimeStamp timeStamp2 = new TimeStamp(TimeStamp);

			//add
			Placemark placemark = new Placemark(name, description, point, styleUrl,timeStamp2);
			placemarks.add(placemark);
		}

		//folder.
		Folder folder = new Folder(placemarks,folderName);
		return folder;
	}



}
