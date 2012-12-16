package com.confedicats.ld25.hats;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.confedicats.ld25.tiles.ColorTile;
import com.confedicats.ld25.weapons.Weapon;
import com.confedicats.ld25.weapons.ammo.Ammo;

public class Hat {
	private int health;
	private int x;
	private int y;
	private Class<? extends Weapon> weapon;
	private static final BufferedImage buff = new ColorTile(Color.BLUE);
	public Hat(Class<? extends Weapon> weap, int x, int y) {
		weapon = weap;
		this.x = x;
		this.y = y;
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 30, 30);
	}
	public int getHealth() {
		return health;
	}
	public Class<? extends Weapon> getWeapon() {
		return weapon;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void paint(Graphics g) {
		update();
		g.drawImage(buff, getX(), getY(), 30, 30, null);
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void update() {
		
	}
}
