package com.confedicats.ld25.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.ammo.Ammo;
import com.confedicats.ld25.weapons.ammo.BossLauncherRocketAmmo;

public class CyborgLincoln extends Boss {
	private BufferedImage LEFT;
	private BufferedImage RIGHT;
	private ArrayList<Ammo> ammoFired = new ArrayList<Ammo>();
	private int tick = 0;
	public Sound SOUND;
	public CyborgLincoln(int health, int x, int y, int multi, boolean angry) {
		super(health, x, y, multi, angry);
		try {
			LEFT = ImageIO.read(UnionSoldier.class.getResource("/com/confedicats/ld25/enemies/cc_clincoln_l.png"));
			RIGHT = ImageIO.read(UnionSoldier.class.getResource("/com/confedicats/ld25/enemies/cc_clincoln_r.png"));
		} catch (Exception e){
		}
		SOUND = Sound.create("bossbattle.au", true);
	}
	public void addAmmo(Ammo ammo) {
		ammoFired.add(ammo);
	}
	public Class<? extends Ammo> getAmmoClass() {
		return BossLauncherRocketAmmo.class;
	}

	public BufferedImage getLeft() {
		return LEFT;
	}

	public BufferedImage getRight() {
		return RIGHT;
	}
	
	public void shoot() {
		try {
			int xv = 0;
			if (isMovingLeft()){
				xv = -1;
			} else if (isMovingRight()){
			    xv = 1;
			}
			Ammo ammo = getAmmoClass().getConstructor(int.class, int.class, int.class, int.class).newInstance(getX(), getY(), xv, 6);
			addAmmo(ammo);
			Sound.create("laser.wav", false).play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void update(Graphics g) {
		System.out.println(isAngry());
		tick++;
		tick%=10;
		if (isAngry() && tick==5) {
			shoot();
		}
		ArrayList<Ammo> toRemove = new ArrayList<Ammo>();
		for (Ammo am:ammoFired) {
			if (am.isDead()) toRemove.add(am);
			am.paint(g);
		}
		ammoFired.removeAll(toRemove);
	}
}
