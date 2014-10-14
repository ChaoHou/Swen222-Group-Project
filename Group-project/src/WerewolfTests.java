import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import gameworld.Room;
import gameworld.Vamp;
import gameworld.Werewolf;

import org.junit.Test;

import ui.Board;


public class WerewolfTests {

	private String filename="resources/maps/map.txt";
	private Board board;
	
	
	@Test
	public void testKill(){
		board=createBoard();
		Vamp vamp=new Vamp(board.registerVamp(), board);
		Room room=board.getRoom("diningarea");
		room.playerEnterRoom(vamp);
		Werewolf werewolf=new Werewolf(board);
		room.werewolfEnterRoom(werewolf);
		werewolf.kill();
		assertTrue(vamp.isDead());
		try{
			Thread.sleep((long)(Vamp.recoveryTime));
			assertTrue(vamp.isRecovering());
		}catch(Exception e){
			
		}
		
		
		
	}
	
	
	@Test
	public void testEnterRoom(){
		board=createBoard();
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
	

	
	
	//Utilites method.
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
