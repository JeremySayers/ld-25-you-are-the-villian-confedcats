package com.confedicats.ld25.weapons;

import java.awt.image.BufferedImage;

import com.confedicats.ld25.Player;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.ammo.Ammo;
import com.confedicats.ld25.weapons.ammo.LaserAmmo;

public class Laser extends Weapon {
	int xv;
	public static final BufferedImage PLAYER1_LEFT = loadImage("cc_laser_l.png");
	public static final BufferedImage PLAYER1_RIGHT = loadImage("cc_laser_r.png");
	public Laser() {
		super(PLAYER1_LEFT);
	}

	public Class<? extends Ammo> getAmmoClass() {
		return LaserAmmo.class;
	}

	public BufferedImage getLeft() {
		return PLAYER1_LEFT;
	}

	public BufferedImage getRight() {
		return PLAYER1_RIGHT;
	}

	public boolean isAutomatic() {
		return false;
	}

	public void release() {
	}

	public void shoot(Player player) {
		try {
			if (player.isMovingLeft()){
				xv = -1;
			} else if (player.isMovingRight()){
			    xv = 1;
			} else {
				xv = player.getLastXVel();
			}
			
			Ammo ammo = getAmmoClass().getConstructor(int.class, int.class, int.class, int.class).newInstance(player.getX(), player.getY(),xv, 6);
			addAmmo(ammo);
			Sound.create("laser.wav", false).play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
