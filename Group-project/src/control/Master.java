package control;

import gameworld.Collectable;
import gameworld.Container;
import gameworld.GameCharacter;
import gameworld.HealthPack;
import gameworld.Orb;
import gameworld.Room;
import gameworld.Vamp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ui.Board;

public class Master extends Thread {
	
	public enum ACTION{
		ROTATE_R,
		ROTATE_L,
		CHANGE_ROOM,
		HIDE_IN,
		GET_OUT,
		PLACE_TO_CONTAINER,
		PLACE_TO_INVENTORY,
		HEAL,
		HURT,
	}
	
	public static final int TYPE_ORB = 0;
	public static final int TYPE_HEALTH = 1;
	
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;

	private final int uid;

	private Board game;

	public Master(Socket socket, Board game, int uid) throws IOException {
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());

		this.uid = uid;

		this.game = game;
		
		output.writeInt(uid);
	}

	public void run() {

		try {
			boolean exit = false;
			while (!exit) {
				try {

					if (input.available() != 0) {
						ACTION action = ACTION.values()[input.readInt()];
						executeAction(action);
						
						
						// broadcast the state of the board to client
						byte[] state = game.toByteArray();
						output.writeInt(state.length);
						output.write(state);
						output.flush();
					}

					//broadcast
					byte[] bytes = game.toByteArray();
					//DataOutputStream output = master.getOutputStream();
					output.writeInt(bytes.length);
					output.write(bytes);
					
					//System.out.println("Inventory size:"+game.getVamp(uid).getInventory().size());
					
					Thread.sleep(200);
				} catch (InterruptedException e) {

				}

				
			}
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void executeAction(ACTION action) throws IOException {
		if(action.equals(ACTION.ROTATE_L) || action.equals(ACTION.ROTATE_R)){
			rotate(action);
		}else if(action.equals(ACTION.CHANGE_ROOM)){
			changeRoom();
		}else if(action.equals(ACTION.HIDE_IN)){
			hideIn();
		}else if(action.equals(ACTION.GET_OUT)){
			getOut();
		}else if(action.equals(ACTION.PLACE_TO_CONTAINER)){
			placeToContainer();
		}else if(action.equals(ACTION.PLACE_TO_INVENTORY)){
			placeToInventory();
		}else if(action.equals(ACTION.HEAL)){
			healPlayer();
		}else if(action.equals(ACTION.HURT)){
			getHurt();
		}
	}

	private void getHurt() {
		game.getVamp(uid).setHealth(game.getVamp(uid).getHealth()-1);	
	}

	private void rotate(ACTION action){
		//System.out.println("Actions");
		if(action.equals(ACTION.ROTATE_L)){
			System.out.println("Rotate L");
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
			System.out.println("Rotated to:"+game.getVamp(uid).getDirectionFacing());
		}else if(action.equals(ACTION.ROTATE_R)){
			
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
		}
	}

	private void changeRoom() {
		// TODO Auto-generated method stub
		game.getVamp(uid).enterRoom();
	}

	private void hideIn() {
		// TODO Auto-generated method stub
		Room room = game.getRoomContainingPlayer(game.getVamp(uid));
		room.hideInFurniture(game.getVamp(uid));
	}

	private void getOut() {
		Room room = game.getRoomContainingPlayer(game.getVamp(uid));
        room.getOutFromFurniture(game.getVamp(uid));
	}

	private void placeToContainer() throws IOException {
		int type = input.readInt();
		System.out.println("To container type:"+type);
		Collectable obj = null;
		if(type == TYPE_ORB){
			int orb = input.readInt();
			obj = new Orb(orb);
		}else if(type == TYPE_HEALTH){
			obj = new HealthPack();
		}
		Vamp vamp = game.getVamp(uid);
		Container container = game.getRoomContainingPlayer(vamp).getContainer();
		
		container.addItem(vamp.remove(obj));
	}

	private void placeToInventory() throws IOException {
		int type = input.readInt();
		Collectable obj = null;
		if(type == TYPE_ORB){
			int orb = input.readInt();
			obj = new Orb(orb);
		}else if(type == TYPE_HEALTH){
			obj = new HealthPack();
		}
		Vamp vamp = game.getVamp(uid);
		Container container = game.getRoomContainingPlayer(vamp).getContainer();
		
		vamp.collectItem(container.remove(obj));
		
//		System.out.println("Player contains:"+vamp.getInventory().contains(obj));
//		System.out.println("Container contains:"+container.getItems().contains(obj));
//		System.out.println("Place to inventory finish");
		
	}

	private void healPlayer() {
		game.getVamp(uid).setHealth(5);	
		HealthPack temp = new HealthPack();
		game.getVamp(uid).remove(temp);
		//Updating the Statistics Information:
//		StatsPanel x = (StatsPanel) ((GameMenu) frame.getPanels().get("game")).getPanels().get("stats");
		//x.updateHealth();
		//x.updateInventory();
	}
	
	public DataOutputStream getOutputStream(){
		return output;
	}
	
	public Socket getSocket(){
		return socket;
	}
}
