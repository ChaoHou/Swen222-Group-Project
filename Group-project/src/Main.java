import gameworld.Container;
import gameworld.Furniture;
import gameworld.Orb;
import gameworld.Room;
import gameworld.Wall;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import control.Slave;
import control.Player;
import control.Master;
import rendering.Renderer;
import ui.Board;
import ui.GameFrame;
import ui.GameMenu;
import ui.MainMenu;


public class Main {

	private static final int DEFAULT_CLK_PERIOD = 20;
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5;

	private static String filename="resources/maps/map.txt";
	
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
				filename = args[i];
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
                System.err.println(e.getMessage());
			}
		}



		
		System.exit(0);
	}

	
	public static void singleUserGame(Board game){
		//This is for the construction of the game	
		int uid = game.registerVamp();		
		System.out.println("uid: "+uid);
		Renderer renderer = new Renderer(game,uid);
		Player player = new Player(uid,game,renderer);
		GameFrame gg = new GameFrame("single user mode", game, uid, player,renderer);
		gg.setVisible(true);

        while(true){
        	Thread.yield();      	
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
			
			int uid = new DataInputStream(s.getInputStream()).readInt();
			
			Board game=createBoardFromFile(filename);
			//TODO
			//need to set the uid to the renderer
			Renderer renderer = new Renderer(game,uid);
			Slave client = new Slave(s,game,renderer,uid);
			GameFrame gg = new GameFrame("single user mode", game, uid, client ,renderer);
			gg.setVisible(true);
			
			//client.run();
			while(true){
				Thread.yield();
			}
			
			
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
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nplayers + " PLAYERS");
		try {
			Board game = createBoardFromFile(filename);
			Master[] connections = new Master[nplayers];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);	
			
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				
				int uid = game.registerVamp();
				System.out.println("PLAYER UID: "+uid);
				
				connections[--nplayers] = new Master(s,game,uid);
				connections[nplayers].start();
				
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

	private static void multiUserGame(Board game, Master[] connections) {
		while(true){
			Thread.yield();
		}
	}

	public static Board createBoardFromFile(String filename) throws IOException{
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
					rooms[i][j]=null;
				}else{
					rooms[i][j]=createRoom(elem);
				}
			}
		}

		Board board=new Board(rooms); 	
		
		
		return board;	
	}
	
	/**
	 * Room file format:
	 *   name: roomName.txt
	 *   content:
	 *   line 1: wall texture index
	 *     format: north east south west
	 *   line 2: container info
	 *     format: 0 type x y z index orb...
	 *   line 3: furniture info
	 *     format: 1 type x y z index
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private static Room createRoom(String name) throws IOException{
		String dir = "resources/maps/"+name+".txt";
//		System.out.println("file name:"+name);
//		System.out.println("dir: "+dir);
		
		try {
			FileReader fr = new FileReader(dir);
			BufferedReader br = new BufferedReader(fr);
			
			Wall[] walls = new Wall[4];
			
			String line = br.readLine();
			String[] tokens = line.split(" ");
			for(int i=0;i<4;i++){
				walls[i] = new Wall(i,Integer.parseInt(tokens[i]));
			}
			
			Room room = new Room(name,walls);
			
			final int typeContainer = 0;
			final int typeFurniture = 1; 
			
			//line = br.readLine();
			while((line = br.readLine()) != null){
				tokens = line.split(" ");
				int objectType = Integer.parseInt(tokens[0]);
				int type = Integer.parseInt(tokens[1]);
				int x = Integer.parseInt(tokens[2]);
				int y = Integer.parseInt(tokens[3]);
				int z = Integer.parseInt(tokens[4]);
				int index = Integer.parseInt(tokens[5]);
				
				if(objectType == typeContainer){
					Container container = new Container(type,x,y,z,index);
					
					for(int i=6;i<tokens.length;i++){
						int color = Integer.parseInt(tokens[i]);
						Orb orb = new Orb(color);
						container.addItem(orb);
					}
					
					room.setContainer(container);
				}else if(objectType == typeFurniture){
					System.out.println("get furniture");
					Furniture furniture = new Furniture(type, x, y, z, index);
					
					room.setFurniture(furniture);
				}
				
				
				
			}
			
			return room;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		
		return null;
	}
}

