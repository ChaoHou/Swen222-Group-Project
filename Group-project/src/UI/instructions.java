package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class instructions extends JPanel{
	/**
	 * This is the instructions menu for the game. It can be opened:
	 * -Any time playing the game
	 * -On the main menu 
	 */
	private static final long serialVersionUID = 1L;
	private JButton back;
	private JPanel instructionsPanel;
	
	public instructions(){		
	    JLabel label1 = new JLabel("Turn Around", JLabel.CENTER);
		//Buttons
		back = new JButton("Go Back");
		//Action Listeners for buttons		
		ButtonListener b = new ButtonListener();
		back.addActionListener(b);
		//Setting up the directionalPanel
		instructionsPanel = new JPanel();
		instructionsPanel.add(label1);
		instructionsPanel.setPreferredSize(new Dimension(300,200));
		instructionsPanel.setBackground(Color.gray);
		instructionsPanel.add(back);
	
		//Setting up the outmost Panel
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setPreferredSize(new Dimension(500,500));
		this.setBackground(Color.LIGHT_GRAY);
		this.add(instructionsPanel);
		

	}	
	
	
	//Actions Listener for the buttons		
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == back){
				setVisible(false);
			}
	
		}
	}	
}
