package com.confedcats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_RGB);
	private static final Graphics bg = buff.getGraphics();
	Player player = new Player();
	boolean jumpKey = false;
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
					player.setxVel(-3);
				if (event.getKeyCode()==KeyEvent.VK_RIGHT) 
					player.setxVel(3);
				if (event.getKeyCode()==KeyEvent.VK_UP) {
					jumpKey = true;
				}
			}
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_LEFT && player.getxVel() < 0) 
					player.setxVel(0);
				if (event.getKeyCode()==KeyEvent.VK_RIGHT && player.getxVel() > 0) 
					player.setxVel(0);
				if (event.getKeyCode()==KeyEvent.VK_UP ) 
					jumpKey = false;
			}
		});
	}
	public void paintComponent(Graphics g) {
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, Driver.WIDTH, Driver.HEIGHT);
		// Start Painting
		bg.setColor(Color.GREEN);
		bg.fillRect(player.getX(), player.getY(), 30, 30);
		// End Painting
		// Paint Buffer To Graphics Handle Stretching The Image To Container Size
		g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, Driver.WIDTH, Driver.HEIGHT, null);
	}
	public void tick() {
		repaint();
		synchronized(player){
			player.updatePlayerPos();
		}
		jumpingStuff();
	}
	
	public void jumpingStuff(){
		if (jumpKey){
			if (!player.isJumping()){
				player.setJumpSpeed(player.getStartJumpSpeed());
				player.setJumping(true);
			}
		}
	}
	
}
