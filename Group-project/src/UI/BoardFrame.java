
package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardFrame extends JFrame {
	private final JPanel canvas=new JPanel();
	
	public BoardFrame(String title, Board game, int uid, KeyListener... keys) {
		super(title);
		
		System.out.println("In boardFrame");
				
		canvas.setPreferredSize(new Dimension(100,100));
		for(KeyListener k : keys) {
			canvas.addKeyListener(k);
		}		
		add(canvas);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);						

		pack();
		setResizable(false);						
	

		// Display window
		setVisible(true);		
		canvas.requestFocus();
	}
	
	public void repaint() {
		canvas.repaint();
	}		
}
