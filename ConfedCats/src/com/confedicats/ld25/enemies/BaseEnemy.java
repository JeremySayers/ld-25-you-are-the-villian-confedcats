package com.confedicats.ld25.enemies;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.confedicats.ld25.GamePanel;
import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;

public abstract class BaseEnemy {
	private boolean isJumping;
	private int jumpSpeed;
	private int jumpStart = -18;
	private int health;
	private int xVel = 0;
	private int yVel = 0;
	private int multi = 1;

	private int gravity = 1;
	private int width = 30;
	private int height = 30;
	private int x;
	private int y;
	private boolean xVelUpdate = false;
	private boolean firstFall = true;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean jumpKey;
	private int downY, upY, leftX, rightX, xTile, yTile;
	
	TileType downRight;
	TileType upRight;
	TileType downLeft;
	
	TileType upLeft;
	private Tile[][] mapStorage;
	private boolean angry;
	private int fullHealth;
	public static int KILL_COUNT = 0;
	public BaseEnemy(int health, int x, int y, int multi, boolean angry){
		setHealth(health);
		setFullHealth(health);
		setX(x);
		setY(y);
		this.setAngry(angry);
		xTile = (int)(Math.floor(x/40));
		yTile = (int)(Math.floor(y/40));
		setMulti(multi);
		 if((int) (Math.random()*2+1)==1){
	    	  movingLeft = false;
	    	  movingRight = true;
	      } else {
	    	  movingLeft = true;
	    	  movingRight = false;
	      }
	}
	public void fall() {
		  if (!isJumping) {
			mapStorage = GamePanel.level.getTiles();
		    getMyCorners (x, y+1,mapStorage);
		    if (downLeft == TileType.EMPTY && downRight == TileType.EMPTY || downLeft == TileType.PIT && downRight == TileType.PIT) {
		      jumpSpeed = 0;
		      isJumping = true;
		      firstFall = true;
		    }
		    if(downLeft == TileType.BEYOND_PIT && downRight == TileType.BEYOND_PIT){
		    	health = -Integer.MAX_VALUE;
		    	System.out.println("Enemy Fell into the pit!");
		    }
		  }
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 30, 30);
	}
	public int getGravity() {
		return gravity;
	}
	
	public int getHealth() {
		return health;
	}
	public int getJumpSpeed() {
		return jumpSpeed;
	}
	public int getJumpStart() {
		return jumpStart;
	}
	public abstract BufferedImage getLeft();
	public int getMulti() {
		return multi;
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
	public abstract BufferedImage getRight();
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
	public abstract boolean isBoss();
	
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
		    multi=jumpSpeed*jump;
		  } else {
		    multi=2;
		}
		  getMyCorners(x, y+multi*yVel,mapStorage);
		  if (yVel == -1) {
		    if (upLeft == TileType.EMPTY && upRight== TileType.EMPTY) {
		      y += multi*yVel;
		    } else {
		      y = yTile*40;
		      jumpSpeed = 0;
		    }
		  }
		  if (yVel == 1) {
		    if (downLeft == TileType.EMPTY && downRight == TileType.EMPTY || downLeft == TileType.PIT && downRight == TileType.PIT) {
		      y += multi*yVel;
		    } else {
		      y = (yTile+1)*40-height;
		      if (firstFall){
		    	  if((int) (Math.random()*3+1)==2){
		    		  movingLeft = true;
		    		  movingRight = false;
		    	  } else {
		    		  movingLeft = false;
		    		  movingRight = true;
		    	  }
		    	  firstFall = false;
		      }
		      isJumping = false;
		      return;
		    }
		  }
		  getMyCorners (x+multi*xVel, y,mapStorage);
		  if (xVel == -1) {
			  
		    if (downLeft == TileType.EMPTY && upLeft == TileType.EMPTY) {
		      x += multi*xVel;
		    } else {
		      movingLeft = false;
		      movingRight = true;
		      return;
		    }
		    fall();
		  }
		  if (xVel == 1) {
			 
		    if (upRight == TileType.EMPTY && downRight == TileType.EMPTY) {
		      x += multi*xVel;
		    } else {
		    	movingLeft = true;
			    movingRight = false;
			    return;
		    }
		    fall();
		  }
		  xTile = (int)(Math.floor(x/40));
		  yTile = (int)(Math.floor(y/40));
	}
	public void setGravity(int gravity) {
		this.gravity = gravity;
	}
	public void setHealth(int health) {
		this.health = health;
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
	public void setJumpStart(int jumpStart) {
		this.jumpStart = jumpStart;
	}
	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}
	public void setMulti(int multi) {
		this.multi = multi;
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
	public void updateEnemyPos(Map map){
		mapStorage = map.getTiles();
		
		//Check if THE PLAYER SHOULD BURNNNNNNN
		if (mapStorage[(int) Math.floor((y+gravity+height)/40)][(int) (Math.floor(x)/40)].getTileType() == TileType.PIT){
			health = 0;
		}
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
	public boolean isAngry() {
		return angry;
	}
	public void setAngry(boolean angry) {
		this.angry = angry;
	}
	public int getFullHealth() {
		return fullHealth;
	}
	public void setFullHealth(int fullHeath) {
		this.fullHealth = fullHeath;
	}
}
