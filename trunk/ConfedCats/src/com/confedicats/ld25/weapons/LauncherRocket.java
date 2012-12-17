package com.confedicats.ld25.weapons;

import java.awt.image.BufferedImage;

import com.confedicats.ld25.Player;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.ammo.Ammo;
import com.confedicats.ld25.weapons.ammo.LauncherRocketAmmo;

public class LauncherRocket extends Weapon {
	public static final BufferedImage PLAYER1_LEFT = loadImage("cc_launcher_l1.png");
	public static final BufferedImage PLAYER1_RIGHT = loadImage("cc_launcher_r2.png");
	public LauncherRocket() {
		super(PLAYER1_LEFT);
	}

	public Class<? extends Ammo> getAmmoClass() {
		return LauncherRocketAmmo.class;
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
			int xv;
			if (player.isMovingLeft()){
				xv = -1;
			} else if (player.isMovingRight()){
			    xv = 1;
			} else {
				xv = player.getLastXVel();
			}
			Ammo ammo = getAmmoClass().getConstructor(int.class, int.class, int.class, int.class).newInstance(player.getX(), player.getY(), xv, 6);
			addAmmo(ammo);
			Sound.create("laser.wav", false).play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
