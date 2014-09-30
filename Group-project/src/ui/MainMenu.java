package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
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
		
		
		
		//Buttons
		JButton newGame, instructions, changeRoom;		
		newGame = new JButton("New Game");
		instructions = new JButton("Instructions");
		changeRoom = new JButton("Credits");
		
		buttons.put("newGame", newGame);
		buttons.put("instructions", instructions);
		buttons.put("changeRoom", changeRoom);
		
		//Action Listeners for buttons		
		ButtonListener b = new ButtonListener();
		newGame.addActionListener(b);
		instructions.addActionListener(b);
		changeRoom.addActionListener(b);	
		
		//Setting up the Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setPreferredSize(new Dimension(200,700));
		this.setBackground(Color.LIGHT_GRAY);
		
		
		this.add(newGame);
		this.add(instructions);
		this.add(changeRoom);
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
				//currentGame.get
				currentGame.showInstructions();
				currentGame.setVisible(true);	
				updateUI();

			}
			else if(event.getSource() == buttons){
				//Use room
				String answer = (String) JOptionPane.showInputDialog(null, "Which Room would you like to go to?", null, 
						JOptionPane.PLAIN_MESSAGE, null, new String[]{ "d", "dd"}, null);

				System.out.println("You moved to Room: " + answer );
			}	
		}
	}	



}


