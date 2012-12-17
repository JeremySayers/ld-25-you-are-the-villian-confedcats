package com.confedicats.ld25.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.Player;
import com.confedicats.ld25.enemies.BaseEnemy;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.ammo.Ammo;

public class Scythe extends Weapon {
	public static final BufferedImage PLAYER1_LEFT = loadImage("cc_scythe_l1.png");
	public static final BufferedImage PLAYER1_RIGHT = loadImage("cc_scythe_r1.png");
	public static final BufferedImage PLAYER1_LEFT_FIRED = loadImage("cc_scythe_l2.png");
	public static final BufferedImage PLAYER1_RIGHT_FIRED = loadImage("cc_scythe_r2.png");
	private boolean fired = false;
	public Scythe() {
		super(PLAYER1_LEFT);
	}
	
	public Class<? extends Ammo> getAmmoClass() {
		return null;
	}

	public Rectangle getBounds() {
		if (fired)
			return new Rectangle(getX(), getY(), getWidth()+11, getHeight());
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public BufferedImage getLeft() {
		return fired?PLAYER1_LEFT_FIRED:PLAYER1_LEFT;
	}

	public BufferedImage getRight() {
		return fired?PLAYER1_RIGHT_FIRED:PLAYER1_RIGHT;
	}

	public boolean isAutomatic() {
		return true;
	}

	public void release() {
		fired = false;
	}
	
	public void shoot(Player player) {
		fired = true;
	}

	public void update(Graphics g){
		super.update(g);
		if (fired) {
			ArrayList<BaseEnemy> removeEnemies = new ArrayList<BaseEnemy>();
			for (BaseEnemy be:GamePanel.enemies) {
				if (be.getBounds().intersects(getBounds())) {
					removeEnemies.add(be);
					Sound.create("enemydeath.wav", false).play();
				}
			}
			GamePanel.enemies.removeAll(removeEnemies);
		}
	}
}
