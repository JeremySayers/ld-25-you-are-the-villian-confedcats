package com.confedicats.ld25;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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
import com.confedicats.ld25.enemies.Boss;
import com.confedicats.ld25.enemies.CyborgLincoln;
import com.confedicats.ld25.enemies.UnionSoldier;
import com.confedicats.ld25.hologear.HoloGear;
import com.confedicats.ld25.maps.LevelSelect;
import com.confedicats.ld25.maps.Map;
import com.confedicats.ld25.maps.Rainbow;
import com.confedicats.ld25.maps.Town;
import com.confedicats.ld25.options.Options;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.AnimationTile;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;
import com.confedicats.ld25.weapons.Weapon;

public class GamePanel extends JPanel {
	public static enum Screen {MAIN_MENU, OPTIONS, RAINBOW, TOWN, INDUSTRIAL, GAME_OVER, LEVEL_SELECT, INSTRUCTIONS};
	// Create Buffers
	private static final BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static final Graphics bg = buff.getGraphics();
	public static ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	private Screen screen = Screen.MAIN_MENU;
	private Screen lastScreen = Screen.MAIN_MENU;
	public static Font font;
	public static MainMenu menu = new MainMenu();
	public static Map level;

	public static Player player;
	boolean jumpKey = false;
	
	public int currentFPS = 0;
    public int FPS = 0;
    public long start = 0;
	private Options options;
	private static final LevelSelect LEVEL_SELECT = new LevelSelect();
	private int ticktock = 0;
	private boolean flashVisible = false;
	private boolean moveHG = true;
	
	Tile[][] mapStorage;
	public static ArrayList<Integer> bossSends = new ArrayList<Integer>();
    public static HoloGear hg = new HoloGear(Weapon.getNewWeapon(), 460, 200);
    private static BufferedImage BACK_NORMAL = Driver.loadRawImage("/com/confedicats/ld25/options/cc_options_back1.png");
	private static BufferedImage BACK_HOVER = Driver.loadRawImage("/com/confedicats/ld25/options/cc_options_back2.png");
	public static final Rectangle BACK_LOC = new Rectangle(120,500,200,80);
	public static boolean back_hovered = false;
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
				if (getScreen()==Screen.RAINBOW||getScreen()==Screen.TOWN||getScreen()==Screen.INDUSTRIAL) {
					if (HoloGear.COUNT%100==2 && !bossSends.contains(HoloGear.COUNT)) {
						System.out.println("lincoln");
						bossSends.add(HoloGear.COUNT);
						ArrayList<Point> spouts = level.getSpouts();
						Point spout = spouts.get((int)(Math.random()*spouts.size()));
						enemies.add(new CyborgLincoln(200,spout.x,20,10,false));
					}
					else if ((int)(Math.random()*3+1) == 1) {
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
				if (getScreen()==Screen.MAIN_MENU) {
					if (MainMenu.START_LOC.contains(scaled)) {
						setScreen(Screen.LEVEL_SELECT);
					} else if (MainMenu.OPT_LOC.contains(scaled)) {
						setScreen(Screen.OPTIONS);
					} else if (MainMenu.INS_LOC.contains(scaled)) {
						setScreen(Screen.INSTRUCTIONS);
					}
				} else if (getScreen()==Screen.OPTIONS) {
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
						setScreen(lastScreen);
					} 
				} else if (getScreen()==Screen.LEVEL_SELECT) {
					if (LevelSelect.BACK_LOC.contains(scaled)) {
						setScreen(lastScreen); 
					} else if (LevelSelect.RAINBOW_LOC.contains(scaled)) {
						setScreen(Screen.RAINBOW);
					} else if (LevelSelect.TOWN_LOC.contains(scaled)) {
						setScreen(Screen.TOWN);
					}
				} else if (getScreen()==Screen.GAME_OVER) {
					setScreen(lastScreen);
				} else if (getScreen()==Screen.INSTRUCTIONS) {
					if (BACK_LOC.contains(scaled)) {
						setScreen(lastScreen); 
					}
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int width = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getWidth() : Driver.WIDTH;
				int height = Driver.POPOUT.isVisible() ? Driver.DEVICE.getDisplayMode().getHeight() : Driver.HEIGHT;
				Point scaled = translateSize(width, height, e.getPoint());
				if (getScreen()==Screen.MAIN_MENU) {
					MainMenu.start_hovered = false;
					MainMenu.opt_hovered = false;
					MainMenu.ins_hovered = false;
					if (MainMenu.START_LOC.contains(scaled)) {
						MainMenu.start_hovered = true;
					} else if (MainMenu.OPT_LOC.contains(scaled)) {
						MainMenu.opt_hovered = true;
					} else if (MainMenu.INS_LOC.contains(scaled)) {
						MainMenu.ins_hovered = true;
					}
				} else if (getScreen()==Screen.OPTIONS) {
					Options.back_hovered = Options.BACK_LOC.contains(scaled);
				} else if (getScreen()==Screen.LEVEL_SELECT) {
					LevelSelect.back_hovered = LevelSelect.BACK_LOC.contains(scaled);
				} else if (getScreen()==Screen.INSTRUCTIONS) {
					back_hovered = BACK_LOC.contains(scaled);
				}
			}
		});
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event) {
				if (getScreen()==Screen.RAINBOW||getScreen()==Screen.TOWN||getScreen()==Screen.INDUSTRIAL) {
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
				} else if (getScreen()==Screen.GAME_OVER) {
					setScreen(level instanceof Town ? Screen.TOWN : Screen.RAINBOW);
				}
			}
			public void keyReleased(KeyEvent event) {
				if (getScreen()==Screen.MAIN_MENU) {
					if (event.getKeyCode()==KeyEvent.VK_2) {
						setScreen(Screen.TOWN);
					}
				}
				if (getScreen()==Screen.RAINBOW||getScreen()==Screen.TOWN||getScreen()==Screen.INDUSTRIAL) {
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
					if (event.getKeyCode()==KeyEvent.VK_M ) {
						Sound.setMute(!Sound.isMute());
					}
					if (event.getKeyCode()==KeyEvent.VK_SPACE && player.hasWeapon()) {
						if (!player.getWeapon().isAutomatic())
							player.shoot();
						else
							player.release();
					}
					if (event.getKeyCode()==KeyEvent.VK_ESCAPE) {
						setScreen(Screen.MAIN_MENU);
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
		Rectangle playerBounds = player.getBounds();
		if (player.isScytheFired() && (player.isMovingLeft() || player.getLastXVel()<0))
			playerBounds.x+=11;
		for (int i = 0; i < enemies.size(); i++){
			if (enemies.get(i).getBounds().intersects(playerBounds)){
				player.setAlive(false);
			}
			if (enemies.get(i).getHealth() == -Integer.MAX_VALUE){
				Class<? extends BaseEnemy> encl = enemies.get(i).getClass();
				int health = enemies.get(i).getFullHealth();
				int multi = enemies.get(i).getMulti()*4;
				enemies.remove(i);
				Sound.create("fallinpit.wav", false).play();
				ArrayList<Point> spouts = level.getSpouts();
				Point spout = spouts.get((int)(Math.random()*spouts.size()));
				try {
					enemies.add(encl.getConstructor(int.class, int.class, int.class, int.class, boolean.class).newInstance(health, spout.x, 20, multi, true));
				} catch (Exception e){
				}
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
		String kills = BaseEnemy.KILL_COUNT+" KILLS";
		switch (getScreen()) {
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
				
				bg.drawString(count, 400-fm.stringWidth(count)/2, 95);
				bg.setFont(bg.getFont().deriveFont(14f));
				fm = bg.getFontMetrics();
				bg.setColor(Color.WHITE);
				bg.drawString(kills, 400-fm.stringWidth(kills)/2, 115);
				bg.setColor(Color.BLACK);
				
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
				synchronized (enemies) {
					for (BaseEnemy be:enemies){
						if (be.isMovingRight())
							bg.drawImage(be.getRight(), be.getX(), be.getY(), null);
						else if (be.isMovingLeft())
							bg.drawImage(be.getLeft(), be.getX(), be.getY(), null);
						if (be.isBoss())
							((Boss)be).update(bg);
					}
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
				bg.setColor(Color.WHITE);
				bg.drawString(kills, 400-fm.stringWidth(kills)/2, 475);
				bg.setColor(Color.BLACK);
				String click = "Press Any Key To Continue!";
				bg.drawString(click, 400-fm.stringWidth(click)/2, 545);
				break;
			case LEVEL_SELECT:
				LEVEL_SELECT.paint(bg);
				break;
			case INSTRUCTIONS:
				bg.setFont(bg.getFont().deriveFont(12f));
				fm = bg.getFontMetrics();
				bg.setColor(new Color(0xFF5b5b5b));
				bg.fillRect(0, 0, 800, 600);
				bg.setColor(Color.BLACK);
				String msg = "Confedicats is the latest in fast paced platform games!\nBut with a lot of unique features that you need to know about.\n\nThese right here       are hologears, they give you weapons to\nkill enemies.\n\nYou will need to kill the unions soldiers for the\nConferate States of America to remain alive!\n\nThe object is simple, get as many hologears as you\ncan without touching an enemy!\n\nUse the arrow keys to move, up to jump,\nspace to shoot, and F for fullscreen.\n\nYou can always get back to the main menu with the escape key!\n\nGood luck confedicat!";
				int y = 40;
				for (String str:msg.split("\n")) {
					bg.drawString(str, 400-fm.stringWidth(str)/2, y);
					y+=fm.getHeight();
				}
				AnimationTile hgt = HoloGear.tile;
				hgt.register(TileType.EMPTY, 240, 60);
				hgt.paintMe(bg);
				bg.drawImage(back_hovered?BACK_HOVER:BACK_NORMAL, BACK_LOC.x, BACK_LOC.y, null);
				break;
			default:
				break;
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
		switch (newScreen) {
			case MAIN_MENU:
				menu.getMusic().play();
				break;
			case OPTIONS:
				options = new Options();
				options.getMusic().play();
				break;
			case LEVEL_SELECT:
				menu.getMusic().play();
				break;
			case RAINBOW:
				HoloGear.COUNT = 0;
				BaseEnemy.KILL_COUNT = 0;
				bossSends.clear();
				enemies.clear();
				level = new Rainbow();
				player = new Player();
				player.moveHG();
				HoloGear.COUNT--;
				level.getMusic().play();
				break;
			case TOWN:
				HoloGear.COUNT = 0;
				BaseEnemy.KILL_COUNT = 0;
				bossSends.clear();
				enemies.clear();
				level = new Town();
				player = new Player();
				player.moveHG();
				HoloGear.COUNT--;
				level.getMusic().play();
				break;
			case INDUSTRIAL:
				HoloGear.COUNT = 0;
				BaseEnemy.KILL_COUNT = 0;
				enemies.clear();
				break;
			case GAME_OVER:
				Sound.create("gameover.au", true).play();
				break;
		case INSTRUCTIONS:
			menu.getMusic().play();
			break;
		default:
			break;
		}
		lastScreen = screen;
		screen = newScreen;
	}
	public void tick() {
		repaint();
		calcFPS();
		if (getScreen()==Screen.RAINBOW||getScreen()==Screen.TOWN||getScreen()==Screen.INDUSTRIAL) {
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
				GamePanel.hg.firstPlace(ranX*40+10,ranY*40+10, Weapon.getNewWeapon());
				foundNewLoc = true;
			} else {
				ranX = (int)(Math.random()*19+1);
				ranY = (int)(Math.random()*13+1);
			}
		}
		moveHG = false;
	}
	public Screen getScreen() {
		return screen;
	}
	
	
}
