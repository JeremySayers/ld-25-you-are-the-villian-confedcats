package com.confedicats.ld25.enemies;

public abstract class Enemy extends BaseEnemy {
	public Enemy(int health, int x, int y){
		super(health, x, y);
	}
	public boolean isBoss() {
		return false;
	}
}
