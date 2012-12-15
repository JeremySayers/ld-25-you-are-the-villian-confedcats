package com.confedcats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	Player player;
	public GamePanel() {
		super();
		
		// Force Repaint To Achieve 60fps
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				tick();
			}
		}, 1, 1000/60);
		startGame();
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_LEFT) 
					player.setxVel(-3);
				if (event.getKeyCode()==KeyEvent.VK_RIGHT) 
					player.setxVel(3);
			}
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_LEFT && player.getxVel() < 0) 
					player.setxVel(0);
				if (event.getKeyCode()==KeyEvent.VK_RIGHT && player.getxVel() > 0) 
					player.setxVel(0);
			}
		});
	}
	public void paintComponent(Graphics g) {
		// Create Buffers
		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics bg = buff.getGraphics();
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, getWidth(), getHeight());
		// Start Painting
		bg.setColor(Color.GREEN);
		bg.fillRect(player.getX(), player.getY(), 30, 30);
		// End Painting
		g.drawImage(buff, 0, 0, null); // Paint Buffer To Graphics Handle
	}
	public void tick() {
		repaint();
		synchronized(player){
			player.updatePlayerPos();
		}
	}
	
	public void startGame(){
		createPlayer();
	}
	public void createPlayer(){
		player = new Player();
	}
}
