package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gameworld.Container;
import gameworld.HealthPack;
import gameworld.Orb;
import gameworld.Vamp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Test;

public class TestContainer {

	@Test
	public void testContainer1(){
		try {
			Container c1 = new Container(Container.DRAWER,0,0,0,0);
			c1.addItem(new Orb(Orb.BLUE));
			c1.addItem(new HealthPack());
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			c1.toOutputStream(dout);
			byte[] bytes = bout.toByteArray();
			DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
			Container c2 = new Container(Container.TREASURE_CHEST,0,0,0,0);
			c2.fromInputStream(din, null);
			
			assertEquals(c1.getItems().get(0),c2.getItems().get(0));
			
			assertEquals(c1.getItems().get(1),c2.getItems().get(1));
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	

	@Test
	public void testContainer2(){
		try {
			Container c1 = new Container(Container.DRAWER,0,0,0,0);
			c1.addItem(new Orb(Orb.BLUE));
			c1.addItem(new HealthPack());
			c1.remove(new Orb(Orb.BLUE));
			int size = c1.getItems().size();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			c1.toOutputStream(dout);
			byte[] bytes = bout.toByteArray();
			DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
			Container c2 = new Container(Container.TREASURE_CHEST,0,0,0,0);
			c2.fromInputStream(din, null);
			int newSize = c2.getItems().size();
			
			assertEquals(size,newSize);
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
}
