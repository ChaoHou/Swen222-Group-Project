package networking;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import UI.TestUI;

public class Client extends Thread implements MouseListener {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	
	private final int uid;
	
	public Client(Socket socket) throws IOException{
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		
		uid = input.readInt();
		
		new TestUI(this);
	}
	
	public void run(){
		while(true){
			try {
				if(input.available() != 0){
					int uid = input.readInt();
					int x = input.readInt();
					int y = input.readInt();
					
					System.out.println("UID:"+uid+" x:"+x+" y:"+y);
				}
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		System.out.println("x: "+x+", y: "+y);
		
		try {
			output.writeInt(uid);
			output.writeInt(x);
			output.writeInt(y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
