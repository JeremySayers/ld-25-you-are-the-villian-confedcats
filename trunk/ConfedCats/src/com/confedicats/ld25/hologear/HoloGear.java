package com.confedicats.ld25.hologear;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.confedicats.ld25.tiles.AnimationTile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.Weapon;

public class HoloGear {
	private int health;
	private int x;
	private int y;
	private Class<? extends Weapon> weapon;
	public static int COUNT;
	private static final AnimationTile tile = new AnimationTile("cc_hologear1.png", "cc_hologear2.png", "cc_hologear3.png", "cc_hologear4.png");
	public HoloGear(Class<? extends Weapon> weap, int x, int y) {
		weapon = weap;
		this.x = x;
		this.y = y;
	}
	public void alter(int x, int y,Class<? extends Weapon> weap){
		weapon = weap;
		this.x = x;
		this.y = y;
		COUNT++;
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
		tile.register(TileType.EMPTY, getX(), getY());
		tile.paintMe(g);
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void update() {
		
	}
}
