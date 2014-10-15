package control;

import gameworld.GameCharacter;

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
	}
	
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

					
					Thread.sleep(50);
				} catch (InterruptedException e) {

				}

				
			}
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void executeAction(ACTION action) {
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
		}
	}

	private void rotate(ACTION action){
		if(action.equals(ACTION.ROTATE_L)){
			
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateToFace(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateToFace(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateToFace(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateToFace(GameCharacter.NORTH);
		}else if(action.equals(ACTION.ROTATE_R)){
			
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateToFace(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateToFace(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateToFace(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateToFace(GameCharacter.NORTH);
		}
	}

	private void changeRoom() {
		// TODO Auto-generated method stub
		
	}

	private void hideIn() {
		// TODO Auto-generated method stub
		
	}

	private void getOut() {
		// TODO Auto-generated method stub
		
	}

	private void placeToContainer() {
		// TODO Auto-generated method stub
		
	}

	private void placeToInventory() {
		// TODO Auto-generated method stub
		
	}
}
