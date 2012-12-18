package com.confedicats.ld25.weapons.ammo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.confedicats.ld25.Driver;
import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.Player;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.ammo.explosion.Explosion;

public class BossLauncherRocketAmmo extends Ammo {
	private static final BufferedImage LAUNCHER_AMMO_L = loadImage("cc_launcher_rocket_l.png");
	private static final BufferedImage LAUNCHER_AMMO_R = loadImage("cc_launcher_rocket_r.png");
	private int direction;
	private int speed;
	private Explosion explosion = null;
	public BossLauncherRocketAmmo(int x, int y, int direction, int speed) {
		super(direction==1?LAUNCHER_AMMO_L:LAUNCHER_AMMO_R, direction==1?x+30:x, y+21);
		this.direction = direction;
		this.speed = speed;
	}
	public boolean isDead() {
		return super.isDead() && explosion==null;
	}
	public void paint(Graphics g) {
		if (super.isDead()) {
			update(g);
			return;
		}
		super.paint(g);
	}
	public void setDead(boolean dead) {
		if (dead && explosion==null) {
			explosion = new Explosion(getX()-50, getY()-50);
		}
		super.setDead(dead);
	}
	public void update(Graphics g) {
		if (super.isDead()) {
			if (explosion!=null) {
				if (explosion.isDead())
					explosion = null;
				else
					explosion.paint(g);
			}
			return;
		}
		setX(getX()+direction*speed);
		if (getX()<40|| getX()>Driver.WIDTH-40) {
			setDead(true);
			return;
		}
		Tile[][] tiles = GamePanel.level.getTiles();
		int row = getY()/40;
		int col = getX()/40;
		if (tiles[row][col].getTileType()!=TileType.EMPTY)
			setDead(true);
		Player be = GamePanel.player;
		if (be.getBounds().intersects(getBounds())) {
			be.setHealth(be.getHealth()-20);
			if (be.getHealth()<=0) {
				be.setX(40);
				be.setY(610);
				be.setAlive(false);
			}
			setDead(true);
		}
	}
}
