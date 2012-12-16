package com.confedicats.ld25.enemies;

public abstract class Boss extends BaseEnemy {
	public Boss(int health, int x, int y,int multi){
		super(health, x, y,multi);
	}
	public boolean isBoss() {
		return true;
	}
}
