package com.confedcats.ld25.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Tile extends BufferedImage {
	public static enum TileType {PLATFORM, SPOUT, PIT, EMPTY};
	private TileType type;
	private int x;
	private int y;
	public Tile(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
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
	public void register(TileType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
}
