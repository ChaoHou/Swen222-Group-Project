package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This Panel represents the "Main Menu" 
 * The Main Menu is what first appears when you start the game. It will ask you to:
 * 1.) Start a new Game
 * 2.) See the Instructions
 * 
 * Keep in mind, the game has not started up when navigating this menu. So it gets its own
 * ActionListeners...
 *
 * @author Raul John De Guzman
 * ID: 300269955
 */

public class MainMenu extends JPanel {	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isNewGame = false;
	private InstructionScreenMain inst;
	private GameFrame game;
	private Map<String, JButton> buttons = new HashMap<String, JButton>();

	public MainMenu(GameFrame board){	
		this.game= board;
		inst = new InstructionScreenMain(game);
		
		//These Images were made to help make the game look authentic:
		BufferedImage img = null;
		BufferedImage img2 = null;
		BufferedImage img3 = null;	
		BufferedImage img4 = null;
		BufferedImage img5 = null;
		try {
			img = ImageIO.read(new File("resources/images/UI/newgame1.png"));
			img2 = ImageIO.read(new File("resources/images/UI/newgame2.png"));
			img3 = ImageIO.read(new File("resources/images/UI/Instructions1.png"));	
			img4 = ImageIO.read(new File("resources/images/UI/Instructions2.png"));
			img5 = ImageIO.read(new File("resources/images/UI/title.png"));
		} catch (IOException e) {
		}
		
		//Buttons
		JButton newGame, instructions;		
		newGame = new JButton();
		newGame.setIcon(new ImageIcon(img));
        newGame.setRolloverIcon(new ImageIcon(img2));
        newGame.setPreferredSize(new Dimension(150,50));
        newGame.setBorder(null);	
		instructions = new JButton();
		instructions = new JButton();
		instructions.setIcon(new ImageIcon(img3));
        instructions.setPreferredSize(new Dimension(150,50));
        instructions.setRolloverIcon(new ImageIcon(img4));
        instructions.setBorder(null);
		buttons.put("newGame", newGame);
		buttons.put("instructions", instructions);
		//Action Listeners for buttons		
		ButtonListener b = new ButtonListener();
		newGame.addActionListener(b);
		instructions.addActionListener(b);			
		
		//Setting up the Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setPreferredSize(new Dimension(800,705));
		this.setBackground(Color.black);
	    this.setLayout(new FlowLayout());
	    
	    JLabel title = new JLabel();
		title.setIcon(new ImageIcon (img5));
		title.setLayout(new FlowLayout());
	    		
	    
	    //Adding the buttons
	    JPanel x = new JPanel();
	    x.setLayout(new FlowLayout());
	    x.setPreferredSize(new Dimension (700, 700));
	    x.add(title);
	    x.add(newGame);
	    x.setBackground(Color.black);
	    x.add(instructions);  
		this.add(x, BorderLayout.NORTH);	
	}	
	

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}


	//Actions Listener for the buttons		
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == buttons.get("newGame")){
				    setNewGame(true);
				    //Starts a new game!
					game.setGame();
					game.getBoard().startGame();
				
					game.setVisible(true);	
					updateUI();
			}
			else if(event.getSource() == buttons.get("instructions")){
				game.getPanels().put("instructions", inst);
 				game.getContentPane().add(inst);
 				game.showInstructionsMenu();
 				game.setVisible(true);			
				updateUI();
			}
		
		}
	}	



}



