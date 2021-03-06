package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;


/**
 * 
 * 
 * 3 Orbs of different colours must be collected by a player to win the game/beat other players.
 * There are 4 sets of orbs in the mansion. (red+blue+green).
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class Orb implements Collectable{
	public static final int BLUE=0;
	public static final int GREEN=1;
	public static final int RED=2;
	
	//colour of the orb.
	private int colour;
	
	public Orb(int colour){
		if(colour==Orb.BLUE || colour==Orb.GREEN || colour==Orb.RED){
			this.colour=colour;
		}else{
			throw new IllegalArgumentException("invalid orb colour");
		}
		
	}
	
	public int getColour(){
		return this.colour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colour;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orb other = (Orb) obj;
		if (colour != other.colour)
			return false;
		return true;
	}
	
	/**
	 * @author Chao
	 */
	public void toOutputStream(DataOutputStream dout) throws IOException {	
		dout.writeInt(colour);
	}
	
	/**
	 * @author Chao
	 * @param din
	 * @param game
	 * @return
	 * @throws IOException
	 */
	public static Orb fromInputStream(DataInputStream din,Board game) throws IOException {
		return new Orb(din.readInt());
	}
}
