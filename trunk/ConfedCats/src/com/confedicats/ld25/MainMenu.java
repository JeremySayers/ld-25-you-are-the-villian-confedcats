package com.confedicats.ld25;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.Weapon;

public class MainMenu {
	private static final BufferedImage BG = loadImage("cc_main_bg.png");
	private static final String PREFIX = "/com/confedicats/ld25/";
	private static BufferedImage START_NORMAL = loadImage("cc_main_play1.png");
	private static BufferedImage START_HOVER = loadImage("cc_main_play2.png");
	public static boolean start_hovered = false;
	public static final Rectangle START_LOC = new Rectangle(300,180,200,80);
	private static BufferedImage OPT_NORMAL = loadImage("cc_main_options1.png");
	private static BufferedImage OPT_HOVER = loadImage("cc_main_options2.png");
	public static final Rectangle OPT_LOC = new Rectangle(250,330,300,80);
	public static boolean opt_hovered = false;
	public MainMenu(){
		start_hovered = false;
		opt_hovered = false;
		Sound.create("menumusic.au", true); // Load sound (aka cache sound) 
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
		g.drawImage(start_hovered?START_HOVER:START_NORMAL, START_LOC.x, START_LOC.y, null);
		g.drawImage(opt_hovered?OPT_HOVER:OPT_NORMAL, OPT_LOC.x, OPT_LOC.y, null);
	}

	public Sound getMusic() {
		return Sound.create("menumusic.au", true);
	}
}
