package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import UI.Board;

public class Server extends Thread {
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;

	private final int uid;

	private Board game;

	public Server(Socket socket, Board game, int uid) throws IOException {
		this.socket = socket;
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());

		this.uid = uid;

		this.game = game;
	}

	public void run() {

		try {
			boolean exit = false;
			while (!exit) {
				try {

					if (input.available() != 0) {

					}

					// broadcast the state of the board to client
					byte[] state = game.toByteArray();
					output.writeInt(state.length);
					output.write(state);
					output.flush();
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

	public void broadcast(int uid, int x, int y) {

	}
}
