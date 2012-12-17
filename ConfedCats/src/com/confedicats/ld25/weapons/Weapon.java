package com.confedicats.ld25.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.Player;
import com.confedicats.ld25.weapons.ammo.Ammo;

public abstract class Weapon extends BufferedImage {
	private int x;
	private int y;
	private ArrayList<Ammo> ammoFired = new ArrayList<Ammo>();
	private static final String PREFIX = "/com/confedicats/ld25/weapons/";
	public Weapon(BufferedImage img) {
		super(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
	}
	public static Class<? extends Weapon> getNewWeapon() {
		ArrayList<Class<? extends Weapon>> possible = getPossibleWeapons();
		return possible.get((int)(Math.round(Math.random()*(possible.size()-1))));
	}
	public static ArrayList<Class<? extends Weapon>> getPossibleWeapons() {
		ArrayList<Class<? extends Weapon>> possible = new ArrayList<Class<? extends Weapon>>();
		possible.add(Laser.class);
		possible.add(Scythe.class);
		possible.add(LauncherRocket.class);
		return possible;
	}
	public static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public void addAmmo(Ammo ammo) {
		ammoFired.add(ammo);
	}
	public abstract Class<? extends Ammo> getAmmoClass();
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public abstract BufferedImage getLeft();
	public abstract BufferedImage getRight();
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public abstract boolean isAutomatic();
	public abstract void release();
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public abstract void shoot(Player player);
	public void update(Graphics g) {
		if (!GamePanel.player.getWeapon().equals(this)) return;
		x = GamePanel.player.getX();
		y = GamePanel.player.getY();
		ArrayList<Ammo> toRemove = new ArrayList<Ammo>();
		for (Ammo am:ammoFired) {
			if (am.isDead()) toRemove.add(am);
			am.paint(g);
		}
		ammoFired.removeAll(toRemove);
	}
}