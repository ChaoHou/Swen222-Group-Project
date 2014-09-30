package ui;

import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestUI {
	
	public TestUI(MouseListener listener){
		JFrame frame = new JFrame();
		frame.addMouseListener(listener);
		frame.setSize(100,100);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
