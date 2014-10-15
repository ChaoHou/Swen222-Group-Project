package tests;
import static org.junit.Assert.*;
import gameworld.Vamp;

import java.io.IOException;

import main.Main;

import org.junit.Test;

import ui.Board;

public class TestBoard {
	
	@Test
	public void testBoard1(){
		try {
			Board game = Main.createBoardFromFile(Main.filename);
			int id = game.registerVamp();
			
			game.fromByteArray(game.toByteArray());
			
			Vamp vamp = game.getVamp(id);
			
			
			
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (IllegalArgumentException e1){
			fail();
		}
	}
}
