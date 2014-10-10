package ui;
import gameworld.Container;
import gameworld.HealthPack;
import gameworld.Orb;
import control.Player;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

import rendering.Renderer;
//import networking.Player;


public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	//Stuff from gameworld package
	private Board board;
	private int uid;
	private boolean runningGame;
 	private ActionListener player;
 	//Stuff from rendering package
	private Renderer renderer;
	private GLCanvas canvas;
	private mapMenu map;
	
		/**
		 * This is the constructor for the Actual JFrame
		 * It starts off with one Panel, the GameMenu screen
		 */
			
		public GameFrame(String string, Board board, int uid, ActionListener player,Renderer renderer){
			 ((Player) player).setFrame(this);
					 
			//Background	
			this.setLayout(new GridLayout());
			BufferedImage img3 = null;
			try {
				img3 = ImageIO.read(new File("src/wallpaper.jpg"));
			} catch (IOException e) {
			}
			JLabel x = new JLabel();
			x.setIcon(new ImageIcon (img3));
			x.setLayout(new FlowLayout());
			this.setContentPane(x);
			//Game logic's information
			this.setBoard(board);
			this.setUid(uid);			
			this.player = player;
			this.renderer = renderer;	
			//Sets up the menu bar
		    JMenuBar menubar = new JMenuBar();
		    JMenu help = new JMenu("Help");
		    menubar.add(help);
		    this.setJMenuBar(menubar);
		    //Set up the instructions 
		    instructionsMenu menu = new instructionsMenu(this);	
			this.getPanels().put("instructions", menu);	
			this.getContentPane().add(menu);
		    this.getPanels().get("instructions").setVisible(false);	 
			//Opens up the main menu
			setMainMenu();		
		}
		
		/**
		 * This method sets up the main menu:
		 */
		
		public void setMainMenu(){
			//Make sure nothing's in the frame right now...
			//this.getContentPane().removeAll();		
			this.setRunningGame(false);
			this.repaint();
			//Set up the main menu now:
		    MainMenu MainMenu = new MainMenu(this);	
			getPanels().put("menu", MainMenu);  
			this.getContentPane().add(MainMenu, BorderLayout.LINE_START);
			this.setTitle("Vampire Mansion");
			this.setSize(1000,740);
			this.setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		/**
		 * This method sets up a new Game:
		 */
		
		public void setGame(){
			//TEMP
			this.getBoard().getVamp(this.getUid()).setHealth(3);
			this.getBoard().getVamp(this.getUid()).getInventory().add(new HealthPack());
			this.getBoard().getVamp(this.getUid()).getInventory().add(new Orb(1));

			//Layout
			this.setLayout(new BorderLayout());
		    //Rendering
			GLProfile glprofile = GLProfile.get(GLProfile.GL2);
	        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
	        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
	        glcanvas.addGLEventListener(renderer);    
	        FPSAnimator animator= new FPSAnimator(glcanvas,60);
	        animator.start();
			//Game menu (The Game's interface)
			GameMenu game = new GameMenu(this, player);		
			//Putting everything into the JFrame
			this.getPanels().put("game", game);			
			this.getContentPane().remove(getPanels().get("menu"));		
			this.canvas = glcanvas;
			this.getContentPane().add(glcanvas, BorderLayout.CENTER);
			this.getContentPane().add(game, BorderLayout.SOUTH);
			this.runningGame = true;
		    this.repaint();
		    map = new mapMenu(this);
		   // ((Thread) player).start();

		}
		
		/**
		 * This method's for showing the instruction menu
		 * @return
		 */
		public void showInstructions(){					
			if(isRunningGame()){
				this.canvas.setVisible(false);
				this.getPanels().get("game").setVisible(false);
				this.getPanels().get("instructions").setVisible(true);
			}
			else{
				this.getPanels().get("menu").setVisible(false);	
				this.getPanels().get("instructions").setVisible(true);
			}
			
		}
		
		/**
		 * This method's for a detailed map
		 * @return
		 */
		public void showMap(){
			    map = new mapMenu(this);
				this.getPanels().put("map", map);
				this.getContentPane().add(map);
 				this.canvas.setVisible(false);			
				this.repaint();
		}
		
		/**
		 * This methods shows the game if it's not appearing
		 */
		
		public void showGame(){
			if(this.getPanels().containsKey("map")){
				this.getPanels().get("map").setVisible(false);
				this.getPanels().remove("map");		
				
			}	
			else if(this.getPanels().containsKey("con")){
				this.getPanels().get("con").setVisible(false);
				this.getPanels().remove("con");					
			}			
			if(isRunningGame()){
				this.canvas.setVisible(true);
				this.getPanels().get("game").setVisible(true);
			}	
			this.repaint();
			
		}
		
		/**
		 * This method opens up a trade panel, 
		 * This refers to when a player clicks a container
		 * 1.) The two fields are two 
		 */
		
		public void showTrade(Container c){			
			    containerMenu CM = new containerMenu(this, c);
				this.getPanels().put("con", CM);
				this.getContentPane().add(CM);
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
		
        public mapMenu getMapPanel(){
        	return this.map;
        }
	
}
