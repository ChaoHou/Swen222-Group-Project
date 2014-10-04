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
 * -Instructions is an inner class in Main Menu
 * 
 *
 * @author Raul John De Guzman-
 */

public class MainMenu extends JPanel {	 
	
	private boolean isNewGame = false;
	private boolean isInstructions = false;
	private boolean isCredits = false;
	
	private GameFrame currentGame;
	private MainMenu menu;
	
	private Map<String, JButton> buttons = new HashMap<String, JButton>();

	public MainMenu(GameFrame board){	
		menu = this;
		this.currentGame= board;
			
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
		//this.add(instructions, BorderLayout.NORTH);
		
	}	
	

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	public boolean isInstructions() {
		return isInstructions;
	}

	public void setInstructions(boolean isInstructions) {
		this.isInstructions = isInstructions;
	}

	public boolean isCredits() {
		return isCredits;
	}

	public void setCredits(boolean isCredits) {
		this.isCredits = isCredits;
	}


	//Actions Listener for the buttons		
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == buttons.get("newGame")){
				setNewGame(true);
				//Starts a new game!
				String answer = (String) JOptionPane.showInputDialog(null, "Select a mode", null, 
						JOptionPane.PLAIN_MESSAGE, null, new String[]{ "Single Player", "MultiPlayer"}, null);

				if(answer.equals("Single Player")){
					currentGame.setGame();
					currentGame.getBoard().startGame();
					currentGame.setVisible(true);	
					updateUI();
				}
				else if(answer.equals("Multiplayer")){
					System.out.println("No");

				}
				
			}
			else if(event.getSource() == buttons.get("instructions")){
				System.out.println("You clicked instructions");
				currentGame.showInstructions();
				currentGame.setVisible(true);	
				updateUI();

			}
	
		
		}
	}	



}



