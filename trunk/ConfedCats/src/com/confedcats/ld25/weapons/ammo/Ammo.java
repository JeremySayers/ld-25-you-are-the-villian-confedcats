package com.confedcats.ld25.weapons.ammo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public abstract class Ammo extends BufferedImage {
	private static final String PREFIX = "/com/confedcats/ld25/weapons/ammo/";
	private boolean dead;
	private int x;
	private int y;
	public Ammo(BufferedImage img, int health, int x, int y) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
		this.setX(x);
		this.setY(y);
	}
	public static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Ammo.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public abstract void getDamage();
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isDead() {
		return dead;
	}
	public void paint(Graphics g) {
		update();
		g.drawImage(this, getX(), getY(), null);
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public abstract void update();
}
