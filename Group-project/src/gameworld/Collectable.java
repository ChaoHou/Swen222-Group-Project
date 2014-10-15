package gameworld;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 
 * 
 * Represents items in the game that can be collected by players.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public interface Collectable {
	public void toOutputStream(DataOutputStream dout) throws IOException;
}

