package com.confedicats.ld25;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.Weapon;

public class Player {
	private static final BufferedImage PLAYER1_LEFT = loadImage("p1l.png");
	private static final BufferedImage PLAYER1_RIGHT = loadImage("p1r.png");
	private static final String PREFIX = "/com/confedicats/ld25/players/";
	private boolean isAlive;
	private boolean isJumping = false;
	private int jumpSpeed = 0;
	private int jumpStart = -18;
	private int gravity = 1;
	private int direction; //0 - Left, 1 - Right
	private int xVel;
	private int yVel;
	private int speed;
	private int x;
	private int y;
	private int width = 30;
	private int height = 30;
	private Weapon weapon = null;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean jumpKey;
	
	private int downY, upY, leftX, rightX, xTile, yTile;
	TileType downRight;
	TileType upRight;
	TileType downLeft;
	TileType upLeft;

	

	private int lastXVel;
	
	private Tile[][] mapStorage;
	
	public Player(){
		ArrayList<Point> spouts = GamePanel.level.getSpouts();
		Point spout = spouts.get((int)(Math.random()*spouts.size()));
		setX(spout.x);
		setY(20);
		setWeapon(Weapon.getNewWeapon());
		xTile = (int)(Math.floor(x/40));
		yTile = (int)(Math.floor(y/40));
		setxVel(0);
		setyVel(0);
		setGravity(1);
		setWidth(30);
		setHeight(30);
		setSpeed(5);
		isAlive = true;
		
	}
	
	protected static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	public static int roundToClosestGrid(double i){
	    return (int) ((Math.floor(i/40) *40))/40;
	}
	public void checkHG(){
	if (hitHG(y,x)){
			Sound.create("pickup.wav", false).play();
			setWeapon(GamePanel.hg.getWeapon());
			int ranX = (int)(Math.random()*18+1);
			int ranY = (int)(Math.random()*13+1);
			boolean foundNewLoc = false;
			while (!foundNewLoc){
				if (mapStorage[(int)(ranY+1)][(int)(ranX)].getTileType() == TileType.PLATFORM && mapStorage[(int)(ranY)][(int)(ranX)].getTileType() == TileType.EMPTY && ranX !=8 && ranY != 5){
					GamePanel.hg.alter(ranX*40+10,ranY*40+10, Weapon.getNewWeapon());
					foundNewLoc = true;
				} else {
					ranX = (int)(Math.random()*19+1);
					ranY = (int)(Math.random()*13+1);
				}
			}
		}	
	}
	public void fall() {
		  if (!isJumping) {
			mapStorage = GamePanel.level.getTiles();
		    getMyCorners (x, y+1,mapStorage);
		    if (downLeft == TileType.EMPTY && downRight == TileType.EMPTY || downLeft == TileType.PIT && downRight == TileType.PIT) {
		      jumpSpeed = 0;
		      isJumping = true;
		    }
		  }
		  if(downLeft == TileType.BEYOND_PIT && downRight == TileType.BEYOND_PIT){
		    	isAlive = false;
		    	System.out.println("Enemy Fell into the pit!");
		    }
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 30, 30);
	}

	public int getDirection() {
		return direction;
	}

	public int getGravity() {
		return gravity;
	}

	public int getHeight() {
		return height;
	}

	public int getJumpSpeed() {
		return jumpSpeed;
	}
	public int getJumpStart() {
		return jumpStart;
	}
	public int getLastXVel() {
		return lastXVel;
	} 
	public BufferedImage getLeft(){
		if (weapon != null) return weapon.getLeft();
		return PLAYER1_LEFT;
	}
	public Tile[][] getMapStorage() {
		return mapStorage;
	}
	public void getMyCorners (int x, int y,Tile tiles[][]) {
		  downY = (int) Math.floor((y+30-1)/40);
		  upY = (int) Math.floor((y)/40);
		  leftX = (int) Math.floor((x)/40);
		  rightX = (int) Math.floor((x+30-1)/40);
		  //check if they are walls
		  upLeft = tiles[upY][leftX].getTileType();
		  downLeft = tiles[downY][leftX].getTileType();
		  upRight = tiles[upY][rightX].getTileType();
		  downRight = tiles[downY][rightX].getTileType();
	}
	public BufferedImage getRight(){
		if (weapon != null) return weapon.getRight();
		return PLAYER1_RIGHT;
	}
	public int getSpeed() {
		return speed;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	
	
	public int getWidth() {
		return width;
	}
	public int getX() {
		return x;
	}
	public int getxVel() {
		return xVel;
	}
	
	
	public int getY() {
		return y;
	}

	public int getyVel() {
		return yVel;
	}

	public boolean hasWeapon() {
		return getWeapon()!=null;
	}

	public boolean hitHG(int y,int x){
		GamePanel.hg.getX();
		if (getBounds().intersects(GamePanel.hg.getBounds())){
			return true;
		} else {
			return false;
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public boolean isJumpKey() {
		return jumpKey;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}
	public void jump() {
		  jumpSpeed = jumpSpeed+gravity;
		  if (jumpSpeed>40-height) {
		    jumpSpeed = 40-height;
		  }
		  if (jumpSpeed<0) {
		    movePlayer(0, -1, -1);  
		  } else if (jumpSpeed>0) {
		    movePlayer(0, 1, 1);  
		  }
	}
	public void movePlayer(int xVel, int yVel, int jump) {
		mapStorage = GamePanel.level.getTiles();
		if (Math.abs(jump)==1) {
		    speed=jumpSpeed*jump;
		  } else {
		    speed=5;
		}
		  getMyCorners(x, y+speed*yVel,mapStorage);
		  if (yVel == -1) {
		    if (upLeft == TileType.EMPTY && upRight== TileType.EMPTY) {
		      y += speed*yVel;
		    } else {
		      y = yTile*40;
		      jumpSpeed = 0;
		    }
		  }
		  if (yVel == 1) {
		    if (downLeft == TileType.EMPTY && downRight == TileType.EMPTY || downLeft == TileType.PIT && downRight == TileType.PIT) {
		      y += speed*yVel;
		    } else {
		      y = (yTile+1)*40-height;
		      isJumping = false;
		    }
		  }
		  getMyCorners (x+speed*xVel, y,mapStorage);
		  if (xVel == -1) {
			  
		    if (downLeft== TileType.EMPTY && upLeft == TileType.EMPTY) {
		      x += speed*xVel;
		    } else {
		      x = xTile*40;
		    }
		    fall();
		  }
		  if (xVel == 1) {
			 
		    if (upRight == TileType.EMPTY && downRight == TileType.EMPTY) {
		      x += speed*xVel;
		    } else {
		       x = (xTile+1)*40-width;
		    }
		    fall();
		  }
		  xTile = (int)(Math.floor(x/40));
		  yTile = (int)(Math.floor(y/40));
	}
	public void playerReset(){
		x = 385;
		y = 525;
	}
	public void release() {
		getWeapon().release();
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	public void setJumpKey(boolean jumpKey) {
		this.jumpKey = jumpKey;
	}

	public void setJumpSpeed(int jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public void setjumpStart(int jumpStart) {
		this.jumpStart = jumpStart;
	}

	public void setLastXVel(int lastXVel) {
		this.lastXVel = lastXVel;
	}

	public void setMapStorage(Tile[][] mapStorage) {
		this.mapStorage = mapStorage;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setWeapon(Class<? extends Weapon> weapon) {
		// Force Weapon Change
		while (this.weapon!=null && weapon.equals(this.weapon.getClass())) {
			weapon = Weapon.getNewWeapon();
		}
		try {
			this.weapon = weapon.getConstructor().newInstance();
		} catch (Exception e) {
		}
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public void shoot() {
		if (weapon!=null)
			weapon.shoot(this);
	}

	public void updateKeys(){
		if (movingLeft)
			movePlayer(-1,0,0);
		if (movingRight)
			movePlayer(1,0,0);
		if (jumpKey){
			if (!isJumping){
				setJumping(true);
				setJumpSpeed(getJumpStart());
			}
		}
			
	}

	public void updateWeapon(Graphics g) {
		if (weapon!=null) {
			weapon.update(g);
		}
	}
}
