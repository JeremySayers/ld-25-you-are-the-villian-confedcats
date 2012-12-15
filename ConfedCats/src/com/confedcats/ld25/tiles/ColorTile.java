package com.confedcats.ld25.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ColorTile extends Tile {
	private Color col = new Color(Color.TRANSLUCENT);
	public ColorTile(Color col) {
		super(generateSolidColor(col));
		this.col = col;
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
		ColorTile c = new ColorTile(Color.BLACK);
		for (int y=0; y<c.getHeight(); y++) {
			for (int x=0; x<c.getWidth(); x++) {
				c.setRGB(x, y, Color.TRANSLUCENT);
			}
		}
		return c;
	}
	public Tile clone() {
		if (getTileType()==TileType.EMPTY)
			return getTransparentTile();
		return new ColorTile(getColor());
	}
}
