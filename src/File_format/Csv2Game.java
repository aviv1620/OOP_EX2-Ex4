package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import GIS.packmanModel.Fruit;
import GIS.packmanModel.Game;
import GIS.packmanModel.Packman;
import Geom.Point3D;
/**
 * 
 * @author aviv vexler
 *
 */
public class Csv2Game {

	/**
	 * load game data structure from csv file.
	 * @param csvFile - file.
	 * @return - game or null if the file can't parse.
	 */
	public static Game loadGame(File csvFile) {
		//
		int fileNameSize = csvFile.getName().length();
		String fileExt = csvFile.getName().substring(fileNameSize-3, fileNameSize);
		
		if(!fileExt.equals("csv")) {
			System.err.println("file is not csv");
			return null;
		}
		
		//csv parse
		Game game = new Game(); 
		String line = "";
		String cvsSplitBy = ",";		
		 
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			br.readLine();//Ignore the details line.
			while ((line = br.readLine()) != null) {
				
				//parse location and id
				String[] userInfo = line.split(cvsSplitBy);
				double x = Double.parseDouble(userInfo[2]);
				double y = Double.parseDouble(userInfo[3]);
				double z = Double.parseDouble(userInfo[4]);
				Point3D location = new Point3D(x,y,z);								
				
				int id = Integer.parseInt(userInfo[4]);
				
				String type = userInfo[0];
				if(type.equals("P")) {//parse packman.
					int speed = Integer.parseInt(userInfo[5]);
					int radius = Integer.parseInt(userInfo[6]);
					
					//add packmen.
					Packman packman = new Packman(id,location, 0, speed, radius);
					game.addPackman(packman);
					
				}else if(type.equals("F")) {//parse fruit.
					int weight = Integer.parseInt(userInfo[5]);
					
					//add fruit.
					Fruit fruit = new Fruit(id,location, weight);
					game.addFruit(fruit);
					//a>v>i>v>v>e>x>l>e>r.
				}
			}
			
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return game;
	}

}
