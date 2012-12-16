package com.confedicats.ld25;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.Pistol;
import com.confedicats.ld25.weapons.Weapon;

public class Player {
	public static final BufferedImage PLAYER1_LEFT = loadImage("p1l.png");
	public static final BufferedImage PLAYER1_RIGHT = loadImage("p1r.png");
	private static final String PREFIX = "/com/confedicats/ld25/players/";
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

	private int lastXVel;
	
	private Tile[][] mapStorage;
	
	public Player(){
		x = 385;
		y = 445;
	}
	
	protected static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 30, 30);
	}
	public void updatePlayerPos(Map map){
		mapStorage = map.getTiles();
		if (canMoveHorizontal(y,x)){
			setX(getX()+getxVel());
		}
		 
		if ((y+gravity+height)/40 != 15){
			if (mapStorage[(int) Math.floor((y+gravity+height)/40)][(int) (Math.floor(x)/40)].getTileType() == TileType.EMPTY && isJumping == false){
				jumpSpeed = 0;
				isJumping = true;
			}
		}
		if (canMoveVertical(y,x)){
			if (isJumping){
				jumpSpeed += gravity;
				y = y + jumpSpeed;
			}
		}
		if (hitHG(y,x)){
			int ranX = (int)(Math.random()*18+1);
			int ranY = (int)(Math.random()*13+1);
			
			if (mapStorage[(int)(ranY+1)][(int)(ranX)].getTileType() == TileType.PLATFORM && mapStorage[(int)(ranY)][(int)(ranX)].getTileType() == TileType.EMPTY && ranX !=8 && ranY != 5){
				GamePanel.hg.alter(ranX*40+10,ranY*40+10,Pistol.class);
			} else {
				ranX = (int)(Math.random()*19+1);
				ranY = (int)(Math.random()*13+1);
			}
		}
	}
	public boolean hitHG(int y,int x){
		GamePanel.hg.getX();
		if (getBounds().intersects(GamePanel.hg.getBounds())){
			return true;
		} else {
			return false;
		}
	}
	public boolean canMoveHorizontal(int y, int x){
		if (xVel > 0){
			if (mapStorage[(int) Math.ceil(y/40)][(int) (Math.ceil(x+width+xVel)/40)].getTileType() == TileType.EMPTY){
				return true;
			} else {
				return false;
			}
		} else {
			if (mapStorage[(int) Math.floor(y/40)][(int) (Math.floor(x+xVel)/40)].getTileType() == TileType.EMPTY){
				return true;
			} else {
				return false;
			}
		}
	}
	public boolean canMoveVertical(int y, int x){
		if (jumpSpeed < 0){
			if (mapStorage[(int) Math.floor((y+jumpSpeed)/40)][(int) (Math.floor(x)/40)].getTileType() == TileType.EMPTY && mapStorage[(int) Math.floor((y+jumpSpeed)/40)][(int) (Math.floor(x+width)/40)].getTileType() == TileType.EMPTY){
				return true;
			}else {
				jumpSpeed = 0;
				return false;
			}
		} else {
			if (mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x)/40)].getTileType() == TileType.EMPTY && mapStorage[(int) Math.ceil((y+gravity+height)/40)][(int) Math.ceil((x+width)/40)].getTileType() == TileType.EMPTY){
				return true;
			} else {
				isJumping = false;
				jumpSpeed = 0;
				return false;
			}
		}
	}
	
	public void playerReset(){
		x = 385;
		y = 525;
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
	public void log(String string){
		System.out.println(string);
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLastXVel() {
		return lastXVel;
	}

	public void setLastXVel(int lastXVel) {
		this.lastXVel = lastXVel;
	}

	public Tile[][] getMapStorage() {
		return mapStorage;
	}

	public void setMapStorage(Tile[][] mapStorage) {
		this.mapStorage = mapStorage;
	}
}
