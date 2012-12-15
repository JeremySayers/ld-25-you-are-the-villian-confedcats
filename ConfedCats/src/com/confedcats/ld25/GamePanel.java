package com.confedcats.ld25;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	public GamePanel() {
		super();
		
		// Force Repaint To Achieve 60fps
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				tick();
			}
		}, 1, 1000/60);
	}
	public void paintComponent(Graphics g) {
		// Create Buffers
		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics bg = buff.getGraphics();
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, getWidth(), getHeight());
		// Start Painting
		
		// End Painting
		g.drawImage(buff, 0, 0, null); // Paint Buffer To Graphics Handle
	}
	public void tick() {
		repaint();
	}
}
