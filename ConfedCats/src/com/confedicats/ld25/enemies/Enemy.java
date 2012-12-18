package com.confedicats.ld25.enemies;

public abstract class Enemy extends BaseEnemy {
	public Enemy(int health, int x, int y, int multi, boolean angry){
		super(health, x, y,multi, angry);
	}
	public boolean isBoss() {
		return false;
	}
}
