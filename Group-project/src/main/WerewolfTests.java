package main;
import static org.junit.Assert.*;
import gameworld.Room;
import gameworld.Vamp;
import gameworld.Werewolf;

import org.junit.Test;

import ui.Board;


/**
 * 
 * 
 * Tests more complex methods in Werewolf class.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class WerewolfTests {

	private Board board;
	
	
	@Test
	public void testKill(){
		board=BoardTests.createBoard();
		Vamp vamp=new Vamp(board.registerVamp(), board);
		Room room=board.getRoom("diningarea");
		room.playerEnterRoom(vamp);
		Werewolf werewolf=new Werewolf(board);
		room.werewolfEnterRoom(werewolf);
		werewolf.kill();
		assertTrue(vamp.isDead());
		try{
			Thread.sleep((Vamp.RECOVERY_TIME));
			assertTrue(vamp.isRecovering());
		}catch(Exception e){
			
		}
		
		
		
	}
	
	
	@Test
	public void testEnterRoom(){
		board=BoardTests.createBoard();
		Werewolf werewolf=new Werewolf(board);
		board.registerWerewolf(werewolf);
		Room previousRoom=board.getRoom("hallway");
		
		for(int i=0;i<20;i++){
			werewolf.enterRoom();
			Room room=board.getRoomContainingWerewolf();
			assertNotSame(room, previousRoom);
			previousRoom=room;
		}
	}
	

	
		
	
}
