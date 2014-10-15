package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

     /**
	 * This is a second instruction screen.
	 * This one is primarily for the main menu
	 * This was made to avoid complications with the networking
	 * 
	 * Created by: Raul John Immanuel De guzman
	 * ID: 300269955
	 * 
	 */

public class InstructionScreenMain extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	//There's a back button
	private JButton backToMain;
	//You will always to use the GameFrame
	private GameFrame game;
	private BufferedImage img;
	
	public InstructionScreenMain(GameFrame game){	
		this.game = game;	
		backToMain = new JButton("Go Back to Main Menu");
		ButtonListener b = new ButtonListener();
		backToMain.addActionListener(b);	

		try {
			img = ImageIO.read(new File("resources/images/UI/instructions.png"));
		} catch(IOException e) {		
		}

		JLabel label1 = new JLabel("", JLabel.CENTER);
		label1.setIcon(new ImageIcon(img));


		//Setting up the outmost Panel
		this.add(label1);
		this.add(backToMain);
		this.setPreferredSize(new Dimension(1000,500));
		this.setBackground(Color.black);
		this.repaint();

	}	


	public GameFrame getGame() {
		return game;
	}

	public void setGame(GameFrame game) {
		this.game = game;
	}


	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == backToMain){
				game.getPanels().get("instructions").setVisible(false);	
				game.getPanels().get("menu").setVisible(true);	
				game.repaint();				    
				updateUI();
			}						
		}
	}	
}
