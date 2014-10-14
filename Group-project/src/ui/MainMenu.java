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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.GameMenu.NavigationPanel;

/**
 * This Panel represents the "Main Menu" 
 * The Main Menu is what first appears when you start the game. It will ask you to:
 * 1.) Start a new Game
 * 2.) See the Instructions
 *
 * @author Raul John De Guzman-
 */

public class MainMenu extends JPanel {	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isNewGame = false;
	private instructionScreenMain inst;
	private GameFrame game;
	private MainMenu menu;
	private Map<String, JButton> buttons = new HashMap<String, JButton>();

	public MainMenu(GameFrame board){	
		menu = this;
		this.game= board;
		inst = new instructionScreenMain(game);
		BufferedImage img = null;
		BufferedImage img2 = null;
		BufferedImage img3 = null;	
		BufferedImage img4 = null;
		try {
			img = ImageIO.read(new File("src/newgame1.png"));
			img2 = ImageIO.read(new File("src/newgame2.png"));
			img3 = ImageIO.read(new File("src/Instructions1.png"));	
			img4 = ImageIO.read(new File("src/Instructions2.png"));
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
		this.setPreferredSize(new Dimension(200,200));
		this.setBackground(Color.black);
	    this.setLayout(new FlowLayout());
	    
	    //Adding the buttons
	    JPanel x = new JPanel();
	    x.add(newGame);
	    x.setBackground(Color.black);
	    x.add(instructions);
	    x.setLayout(new FlowLayout());
	    x.setPreferredSize(new Dimension (150, 400));
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



