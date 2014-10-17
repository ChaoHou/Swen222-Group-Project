package ui;
import gameworld.Collectable;
import gameworld.Container;
import gameworld.Furniture;
import gameworld.Room;
import gameworld.Vamp;
import gameworld.Werewolf;
import control.Player;
import control.PlayerInterface;
import control.WerewolfThread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

import rendering.Renderer;
import ui.GameMenu.StatsPanel;

/**
 * This is the actual JFrame.
 * What first happens is that it is initialized to get all the information from the other
 * packages, such as the board's information (the actual game), the renderer/canvas (The game's visuals)
 * and an action listener (to comply with multiplayer capacities)
 * 
 * The JFrame primarily handles these classes:
 * 1.) Menus (JPanels primarily used for interaction)
 * - The Main Menu (The starting JPanel you see)
 * - Game Menu (The actual game's "controls"). 
 * 2.) Pop up Screens (JPanels primarily used to show information, and some interaction)
 * - An Instruction screen
 * - Hiding Screen
 * - Container Screen (A screen when a player takes/puts items into a container)
 *     
 * The JFrame is mainly responsible for hiding and showing info, such as hiding the rendering/canvas
 * in order to show the instructions, then to show the canvas again once done. The JFrame
 * keeps track of the JPanel (PopupScreen) so that it can handle hiding it easily
 *     
 * The JFrame also keeps track of all the game's buttons, this is so because some actions
 * for the player class need the JFrame. The JFrame's info is passed to the Actionlistener
 * once setGame() is called.    
 *     
 *   
 * @author Raul John Immanuel De Guzman-
 * ID: 300269955
 *
 */



public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();	 
	//This keeps track on the popups on the frame right now
 	private PopUpScreen currentScreen;
 	//This keeps track of the buttons on PopUps.
	private Map<JButton, String> screenButtons = new HashMap<JButton, String>();
	//This keeps track of the buttons on the main menu
	private Map<JButton, String> mainMenuButtons = new HashMap<JButton, String>();
	//Stuff from gameworld package
	private Board board;
	private int uid;
	public boolean runningGame;
	public boolean playerAlive;
	public boolean victory;
 	private ActionListener player;
 	//Stuff from rendering package
	private Renderer renderer;
	private GLCanvas canvas;
	private MapScreen map;
	
		
		public GameFrame(String string, Board board, int uid, ActionListener player,Renderer renderer){ 
			this.addKeyListener((KeyListener) player);	
			this.setFocusable(true);
			//Background and Layout adjusting	
			this.setLayout(new GridLayout());		
			JLabel x = new JLabel();
			x.setLayout(new FlowLayout());
			x.setPreferredSize(new Dimension(600, 600));
			this.setContentPane(x);		
			this.setBackground(Color.black);
			//Game logic's information
			this.setBoard(board);
			this.setUid(uid);			
			this.setPlayer(player);
			this.renderer = renderer;	
		    //Set up the instructions 
		    InstructionsScreen menu = new InstructionsScreen("instructions", this);		    
			this.getPanels().put("instructions", menu);	
			this.getContentPane().add(menu);
		    this.getPanels().get("instructions").setVisible(false);	 
			//Opens up the main menu
		    //Initialize Main menu/Jframe and show only the main menu
		    this.setRunningGame(false);
			this.repaint();	
			//Initialize all the panels about to be used 
		    MainMenu MainMenu = new MainMenu(this);	
			getPanels().put("menu", MainMenu);  
			GameOverScreen over = new GameOverScreen("over", this, false);
			getPanels().put("over", over);
			//Set up the JFrame now:
			this.getContentPane().add(MainMenu, BorderLayout.LINE_START);
			this.setTitle("Vampire Mansion");
			this.setSize(1000,740);
			this.setResizable(true);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);			
		}
		
		/**
		 * This method sets up the main menu:
		 */
		
		public void showMainMenu(){	
			this.getPanels().get("menu").setVisible(true);
			
		}
		
		/**
		 * This method opens up the instructions (from the main menu, not the game!)
		 */
		
		public void showInstructionsMenu(){	
			this.getPanels().get("menu").setVisible(false);
			this.getPanels().get("instructions").setVisible(true);
			this.repaint();
		}
		
		/**
		 * This method sets up a new Game:
		 */
		
		public void setGame(){
			//Temp
			this.board.getVamp(uid).setHealth(5);
		
			//Sets up the menu bar
		    JMenuBar menubar = new JMenuBar();
		    JMenu help = new JMenu("Help");	
		    JMenuItem showInstructions = new JMenuItem("Show Instructions");
		    showInstructions.addActionListener(getPlayer());
		    help.add(showInstructions);
		    menubar.add(help);
		    
		    //Sets up the cheat codes
		    JMenu cheats = new JMenu("Cheats");	
		    JMenuItem instantDeath = new JMenuItem("Commit Suicide");
		    JMenuItem allItems = new JMenuItem("Give me all Items");
		    JMenuItem hideNow = new JMenuItem("Hide into Nothingness");
		    instantDeath.addActionListener(getPlayer());
		    allItems.addActionListener(getPlayer());
		    hideNow.addActionListener(getPlayer());
		    cheats.add(instantDeath);
		    cheats.add(allItems);
		    cheats.add(hideNow);
		    menubar.add(cheats);
		    
		    this.setJMenuBar(menubar);
			
			//Layout
			this.setLayout(new BorderLayout());
		    //Rendering
			GLProfile glprofile = GLProfile.get(GLProfile.GL2);
	        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
	        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
	        glcanvas.addGLEventListener(renderer);    
	        glcanvas.addMouseListener((MouseListener)player);
	        FPSAnimator animator= new FPSAnimator(glcanvas,60);
	        animator.start();
			//Game menu (The Game's interface)
			GameMenu game = new GameMenu(this, getPlayer());		
			//Putting everything into the JFrame
			this.getPanels().put("game", game);			
			this.getPanels().get("menu").setVisible(false);
 			this.canvas = glcanvas;
			this.getContentPane().add(glcanvas, BorderLayout.CENTER);
			this.getContentPane().add(game, BorderLayout.SOUTH);
			this.runningGame = true;
			this.setBackground(Color.GRAY);
		    this.repaint();

		    map = new MapScreen("map",this);
		    //Specific ActionListeners and thread stuff will run now

		    if(player instanceof Player){
		    	Werewolf werewolf=new Werewolf(board);
				board.registerWerewolf(werewolf);
				WerewolfThread werewolfThread = new WerewolfThread(werewolf);
				werewolfThread.start();
		    }
 			((PlayerInterface) getPlayer()).setFrame(this);
		    ((Thread) getPlayer()).start();

		}
		
				
		/**
		 * This method is responsible for initializing the requested pop up.
		 * It obscures the canvas, and disables the gamemenu's buttons as well.
		 * 
		 */
		
        public void showPopUp(PopUpScreen screen){ 
        	currentScreen = screen;
        	if(runningGame != true){
    			this.getPanels().get("menu").setVisible(false);
        		this.getPanels().put(screen.getName(), screen);
            	this.getContentPane().add(screen);
    			this.repaint();	
        	}
        	else{
        		this.getPanels().put(screen.getName(), screen);
        		this.getContentPane().add(screen);
        		this.canvas.setVisible(false);	
        		((GameMenu) this.getPanels().get("game")).disableButtons();
        		this.repaint();					
        	}
		}
        
    	/**
		 * This method is responsible for removing a PopUp that is on the frame, 
		 * It draws the canvas back, and enables the gamemenu's buttons again as well.
		 * 
		 */
		
		public void showGame(PopUpScreen screen){	
			currentScreen = null;
			if(this.getPanels().containsKey(screen.getName())){
			    this.getPanels().get(screen.getName()).setVisible(false);	
			    this.getPanels().remove(screen.getName());	
			}		
			if(isRunningGame()){
				this.canvas.setVisible(true);
				((GameMenu) this.getPanels().get("game")).enableButtons();
			    this.repaint();		
			}	
			else{
    			this.getPanels().get("menu").setVisible(true);
				this.showMainMenu();
			    this.repaint();		
			}
		}
		
				
		/**
		 * This method opens up a trade panel, 
		 * This is similar to showPopUp, only in that a container is passed within.
		 * 
		 */
		
		public void showTrade(Container c){		
			    ContainerScreen CM = new ContainerScreen("container",this, c);
			    currentScreen = CM;
				this.getPanels().put("container", CM);					
				this.getContentPane().add(CM);
				this.canvas.setVisible(false);			
				((GameMenu) this.getPanels().get("game")).disableButtons();
			    this.repaint();    

		}
		
		/**
		 * This method opens up a hiding screen.
		 * This is similar to showPopup, however it's passed in furniture 
		 * (Which may even have another player too!)
		 * 
		 */
		public void showHidingScreen(){				
			
		    HidingScreen CM = new HidingScreen("furniture",this);
		    currentScreen = CM;
			this.getPanels().put("furniture", CM);						
			this.getContentPane().add(CM);		
			//f.hidePlayer(board.getVamp(uid));
			board.getVamp(uid).setHiding(true);
	
			this.canvas.setVisible(false);			
			((GameMenu) this.getPanels().get("game")).disableButtons();
		    this.repaint();    

	}
		
		
		/**
		 * 
		 * In a thread this will be called every time.
		 * 
		 */
		
		public void showGameOver(){
				this.getContentPane().add(this.getPanels().get("over"));
				this.canvas.setVisible(false);
				this.repaint();	 		
		}
		

		public Map<String, JPanel> getPanels() {
			return panels;
		}


		public void setPanels(Map<String, JPanel> panels) {
			this.panels = panels;
		}

		public Board getBoard() {
			return board;
		}

		public void setBoard(Board board) {
			this.board = board;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public boolean isRunningGame() {
			return runningGame;
		}

		public void setRunningGame(boolean runningGame) {
			this.runningGame = runningGame;
		}
		
        public MapScreen getMapPanel(){
        	return this.map;
        }

		public ActionListener getPlayer() {
			return player;
		}

		public void setPlayer(ActionListener player) {
			this.player = player;
		}

		public Map<JButton, String> getMainMenuButtons() {
			return mainMenuButtons;
		}

		public void setMainMenuButtons(Map<JButton, String> mainMenuButtons) {
			this.mainMenuButtons = mainMenuButtons;
		}

		public Map<JButton, String> getScreenButtons() {
			return screenButtons;
		}

		public void setScreenButtons(Map<JButton, String> screenButtons) {
			this.screenButtons = screenButtons;
		}

		public PopUpScreen getCurrentScreen() {
			return currentScreen;
		}

		public void setCurrentScreen(PopUpScreen currentScreen) {
			this.currentScreen = currentScreen;
		}
	
}
