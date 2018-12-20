package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import Coords.MyCoords;
import GIS.packmanModel.Fruit;
import GIS.packmanModel.Game;
import GIS.packmanModel.Patch;
import GIS.packmanModel.PatchPackman;
import GIS.packmanModel.PatchPoint;
import Geom.Point3D;

/**
 * calculate the faster patch that packman to packmen.
 * @author aviv vexler*/
public class ShortestPathAlgo {

	private MyCoords myCoords = new MyCoords();
	private Game game;
	private long timeToComplete;	
	private Patch patch;

	/*
	 *in some case like in the csv that i get,
	 * i want the packmen not move more then some distance. 
	 */
	private boolean specificCases = true;
	private int ttl = 0;//time to leave

	private ComparatorFruit comFruit = new ComparatorFruit();
	//Comparator Patch Lest Fruit.
	private ComparatorPLF comPLF = new ComparatorPLF();


	/**
	 * get locatin and compare whith point closer to location
	 * @param f1 - point 1
	 * @param f2 - point 1
	 * @param location - location
	 * @return -1 if p2 closer to location,1 if p2 closer to location.0 if both have same distance.
	 */
	private int compareCloserPoint(Point3D f1, Point3D f2,Point3D location) {
		double distanceF1 = myCoords.distance3d(location, f1);
		double distanceF2 = myCoords.distance3d(location, f2);

		if(distanceF1 - distanceF2 < 0) {
			return -1;
		}else if(distanceF1 - distanceF2 > 0){
			return 1;
		}else {
			return 0;
		}
	}

	/**
	 * run the algorithms.
	 * @param game - game data structure.
	 */
	public ShortestPathAlgo(Game game) {
		this.game = game;
		runGreedyAlgorithm();

		runImproveAlgorithms();

		calculateTotalTime();
	}

	/**
	 * 
	 * after greedy algorithm some packmans
	 * finish the tour more fast then other packman.
	 * this improve algorithm balance the packmans tours.
	 * 
	 * before improve:<br>
	 * game_1543684662657.csv run in: 301 ms:<br>
	 * game_1543685769754.csv run in: 861 ms:<br>
	 * game_1543693822377.csv run in: 644 ms:<br>
	 * game_1543693911932.csv run in: 643 ms:<br>
	 * game_1543693911932_a.csv run in: <b>643 ms</b>:<br>
	 * game_1543693911932_b.csv run in: 320 ms:<br>
	 *  
	 * game_1543684662657.csv run in: 301 ms:<br>
	 * game_1543685769754.csv run in: <b>600 ms</b>:<br>
	 * game_1543693822377.csv run in: 644 ms --MAXFEST:<br>
	 * game_1543693911932.csv run in: <b>384 ms</b>:<br>
	 * game_1543693911932_a.csv run in: 169 ms:<br>
	 * game_1543693911932_b.csv run in: <b>160 ms</b>:<br>
	 */
	private void runImproveAlgorithms() {
		//ptt=patch shorted total time/
		PatchPackman []ptt = patch.getPatchPackmanShorteTotalTime();

		/*the slowest a patch not need balance to other patch
		 * show i start for 1.*/
		for(int i=1;i<ptt.length;i++)
			balanceFruit(i,ptt);

	}

	/**
	 * get patchPackman index and move fruit to patchPackman 
	 * that finish the tour slower. 
	 * @param i - index
	 * @param ptt - PatchPackman array shorted by total time.
	 */
	private void balanceFruit(int i, PatchPackman[] ptt) {
		//in some case i want skip thi fist one
		if(specificCases && i == 1)
			return;

		boolean loop = true;

		while(loop) {
			//op = optimal PatchPackman
			PatchPackman op = optimalPatchPackman(i,ptt);					

			//fix bug
			if(op.getTotalTime() == 0)
				break;
			
			MoveLestPoint(op,ptt[i]);
			
			/*if the move make the index packmen be slowest i move the fruit back.
			 *op.getTotalTime() = 0 is irregular case that
			 *some packman so slowest that favorite this packman not take any fruit.
			 * 
			 */
			if(ptt[i].getTotalTime() > op.getTotalTime() && op.getTotalTime() != 0) {
				MoveLestPoint(ptt[i],op);
				loop = false;
			}
		}

	}

	/**
	 * move fruit from PatchPackman a to PatchPackman b.
	 * @param a - PatchPackman a.
	 * @param b - PatchPackman b.
	 */
	private void MoveLestPoint(PatchPackman a, PatchPackman b) {
		PatchPoint f = a.pop();
		b.push(f);
	}

	/**
	 * find the PatchPackman that 
	 * the lest fruit is closer to ptt[i] and is slowest from prr[i]. 
	 * @param i - index.
	 * @param ptt - PatchPackman array shorted by total time.
	 * @return optimal PatchPackman for balanceFruit.
	 */
	private PatchPackman optimalPatchPackman(int i, PatchPackman[] ptt) {
		//copy pointer to new array
		PatchPackman []f = new PatchPackman[i];

		for (int index = 0; index < f.length; index++) {
			f[index] = ptt[index];
		}

		//sort close to for.
		comPLF.setLocation(ptt[i].cournetLocation);
		Arrays.sort(f, comPLF);
		
		//fix bug in empty PatchPackman.
		int a = 0;
		while(a < f.length - 1 && f[a].getTotalTime() == 0) {
			a++;
		}

		//return.
		return f[a];
	}

	/** jump to packman and go to closer fruit.*/
	private void runGreedyAlgorithm() {
		//make empty patch data structure and copy the fruit.
		patch = new Patch(game);		
		ArrayList<Fruit> copyFruit = game.CopyPointerFruit();		
		
		//while have fruit in the array.
		while(copyFruit.size() > 0) {
			//give to packman.
			for(int pi=0;pi < patch.getNumPackmens() && copyFruit.size() > 0;pi++) {//pi = pacmenIndex
				PatchPackman patchPackman = patch.getPatchPackman(pi);
				Point3D cournetLocation = patchPackman.cournetLocation;
				//shorted
				shorted(copyFruit, cournetLocation);	

				//boazImproveCheat
				if(specificCases) {
					double dis = myCoords.distance3d(cournetLocation, copyFruit.get(0).location);
					ttl++;
					if(dis > 150 && ttl < 500)
						continue;
				}

				//insert first
				patchPackman.push(copyFruit.get(0).location,copyFruit.get(0).Weight);

				//remove from array
				copyFruit.remove(0);
			}
		}		

	}
	/**the PatchPackman that finish lest is the total time*/
	private void calculateTotalTime(){
		for(int pi=0;pi < patch.getNumPackmens();pi++) {
			PatchPackman patchPackman = patch.getPatchPackman(pi);			
			timeToComplete = Math.max(timeToComplete, patchPackman.getTotalTime());
		}
	}


	/**
	 * @return the patch
	 */
	public Patch getPatch() {
		return patch;
	}

	/**
	 * @param patch the patch to set
aviv */
	public void setPatch(Patch patch) {
		this.patch = patch;
	}

	/**
	 * shorted by distance to location.
	 * @param arrayFruit - arrayList of Fruit.
	 * @param location - location
vexle*/
	private void shorted(ArrayList<Fruit> arrayFruit,Point3D location) {
		comFruit.setLocation(location);
		arrayFruit.sort(comFruit);
	}



	/**
	 * @return the timeToComplete
	 */
	public long getTimeToComplete() {
		return timeToComplete;
	}

	/**
	 * Comparator for PatchPackman compare the lest fruit.
	 * compare the close to far.
	 * @author Aviv Vexler
	 */
	class ComparatorPLF implements Comparator<PatchPackman>{
		private Point3D location;
		/**
		 * @param location location to compare
		 */
		private void setLocation(Point3D location) {
			this.location = location;
		}

		@Override
		public int compare(PatchPackman p1, PatchPackman p2) {
			
			if(p1.top() == null || p2.top() == null)
				return 0;
			
			Point3D f1 = p1.top().getLocation();
			Point3D f2 = p2.top().getLocation();
			return compareCloserPoint(f1, f2, location);
		}
	}

	/**
	 * omparator for Fruit compare the close to far
	 * @author Aviv Vexler
	 *
	 */
	class ComparatorFruit implements Comparator<Fruit>{
		private Point3D location;
		/**
		 * @param location location to compare
		 */
		private void setLocation(Point3D location) {
			this.location = location;
		}

		@Override
		public int compare(Fruit f1, Fruit f2) {
			return compareCloserPoint(f1.location,f2.location,location);
		}
	}


}
