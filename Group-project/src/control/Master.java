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
			
			if(game.getVamp(uid).getDirectionFacing() == GameCharacter.NORTH)
				game.getVamp(uid).rotateTo(GameCharacter.WEST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.WEST)
				game.getVamp(uid).rotateTo(GameCharacter.SOUTH);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.SOUTH)
				game.getVamp(uid).rotateTo(GameCharacter.EAST);
			else if(game.getVamp(uid).getDirectionFacing() == GameCharacter.EAST)
				game.getVamp(uid).rotateTo(GameCharacter.NORTH);
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
}
