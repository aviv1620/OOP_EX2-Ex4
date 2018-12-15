package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import Coords.MyCoords;
import GIS.packmanModel.Fruit;
import GIS.packmanModel.Game;
import GIS.packmanModel.Packman;
import GIS.packmanModel.PatchA;
import GIS.packmanModel.PatchPackman;
import Geom.Point3D;
/**
 * 
 * @author aviv vexler
 *
 */
public class ShortestPathAlgo {
	private MyCoords myCoords = new MyCoords();
	private Game game;
	private long timeToComplete;
	//private ArrayList<ArrayList<Patch>> patchPackmans;
	private PatchA patch;


	public ShortestPathAlgo(Game game) {
		this.game = game;
		runNaiveAlgorithms();
	}

	/**
	 * jump to packman and go to closer fruit.
	 */
	private void runNaiveAlgorithms() {
		//make empty patch data structure and copy the fruit.
		patch = new PatchA(game);		
		ArrayList<Fruit> copyFruit = game.CopyPointerFruit();
		
		//while have fruit in the array.
		while(copyFruit.size() > 0) {
			//give to packman.
			for(int pi=0;pi < patch.getNumPackmens() && copyFruit.size() > 0;pi++) {//pi = pacmenIndex
				PatchPackman patchPackman = patch.getPatchPackman(pi);
				Point3D cournetLocation = patchPackman.cournetLocation;
				//shorted
				shorted(copyFruit, cournetLocation);				
				//insert first
				patchPackman.add(copyFruit.get(0).location);
				//timeToComplete += time;//FIXME bed
				//remove from array
				copyFruit.remove(0);
			}
		}

		//time
		for(int pi=0;pi < patch.getNumPackmens();pi++) {
			PatchPackman patchPackman = patch.getPatchPackman(pi);			
			timeToComplete = Math.max(timeToComplete, patchPackman.getTotalTime());
		}

	}



	/**
	 * @return the patch
	 */
	public PatchA getPatch() {
		return patch;
	}

	/**
	 * @param patch the patch to set
aviv */
	public void setPatch(PatchA patch) {
		this.patch = patch;
	}

	/**
	 * shorted by distance to location.
	 * @param arrayFruit - arrayList of Fruit.
	 * @param location - location
vexle*/
	private void shorted(ArrayList<Fruit> arrayFruit,Point3D location) {
		arrayFruit.sort(new Comparator<Fruit>() {

			@Override
			public int compare(Fruit f1, Fruit f2) {
				double distanceF1 = myCoords.distance3d(location, f1.location);
				double distanceF2 = myCoords.distance3d(location, f2.location);

				if(distanceF1 - distanceF2 < 0) {
					return -1;
				}else if(distanceF1 - distanceF2 > 0){
					return 1;
				}else {
					return 0;
				}
			}
		});
	}



	/**
	 * @return the timeToComplete
	 */
	public long getTimeToComplete() {
		return timeToComplete;
	}





}
