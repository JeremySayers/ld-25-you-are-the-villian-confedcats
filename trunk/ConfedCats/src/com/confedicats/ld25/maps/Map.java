package com.confedicats.ld25.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.confedicats.ld25.Driver;
import com.confedicats.ld25.enemies.Boss;
import com.confedicats.ld25.enemies.Enemy;
import com.confedicats.ld25.sounds.Sound;
import com.confedicats.ld25.tiles.AnimationTile;
import com.confedicats.ld25.tiles.ColorTile;
import com.confedicats.ld25.tiles.Tile;
import com.confedicats.ld25.tiles.Tile.TileType;

public abstract class Map {
	private static final String PREFIX = "/com/confedicats/ld25/maps/";
	private BufferedImage buff = new BufferedImage(Driver.WIDTH, Driver.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	public BufferedImage getBuff() {
		return buff;
	}
	private Tile[][] tiles;
	private ArrayList<Point> spouts = new ArrayList<Point>();
	public Map() {
		this.tiles = convertMap();
		generateBuffer();
	}
	protected static BufferedImage loadImage(String fname) {
		try {
			return ImageIO.read(Map.class.getResource(PREFIX+fname).openStream());
		} catch (Exception e) {
		}
		return null;
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
						map[y][x].register(types[intMap[y][x]], x*40, y*40);
					} catch (Exception e) {
						map[y][x] = new ColorTile(Color.WHITE);
						map[y][x].register(types[intMap[y][x]], x*40, y*40);
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
				TileType type = tiles[y][x].getTileType();
				if (type!=TileType.EMPTY)
					tiles[y][x].paintMe(g);
				if (type==TileType.SPOUT)
					spouts.add(new Point(x*40, y*40));
			}
		}
	}
	public abstract int[][] generateMap();
	public abstract Image getBackground();
	public abstract HashMap<Integer, Boss> getBosses();
	public abstract int getDifficulty();
	public abstract Tile[] getMapTiles();
	public abstract TileType[] getMapTileTypes();
	public abstract Sound getMusic();
	public abstract String getName();
	public ArrayList<Point> getSpouts() {
		return spouts;
	}
	public Tile[][] getTiles() {
		return tiles;
	}
	public abstract Enemy[] getValidEnemies();
	public void paint(Graphics g) {
		g.drawImage(buff, 0, 0, null);
		for (int y=0; y<tiles.length; y++) {
			for (int x=0; x<tiles[y].length; x++) {
				if (tiles[y][x] instanceof AnimationTile)
					tiles[y][x].paintMe(g);
			}
		}
	}
}
