package com.confedicats.ld25.enemies;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class UnionSoldier extends Enemy {
	private BufferedImage LEFT;
	private BufferedImage RIGHT;
	public UnionSoldier(int health, int x, int y){
		super(health, x, y);
		try {
			LEFT = ImageIO.read(UnionSoldier.class.getResource("/com/confedicats/ld25/enemies/cc_enemy_basicl.png"));
			RIGHT = ImageIO.read(UnionSoldier.class.getResource("/com/confedicats/ld25/enemies/cc_enemy_basicr.png"));
		} catch (Exception e){
		}
	}
	public BufferedImage getLeft() {
		return LEFT;
	}
	public BufferedImage getRight() {
		return RIGHT;
	}
}
