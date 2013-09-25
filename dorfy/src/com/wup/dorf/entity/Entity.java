package com.wup.dorf.entity;

import java.util.List;

import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;
import com.wup.dorf.world.World;

public class Entity {

	public World world;
	public int x, y, z;
	public boolean remove;
	
	public void init() { }
	
	public void tick(){
		
	}
	
	public void move(int x, int y, int z){
		List<Entity> inTile = world.getInTile(this.x + x, this.y + y);
		for(int e = 0; e < inTile.size(); e++){
			if(inTile.get(e).solid()) return;
		}
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void render(Screen s){
		s.draw(Art.guy, x * 16, y * 16);
	}
	
	public void setLocation(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean solid(){
		return false;
	}
	
	public void destroy(Entity destroyer) { }
	
}
