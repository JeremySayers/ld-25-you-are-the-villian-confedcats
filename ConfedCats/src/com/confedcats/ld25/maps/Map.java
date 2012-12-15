package com.confedcats.ld25.maps;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.confedcats.ld25.enemies.Boss;
import com.confedcats.ld25.enemies.Enemy;

public abstract class Map {
	private Image[][] tiles;
	public Map() {
		super();
		this.tiles = convertMap();
	}
	public Image[][] convertMap() {
		int[][] intMap = generateMap();
		Image[] tiles = getMapTiles();
		Image[][] map;
		if (intMap.length>0) {
			map = new Image[intMap.length][intMap[1].length];
			for (int y=0; y<intMap.length; y++) {
				for (int x=0; x<intMap[y].length; x++) {
					try {
						map[y][x] = tiles[intMap[y][x]];
					} catch (Exception e) {
						map[y][x] = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
					}
				}
			}
		} else {
			return new Image[0][0];
		}
		return map;
	}
	public abstract int[][] generateMap();
	public abstract Image getBackground();
	public abstract HashMap<Integer, Boss> getBosses();
	public abstract int getDifficulty();
	public abstract Image[] getMapTiles();
	public abstract String getName();
	public Image[][] getTiles() {
		return tiles;
	}
	public abstract Enemy[] getValidEnemies();
	public void paint(Graphics g) {
		g.drawImage(getBackground(), 0, 0, null);
		Image[][] tiles = getTiles();
		for (int y=0; y<tiles.length; y++) {
			for (int x=0; x<tiles[y].length; x++) {
				g.drawImage(tiles[y][x], x*40, y*40, (x+1)*40, (y+1)*40, null);
			}
		}
	}
}
