package com.confedicats.ld25.weapons;

import java.awt.image.BufferedImage;

import com.confedicats.ld25.Player;
import com.confedicats.ld25.weapons.ammo.Ammo;
import com.confedicats.ld25.weapons.ammo.LaserAmmo;

public class Laser extends Weapon {
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

	public void shoot(Player player) {
		try {
			Ammo ammo = getAmmoClass().getConstructor(int.class, int.class, int.class, int.class).newInstance(player.getX(), player.getY(), player.getxVel()<0?-1:player.getxVel()>0?1:player.getLastXVel()<0?-1:1, 6);
			addAmmo(ammo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
