package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;

/**
 * 
 *  Restores full health of the Vamp.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class HealthPack implements Collectable{
	
	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		return true;
	}

	/**
	 * @author Chao
	 */
	public void toOutputStream(DataOutputStream dout) throws IOException {	
		//do nothing
	}
	
	/**
	 * @author Chao
	 * @param din
	 * @param game
	 * @return
	 * @throws IOException
	 */
	public static HealthPack fromInputStream(DataInputStream din,Board game) throws IOException {
		return new HealthPack();
	}
}
