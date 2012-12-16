package com.confedicats.ld25.enemies;

public abstract class Boss extends BaseEnemy {
	public Boss(int health, int x, int y){
		super(health, x, y);
	}
	public boolean isBoss() {
		return true;
	}
}
