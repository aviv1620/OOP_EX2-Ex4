package GIS.packmanModel;

import java.util.Arrays;
import java.util.Comparator;

public class Patch {
	private final PatchPackman[] patchPackmans;
	
	private Comparator<PatchPackman> comparatorTime = new Comparator<PatchPackman>() {

		@Override
		public int compare(PatchPackman o1, PatchPackman o2) {
			
			return o2.getTotalTime()-o1.getTotalTime();
		}

	
	};
	
	private Comparator<PatchPackman> comparatorSpeed = new Comparator<PatchPackman>() {

		@Override
		public int compare(PatchPackman o1, PatchPackman o2) {
			if(o1.getSpeed() == -1 || o2.getSpeed() == -1)
				return 0;
			return o1.getSpeed()-o2.getSpeed();
		}

	
	};
	
	public Patch(Game game) {
		patchPackmans = new PatchPackman[game.countPackmens()];		
		for(int i=0; i < patchPackmans.length;i++) {
			patchPackmans[i] = new PatchPackman(game.getPackmen(i)); 
		}
	}
	
	public PatchPackman[] getPatchPackmanShorteTotalTime() {
		//copy
		PatchPackman copy[] = Arrays.copyOf(patchPackmans, patchPackmans.length);
		Arrays.sort(copy,comparatorTime);
		return copy;
	}
	
	public PatchPackman[] getPatchPackmanShorteSpeed() {
		//copy
		PatchPackman copy[] = Arrays.copyOf(patchPackmans, patchPackmans.length);
		Arrays.sort(copy,comparatorSpeed);
		return copy;
	}
	
	public PatchPackman getPatchPackman(int index) {
		return patchPackmans[index];
	}
	
	public int getNumPackmens() {
		return patchPackmans.length;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PatchA [patchPackmans=" + Arrays.toString(patchPackmans) + "]";
	}
	
	
	
}
