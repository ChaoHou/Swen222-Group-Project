package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server {
	private final Socket[] sockets;
	private final DataOutputStream[] outputs;
	private final DataInputStream[] inputs;
	
	public Server(Socket[] sockets) throws IOException{
		this.sockets = sockets;
		outputs = new DataOutputStream[sockets.length];
		inputs = new DataInputStream[sockets.length];
		for(int i=0;i<sockets.length;i++){
			outputs[i] = new DataOutputStream(sockets[i].getOutputStream());
			inputs[i] = new DataInputStream(sockets[i].getInputStream());
		}
	}
	
	public void run(){
		while(true){
			try {
				for(DataInputStream input:inputs){
					if(input.available() != 0){
						int uid = input.readInt();
						int x = input.readInt();
						int y = input.readInt();
						broadcast(uid, x, y);
					}
				}
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(int uid,int x,int y){
		System.out.println("uid: "+uid+" x:"+x+" y:"+y);
		for(DataOutputStream output:outputs){
			try {
				output.writeInt(uid);
				output.writeInt(x);
				output.writeInt(y);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
