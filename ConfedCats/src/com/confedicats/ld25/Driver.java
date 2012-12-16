package com.confedicats.ld25;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.confedicats.ld25.sounds.Sound;

public class Driver extends Applet {
	public static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	public static final GamePanel PANEL = new GamePanel();
	public static final JFrame POPOUT = new JFrame("ConfediCats");
	private boolean muted = false;
	// First you need to create controller.
	//public static final JInputJoystick joystick = new JInputJoystick(Controller.Type.GAMEPAD);
	// Check if the controller was found.
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
					setFullscreen(!POPOUT.isVisible());
				}
			}
		});
		/* End Of Full Screen Pop Out Class */
		
		/*if(!joystick.isControllerConnected()){
			   System.out.println("No controller found!");
		}
		new java.util.Timer().schedule(new java.util.TimerTask(){
			public void run() {
				if(joystick.isControllerConnected()){
					joystick.pollController();
					if (joystick.getButtonValue(0)){
						setFullscreen(!POPOUT.isVisible());
					}
				}
			}
		}, 1, 100);
		 */
	}
	
	public void setFullscreen(boolean fulls) {
		if (fulls) {
			POPOUT.add(PANEL, BorderLayout.CENTER);
            PANEL.setPreferredSize(new Dimension(1,1));
            POPOUT.setVisible(true);
            PANEL.requestFocus();
            DEVICE.setFullScreenWindow(POPOUT); // Re-Associate popout with full screen.
		} else {
			add(PANEL, BorderLayout.CENTER);
			PANEL.setSize(new Dimension(WIDTH,HEIGHT));
			POPOUT.setVisible(false);
			PANEL.requestFocus();
			DEVICE.setFullScreenWindow(null); // Un-Associate popout with full screen.
		}
	}
	public void start() {
		Sound.setMute(muted);
	}
	public void stop() {
		muted = Sound.isMute();
		Sound.setMute(true);
	}
}
