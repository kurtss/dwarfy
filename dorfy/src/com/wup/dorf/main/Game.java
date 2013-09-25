package com.wup.dorf.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wup.dorf.entity.Entity;
import com.wup.dorf.entity.Player;
import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;
import com.wup.dorf.tile.Tile;
import com.wup.dorf.world.World;

public class Game {

	public static final String TITLE = "dorfy";
	public static final int WIDTH = 640 / 2;
	public static final int HEIGHT = 480 / 2;
	public static final int SCALE = 2;
	public static boolean DEBUG = false;
	public static final int HALFWIDTH = WIDTH / 2;
	public static final int HALFHEIGHT = HEIGHT / 2;
	public Input input;
	
	public World world;
	public Player player;
	
	private static Comparator<Entity> sorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity e0, Entity e1) {
			if(e1.y < e0.y) return +1;
			if(e1.y > e0.y) return -1;
			return 0;
		}
	};
		
	public void init(){
		world = new World(128 / 1, 128 / 1, 16);
		player = new Player();
		player.setLocation(0, 0, 0);
		world.spawn(player);
	}
	
	public void update(){
		world.update();
	}
	
	public void draw(Screen s){
		int x1 = player.x - HALFWIDTH / 16;
		int x2 = player.x + HALFWIDTH / 16;
		int y1 = player.y - HALFHEIGHT / 16;
		int y2 = player.y + HALFHEIGHT / 16;
		s.xOffset = x1 * 16;
		s.yOffset = y1 * 16;
		for(int i = x1; i <= x2; i++){
			for(int j = y1; j <= y2; j++){
				if(world.valid(i, j)){
					if(world.getTile(i, j) > 0){
						Tile.list[world.getTile(i, j)].render(s, i, j);
					}
					if(world.getStockpile(i, j) != null){
						//s.fill(i * 16, j * 16, 16, 16, 0x0000ff);
						s.draw(Art.stockpile, i * 16, j * 16);
						if(world.getStockpile(i, j).getItem(i, j) != null){
							world.getStockpile(i, j).getItem(i, j).render(s);
						}
					}
				}
			}
		}
		List<Entity> within = world.getInTiles(x1, y1, x2, y2);
		Collections.sort(within, sorter);
		for(int e = 0; e < within.size(); e++){
			within.get(e).render(s);
		}
		s.xOffset = s.yOffset = 0;
	}	
	
	public void stop(){

	}
	
	public void keyDown(int key){
		
	}
	
	public void keyTyped(int key, char keychar){
		int mx = 0;
		int my = 0;
		if(key == Keys.up) my = -1;
		if(key == Keys.down) my = 1;
		if(key == Keys.left) mx = -1;
		if(key == Keys.right) mx = 1;
		player.move(mx, my, 0);
		if(keychar == 'q'){
			if(player.actionStartX == -1 && player.actionStartY == -1){
				player.actionStartX = player.x;
				player.actionStartY = player.y;
			}
			else if(player.actionStartX > -1 && player.actionStartY > -1){
				int w = player.x - player.actionStartX + 1;
				int h = player.y - player.actionStartY + 1;
				world.addStockpile(player.actionStartX, player.actionStartY, w, h);
				player.actionStartX = player.actionStartY = -1;
			}
			//world.addStockpile(player.x, player.y, 1, 1);
		}
	}
	
	public void leftMouseClick(int x, int y){

	}
	
	public void rightMouseClick(int x, int y){

	}
	
}
