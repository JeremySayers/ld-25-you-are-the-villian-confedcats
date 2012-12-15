package com.confedcats.ld25.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ColorTile extends Tile {
	private boolean trans = false;
	private Color col = new Color(Color.TRANSLUCENT);
	public ColorTile() {
		super(new BufferedImage(40, 40, TYPE_INT_ARGB));
		this.col = new Color(Color.TRANSLUCENT);
		trans = true;
	}
	public ColorTile(Color col) {
		super(generateSolidColor(col));
		this.col = col;
		trans = false;
	}
	private static BufferedImage generateSolidColor(Color col) {
		BufferedImage img = new BufferedImage(40, 40, TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(col);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		return img;
	}
	public Color getColor() {
		return col;
	}
	public static ColorTile getTransparentTile() {
		return new ColorTile();
	}
	private boolean isTransparent() {
		return trans;
	}
	public Tile clone() {
		if (isTransparent())
			return getTransparentTile();
		return new ColorTile(getColor());
	}
	public void paintMe(Graphics g){
		g.drawImage(this, getX(), getY(), null);
	}
}
