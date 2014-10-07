package gameworld;

import java.util.ArrayList;
import java.util.List;

public class Container {
	
	public static final int DRAWER=0;
	public static final int TREASURE_CHEST=1;
	
	//type of this containter, a drawer of a treasure chest.
	private int containerType;
	private List<Collectable> items=new ArrayList<Collectable>();
	
	
	public Container(int containerType){
		this.containerType=containerType;
	}

	public void addItem(Collectable item){
		items.add(item);
	}
	
	public void removeItem(Collectable item){
		items.remove(item);
	}
	
	public int getContainerType(){
		return this.containerType;
	}
	
	
}
