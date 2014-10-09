package gameworld;

public class Furniture {

	public static final int BED=0;
	public static final int CLOSET=1;
	public static final int CURTAIN=2;
	
	private int furnitureType;
	private Vamp hidingPlayer;
	
	
	public Furniture(int furnitureType) {
		this.furnitureType=furnitureType;
	}
	
	public void hidePlayer(Vamp player){
		hidingPlayer=player;
	}
	
	public Vamp getHidingPlayer(){
		return this.hidingPlayer;
	}
	
	public void removePlayer(Vamp player){
		hidingPlayer=null;
	}
	
	public int getFurnitureType(){
		return this.furnitureType;
	}
	 

}
