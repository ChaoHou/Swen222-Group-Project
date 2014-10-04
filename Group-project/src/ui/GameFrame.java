package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

import rendering.Renderer;
import rendering.RendererTest;
import networking.Player;


public class GameFrame extends JFrame {
	
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	
	//Stuff from gameworld package
	private Board board;
	private int uid;
	private boolean runningGame;
 	private ActionListener player;
 	//Stuff from rendering package
	private Renderer renderer;
	private GLCanvas canvas;
	
		/**
		 * This is the constructor for the Actual JFrame
		 * It starts off with one Panel, the GameMenu screen
		 */
			
		public GameFrame(String string, Board board, int uid, ActionListener player,Renderer renderer){
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
		    //Set up the instructions and map(while keeping them invisible)
		    instructionsMenu menu = new instructionsMenu(this);	
		    mapMenu map = new mapMenu(this);
			this.getPanels().put("instructions", menu);	
			this.getPanels().put("map", map);
			this.getContentPane().add(menu);
			this.getContentPane().add(map);
		    this.getPanels().get("instructions").setVisible(false);	 
		    this.getPanels().get("map").setVisible(false);
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
			    mapMenu map = new mapMenu(this);
				this.getPanels().put("map", map);
				this.getContentPane().add(map);
 				this.canvas.setVisible(false);
				this.getPanels().get("game").setVisible(false);
				this.getPanels().get("map").setVisible(true);
				this.repaint();
		}
		
		/**
		 * This methods shows the game if it's not appearing
		 */
		
		public void showGame(){
			if(isRunningGame()){
				this.canvas.setVisible(true);
				this.getPanels().get("game").setVisible(true);
			}	
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

}
