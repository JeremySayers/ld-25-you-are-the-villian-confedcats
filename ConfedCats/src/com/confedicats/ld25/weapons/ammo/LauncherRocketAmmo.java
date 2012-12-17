package com.confedicats.ld25.weapons.ammo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.confedicats.ld25.Driver;
import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.enemies.BaseEnemy;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.ammo.explosion.Explosion;

public class LauncherRocketAmmo extends Ammo {
	private static final BufferedImage LAUNCHER_AMMO_L = loadImage("cc_launcher_rocket_l.png");
	private static final BufferedImage LAUNCHER_AMMO_R = loadImage("cc_launcher_rocket_r.png");
	private static final BufferedImage SMOKE = loadImage("cc_smoke.png");
	private int direction;
	private int speed;
	private int startx;
	private Explosion explosion = null;
	public LauncherRocketAmmo(int x, int y, int direction, int speed) {
		super(direction==1?LAUNCHER_AMMO_L:LAUNCHER_AMMO_R, direction==1?x+30:x, y+11);
		this.direction = direction;
		this.speed = speed;
		startx = x;
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
		if (direction==1) {	
			for (int x=getX()-10; x>startx; x-=10)
				g.drawImage(SMOKE, x, getY()-1, null);
		} else {
			for (int x=getX()-10; x<startx; x+=10)
				g.drawImage(SMOKE, x, getY()-1, null);
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
		ArrayList<BaseEnemy> removeEnemies = new ArrayList<BaseEnemy>();
		for (BaseEnemy be:GamePanel.enemies) {
			if (be.getBounds().intersects(getBounds())) {
				removeEnemies.add(be);
				Sound.create("enemydeath.wav", false).play();
				setDead(true);
				break;
			}
		}
		GamePanel.enemies.removeAll(removeEnemies);
	}
}
