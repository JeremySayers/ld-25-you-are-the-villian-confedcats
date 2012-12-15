package com.confedcats.ld25.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


public class AnimationTile extends Tile {
	private ArrayList<Image> frames = new ArrayList<Image>();
	private int frame = 0;
	private int id = 0;
	public AnimationTile(String... fnames) {
		super(new ColorTile(Color.GREEN));
		for (String f:fnames) {
			frames.add(loadImage(f));
		}
	}
	private AnimationTile(ArrayList<Image> iframes) {
		super(new ColorTile());
		frames.addAll(iframes);
	}
	public AnimationTile clone() {
		return new AnimationTile(frames);
	}
	public void paintMe(Graphics g){
		g.drawImage(frames.get(frame), getX(), getY() , null);
		id ++;
		if (id==12) {
			frame++;
			id=0;
		}
		if (frame==frames.size()) {
			frame=0;
			id=0;
		}
	}

}
