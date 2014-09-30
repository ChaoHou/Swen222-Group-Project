package UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import networking.Player;


public class GameFrame extends JFrame {
	
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	
	//Stuff to use from Gameworld Package
	private Board board;
	private int uid;
	private Player player;
	//This is to check if a game's running or not
	private boolean runningGame = false;
	//These are images to be used
	private Image instructions;
	private Image map;
	private Image poster;
			
		/**
		 * This is the constructor for the Actual JFrame
		 * It starts off with one Panel, the GameMenu screen
		 * @param player 
		 * @param uid 
		 * @param string 
		 */
			
		public GameFrame(String string, Board board, int uid, Player player){		
			//Game logic's information
			this.setBoard(board);
			this.setUid(uid);			
			this.player = player;
			//Layout
			FlowLayout fullMenu = new FlowLayout();
			fullMenu.setAlignment(100);				
			fullMenu.setHgap(0);		
			fullMenu.setAlignment(FlowLayout.CENTER);
			this.setLayout(fullMenu);	
			//Sets up the menu bar
		    JMenuBar menubar = new JMenuBar();
		    JMenu help = new JMenu("Help");
		    menubar.add(help);
		    this.setJMenuBar(menubar);
		    //Set up the instructions and map(while keeping them invisible)
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
			this.getContentPane().add(MainMenu);
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
			JPanel render = new JPanel();
			render.setPreferredSize(new Dimension (1000, 480));
			render.setBackground(Color.white);
			GameMenu game = new GameMenu(this);			
			this.getPanels().put("render", render);
			this.getPanels().put("game", game);
			this.getContentPane().remove(getPanels().get("menu"));			
			this.getContentPane().add(render);
			this.getContentPane().add(game);
			this.setRunningGame(true);
		    this.repaint();
		}
		
		/**
		 * This method's for showing the instruction menu
		 * @return
		 */
		public void showInstructions(){					
			if(isRunningGame()){
				this.getPanels().get("render").setVisible(false);
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
			
			
		}
		
		/**
		 * This methods shows the game if it's not appearing
		 */
		
		public void showGame(){
			if(isRunningGame()){
				this.getPanels().get("render").setVisible(true);
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
