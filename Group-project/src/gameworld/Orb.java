package gameworld;

public class Orb implements Collectable{
	public static final int BLUE=0;
	public static final int GREEN=1;
	public static final int RED=2;
	
	//colour of the orb.
	private int orbColour;
	
	public Orb(int colour){
		this.orbColour=colour;
	}
	
	public int getOrbColour(){
		return this.orbColour;
	}
}
