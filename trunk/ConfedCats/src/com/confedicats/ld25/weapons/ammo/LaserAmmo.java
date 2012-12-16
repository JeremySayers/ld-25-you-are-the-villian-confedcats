package com.confedicats.ld25.weapons.ammo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.confedicats.ld25.Driver;
import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.enemies.BaseEnemy;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;

public class LaserAmmo extends Ammo {
	private static final BufferedImage LASER_AMMO = loadImage("cc_laser_shot.png");
	private int direction;
	private int speed;
	public LaserAmmo(int x, int y, int direction, int speed) {
		super(LASER_AMMO, direction==1?x+30:x, y+17);
		this.direction = direction;
		this.speed = speed;
	}
	public void update() {
		if (isDead()) return;
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
				be.setHealth(be.getHealth()-10);
				if (be.getHealth()<=0) {
					removeEnemies.add(be);
					Sound.create("enemydeath.wav", false).play();
				}
				setDead(true);
				break;
			}
		}
		GamePanel.enemies.removeAll(removeEnemies);
	}
}
