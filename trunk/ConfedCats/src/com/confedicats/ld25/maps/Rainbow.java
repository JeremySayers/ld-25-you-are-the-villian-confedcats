package com.confedicats.ld25.maps;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.confedicats.ld25.enemies.Boss;
import com.confedicats.ld25.enemies.Enemy;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.AnimationTile;
import com.confedicats.ld25.tiles.ColorTile;
import com.confedicats.ld25.tiles.ImageTile;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;

public class Rainbow extends Map {
	/* 
	 * 0 - Empty
	 * 1 - Left
	 * 2 - Center
	 * 3 - Right
	 * 4 - TL Corner
	 * 5 - TR Corner
	 * 6 - BL Corner
	 * 7 - BR Corner
	 * 8 - Vertical
	 * 9 - Pit
	 * A - Spout
	 * B - Left Border
	 * C - Right Border
	 */
	public static final int[][] MAP_MASK = new int[][]{

		{0x4,0x2,0x2,0x2,0x3,0xA,0xA,0x1,0x2,0x2,0x2,0x2,0x3,0xA,0xA,0x1,0x2,0x2,0x2,0x5},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x1,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x3,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x1,0x2,0x3,0x0,0x0,0x1,0x2,0x2,0x2,0x2,0x3,0x0,0x0,0x1,0x2,0x3,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x1,0x2,0x2,0x2,0x3,0x0,0x0,0x0,0x0,0x1,0x2,0x2,0x2,0x3,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0xB,0x2,0x2,0x2,0x3,0x0,0x0,0x0,0x1,0x2,0x2,0x3,0x0,0x0,0x1,0x2,0x2,0x2,0x2,0xC},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0x6,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x3,0x9,0x9,0x1,0x2,0x2,0x2,0x2,0x2,0x2,0x2,0x7},
		{0x8,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8},
		{0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD,0xD}};

	private static final String PREFIX = "rainbow/";
	private static final BufferedImage BACKGROUND = loadImage(PREFIX+"background.png");
	public int[][] generateMap() {
		return MAP_MASK;
	}
	public Image getBackground() {
		return BACKGROUND;
	}

	public HashMap<Integer, Boss> getBosses() {
		return new HashMap<Integer, Boss>();
	}

	public int getDifficulty() {
		return 0;
	}

	public Tile[] getMapTiles() {
		return new Tile[]{new ColorTile(), new ImageTile(PREFIX+"left.png"), new ImageTile(PREFIX+"center.png"), 
				new ImageTile(PREFIX+"right.png"), new ImageTile(PREFIX+"tl.png"), new ImageTile(PREFIX+"tr.png"),
				new ImageTile(PREFIX+"bl.png"), new ImageTile(PREFIX+"br.png"), new ImageTile(PREFIX+"vertical.png"),
				new AnimationTile("/com/confedicats/ld25/maps/cc_firepit1.png", "/com/confedicats/ld25/maps/cc_firepit2.png"), new ColorTile(), new ImageTile(PREFIX+"leftborder.png"), new ImageTile(PREFIX+"rightborder.png"),
				new ColorTile(Color.BLACK)};
	}
	
	public TileType[] getMapTileTypes() {
		return new TileType[]{TileType.EMPTY, TileType.PLATFORM, TileType.PLATFORM, 
				TileType.PLATFORM, TileType.PLATFORM, TileType.PLATFORM, 
				TileType.PLATFORM, TileType.PLATFORM, TileType.PLATFORM,  TileType.PIT, 
				TileType.SPOUT, TileType.PLATFORM, TileType.PLATFORM,TileType.BEYOND_PIT};
	}

	public Sound getMusic() {
		return Sound.create("rainbow.au", true);
	}
	public String getName() {
		return "Rainbow";
	}
	public Enemy[] getValidEnemies() {
		return new Enemy[]{};
	}

}
