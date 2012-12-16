package com.confedicats.ld25.tiles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.weapons.Weapon;

public abstract class Tile extends BufferedImage {
	public static enum TileType {PLATFORM, SPOUT, PIT, EMPTY};
	private TileType type;
	private int x;
	private int y;
	public static final String PREFIX = "/com/confedicats/ld25/maps/";
	public Tile(Image image) {
		super(40, 40, BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
	}
	public abstract Tile clone();
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public TileType getTileType() {
		return type;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Tile.class.getResource(PREFIX+fname).openStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void register(TileType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	public abstract void paintMe(Graphics g);
}
