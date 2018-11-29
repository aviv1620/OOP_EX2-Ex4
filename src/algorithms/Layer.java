package algorithms;

import java.util.ArrayList;
import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.Meta_data;

/**
 * @author Aviv Vexler
 *store name of file and element list.
 */
public class Layer extends ArrayList< GIS_element > implements GIS_layer {
	private String fileName;
	
	public Layer(String fileName) {
		this.fileName = fileName;
	}

	/**  java requires me */
	private static final long serialVersionUID = 1L;

	/** 
	 * @see GIS.GIS_layer#get_Meta_data()
	 *  time to first element and name of file.*/
	@Override
	public Meta_data get_Meta_data() {
		//time
		long utc = get(0).getData().getUTC();
		
		//return
		Data data = new Data(null,utc , fileName);
		return data;
	}

}
