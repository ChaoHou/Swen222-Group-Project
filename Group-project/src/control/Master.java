package control;

import gameworld.GameCharacter;
import gameworld.Room;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ui.Board;

public class Master extends Thread {
	
	public enum ACTION{
		ROTATE_R,
		ROTATE_L,
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
						rotate(action);
						
						
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

	public void rotate(ACTION action){
		if(action.equals(ACTION.ROTATE_L)){
			
			if(game.getVamp(uid).getDirectionFacing() == Room.NORTH)
				game.getVamp(uid).rotateToFace(Room.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == Room.WEST)
				game.getVamp(uid).rotateToFace(Room.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == Room.SOUTH)
				game.getVamp(uid).rotateToFace(Room.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == Room.EAST)
				game.getVamp(uid).rotateToFace(Room.NORTH);
		}else if(action.equals(ACTION.ROTATE_R)){
			
			if(game.getVamp(uid).getDirectionFacing() == Room.NORTH)
				game.getVamp(uid).rotateToFace(Room.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == Room.EAST)
				game.getVamp(uid).rotateToFace(Room.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == Room.SOUTH)
				game.getVamp(uid).rotateToFace(Room.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == Room.WEST)
				game.getVamp(uid).rotateToFace(Room.NORTH);
		}
	}
}
