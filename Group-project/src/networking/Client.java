package networking;

import gameworld.GameCharacter;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import rendering.Renderer;
import rendering.RendererTest;
import ui.Board;
import ui.GameFrame;
import ui.TestUI;

public class Client extends Thread implements MouseListener,KeyListener,ActionListener {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	
	private final int uid;
	
	private Board game;
	
	private GameFrame gameFrame;
	
	private Renderer renderer;
	
	public Client(Socket socket,Board game,Renderer renderer) throws IOException{
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		
		uid = input.readInt();
		this.renderer = renderer;
		this.game = game;
		//new TestUI(this);
		gameFrame = new GameFrame("multi user mode", game, uid, this,renderer);
		gameFrame.setVisible(true);    
		gameFrame.repaint();
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
						
						
						String facing = game.getCharacter(uid).intDirToString();
						System.out.println("Player:"+uid+" now facing:"+facing);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		//System.out.println(action);
		try {
			if(action.equals("Turn Left")){
				output.writeInt(Server.ACTION.ROTATE_L.ordinal());
				renderer.rotateL();
			}				
			//When turning right
			else if(action.equals("Turn Right")){
				output.writeInt(Server.ACTION.ROTATE_R.ordinal());
				renderer.rotateR();

			}
			else if(action.equals("Change Room")){
				//Use room
				String answer = (String) JOptionPane.showInputDialog(null, "Which Room would you like to go to?", null, 
						 JOptionPane.PLAIN_MESSAGE, null, new String[]{ "d", "dd"}, null);
			
				System.out.println("You moved to Room: " + answer );
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
