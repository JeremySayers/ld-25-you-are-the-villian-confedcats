package com.confedicats.ld25.options;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.confedicats.ld25.Driver;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.weapons.Weapon;

public class Options {
	private static final BufferedImage BG = loadImage("cc_options.png");
	private static final String PREFIX = "/com/confedicats/ld25/options/";
	private static BufferedImage BGM_ON = loadImage("cc_options_bgm2.png");
	private static BufferedImage BGM_OFF = loadImage("cc_options_bgm1.png");
	public static final Rectangle BGM_LOC = new Rectangle(200,230,200,80);
	private static BufferedImage SFX_ON = loadImage("cc_options_sfx2.png");
	private static BufferedImage SFX_OFF = loadImage("cc_options_sfx1.png");
	public static final Rectangle SFX_LOC = new Rectangle(400,230,200,80);
	private static BufferedImage MUTE_ON = loadImage("cc_options_muteall2.png");
	private static BufferedImage MUTE_OFF = loadImage("cc_options_muteall1.png");
	public static final Rectangle MUTE_LOC = new Rectangle(320,300,170,130);
	private static BufferedImage FULL_ON = loadImage("cc_options_fson2.png");
	private static BufferedImage FULL_OFF = loadImage("cc_options_fsoff1.png");
	public static final Rectangle FULL_LOC = new Rectangle(100,430,650,80);
	private static BufferedImage BACK_NORMAL = loadImage("cc_options_back1.png");
	private static BufferedImage BACK_HOVER = loadImage("cc_options_back2.png");
	public static final Rectangle BACK_LOC = new Rectangle(100,530,200,80);
	public static boolean back_hovered = false;
	public Options(){
	}
	
	private static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Weapon.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
	}
	
	public void paint(Graphics g) {
		g.drawImage(BG, 0, 0, null);
		g.drawImage(Sound.isMuteBGM()?BGM_ON:BGM_OFF, BGM_LOC.x, BGM_LOC.y, null);
		g.drawImage(Sound.isMuteSFX()?SFX_ON:SFX_OFF, SFX_LOC.x, SFX_LOC.y, null);
		g.drawImage(Sound.isMute()?MUTE_ON:MUTE_OFF, MUTE_LOC.x, MUTE_LOC.y, null);
		g.drawImage(Driver.POPOUT.isVisible()?FULL_ON:FULL_OFF, FULL_LOC.x, FULL_LOC.y, null);
		g.drawImage(back_hovered?BACK_HOVER:BACK_NORMAL, BACK_LOC.x, BACK_LOC.y, null);
		System.out.println(back_hovered);
	}

	public Sound getMusic() {
		return Sound.create("menumusic.au", true);
	}
}
