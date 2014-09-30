package gameworld;

import java.util.ArrayList;
import java.util.List;

public class Container {

	List<Collectable> collectables=new ArrayList<Collectable>();
	
	public Container() {

	}

	public void addCollectable(Collectable collectable){
		collectables.add(collectable);
	}
}
