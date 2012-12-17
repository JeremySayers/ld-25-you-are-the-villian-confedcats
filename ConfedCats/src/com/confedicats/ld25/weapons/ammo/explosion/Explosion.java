package com.confedicats.ld25.weapons.ammo.explosion;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.enemies.BaseEnemy;
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
		Ellipse2D el = new Ellipse2D.Double();
		if (animation.getFrame()<5)
			el.setFrame(x+50-3*animation.getFrame(), y+50-3*animation.getFrame(), 6*animation.getFrame()-1, 6*animation.getFrame()-1);
		else if (animation.getFrame()<7)
			el.setFrame(x+39, y+39, 23, 23);
		else if (animation.getFrame()<8)
			el.setFrame(x+38, y+38, 21, 21);
		else if (animation.getFrame()<8)
			el.setFrame(x+45, y+45, 11, 11);
		else
			el.setFrame(x+48, y+48, 3, 3);
		for (int i = 0; i < GamePanel.enemies.size(); i++){
			BaseEnemy en = GamePanel.enemies.get(i);
			if (el.intersects(en.getBounds())) {
				Sound.create("enemydeath.wav", false).play();
				GamePanel.enemies.remove(en);
				i--;
			}
		}
	}
}
