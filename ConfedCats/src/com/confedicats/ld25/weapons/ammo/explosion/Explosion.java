package com.confedicats.ld25.weapons.ammo.explosion;

import java.awt.Graphics;

import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.AnimationTile;
import com.confedicats.ld25.tiles.Tile.TileType;

public class Explosion {
	private int x;
	private int y;
	private static final String PREFIX = "../weapons/ammo/explosion/";
	private AnimationTile animation = new AnimationTile(PREFIX+"cc_explosion1.png",PREFIX+"cc_explosion2.png",
			PREFIX+"cc_explosion3.png",PREFIX+"cc_explosion4.png",PREFIX+"cc_explosion5.png",
			PREFIX+"cc_explosion6.png",PREFIX+"cc_explosion7.png",PREFIX+"cc_explosion8.png",
			PREFIX+"cc_explosion9.png");
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		Sound.create("explosion.wav", false).play();
	}
	public boolean isDead() {
		return animation.getFrame()==8 && animation.getId()==11;
	}
	public void paint(Graphics g) {
		animation.register(TileType.EMPTY, x, y);
		animation.paintMe(g);
	}
}
