package gameworld;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
		getItems().add(item);
	}
	
	public void removeItem(Collectable item){
		getItems().remove(item);
	}
	
	public int getContainerType(){
		return this.containerType;
	}

	public List<Collectable> getItems() {
		return items;
	}

	public void setItems(List<Collectable> items) {
		this.items = items;
	}
	
	
	public Collectable remove(Collectable c){
		Collectable temp = null;
		//We'll need to iterate through the whole container for this...
		for(Collectable l : this.getItems()){
			//Is it an Orb? Are they the same kind of Orb?	
			if((c instanceof Orb && l instanceof Orb) && 
				((Orb) c).getColor() == ((Orb) l).getColor()){
				temp = (Orb) l;
				this.getItems().remove(l);
				return temp;
			}
			//Is it a HealthPack?
			else if(c instanceof HealthPack && l instanceof HealthPack){
				temp = (HealthPack) l;
				this.getItems().remove(l);
				return temp;
			}
		}
		return null;
	}

}
