package com.confedicats.ld25;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.confedicats.ld25.enemies.BaseEnemy;
import com.confedicats.ld25.enemies.UnionSoldier;
import com.confedicats.ld25.hologear.HoloGear;
import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.maps.Rainbow;
import com.confedicats.ld25.maps.Town;
import com.confedicats.ld25.options.Options;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.Weapon;

public class GamePanel extends JPanel {
	public static enum Screen {MAIN_MENU, OPTIONS, RAINBOW, TOWN, INDUSTRIAL, GAME_OVER};
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics bg = buff.getGraphics();
	public static ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	public Screen screen = Screen.MAIN_MENU;
	public static Font font;
	public static MainMenu menu = new MainMenu();
	public static Map level;

	public static Player player = new Player(0, 0);
	boolean jumpKey = false;
	
	public int currentFPS = 0;
    public int FPS = 0;
    public long start = 0;
	private Options options;
	private int ticktock = 0;
	private boolean flashVisible = false;
	private boolean moveHG = true;
	
	Tile[][] mapStorage;
    public static HoloGear hg = new HoloGear(Weapon.getNewWeapons(), 460, 200);
	public GamePanel() {
		super();
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, GamePanel.class.getResource("/PressStart2P.ttf").openStream()).deriveFont(48f);
		} catch (Exception e1) {
			font = new Font("Arial Black", Font.PLAIN, 48);
		}
		
		
		// Force Repaint To Achieve 60fps
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				tick();
			}
		}, 1, 1000/60);
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask(){
			public void run() {
				if (screen==Screen.RAINBOW||screen==Screen.TOWN||screen==Screen.INDUSTRIAL) {
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
				int width = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getWidth() : Driver.WIDTH;
				int height = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getHeight() : Driver.HEIGHT;
				Point scaled = translateSize(width, height, e.getPoint());
				if (screen==Screen.MAIN_MENU) {
					if (MainMenu.START_LOC.contains(scaled)) {
						setScreen(Screen.RAINBOW);
					} else if (MainMenu.OPT_LOC.contains(scaled)) {
						setScreen(Screen.OPTIONS);
					}
				} else if (screen==Screen.OPTIONS) {
					if (Options.BGM_LOC.contains(scaled)) {
						Sound.setMuteBGM(!Sound.isMuteBGM());
					} else if (Options.SFX_LOC.contains(scaled)) {
						Sound.setMuteSFX(!Sound.isMuteSFX());
					} else if (Options.MUTE_LOC.contains(scaled)) {
						Sound.setMute(!Sound.isMute());
					} else if (Options.FULL_LOC.contains(scaled)) {
						for (KeyListener kl:Driver.PANEL.getKeyListeners()) {
							kl.keyReleased(new KeyEvent(Driver.PANEL, KeyEvent.KEY_LOCATION_STANDARD, System.currentTimeMillis(), 0, KeyEvent.VK_F, 'f'));
						}
					} else if (Options.BACK_LOC.contains(scaled)) {
						setScreen(Screen.MAIN_MENU);
					} 
				} else if (screen==Screen.GAME_OVER) {
					setScreen(Screen.RAINBOW);
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int width = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getWidth() : Driver.WIDTH;
				int height = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getHeight() : Driver.HEIGHT;
				Point scaled = translateSize(width, height, e.getPoint());
				if (screen==Screen.MAIN_MENU) {
					MainMenu.start_hovered = false;
					MainMenu.opt_hovered = false;
					if (MainMenu.START_LOC.contains(scaled)) {
						MainMenu.start_hovered = true;
					} else if (MainMenu.OPT_LOC.contains(scaled)) {
						MainMenu.opt_hovered = true;
					}
				} else if (screen==Screen.OPTIONS) {
					Options.back_hovered = Options.BACK_LOC.contains(scaled);
				}
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event) {
				if (screen==Screen.RAINBOW||screen==Screen.TOWN||screen==Screen.INDUSTRIAL) {
					if (event.getKeyCode()==KeyEvent.VK_LEFT)
						player.setMovingLeft(true);
					if (event.getKeyCode()==KeyEvent.VK_RIGHT) 
						player.setMovingRight(true);
					if (event.getKeyCode()==KeyEvent.VK_UP){
						player.setJumpKey(true);
					}
					if (event.getKeyCode()==KeyEvent.VK_SPACE && player.hasWeapon() && player.getWeapon().isAutomatic()) {
						player.shoot();
					}
				} else if (screen==Screen.GAME_OVER) {
					setScreen(level instanceof Town ? Screen.TOWN : Screen.RAINBOW);
				}
			}
			public void keyReleased(KeyEvent event) {
				if (screen==Screen.MAIN_MENU) {
					if (event.getKeyCode()==KeyEvent.VK_2) {
						setScreen(Screen.TOWN);
					}
				}
				if (screen==Screen.RAINBOW||screen==Screen.TOWN||screen==Screen.INDUSTRIAL) {
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
					}
					if (event.getKeyCode()==KeyEvent.VK_SPACE && player.hasWeapon()) {
						if (!player.getWeapon().isAutomatic())
							player.shoot();
						else
							player.release();
					}
				}
			}
		});
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
			if (enemies.get(i).getBounds().intersects(player.getBounds())){
				player.setAlive(false);
			}
			if (enemies.get(i).getHealth() == -Integer.MAX_VALUE){
				enemies.remove(i);
				Sound.create("fallinpit.wav", false).play();
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
	public void paintComponent(Graphics g) {
		// Clear Screen
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, Driver.WIDTH, Driver.HEIGHT);
		// Start Painting
		bg.setFont(font);
		bg.setColor(Color.BLACK);
		String count = ""+HoloGear.COUNT;
		FontMetrics fm = bg.getFontMetrics();
		switch (screen) {
			case MAIN_MENU:
				menu.paint(bg);
				break;
			case OPTIONS:
				options.paint(bg);
				break;
			case RAINBOW:
			case TOWN:
			case INDUSTRIAL:
				level.paint(bg);
				
				bg.drawString(count, 400-fm.stringWidth(count)/2, 100);
				
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
				level.paint(bg);
				
				//Paints the player
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
				
				bg.setColor(Color.BLACK);
				if (ticktock==12) {
					ticktock=0;
					flashVisible = !flashVisible;
				} else {
					ticktock++;
				}
				if (flashVisible) {
					bg.setFont(bg.getFont().deriveFont(75f));
					fm = bg.getFontMetrics();
					String go = "GAME OVER!";
					bg.drawString(go, 400-fm.stringWidth(go)/2, 200);
				}
				bg.setFont(bg.getFont().deriveFont(150f));
				fm = bg.getFontMetrics();
				bg.drawString(count, 400-fm.stringWidth(count)/2, 400);
				bg.setFont(bg.getFont().deriveFont(30f));
				fm = bg.getFontMetrics();
				String click = "Press Any Key To Continue!";
				bg.drawString(click, 400-fm.stringWidth(click)/2, 500);
		}
		//Paints the FPS counter
		//bg.setColor(Color.RED);
		//bg.drawString(FPS+" FPS (r46)", 20, 20);
		// End Painting
		// Paint Buffer To Graphics Handle Stretching The Image To Container Size
		g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, Driver.WIDTH, Driver.HEIGHT, null);
	}
	public void setScreen(Screen newScreen) {
		try {
			Sound.stopAll();
			Sound.clearAll();
		} catch (Exception e){
		}
		ArrayList<Point> spouts;
		Point spout;
		switch (newScreen) {
			case MAIN_MENU:
				menu.getMusic().play();
				break;
			case OPTIONS:
				options = new Options();
				options.getMusic().play();
				break;
			case RAINBOW:
				HoloGear.COUNT = 0;
				enemies.clear();
				level = new Rainbow();
				spouts = level.getSpouts();
				spout = spouts.get((int)(Math.random()*spouts.size()));
				player = new Player(spout.x, 20);
				level.getMusic().play();
				break;
			case TOWN:
				HoloGear.COUNT = 0;
				enemies.clear();
				level = new Town();
				spouts = level.getSpouts();
				spout = spouts.get((int)(Math.random()*spouts.size()));
				player = new Player(spout.x, 20);
				level.getMusic().play();
				break;
			case INDUSTRIAL:
				HoloGear.COUNT = 0;
				enemies.clear();
				break;
			case GAME_OVER:
				Sound.create("gameover.au", true).play();
				break;
		}
		screen = newScreen;
	}
	public void tick() {
		repaint();
		calcFPS();
		if (screen==Screen.RAINBOW||screen==Screen.TOWN||screen==Screen.INDUSTRIAL) {
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
			Tile[] tiles = level.getTiles()[14];
			if (!player.isAlive())
					setScreen(Screen.GAME_OVER);
			if (moveHG)
				moveHGOnCreation();
			
		}
		
		//checkJoystick();
	}
	
	private Point translateSize(int width, int height, Point orig) {
		return new Point((int)(orig.x*1.0/width*Driver.WIDTH), (int)(orig.y*1.0/height*Driver.HEIGHT));
	}
	public void moveHGOnCreation(){
		mapStorage = GamePanel.level.getTiles();
		int ranX = (int)(Math.random()*18+1);
		int ranY = (int)(Math.random()*13+1);
		boolean foundNewLoc = false;
		while (!foundNewLoc){
			if (mapStorage[(int)(ranY+1)][(int)(ranX)].getTileType() == TileType.PLATFORM && mapStorage[(int)(ranY)][(int)(ranX)].getTileType() == TileType.EMPTY && ranX !=8 && ranY != 5){
				GamePanel.hg.firstPlace(ranX*40+10,ranY*40+10, Weapon.getNewWeapons());
				foundNewLoc = true;
			} else {
				ranX = (int)(Math.random()*19+1);
				ranY = (int)(Math.random()*13+1);
			}
		}
		moveHG = false;
	}
	
	
}
