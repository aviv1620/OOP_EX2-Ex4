package algorithms;

import java.util.ArrayList;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.Meta_data;

public class Project extends ArrayList<GIS_layer> implements GIS_project {

	/**  java requires me */
	private static final long serialVersionUID = 1L;

	/**
	 * @see GIS.GIS_project#get_Meta_data()
	 * time to first element and string whith any data in aby layer.
	 */
	@Override
	public Meta_data get_Meta_data() {
		//time
		long utc = get(0).get_Meta_data().getUTC();
		
		//string for any Meta_data an any layer.
		String str = "";
		int i = 1;
		for(GIS_layer layer:this) {
			str += "layer " + i +" " + layer.get_Meta_data().toString() + "\n";
			i++;
		}
		
		//return
		Data data = new Data(null,utc ,str);
		return data;
	}

}
