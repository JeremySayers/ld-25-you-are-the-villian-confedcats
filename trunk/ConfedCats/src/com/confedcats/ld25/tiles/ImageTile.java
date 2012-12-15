package com.confedcats.ld25.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageTile extends Tile {
	public ImageTile(BufferedImage img) {
		super(img);
	}
	public ImageTile(String fname) {
		super(loadImage(fname));
	}
	public Tile clone() {
		return new ImageTile(this);
	}
	public void paintMe(Graphics g){
		g.drawImage(this, getX(), getY(), null);
	}
}
