package com.confedcats.ld25.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.confedcats.ld25.Driver;
import com.confedcats.ld25.enemies.Boss;
import com.confedcats.ld25.enemies.Enemy;
import com.confedcats.ld25.tiles.ColorTile;
import com.confedcats.ld25.tiles.Tile;
import com.confedcats.ld25.tiles.Tile.TileType;

public abstract class Map {
	private BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private Tile[][] tiles;
	public Map() {
		this.tiles = convertMap();
		generateBuffer();
	}
	public Tile[][] convertMap() {
		int[][] intMap = generateMap();
		Tile[] tiles = getMapTiles();
		TileType[] types = getMapTileTypes();
		Tile[][] map;
		if (intMap.length>0) {
			map = new Tile[intMap.length][intMap[1].length];
			for (int y=0; y<intMap.length; y++) {
				for (int x=0; x<intMap[y].length; x++) {
					try {
						map[y][x] = tiles[intMap[y][x]].clone();
						map[y][x].register(types[intMap[y][x]], x, y);
					} catch (Exception e) {
						map[y][x] = new ColorTile(Color.WHITE);
						map[y][x].register(types[intMap[y][x]], x, y);
					}
				}
			}
		} else {
			return new Tile[0][0];
		}
		return map;
	}
	public void generateBuffer() {
		Graphics g = buff.getGraphics();
		g.drawImage(getBackground(), 0, 0, null);
		Tile[][] tiles = getTiles();
		for (int y=0; y<tiles.length; y++) {
			for (int x=0; x<tiles[y].length; x++) {
				System.out.println(String.format("(%s,%s)", x*40, y*40));
				g.drawImage(tiles[y][x], x*40, y*40, 40, 40, null);
			}
		}
	}
	public abstract int[][] generateMap();
	public abstract Image getBackground();
	public abstract HashMap<Integer, Boss> getBosses();
	public abstract int getDifficulty();
	public abstract Tile[] getMapTiles();
	public abstract TileType[] getMapTileTypes();
	public abstract String getName();
	public Tile[][] getTiles() {
		return tiles;
	}
	public abstract Enemy[] getValidEnemies();
	public void paint(Graphics g) {
		g.drawImage(buff, 0, 0, null);
	}
}
