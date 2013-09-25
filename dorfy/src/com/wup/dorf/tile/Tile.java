package com.wup.dorf.tile;

import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;

public class Tile {

	public int id;
	public int tex;
	
	public static final Tile[] list = new Tile[256];
	public static final Tile stone = new Tile(1);
	public static final Tile grass = new Tile(2);
	
	public Tile(int i, int t){
		if(list[i] != null) throw new IllegalArgumentException(i + " is occupied by " + list[i].getClass().getSimpleName());
		list[i] = this;
		id = i;
		tex = t;
	}
	
	public Tile(int i){
		this(i, i - 1);
	}
	
	public void render(Screen s, int x, int y){
		s.draw(Art.tiles[tex], x * 16, y * 16);
	}
	
}
