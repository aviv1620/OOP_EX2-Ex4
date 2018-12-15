package GIS.packmanModel;

import java.util.Arrays;

public class Patch {
	private final PatchPackman[] patchPackmans;
	
	public Patch(Game game) {
		patchPackmans = new PatchPackman[game.countPackmens()];		
		for(int i=0; i < patchPackmans.length;i++) {
			patchPackmans[i] = new PatchPackman(game.getPackmen(i)); 
		}
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
