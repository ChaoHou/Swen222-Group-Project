package gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ui.Board;


public class HealthPack implements Collectable{
	
	public void toOutputStream(DataOutputStream dout) throws IOException {	
		//do nothing
	}
	
	public static HealthPack fromInputStream(DataInputStream din,Board game) throws IOException {
		return new HealthPack();
	}
}
