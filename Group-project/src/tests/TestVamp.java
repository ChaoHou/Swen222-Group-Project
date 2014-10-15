package tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import gameworld.Container;
import gameworld.HealthPack;
import gameworld.Orb;
import gameworld.Vamp;
import main.Main;

import org.junit.Test;

import ui.Board;
import static org.junit.Assert.*;

public class TestVamp {

	@Test
	public void testVampFacingDir(){
		
		try {
			Vamp vamp = new Vamp(0,null);
			vamp.setDirectionFacing(2);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			vamp.toOutputStream(dout);
			byte[] bytes = bout.toByteArray();
			DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
			Vamp newVamp = Vamp.fromInputStream(din, null);
			assertEquals(newVamp.getDirectionFacing(),vamp.getDirectionFacing());
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testVampContainsObj(){
		
		try {
			Vamp vamp = new Vamp(0,null);
			vamp.collectItem(new Orb(Orb.BLUE));
			vamp.collectItem(new HealthPack());
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			vamp.toOutputStream(dout);
			byte[] bytes = bout.toByteArray();
			DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
			Vamp newVamp = Vamp.fromInputStream(din, null);
			assertEquals(newVamp.getInventory().get(0),vamp.getInventory().get(0));
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testVampInventorySize(){
		try {
			Board game = Main.createBoardFromFile(Main.filename);
			int id = game.registerVamp();
			
			Vamp vamp = game.getVamp(id);
			vamp.collectItem(new Orb(Orb.BLUE));
			vamp.collectItem(new HealthPack());
			
			int size = vamp.getInventory().size();
			
			game.fromByteArray(game.toByteArray());
			
			assertEquals(size,vamp.getInventory().size());
			
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (IllegalArgumentException e1){
			fail();
		}
	}

	@Test
	public void testVampPickUpFromContainer(){
		try {
			Board game = Main.createBoardFromFile(Main.filename);
			int id = game.registerVamp();
			
			Vamp vamp = game.getVamp(id);
			Container container = game.getRoomContainingPlayer(vamp).getContainer();
			int size = vamp.getInventory().size();
			vamp.collectItem(container.remove(new Orb(0)));
			game.fromByteArray(game.toByteArray());
			int sizeAfter = vamp.getInventory().size();
			
			assertEquals(size+1,sizeAfter);
			
			
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (IllegalArgumentException e1){
			fail();
		}
	}
}
