package com.confedcats.ld25;

public class Player {
	private boolean isAlive;
	private int direction; //0 - Left, 1 - Right
	private int xVel;
	private int yVel;
	private int x;
	private int y;
	
	public Player(){
		
	}
	
	public void updatePlayerPos(){
		setX(getX()+getxVel());
	}
	
	public int getDirection() {
		return direction;
	}
	public int getxVel() {
		return xVel;
	}
	public int getyVel() {
		return yVel;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public void setDirection(int direction) {
		this.direction = direction;
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

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
}
