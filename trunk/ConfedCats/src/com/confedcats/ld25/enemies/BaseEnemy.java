package com.confedcats.ld25.enemies;

import com.confedcats.ld25.maps.Map;

public abstract class BaseEnemy {
	private int health;
	private int xVel = 0;
	private int yVel = 0;
	private int gravity = 10;
	private int x;
	private int y;
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getxVel() {
		return xVel;
	}
	public void setxVel(int xVel) {
		this.xVel = xVel;
	}
	public int getyVel() {
		return yVel;
	}
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
	public int getGravity() {
		return gravity;
	}
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public BaseEnemy(int health, int x, int y){
		setHealth(health);
		setX(x);
		setY(y);
	}
	public void updateEnemyPos(Map map){
		if (y < 570)
			y += gravity;
	}
	public abstract boolean isBoss();
}
