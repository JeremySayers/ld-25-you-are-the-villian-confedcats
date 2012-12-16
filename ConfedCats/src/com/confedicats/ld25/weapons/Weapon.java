package com.confedicats.ld25.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.confedicats.ld25.weapons.ammo.Ammo;

public abstract class Weapon extends BufferedImage {
	private int health;
	private int x;
	private int y;
	private ArrayList<Ammo> ammoFired = new ArrayList<Ammo>();
	private static final String PREFIX = "/com/confedicats/ld25/weapons/";
	public Weapon(BufferedImage img, int health) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
		this.setHealth(health);
	}
	public static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public abstract Ammo getAmmo();
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public int getHealth() {
		return health;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void paint(Graphics g) {
		update();
		g.drawImage(this, getX(), getY(), null);
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public abstract void shoot();
	public abstract void update();
}
