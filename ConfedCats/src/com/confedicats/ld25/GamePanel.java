package com.confedicats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.confedicats.ld25.enemies.BaseEnemy;
import com.confedicats.ld25.enemies.UnionSoldier;
import com.confedicats.ld25.hologear.HoloGear;
import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.maps.Rainbow;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.Laser;

public class GamePanel extends JPanel {
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics bg = buff.getGraphics();
	public static Player player = new Player();
	public static ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();


	public static Map level = new Rainbow();
	boolean jumpKey = false;
	
	public int currentFPS = 0;
    public int FPS = 0;
    public long start = 0;
    public static HoloGear hg = new HoloGear(Laser.class, 460, 200);
	public GamePanel() {
		super();
		
		// Force Repaint To Achieve 60fps
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				tick();
			}
		}, 1, 1000/60);
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				if ((int)(Math.random()*99+1) % 5 == 0)
					enemies.add(new UnionSoldier(20,385,20,2));
			}
		}, 1, 1000);
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_LEFT) 
					player.setxVel(-5);
				if (event.getKeyCode()==KeyEvent.VK_RIGHT) 
					player.setxVel(5);
				if (event.getKeyCode()==KeyEvent.VK_UP) {
					jumpKey = true;
				}
			}
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_LEFT && player.getxVel() < 0){ 
					player.setxVel(0);
					player.setLastXVel(-1);
				}
				if (event.getKeyCode()==KeyEvent.VK_RIGHT && player.getxVel() > 0){
					player.setxVel(0);
					player.setLastXVel(1);
				}
					
				if (event.getKeyCode()==KeyEvent.VK_UP ) 
					jumpKey = false;
				if (event.getKeyCode()==KeyEvent.VK_R ) 
					player.playerReset();
				if (event.getKeyCode()==KeyEvent.VK_M ) {
					Sound.setMute(!Sound.isMute());
					System.out.println(Sound.isMute()+"");
				}
				if (event.getKeyCode()==KeyEvent.VK_E ) 
					enemies.add(new UnionSoldier(20,385,20,2));
				if (event.getKeyCode()==KeyEvent.VK_SPACE) {
					player.shoot();
				}
			}
		});
		level.getMusic().play();
	}
	public void paintComponent(Graphics g) {
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, Driver.WIDTH, Driver.HEIGHT);
		// Start Painting
		level.paint(bg);
		
		//Paints the player
		player.updateWeapon(bg);
		if (player.getxVel()>0)
			bg.drawImage(player.getRight(), player.getX(), player.getY(), null);
		else if (player.getxVel()<0)
			bg.drawImage(player.getLeft(), player.getX(), player.getY(), null);
		else
			bg.drawImage(player.getLastXVel()>0?player.getRight():player.getLeft(), player.getX(), player.getY(), null);
		
		hg.paint(bg);
		
		//Paints the enemies
		bg.setColor(Color.RED);
		for (BaseEnemy be:enemies){
			if (be.getxVel()>0)
				bg.drawImage(be.getRight(), be.getX(), be.getY(), null);
			else if (be.getxVel()<0)
				bg.drawImage(be.getLeft(), be.getX(), be.getY(), null);
		}
		//Paints the FPS counter
		bg.setColor(Color.RED);
		bg.drawString(FPS+" FPS (r41)", 20, 20);
		// End Painting
		// Paint Buffer To Graphics Handle Stretching The Image To Container Size
		g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, Driver.WIDTH, Driver.HEIGHT, null);
	}
	public void tick() {
		repaint();
		calcFPS();
		synchronized(player){
			player.updatePlayerPos(level);
		}
		for (int i = 0; i < enemies.size(); i++){
			enemies.get(i).updateEnemyPos(level);
		}
		checkEnemiesAlive();
		player.jumpingStuff(jumpKey);
		//checkJoystick();
	}
	public void calcFPS(){
		currentFPS++;
        if(System.currentTimeMillis() - start >= 1000) {
            FPS = currentFPS;
            currentFPS = 0;
            start = System.currentTimeMillis();
        }
	}
	
	public void checkEnemiesAlive(){
		for (int i = 0; i < enemies.size(); i++){
			if (enemies.get(i).getHealth() == 0){
				int tempMulti = enemies.get(i).getMulti();
				enemies.remove(i);
				enemies.add(new UnionSoldier(100,385,20,4));
				i--;
			}
		}
	}
	/*
	public void checkJoystick(){
		try {
			if(Driver.joystick.isControllerConnected()){
				Driver.joystick.pollController();
				if (Driver.joystick.getXAxisPercentage() > 60){
					player.setxVel(5);
				} else if (Driver.joystick.getXAxisPercentage() < 40){
					player.setxVel(-5);
				} else {
					player.setxVel(0);
				}
				if (Driver.joystick.getButtonValue(1)){
					jumpKey = true;
				} else {
					jumpKey =false;
				}
			}	
		} catch (NullPointerException e){}
	}*/
	
	
	
}
