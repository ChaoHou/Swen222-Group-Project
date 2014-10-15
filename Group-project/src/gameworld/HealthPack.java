package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;


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

	public void toOutputStream(DataOutputStream dout) throws IOException {	
		//do nothing
	}
	
	public static HealthPack fromInputStream(DataInputStream din,Board game) throws IOException {
		return new HealthPack();
	}
}
