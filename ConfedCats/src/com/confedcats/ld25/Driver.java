package com.confedcats.ld25;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Driver extends Applet {
	public static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final JFrame popout = new JFrame("Confederate Cats");
	public static final GamePanel panel = new GamePanel();
	public void init() {
		// Initialize Applet
		setSize(800, 600);
		setLayout(new BorderLayout());
		
		// Initialize Game Panel
		panel.setFocusable(true);
		add(panel, BorderLayout.CENTER);
		
		/* Start Of Full Screen Pop Out Stuff */
		popout.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		popout.setLayout(new BorderLayout());
		popout.setUndecorated(true);
		panel.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode()==KeyEvent.VK_F11) {
					if (popout.isVisible()) {
						add(panel, BorderLayout.CENTER);
						popout.setVisible(false);
						device.setFullScreenWindow(null); // Un-Associate popout with full screen.
					} else {
						popout.add(panel, BorderLayout.CENTER);
						popout.setVisible(true);
						device.setFullScreenWindow(popout); // Re-Associate popout with full screen.
					}
				}
			}
		});
		/* End Of Full Screen Pop Out Class */
	}
}
