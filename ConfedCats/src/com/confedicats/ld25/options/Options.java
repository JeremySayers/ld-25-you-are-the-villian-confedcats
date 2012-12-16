package com.confedicats.ld25.options;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.Weapon;

public class Options {
	private static final BufferedImage BG = loadImage("cc_options_bg.png");
	private static final String PREFIX = "/com/confedicats/ld25/options";
	private static BufferedImage BGM_ON = loadImage("cc_options_bgm2.png");
	private static BufferedImage BGM_OFF = loadImage("cc_options_bgm1.png");
	public static final Rectangle BGM_LOC = new Rectangle(300,180,200,80);
	private static final Sound MUSIC = Sound.create("menumusic.au", true);
	public Options(){
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
	}

	public Sound getMusic() {
		return MUSIC;
	}
}
