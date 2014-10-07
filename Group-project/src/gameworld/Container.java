package gameworld;

import java.util.HashSet;
import java.util.Set;

public class Container {
	
	public static final int DRAWER=0;
	public static final int TREASURE_CHEST=1;
	
	//type of this container, a drawer or a treasure chest.
	private int containerType;
	private Set<Collectable> items=new HashSet<Collectable>();
	
	
	public Container(int containerType){
		this.containerType=containerType;
	}

	public void addItem(Collectable item){
		items.add(item);
	}
	
	public void removeItem(Collectable item){
		items.remove(item);
	}
	
	public Set<Collectable> getItems(){
		return this.items;
	}
	
	public int getContainerType(){
		return this.containerType;
	}
	
	
}
