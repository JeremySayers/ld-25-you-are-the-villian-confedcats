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

public class Town extends Map {
	/* 
	 * 0 - Empty
	 * 1 - Left
	 * 2 - Center
	 * 3 - Right
	 * 4 - Corner
	 * 5 - Pit
	 * 6 - Spout
	 */
	public static final int[][] MAP_MASK = new int[][]{

		{4,2,2,2,3,6,1,2,3,6,6,1,2,3,6,1,2,2,2,4},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,2,2,2,3,0,0,0,0,0,0,0,0,0,0,1,2,2,2,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,3,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,2,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,1,2,3,0,0,0,0,0,0,0,0,1,2,3,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{2,3,5,5,1,2,3,5,5,1,3,5,5,1,2,3,5,5,1,2},
		{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
		{4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4}};

	private static final String PREFIX = "town/";
	private static final BufferedImage BACKGROUND = loadImage(PREFIX+"cc_town_bg.png");
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
		return 1;
	}

	public Tile[] getMapTiles() {
		return new Tile[]{new ColorTile(), new ImageTile(PREFIX+"cc_town_platl.png"), new ImageTile(PREFIX+"cc_town_platc.png"), 
				new ImageTile(PREFIX+"cc_town_platr.png"), new ImageTile(PREFIX+"cc_town_corner.png"), 
				new AnimationTile("/com/confedicats/ld25/maps/cc_firepit1.png", "/com/confedicats/ld25/maps/cc_firepit2.png"), new ColorTile(),
				new ColorTile(Color.BLACK)};
	}
	
	public TileType[] getMapTileTypes() {
		return new TileType[]{TileType.EMPTY, TileType.PLATFORM, TileType.PLATFORM, 
				TileType.PLATFORM, TileType.PLATFORM, TileType.PIT, 
				TileType.SPOUT, TileType.BEYOND_PIT};
	}

	public Sound getMusic() {
		return Sound.create("town.au", true);
	}
	public String getName() {
		return "Town";
	}
	public Enemy[] getValidEnemies() {
		return new Enemy[]{};
	}

}
