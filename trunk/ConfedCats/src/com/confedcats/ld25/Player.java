package com.confedcats.ld25;

import com.confedcats.ld25.maps.Map;
import com.confedcats.ld25.tiles.Tile;
import com.confedcats.ld25.tiles.Tile.TileType;

public class Player {
	private boolean isAlive;
	private boolean isJumping = false;
	private int jumpSpeed = 0;
	private int startJumpSpeed = -20;
	private int gravity = 1;
	private int direction; //0 - Left, 1 - Right
	private int xVel;
	private int yVel;
	private int x;
	private int y;
	private int width = 30;
	private int height = 30;
	//Corners for collision detection
	private int topRightX;
	private int topRightY;
	private int topLeftX;
	private int topLeftY;
	private int bottomRightX;
	private int bottomRightY;
	private int bottomLeftX;
	private int bottomLeftY;
	
	private Tile[][] mapStorage;
	
	public Player(){
		x = 385;
		y = 525;
	}
	
	public void updatePlayerPos(Map map){
		mapStorage = map.getTiles();
		if (canMoveHorizontal(y,x)){
			setX(getX()+getxVel());
		}
			if (isJumping){
				y = y + jumpSpeed;
				jumpSpeed += gravity;
			}
		
	}
	public boolean canMoveHorizontal(int y, int x){
		if (xVel > 0){
			if (mapStorage[(y/40)][(x+width+xVel)/40].getTileType() == TileType.EMPTY){
				return true;
			} else {
				return false;
			}
		} else {
			if (mapStorage[(y/40)][(x+xVel)/40].getTileType() == TileType.EMPTY){
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static int roundToClosestGrid(double i){
	    return (int) ((Math.floor(i/40) *40))/40;
	}
	public void jumpingStuff(boolean jumpKey){
		if (jumpKey){
			if (!isJumping()){
				setJumpSpeed(getStartJumpSpeed());
				setJumping(true);
			}
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