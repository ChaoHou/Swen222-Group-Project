package tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gameworld.Orb;
import gameworld.Room;
import gameworld.Vamp;
import gameworld.Werewolf;

import org.junit.Test;

import ui.Board;

/**
 * 
 * 
 * Tests more complex methods in Vamp class.
 * 
 * By Peide Ng.
 * ID:300280258
 * 
 * 
 *
 */
public class VampTests {

	private Board board;
	
	@Test
	public void testHasAllOrbs(){
		board=BoardTests.createBoard();
		int uid=board.registerVamp();
		Vamp vamp=board.getVamp(uid);
		Orb blue=new Orb(Orb.BLUE);
		Orb green=new Orb(Orb.GREEN);
		Orb red=new Orb(Orb.RED);
		vamp.collectItem(red);
		vamp.collectItem(green);
		vamp.collectItem(blue);
		assertTrue(vamp.hasAllOrbs());
		vamp.removeItem(blue);
		System.out.println("has all orbs:"+vamp.hasAllOrbs());
		assertFalse(vamp.hasAllOrbs());
		Orb red2=new Orb(Orb.RED);
		vamp.collectItem(red2);
		assertFalse(vamp.hasAllOrbs());
	}
	
	
	@Test
	public void testEnterRoom(){
		board=BoardTests.createBoard();
		int uid=board.registerVamp();
		Vamp vamp=board.getVamp(uid);
		Room from=board.getRoom("hallway");
		Room startroom=board.getRoom("startroom");
		startroom.playerLeaveRoom(vamp);
		from.playerEnterRoom(vamp);
		vamp.setDirectionFacing(Room.EAST);
		vamp.enterRoom();
		assertSame(board.getRoom("library"), board.getRoomContainingPlayer(vamp));
		
	}
	
	
	@Test
	public void testRespawn(){
		board=BoardTests.createBoard();
		int uid=board.registerVamp();
		Vamp vamp=board.getVamp(uid);
		Werewolf werewolf=new Werewolf(board);
		Room plaza=board.getRoom("plaza");
		Room startroom=board.getRoom("startroom");
		startroom.playerLeaveRoom(vamp);
		plaza.playerEnterRoom(vamp);
		Room exit=board.getRoom("exit");
		exit.werewolfLeaveRoom(werewolf);
		plaza.werewolfEnterRoom(werewolf);
		werewolf.prowl();
		assertTrue(vamp.isDead());
//		try{
//			Thread.sleep(Vamp.RECOVERY_TIME);
//			assertTrue(vamp.isRecovering());
//			Thread.sleep(Vamp.RECOVERY_TIME);
//			assertTrue(vamp.isAlive());
//		}catch(Exception e){
//		}
		
			
	}

}
