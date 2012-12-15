package com.confedcats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.confedcats.ld25.enemies.BaseEnemy;
import com.confedcats.ld25.enemies.ConfederateSoldier;
import com.confedcats.ld25.maps.Map;
import com.confedcats.ld25.maps.Rainbow;
import com.confedcats.ld25.sounds.Sound;

public class GamePanel extends JPanel {
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics bg = buff.getGraphics();
	Player player = new Player();
	ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();
	Map level1 = new Rainbow();
	boolean jumpKey = false;
	
	public int currentFPS = 0;
    public int FPS = 0;
    public long start = 0;
	public GamePanel() {
		super();
		
		// Force Repaint To Achieve 60fps
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				tick();
			}
		}, 1, 1000/60);
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
					enemies.add(new ConfederateSoldier(100,385,20));
			}
		});
		level1.getMusic().play();
	}
	public void paintComponent(Graphics g) {
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, Driver.WIDTH, Driver.HEIGHT);
		// Start Painting
		level1.paint(bg);
		
		//Paints the player
		if (player.getxVel()>0)
			bg.drawImage(Player.PLAYER1_RIGHT, player.getX(), player.getY(), null);
		else if (player.getxVel()<0)
			bg.drawImage(Player.PLAYER1_LEFT, player.getX(), player.getY(), null);
		else
			bg.drawImage(player.getLastXVel()>0?Player.PLAYER1_RIGHT:Player.PLAYER1_LEFT, player.getX(), player.getY(), null);
		
		//Paints the enemies
		bg.setColor(Color.RED);
		for (int i = 0; i < enemies.size(); i++){
			bg.fillRect(enemies.get(i).getX(), enemies.get(i).getY(), 30, 30);
		}
		//Paints the FPS counter
		bg.setColor(Color.WHITE);
		bg.drawString(FPS+" FPS", 20, 20);
		// End Painting
		// Paint Buffer To Graphics Handle Stretching The Image To Container Size
		g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, Driver.WIDTH, Driver.HEIGHT, null);
	}
	public void tick() {
		repaint();
		calcFPS();
		synchronized(player){
			player.updatePlayerPos(level1);
		}
		for (int i = 0; i < enemies.size(); i++){
			enemies.get(i).updateEnemyPos(level1);
		}
		player.jumpingStuff(jumpKey);
	}
	public void calcFPS(){
		currentFPS++;
        if(System.currentTimeMillis() - start >= 1000) {
            FPS = currentFPS;
            currentFPS = 0;
            start = System.currentTimeMillis();
        }
	}
	
	
	
}
