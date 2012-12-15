package com.confedcats.ld25;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Driver extends Applet {
	public static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	public static final GamePanel PANEL = new GamePanel();
	public static final JFrame POPOUT = new JFrame("Confederate Cats");
	public void init() {
		// Initialize Applet
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		
		// Initialize Game Panel
		PANEL.setFocusable(true);
		add(PANEL, BorderLayout.CENTER);
		
		/* Start Of Full Screen Pop Out Stuff */
		POPOUT.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		POPOUT.setLayout(new BorderLayout());
		POPOUT.setUndecorated(true);
		PANEL.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_F) {
					if (POPOUT.isVisible()) {
						add(PANEL, BorderLayout.CENTER);
						PANEL.setSize(new Dimension(WIDTH,HEIGHT));
						POPOUT.setVisible(false);
						DEVICE.setFullScreenWindow(null); // Un-Associate popout with full screen.
					} else {
						POPOUT.add(PANEL, BorderLayout.CENTER);
						PANEL.setPreferredSize(new Dimension(1,1));
						POPOUT.setVisible(true);
						DEVICE.setFullScreenWindow(POPOUT); // Re-Associate popout with full screen.
					}
				}
			}
		});
		/* End Of Full Screen Pop Out Class */
	}
}
