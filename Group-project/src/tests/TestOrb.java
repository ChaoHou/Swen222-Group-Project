package tests;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import gameworld.Orb;

import org.junit.Test;

public class TestOrb {

	@Test
	public void testEqual(){
		Orb orb = new Orb(Orb.BLUE);
		assertTrue(orb.equals(new Orb(Orb.BLUE)));
	}
	
	@Test
	public void testNotEqual(){
		assertFalse(new Orb(Orb.BLUE).equals(new Orb(Orb.RED)));
	}
	
	@Test
	public void testDataConversion(){
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(bout);
			
			Orb orb1 = new Orb(Orb.BLUE);
			Orb orb2 = new Orb(Orb.RED);
			Orb orb3 = new Orb(Orb.GREEN);
			orb1.toOutputStream(dout);
			orb2.toOutputStream(dout);
			orb3.toOutputStream(dout);
			dout.flush();
			
			byte[] bytes = bout.toByteArray();
			
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			DataInputStream din = new DataInputStream(bin);
			
			Orb newOrb1 = Orb.fromInputStream(din, null);
			Orb newOrb2 = Orb.fromInputStream(din, null);
			Orb newOrb3 = Orb.fromInputStream(din, null);
			
			assertEquals(orb1, newOrb1);
			assertEquals(orb2, newOrb2);
			assertEquals(orb3, newOrb3);
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
}
