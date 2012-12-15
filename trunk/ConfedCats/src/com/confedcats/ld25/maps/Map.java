package com.confedcats.ld25.maps;

import java.awt.Image;
import java.util.HashMap;

import com.confedcats.ld25.enemies.Boss;
import com.confedcats.ld25.enemies.Enemy;

public abstract class Map {
	private String name;
	private Image[][] tiles;
	private Image background;
	private int difficulty;
	private Enemy[] validEnemies;
	private HashMap<Integer, Boss> bosses; // Level:Boss
	public Map(String name, Image background, int difficulty,
			Enemy[] validEnemies, HashMap<Integer, Boss> bosses) {
		super();
		this.name = name;
		this.background = background;
		this.difficulty = difficulty;
		this.validEnemies = validEnemies;
		this.bosses = bosses;
		this.tiles = generateMap();
	}
	public abstract Image[][] generateMap();
	public Image getBackground() {
		return background;
	}
	public HashMap<Integer, Boss> getBosses() {
		return bosses;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public String getName() {
		return name;
	}
	public Image[][] getTiles() {
		return tiles;
	}
	public Enemy[] getValidEnemies() {
		return validEnemies;
	}
}
