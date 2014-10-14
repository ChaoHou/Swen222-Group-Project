import static org.junit.Assert.*;

import gameworld.Orb;
import gameworld.Vamp;

import org.junit.Test;

import ui.Board;


public class VampTests {

	private String filename="resources/maps/map.txt";
	private Board board;
	
	@Test
	public void testHasAllOrbs(){
		board=createBoard();
		Vamp vamp=new Vamp(board.registerVamp(), board);
		
		Orb blue=new Orb(Orb.BLUE);
		Orb green=new Orb(Orb.GREEN);
		Orb red=new Orb(Orb.RED);
		vamp.collectItem(red);
		vamp.collectItem(green);
		vamp.collectItem(blue);
		assertTrue(vamp.hasAllOrbs());
		vamp.removeItem(blue);
		assertFalse(vamp.hasAllOrbs());
		Orb red2=new Orb(Orb.RED);
		vamp.collectItem(red2);
		assertFalse(vamp.hasAllOrbs());
	}
	
	
	@Test
	public void testEnterRoom(){
		board=createBoard();
	}
	
	
	@Test
	public void testRotateToFace(){
		board=createBoard();
	}
	
	
	
	
	//Utitlies method.
	public Board createBoard(){
		Board board=null;
		try{
			board=Main.createBoardFromFile(filename);
		}catch(Exception e){	
		}
		assertNotNull(board);
		return board;
	}
}
