

import static org.junit.Assert.*;

import gameworld.Room;
import gameworld.Vamp;

import org.junit.Test;

import ui.Board;



public class BoardTests {
	
	private String filename="resources/maps/map.txt";
	private Board board;
	
	
	@Test
	public void testGetRoom(){
		//have to test testGetRoom() before testGetRoomAhead().
		board=createBoard();
		checkInvalidRoom(null);
		checkInvalidRoom("ho hey");	
	}
	
	private void checkInvalidRoom(String roomName){
		try{
			board.getRoom(roomName);
			fail("failed");
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void testGetRoomAhead() {
		board=createBoard();
	
		//in startroom
		assertTrue(checkCorrectRoomAhead("startroom", Room.SOUTH, "hallway"));
		assertTrue(checkCorrectRoomAhead("startroom", Room.NORTH, null));
		assertTrue(checkCorrectRoomAhead("startroom", Room.EAST, null));
		assertTrue(checkCorrectRoomAhead("startroom", Room.WEST, null));
		//in hallway
		assertTrue(checkCorrectRoomAhead("hallway", Room.SOUTH, "plaza"));
		assertTrue(checkCorrectRoomAhead("hallway", Room.NORTH, "startroom"));
		assertTrue(checkCorrectRoomAhead("hallway", Room.EAST, "library"));
		assertTrue(checkCorrectRoomAhead("hallway", Room.WEST, "bathroom"));
		//in plaza
		assertTrue(checkCorrectRoomAhead("plaza", Room.SOUTH, "exit"));
		assertTrue(checkCorrectRoomAhead("plaza", Room.NORTH, "hallway"));
		assertTrue(checkCorrectRoomAhead("plaza", Room.WEST, null));
		assertTrue(checkCorrectRoomAhead("plaza", Room.EAST, "diningarea"));
		//in dungeon
		assertTrue(checkCorrectRoomAhead("dungeon", Room.SOUTH, "diningarea"));
		assertTrue(checkCorrectRoomAhead("dungeon", Room.NORTH, null));
		assertTrue(checkCorrectRoomAhead("dungeon", Room.EAST, null));
		assertTrue(checkCorrectRoomAhead("dungeon", Room.WEST, "library"));
		//in bathroom
		assertTrue(checkCorrectRoomAhead("bathroom", Room.SOUTH, "exit"));
		assertTrue(checkCorrectRoomAhead("bathroom", Room.NORTH, null));
		assertTrue(checkCorrectRoomAhead("bathroom", Room.EAST, "hallway"));
		assertTrue(checkCorrectRoomAhead("bathroom", Room.WEST, null));
		//in diningarea
		assertTrue(checkCorrectRoomAhead("diningarea", Room.SOUTH, null));
		assertTrue(checkCorrectRoomAhead("diningarea", Room.NORTH, "library"));
		assertTrue(checkCorrectRoomAhead("diningarea", Room.EAST, "dungeon"));
		assertTrue(checkCorrectRoomAhead("diningarea", Room.WEST, "plaza"));		
	}
	
	private boolean checkCorrectRoomAhead(String roomS, int dir, String roomAheadS){
		Room room=null;
		Room roomAhead=null;
		Room roomReturned=null;
		
		
		room=board.getRoom(roomS);
		
		if(roomAheadS!=null){
			roomAhead=board.getRoom(roomAheadS);

		}
		roomReturned=board.getRoomAhead(room, dir);
		
		return roomAhead==roomReturned;
	}
	
	@Test
	public void testGetRoomContainingPlayer(){
		board=createBoard();
		Vamp vamp=new Vamp(board.registerVamp(), board);
		Room room=board.getRoom("diningarea");
		room.playerEnterRoom(vamp);
		assertSame(board.getRoomContainingPlayer(vamp), room);
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
