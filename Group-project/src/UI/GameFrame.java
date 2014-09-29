package UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameFrame extends JFrame {
	

	private Map<String, JPanel> panels = new HashMap<String, JPanel>();

	
	
		public static void main(String args []){
			GameFrame gg = new GameFrame();
	        gg.setVisible(true);
	        	         
	        
	        while(1 == 1){
	        	//The player clicked New game
	        	if(((MainMenu) gg.panels.get("menu")).isNewGame()){
	        		System.out.println("Starting a new game");
	        		gg.setGame();
	        		gg.setVisible(true);
	        		break;
	        	}   
	        	//The player clicker Instructions
	        	//else if(((MainMenu) gg.panels.get("instructions")).isNewGame()){
	        		
	        //	}
	        	
	        	
	        	
	        	
	        }
		}
		
		
		/**
		 * This is the constructor for the Actual JFrame
		 * It starts off with one Panel, the GameMenu screen
		 */
		
		
		public GameFrame(){
			//Layout
			FlowLayout fullMenu = new FlowLayout();
			fullMenu.setAlignment(100);				
			fullMenu.setHgap(0);		
			fullMenu.setAlignment(FlowLayout.CENTER);
			this.setLayout(fullMenu);	

			//Set up the main menu
			MainMenu MainMenu = new MainMenu();	
			panels.put("menu", MainMenu);  
			
			//Start up at the main menu first:
			this.getContentPane().add(MainMenu);
			this.setTitle("Vampire Mansion");
			this.setSize(1000,740);
			this.setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
			
		}
		
		/**
		 * This sets up a new Game:
		 */
		
		public void setGame(){
			JPanel render = new JPanel();
			render.setPreferredSize(new Dimension (1000, 500));
			render.setBackground(Color.white);
			GameMenu game = new GameMenu();
			
			this.panels.put("render", render);
			this.panels.put("game", game);
			
			this.getContentPane().remove(panels.get("menu"));
			
			this.getContentPane().add(render);
			this.getContentPane().add(game);
		    this.repaint();

			
		}


		



}
