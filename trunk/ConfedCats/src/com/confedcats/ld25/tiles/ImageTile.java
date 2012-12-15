package com.confedcats.ld25.tiles;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedcats.ld25.weapons.Weapon;

public class ImageTile extends Tile {
	public static final String PREFIX = "/com/confedcats/ld25/maps/";
	public ImageTile(BufferedImage img) {
		super(img);
	}
	public ImageTile(String fname) {
		super(loadImage(fname));
	}
	public static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public Tile clone() {
		return new ImageTile(this);
	}
}
