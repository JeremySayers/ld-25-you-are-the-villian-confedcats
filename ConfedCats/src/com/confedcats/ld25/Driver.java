package com.confedcats.ld25;
import java.applet.Applet;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Driver extends Applet {
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
	}
}
