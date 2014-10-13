package ui;

import gameworld.Collectable;
import gameworld.HealthPack;
import gameworld.Orb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ItemIcon extends ImageIcon {

	/**
	 * This class returns the proper image icon of an item.
	 * I made this class as it will be used in several classes, GameMenu and containerMenu
	 * The icon that will be returned are:
	 * - Orbs and "Checked" Orbs
	 * - Health Potions and "Checked" Health Potions
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
		
	public ItemIcon(Collectable c, Boolean isChecked){
		BufferedImage img = null;
		try {
			if (c instanceof Orb){
				if(((Orb) c).getColor()==((Orb) c).BLUE){
					if(isChecked){
					    img = ImageIO.read(new File("src/blueOrbChecked.png"));
					}
					else{
						img = ImageIO.read(new File("src/blueOrb.png"));
					}
				}
				else if(((Orb) c).getColor()==((Orb) c).GREEN){
					if(isChecked){
						img = ImageIO.read(new File("src/greenOrbChecked.png"));
					}
					else{
						img = ImageIO.read(new File("src/greenOrb.png"));
					}
				}
				else if(((Orb) c).getColor()==((Orb) c).RED){
					if(isChecked){
						img = ImageIO.read(new File("src/redOrbChecked.png"));
					}
					else{
						img = ImageIO.read(new File("src/redOrb.png"));
					}
				}
			}
			else if (c instanceof HealthPack){
				if(isChecked){
					img = ImageIO.read(new File("src/healthChecked.png"));
				}
				else{
					img = ImageIO.read(new File("src/health.png"));
				}
			}
		}
		catch (IOException e) {
		}
		this.setImage(img);
	}

}
