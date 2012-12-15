package com.confedcats.ld25.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.confedcats.ld25.Driver;
import com.confedcats.ld25.enemies.Boss;
import com.confedcats.ld25.enemies.Enemy;
import com.confedcats.ld25.sounds.Sound;
import com.confedcats.ld25.tiles.ColorTile;
import com.confedcats.ld25.tiles.Tile;
import com.confedcats.ld25.tiles.Tile.TileType;

public class Rainbow extends Map {
	/* 
	 * 0 - Empty
	 * 1 - Left
	 * 2 - Center
	 * 3 - Right
	 * 4 - Pit
	 * 5 - Spout
	 */
	public static final int[][] MAP_MASK = new int[][]{
		{2,2,2,2,2,2,2,2,2,5,5,2,2,2,2,2,2,2,2,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,1,2,2,2,2,2,2,2,2,2,2,3,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,1,2,3,0,0,1,2,2,2,2,3,0,0,1,2,3,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,1,2,3,0,1,2,3,0,1,2,3,0,1,2,3,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,2,2,2,3,0,0,0,1,2,3,0,0,0,1,2,2,2,2,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,2,2,2,2,2,2,2,2,4,4,2,2,2,2,2,2,2,2,2}};
	private static final Sound MUSIC = Sound.create("rainbow.mp3", true);
	public int[][] generateMap() {
		return MAP_MASK;
	}
	public Image getBackground() {
		BufferedImage img = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.PINK);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		return img;
	}

	public HashMap<Integer, Boss> getBosses() {
		return new HashMap<Integer, Boss>();
	}

	public int getDifficulty() {
		return 0;
	}

	public Sound getMusic() {
		return MUSIC;
	}
	
	public String getName() {
		return "Rainbow";
	}

	public Enemy[] getValidEnemies() {
		return new Enemy[]{};
	}
	public Tile[] getMapTiles() {
		return new Tile[]{ColorTile.getTransparentTile(), new ColorTile(Color.LIGHT_GRAY), new ColorTile(Color.GRAY), new ColorTile(Color.DARK_GRAY), new ColorTile(Color.RED), new ColorTile(Color.GREEN)};
	}
	public TileType[] getMapTileTypes() {
		return new TileType[]{TileType.EMPTY, TileType.PLATFORM, TileType.PLATFORM, TileType.PLATFORM, TileType.PIT, TileType.SPOUT};
	}

}
