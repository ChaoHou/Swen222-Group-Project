package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
	
/**
 * This class is responsible for showing a game over screen.
 * Depending whether the player won or lost, the information shown will differ.
 * 
 * Created by: Raul John Immanuel De Guzman 
 * ID: 300269955
 *
 */


public class GameOverScreen extends PopUpScreen {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton backMainMenu;
	private boolean playerWon = false;


	public GameOverScreen(String name, GameFrame game, Boolean playerWon){	
		
		super(name, game);		
		//Buttons
		backMainMenu = new JButton("To Main Menu");
		//Action Listeners for buttons		
		//ButtonListener b = new ButtonListener();
		backMainMenu.addActionListener(getGame().getPlayer());		
		getGame().getScreenButtons().put(backMainMenu, "backMainMenu");
		try {
			if(playerWon == true){
				img = ImageIO.read(new File("resources/images/UI/victory.jpg"));
			}
			else{
				img = ImageIO.read(new File("resources/images/UI/gameover.jpg"));
			}	
		} 
		catch(IOException e) {		
		}
		
		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setBackground(Color.black);
		
		//Game over screen
		String info = "GAME OVER";
		if(playerWon){
			info = "You escaped the mansion!";	
		}
		JLabel credits = new JLabel("<html> Credits: <br> "
				+ " <br> Raul John Immanuel De Guzman: User Interface "
				+ " <br> Peide Ng: Game Logic"
				+ " <br> Kyouhei: Rendering"
				+ " <br> Chao: Networking"
				+ " <br> All images were obtained from the internet, with the exception of"
				+ "the title drawing, map, and custom buttoms, made by Raul. "
				+ " </html> ", JLabel.CENTER);
		credits.setForeground(Color.white);
		credits.setPreferredSize(new Dimension(1000, 150));
		JLabel title = new JLabel(info, JLabel.CENTER);
		title.setPreferredSize(new Dimension(1000, 40));
		title.setForeground(Color.white);
		
		
		
		this.add(title);
		if(playerWon)
		this.add(credits);
		this.add(backMainMenu);
		this.remove(getBackToGame());
		this.repaint();
		
	}

	public boolean isPlayerWon() {
		return playerWon;
	}

	public void setPlayerWon(boolean playerWon) {
		this.playerWon = playerWon;
	}	

	
}