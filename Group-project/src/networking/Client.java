package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import GameWorld.GameCharacter;
import UI.Board;
import UI.TestUI;

public class Client extends Thread implements MouseListener,KeyListener {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	
	private final int uid;
	
	private Board game;
	
	public Client(Socket socket,Board game) throws IOException{
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		
		uid = input.readInt();
		
		this.game = game;
		//new TestUI(this);
	}
	
	public void run(){
		try {
			boolean exit = false;
			while (!exit) {
				try {

					if (input.available() != 0) {
						int amount = input.readInt();
						byte[] data = new byte[amount];
						input.readFully(data);					
						game.fromByteArray(data);		
					}

					Thread.sleep(50);
				} catch (InterruptedException e) {

				}

				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		System.out.println("x: "+x+", y: "+y);
		
//		try {
//			output.writeInt(uid);
//			output.writeInt(x);
//			output.writeInt(y);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		
		if(code==KeyEvent.VK_W){
			game.getCharacter(this.uid).rotateTo(GameCharacter.NORTH);
		}else if(code==KeyEvent.VK_D){
			game.getCharacter(this.uid).rotateTo(GameCharacter.EAST);
		}else if(code==KeyEvent.VK_S){
			game.getCharacter(this.uid).rotateTo(GameCharacter.SOUTH);
		}else if(code==KeyEvent.VK_A){
			game.getCharacter(this.uid).rotateTo(GameCharacter.WEST);
		}else if(code==KeyEvent.VK_E){
			game.getCharacter(this.uid).enterRoom();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
