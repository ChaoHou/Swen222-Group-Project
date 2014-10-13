package ui;

import gameworld.Collectable;
import gameworld.HealthPack;
import gameworld.Orb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class itemIcon extends ImageIcon {

	/**
	 * This class returns the proper image icon of an item.
	 * I made this class as it will be used in several classes, GameMenu and containerMenu
	 * Stuff:
	 * - Orbs
	 * - A Health Potion
	 */
	
	private static final long serialVersionUID = 1L;
	
	public itemIcon(Collectable c){
		//The constructor is passed 	
		BufferedImage img = null;
		try {
			if (c instanceof Orb){
				if(((Orb) c).getColor()==((Orb) c).BLUE){
					img = ImageIO.read(new File("src/blueOrb.png"));
				}
				else if(((Orb) c).getColor()==((Orb) c).GREEN){
					img = ImageIO.read(new File("src/greenOrb.png"));
				}
				else if(((Orb) c).getColor()==((Orb) c).RED){
					img = ImageIO.read(new File("src/redOrb.png"));
				}
			}
			else if (c instanceof HealthPack){
				img = ImageIO.read(new File("src/health.png"));
			}
		}
		catch (IOException e) {
		}
		this.setImage(img);
	}

}
