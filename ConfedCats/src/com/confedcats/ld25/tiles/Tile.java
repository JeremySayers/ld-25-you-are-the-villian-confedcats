package com.confedcats.ld25.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile extends BufferedImage {
	private int x;
	private int y;
	public Tile(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
	}
	public Tile clone() {
		return new Tile(this);
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
