import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import networking.Client;
import networking.Player;
import networking.Server;
import GameWorld.Room;
import UI.Board;
import UI.BoardFrame;
import UI.GameFrame;
import UI.MainMenu;
import UI.GameFrame;
import UI.GameMenu;
import UI.MainMenu;
import UI.GameFrame;


public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	private static final String filename="map.txt";
	
	public static void main(String[] args) {

		
		boolean server = false;
		int nplayers = 0;
		String url = null;
		int gameClock = DEFAULT_CLK_PERIOD;
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = 32768;

		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if (arg.equals("-help")) {
					usage();
					System.exit(0);
				} else if (arg.equals("-server")) {
					server = true;
					nplayers = Integer.parseInt(args[++i]);
				} else if (arg.equals("-connect")) {
					url = args[++i];
				} else if (arg.equals("-clock")) {
					gameClock = Integer.parseInt(args[++i]);
				} else if (arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				}
			} else {
				System.out.println("Wrong argument");
			}
		}

		// Sanity checks
		if (url != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		} else if (url != null && gameClock != DEFAULT_CLK_PERIOD) {
			System.out.println("Cannot overide clock period when connecting to server.");
			System.exit(1);
		} 
		
		if(server){
			runServer(port, nplayers, gameClock, broadcastClock);
		}else if(url!=null){
			runClient(url,port,gameClock,broadcastClock);
		}else{
			try{
				Board game=createBoardFromFile(filename);
				singleUserGame(game);			
				
				
			}catch(Exception e){
				
			}
		}
		
		
		
		
		System.exit(0);
	}

	
	public static void singleUserGame(Board game){
//		System.out.println("in single user mode-------------------------");
//
//		int uid=game.registerVamp();
//		System.out.println("done with registering vamp");
//		BoardFrame frame=new BoardFrame("single user mode", game, uid, new Player(uid, game));
//		game.startGame();
//		
//		while(true){
//			//game running
//		}
//		
		
		//This is for the construction of the game
		
		int uid = game.registerVamp();		
		GameFrame gg = new GameFrame("single user mode", game, uid, new Player(uid, game));
		gg.setVisible(true);    
        while(true){
        	//game running	        	
        }
		
	}
	
	private static void usage() {
		String[][] info = {
				{ "server <n>",
						"Run in server mode, awaiting n client connections" },
				{ "connect <url>", "Connect to server at <url>" },
				{ "clock", "Set clock period (default 20ms)" },
				{ "bclock", "Set broadcast clock period (default 5ms)" },
				{ "port", "Set port for use for connection (default 32768)" },
				{ "player <n>", "Set the number of players" }, };
		System.out.println("Usage: java com.cluedo.Main <options> ");
		System.out.println("Options:");

		// first, work out gap information
		int gap = 0;

		for (String[] p : info) {
			gap = Math.max(gap, p[0].length() + 5);
		}

		// now, print the information
		for (String[] p : info) {
			System.out.print("  -" + p[0]);
			int rest = gap - p[0].length();
			for (int i = 0; i != rest; ++i) {
				System.out.print(" ");
			}
			System.out.println(p[1]);
		}
	}
	
	private static void runClient(String addr, int port,int gameClock, int broadcastClock){		
		
		try {
			Socket s = new Socket(addr,port);
			System.out.println("CLIENT CONNECTED TO " + addr + ":" + port);			
			//SlaveConnection slave = new SlaveConnection(s,broadcastClock);
			//SlaveActionHandler actionSlave = new SlaveActionHandler(slave,gameClock);
			//slave.setActionHandler(actionSlave);
			
			//slave.start();
			//actionSlave.run();
			Board game=createBoardFromFile(filename);
			
			Client client = new Client(s,game);
			client.run();
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}


	private static void runServer(int port, int nplayers, int gameClock, int broadcastClock) {	
		//ClockThread clk = new ClockThread(gameClock);	
		
		// Listen for connections
		System.out.println("CLUEDO SERVER LISTENING ON PORT " + port);
		System.out.println("CLUEDO SERVER AWAITING " + nplayers + " PLAYERS");
		try {
			Board game = createBoardFromFile(filename);
			//Socket[] sockets = new Socket[nplayers];
			Server[] connections = new Server[nplayers];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);	
			
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				
				int uid = game.registerVamp();
				System.out.println("PLAYER UID: "+uid);
				
				connections[--nplayers] = new Server(s,game,uid);
				connections[nplayers].start();
				//connections[--nplayers] = new MasterConnection(s,broadcastClock,uid);
				if(nplayers == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					multiUserGame(game,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					
					ss.close();
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
			System.exit(1);
		} 
	}


	private static void multiUserGame(Board game, Server[] connections) {
		// TODO Auto-generated method stub
		
	}


	private static Board createBoardFromFile(String filename) throws IOException{
		FileReader fr = new FileReader(filename);		
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String[]> lines = new ArrayList<String[]>();
		int width = -1;
		String line;		
		while((line = br.readLine()) != null) {
			String[] tokens=line.split(" ");
			if(width==-1){
				width=tokens.length;
			}else if(width!=tokens.length){
				System.out.println("wrong argument");
				throw new IllegalArgumentException("Input file \"" + filename + "\" is malformed; line " + lines.size() + " incorrect width.");
			}
			lines.add(tokens);		
		}
		
		Room[][] rooms=new Room[width][lines.size()];
		for(int i=0;i<lines.size();i++){
			for(int j=0;j<width;j++){
				String elem=lines.get(i)[j];
				if(elem.equals("-")){
					System.out.println("detected a dash");
					rooms[i][j]=null;
				}else{
					rooms[i][j]=new Room(elem);
				}
			}
		}

		Board board=new Board(rooms); 	
		
		
		return board;	
	}
	
	
}

