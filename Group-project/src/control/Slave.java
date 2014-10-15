package control;

import gameworld.Room;

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
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import rendering.Renderer;
import ui.Board;
import ui.GameFrame;
import ui.GameMenu;
import ui.TestUI;

public class Slave extends Thread implements MouseListener,KeyListener,ActionListener,PlayerInterface {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	
	private final int uid;
	
	private Board game;
	
	private GameFrame frame;
	private Map<JButton, String> buttons;
	
	private Renderer renderer;
	
	public Slave(Socket socket,Board game,Renderer renderer,int uid) throws IOException{
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		
		this.uid = uid;
		this.renderer = renderer;
		this.game = game;
		//new TestUI(this);
		//frame = new GameFrame("multi user mode", game, uid, this,renderer);
		//frame.setVisible(true);    
		//frame.repaint();
	}
	
	public void run(){
		try {
			boolean exit = false;
			while (!exit) 	 {
				try {

					if (input.available() != 0) {
						int amount = input.readInt();
						byte[] data = new byte[amount];
						input.readFully(data);					
						game.fromByteArray(data);	
						
						
						String facing = game.getVamp(uid).getDirectionFacingInString();
						System.out.println("Player:"+uid+" now facing:"+facing);
					}

					Thread.sleep(100);
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
			game.getVamp(this.uid).rotateToFace(Room.NORTH);
		}else if(code==KeyEvent.VK_D){
			game.getVamp(this.uid).rotateToFace(Room.EAST);
		}else if(code==KeyEvent.VK_S){
			game.getVamp(this.uid).rotateToFace(Room.SOUTH);
		}else if(code==KeyEvent.VK_A){
			game.getVamp(this.uid).rotateToFace(Room.WEST);
		}else if(code==KeyEvent.VK_E){
			game.getVamp(this.uid).enterRoom();
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
		try {
			if(action.equals("Turn Left")){
				output.writeInt(Master.ACTION.ROTATE_L.ordinal());
			}				
			else if(action.equals("Turn Right")){
				output.writeInt(Master.ACTION.ROTATE_R.ordinal());

			}
			else if(action.equals("Change Room")){
				
			}
			
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setFrame(GameFrame g){
		this.frame = g;
		if(((GameMenu) frame.getPanels().get("game")).getButtons() != null){
					this.buttons = ((GameMenu) frame.getPanels().get("game")).getButtons();

		}
	}
}
