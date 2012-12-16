package com.confedicats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	public static enum Screen {MAIN_MENU, OPTIONS, RAINBOW, LEVEL2, INDUSTRIAL, GAME_OVER};
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics bg = buff.getGraphics();
	public static Player player = new Player(385,460);
	public static ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public Screen screen = Screen.MAIN_MENU;
	public static MainMenu menu = new MainMenu();
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
				if (screen==Screen.RAINBOW||screen==Screen.LEVEL2||screen==Screen.INDUSTRIAL) {
					if ((int)(Math.random()*3+1) == 1) {
						ArrayList<Point> spouts = level.getSpouts();
						Point spout = spouts.get((int)(Math.random()*spouts.size()));
						enemies.add(new UnionSoldier(20,spout.x,20,1,false));
					}
				}
			}
		}, 1, 1000);
		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if (screen==Screen.MAIN_MENU) {
					int width = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getWidth() : Driver.WIDTH;
					int height = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getHeight() : Driver.HEIGHT;
					if (MainMenu.START.contains(translateSize(width, height, e.getPoint()))) {
						setScreen(Screen.RAINBOW);
					}
				}
			}
			private Point translateSize(int width, int height, Point orig) {
				return new Point((int)(orig.x*1.0/width*Driver.WIDTH), (int)(orig.y*1.0/height*Driver.HEIGHT));
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event) {
				if (screen==Screen.RAINBOW||screen==Screen.LEVEL2||screen==Screen.INDUSTRIAL) {
					if (event.getKeyCode()==KeyEvent.VK_LEFT)
						player.setMovingLeft(true);
					if (event.getKeyCode()==KeyEvent.VK_RIGHT) 
						player.setMovingRight(true);
					if (event.getKeyCode()==KeyEvent.VK_UP){
						player.setJumpKey(true);
					}
				}
			}
			public void keyReleased(KeyEvent event) {
				if (screen==Screen.RAINBOW||screen==Screen.LEVEL2||screen==Screen.INDUSTRIAL) {
					if (event.getKeyCode()==KeyEvent.VK_LEFT){ 
						player.setMovingLeft(false);
						player.setLastXVel(-1);
					}
					if (event.getKeyCode()==KeyEvent.VK_RIGHT){
						player.setMovingRight(false);
						player.setLastXVel(1);
					}
					if (event.getKeyCode()==KeyEvent.VK_UP ){ 
						player.setJumpKey(false);
					}
					if (event.getKeyCode()==KeyEvent.VK_R ) 
						player.playerReset();
					if (event.getKeyCode()==KeyEvent.VK_M ) {
						Sound.setMute(!Sound.isMute());
						System.out.println(Sound.isMute()+"");
					}
					if (event.getKeyCode()==KeyEvent.VK_SPACE) {
						player.shoot();
					}
				}
			}
		});
	}
	public void paintComponent(Graphics g) {
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, Driver.WIDTH, Driver.HEIGHT);
		// Start Painting
		switch (screen) {
			case MAIN_MENU:
				menu.paint(bg);
				break;
			case OPTIONS:
				break;
			case RAINBOW:
			case LEVEL2:
			case INDUSTRIAL:
				level.paint(bg);
				//Paints the player
				player.updateWeapon(bg);
				if (player.isMovingRight())
					bg.drawImage(player.getRight(), player.getX(), player.getY(), null);
				else if (player.isMovingLeft())
					bg.drawImage(player.getLeft(), player.getX(), player.getY(), null);
				else
					bg.drawImage(player.getLastXVel()>0?player.getRight():player.getLeft(), player.getX(), player.getY(), null);
				
				hg.paint(bg);
				
				//Paints the enemies
				bg.setColor(Color.RED);
				for (BaseEnemy be:enemies){
					if (be.isMovingRight())
						bg.drawImage(be.getRight(), be.getX(), be.getY(), null);
					else if (be.isMovingLeft())
						bg.drawImage(be.getLeft(), be.getX(), be.getY(), null);
				}
				break;
			case GAME_OVER:
				break;
		}
		//Paints the FPS counter
		//bg.setColor(Color.RED);
		//bg.drawString(FPS+" FPS (r46)", 20, 20);
		// End Painting
		// Paint Buffer To Graphics Handle Stretching The Image To Container Size
		g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, Driver.WIDTH, Driver.HEIGHT, null);
	}
	public void tick() {
		repaint();
		calcFPS();
		for (int i = 0; i < enemies.size(); i++){
			enemies.get(i).fall();
			enemies.get(i).jump();
			enemies.get(i).updateKeys();
		}
		checkEnemiesAlive();
		player.fall();
		player.jump();
		player.updateKeys();
		player.checkHG();
		
		//checkJoystick();
	}
	public void setScreen(Screen newScreen) {
		Sound.stopAll();
		Sound.clearAll();
		switch (newScreen) {
			case MAIN_MENU:
				break;
			case OPTIONS:
				break;
			case RAINBOW:
				level = new Rainbow();
				level.getMusic().play();
				break;
			case LEVEL2:
				break;
			case INDUSTRIAL:
				break;
			case GAME_OVER:
				break;
		}
		screen = newScreen;
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
				enemies.remove(i);
				ArrayList<Point> spouts = level.getSpouts();
				Point spout = spouts.get((int)(Math.random()*spouts.size()));
				enemies.add(new UnionSoldier(20,spout.x,20,4,true));
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
