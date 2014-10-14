package gameworld;

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
			throw new IllegalArgumentException();
		}
	}
	
	public int getColor(){
		return this.colour;
	}
	
	
}
