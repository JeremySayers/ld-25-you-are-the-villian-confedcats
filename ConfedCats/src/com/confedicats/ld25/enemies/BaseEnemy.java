package com.confedicats.ld25.enemies;

import java.awt.image.BufferedImage;

import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;

public abstract class BaseEnemy {
	private boolean isJumping;
	private int jumpSpeed;
	private int health;
	private int xVel = 0;
	private int yVel = 0;
	private int multi = 5;
	private int gravity = 1;
	private int width = 30;
	private int height = 30;
	private int x;
	private int y;
	private boolean xVelUpdate = false;
	
	private Tile[][] mapStorage;
	
	public BaseEnemy(int health, int x, int y, int multi){
		setHealth(health);
		setX(x);
		setY(y);
		setMulti(multi);
		int rand = (int) (Math.random()*2+1);
		xVel = (rand == 1)?-1*multi: 1*multi;
	}
	public int getMulti() {
		return multi;
	}
	public void setMulti(int multi) {
		this.multi = multi;
	}
	public abstract BufferedImage getLeft();
	public abstract BufferedImage getRight();
	public void updateEnemyPos(Map map){
		mapStorage = map.getTiles();
		if (canMoveHorizontal(y,x)){
			setX(getX()+getxVel());
		} else {
			setxVel(-getxVel());
		}
		
		if ((y+gravity+height)/40 < 14){
			if (mapStorage[(int) Math.floor((y+gravity+height)/40)][(int) (Math.floor(x)/40)].getTileType() == TileType.EMPTY && isJumping == false){
				jumpSpeed = 0;
				isJumping = true;
			}
		}
		//Check if THE PLAYER SHOULD BURNNNNNNN
		System.out.println(mapStorage[(int) Math.floor((y+gravity+height)/40)][(int) (Math.floor(x)/40)].getTileType());
		if (mapStorage[(int) Math.floor((y+gravity+height)/40)][(int) (Math.floor(x)/40)].getTileType() == TileType.PIT){
			health = 0;
			System.out.println("ENEMY BURNED");
		}
		if ((y+gravity+height)/40 < 14){
			System.out.println(""+(y+gravity+height)/40);
			if (canMoveVertical(y,x)){
				if (isJumping){
					jumpSpeed += gravity;
					y = y + jumpSpeed;
				}
			}
		}
	}
	
	public boolean canMoveHorizontal(int y, int x){
		if (xVel > 0){
			if (mapStorage[(int) Math.ceil(y/40)][(int) (Math.ceil(x+width+xVel)/40)].getTileType() == TileType.EMPTY || mapStorage[(int) Math.ceil(y/40)][(int) (Math.ceil(x+width+xVel)/40)].getTileType() == TileType.SPOUT){
				return true;
			} else {
				return false;
			}
		} else {
			if (mapStorage[(int) Math.floor(y/40)][(int) (Math.floor(x+xVel)/40)].getTileType() == TileType.EMPTY || mapStorage[(int) Math.floor(y/40)][(int) (Math.floor(x+xVel)/40)].getTileType() == TileType.SPOUT){
				return true;
			} else {
				return false;
			}
		}
	}
	public boolean canMoveVertical(int y, int x){
		if (mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x)/40)].getTileType() == TileType.EMPTY && mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x+width)/40)].getTileType() == TileType.EMPTY || mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x)/40)].getTileType() == TileType.SPOUT && mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x+width)/40)].getTileType() == TileType.SPOUT){
			if (!xVelUpdate){
				int rand = (int) (Math.random()*2+1);
				xVel = (rand == 1)?-1*multi: 1*multi;
				xVelUpdate = true;
				y-=1;
			}
			return true;
		} else {
			isJumping = false;
			jumpSpeed = 0;
			xVelUpdate = false;
			return false;
		}
	}
	
	
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
	
	public abstract boolean isBoss();
}
