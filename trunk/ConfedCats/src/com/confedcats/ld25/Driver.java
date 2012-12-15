package com.confedcats.ld25;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver extends Applet {
	//Double buffer stuff
	Image offscreen;
	Dimension dim;
	Graphics g;
	int xMouse, yMouse;
		
	public static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final JFrame popout = new JFrame("Confederate Cats");
	public static final JPanel panel = new JPanel();
	public void init() {
		popout.setUndecorated(true);
		popout.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		add(panel);
		panel.setFocusable(true);
		panel.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_F11) {
					if (popout.isVisible()) {
						add(panel);
						popout.setVisible(false);
						device.setFullScreenWindow(null);
					} else {
						popout.add(panel);
						popout.setVisible(true);
						device.setFullScreenWindow(popout);
					}
				}
			}
		});
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent me) {
				
			}

			@Override
			public void mouseMoved(MouseEvent me) {
				xMouse = me.getX();
				yMouse = me.getY();
				repaint();
				
			}
			
		});
	
		
		this.setSize(800, 600);
		//Double buffer stuff
	    dim = this.getSize();
	    //More double buffer stuff
	  	offscreen = createImage(dim.width,dim.height);
	  	g = offscreen.getGraphics();
	}
	
	public void paint(Graphics bufferGraphics){
		//More double buffer stuff
		g = offscreen.getGraphics(); 
		g.clearRect(0,0,dim.width,dim.height);
		
		g.setColor(Color.BLACK);
		g.fillRect(xMouse, yMouse, 100, 100);
		
		//Last of double buffer stuff, not including the update method
		bufferGraphics.drawImage(offscreen,0,0,this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
}
