package com.confedcats.ld25.weapons;

import java.awt.image.BufferedImage;

import com.confedcats.ld25.weapons.ammo.Ammo;

public class Pistol extends Weapon {

	public Pistol(BufferedImage img, int health) {
		super(img, health);
	}

	public Ammo getAmmo() {
		return null;
	}

	public void shoot() {

	}

	public void update() {
		
	}
}
