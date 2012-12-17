package com.confedicats.ld25.maps;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.weapons.Weapon;

public class LevelSelect {
	private static final BufferedImage BG = loadImage("../cc_main_bg.png");
	private static final String PREFIX = "/com/confedicats/ld25/maps/";
	private static final BufferedImage RAINBOW = new Rainbow().getBuff();
	public static final Rectangle RAINBOW_LOC = new Rectangle(195, 200, 200, 150);
	private static final BufferedImage TOWN = new Town().getBuff();
	public static final Rectangle TOWN_LOC = new Rectangle(405, 200, 200, 150);
	private static BufferedImage BACK_NORMAL = loadImage("../options/cc_options_back1.png");
	private static BufferedImage BACK_HOVER = loadImage("../options/cc_options_back2.png");
	public static final Rectangle BACK_LOC = new Rectangle(5,490,200,80);
	public static boolean back_hovered = false;
	public LevelSelect() {
		
	}
	private static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public void paint(Graphics g) {
		g.drawImage(BG, 0, 0, null);
		g.setFont(g.getFont().deriveFont(24f));
		FontMetrics fm = g.getFontMetrics();
		g.drawString("SELECT A MAP", 400-g.getFontMetrics().stringWidth("SELECT A MAP")/2, 180);
		g.drawString("RAINBOW", (int)RAINBOW_LOC.getCenterX()-fm.stringWidth("RAINBOW")/2, (int)RAINBOW_LOC.getMaxY()+30);
		g.drawImage(RAINBOW, RAINBOW_LOC.x, RAINBOW_LOC.y, RAINBOW_LOC.width, RAINBOW_LOC.height, null);
		g.drawString("(EASY)", (int)RAINBOW_LOC.getCenterX()-fm.stringWidth("(EASY)")/2, (int)RAINBOW_LOC.getMaxY()+60);
		g.drawString("TOWN", (int)TOWN_LOC.getCenterX()-fm.stringWidth("TOWN")/2, (int)TOWN_LOC.getMaxY()+30);
		g.drawImage(TOWN, TOWN_LOC.x, TOWN_LOC.y, TOWN_LOC.width, TOWN_LOC.height, null);
		g.drawString("(HARD)", (int)TOWN_LOC.getCenterX()-fm.stringWidth("(Hard)")/2, (int)TOWN_LOC.getMaxY()+60);
		g.drawImage(back_hovered?BACK_HOVER:BACK_NORMAL, BACK_LOC.x, BACK_LOC.y, null);		
		
	}
}
