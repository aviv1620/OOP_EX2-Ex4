package GIS.packmanModel;

import java.util.Arrays;
import java.util.Comparator;
/**
 * store PatchPackman patches {@link PatchPackman}.
 * @author Aviv Vexler
 *
 */
public class Patch {
	private final PatchPackman[] patchPackmans;
	
	/**compare time*/
	private Comparator<PatchPackman> comparatorTime = new Comparator<PatchPackman>() {

		@Override
		public int compare(PatchPackman o1, PatchPackman o2) {
			
			return o2.getTotalTime()-o1.getTotalTime();
		}

	
	};
	
	/**compare speed.*/
	private Comparator<PatchPackman> comparatorSpeed = new Comparator<PatchPackman>() {

		@Override
		public int compare(PatchPackman o1, PatchPackman o2) {
			if(o1.getSpeed() == -1 || o2.getSpeed() == -1)
				return 0;
			return o1.getSpeed()-o2.getSpeed();
		}

	
	};
	
	/**
	 * build the data structure.
	 * @param game - to know how much packmans have in the game.
	 */
	public Patch(Game game) {
		patchPackmans = new PatchPackman[game.countPackmens()];		
		for(int i=0; i < patchPackmans.length;i++) {
			patchPackmans[i] = new PatchPackman(game.getPackmen(i)); 
		}
	}
	
	/**@return Arrays withe new pointer that sorted by time.*/
	public PatchPackman[] getPatchPackmanShorteTotalTime() {
		//copy
		PatchPackman copy[] = Arrays.copyOf(patchPackmans, patchPackmans.length);
		Arrays.sort(copy,comparatorTime);
		return copy;
	}
	
	/**@return Arrays withe new pointer that sorted by speed.*/
	public PatchPackman[] getPatchPackmanShorteSpeed() {
		//copy
		PatchPackman copy[] = Arrays.copyOf(patchPackmans, patchPackmans.length);
		Arrays.sort(copy,comparatorSpeed);
		return copy;
	}
	
	/**@param index - index in the array.
	 * @return index.*/
	public PatchPackman getPatchPackman(int index) {
		return patchPackmans[index];
	}
	
	/** @return size of the array */
	public int getNumPackmens() {
		return patchPackmans.length;
	}

	@Override
	public String toString() {
		return "PatchA [patchPackmans=" + Arrays.toString(patchPackmans) + "]";
	}
	
	
	
}
