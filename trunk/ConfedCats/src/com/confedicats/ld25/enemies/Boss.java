package com.confedicats.ld25.enemies;

import java.awt.Graphics;

public abstract class Boss extends BaseEnemy {
	public Boss(int health, int x, int y,int multi,boolean angry){
		super(health, x, y,multi,angry);
	}
	public boolean isBoss() {
		return true;
	}
	public abstract void update(Graphics bg);
}
