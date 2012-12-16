package com.confedicats.ld25;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.weapons.Weapon;

public class MainMenu {
	private static final BufferedImage BG = loadImage("confedicats_start_bg.png");
	private static final String PREFIX = "/com/confedicats/ld25/";
	public static final Rectangle START = new Rectangle(300,230,200,100);
	public MainMenu(){
		
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
}
