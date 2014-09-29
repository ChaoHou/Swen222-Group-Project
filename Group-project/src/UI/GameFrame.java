package UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import networking.Player;


public class GameFrame extends JFrame {
	
	private Map<String, JPanel> panels = new HashMap<String, JPanel>();
	
	//Stuff to use from gameworld...
	private Board board;
	private int uid;
	//private Player player; we don't need a player here, we will pass in an actionlistener
	private ActionListener player;
			
		/**
		 * This is the constructor for the Actual JFrame
		 * It starts off with one Panel, the GameMenu screen
		 * @param player 
		 * @param uid 
		 * @param string 
		 */
			
		public GameFrame(String string, Board board, int uid, ActionListener player){
			
			//Game logic's information
			this.setBoard(board);
			this.uid = uid;			
			this.player = player;
			
			
			//Layout
			FlowLayout fullMenu = new FlowLayout();
			fullMenu.setAlignment(100);				
			fullMenu.setHgap(0);		
			fullMenu.setAlignment(FlowLayout.CENTER);
			this.setLayout(fullMenu);	

			//Set up the main menu
			MainMenu MainMenu = new MainMenu(this);	
			getPanels().put("menu", MainMenu);  
			
			//Start up at the main menu first:
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
			render.setPreferredSize(new Dimension (1000, 500));
			render.setBackground(Color.white);
			GameMenu game = new GameMenu(board, uid, player);			
			this.getPanels().put("render", render);
			this.getPanels().put("game", game);
			this.getContentPane().remove(getPanels().get("menu"));			
			this.getContentPane().add(render);
			this.getContentPane().add(game);
		    this.repaint();
		}
		
		/**
		 * This method's for the instruction menu
		 * @return
		 */
		public void showInstructions(){
			
			
		}
		
		/**
		 * This method's for a detailed map
		 * @return
		 */
		public void showMap(){
			
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


		



}
