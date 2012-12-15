package com.confedcats.ld25;

public class Player {
	private boolean isAlive;
	private boolean isJumping = false;
	private int jumpSpeed = -10;
	private int startJumpSpeed = -20;
	private int gravity = 1;
	private int direction; //0 - Left, 1 - Right
	private int xVel;
	private int yVel;
	private int x;
	private int y;
	
	public Player(){
		x = 385;
		y = 570;
	}
	
	public void updatePlayerPos(){
		setX(getX()+getxVel());
		if (isJumping){
				y = y + jumpSpeed;
				jumpSpeed += gravity;
			if (y >= 570)
				isJumping = false;
		}
	}
	
	
	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public int getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(int jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public int getStartJumpSpeed() {
		return startJumpSpeed;
	}

	public void setStartJumpSpeed(int startJumpSpeed) {
		this.startJumpSpeed = startJumpSpeed;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
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
