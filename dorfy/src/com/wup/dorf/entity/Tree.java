package com.wup.dorf.entity;

import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;

public class Tree extends Entity{

	public void render(Screen s){
		s.draw(Art.tree, x * 16, y * 16 - 8);
	}
	
	public boolean solid(){
		return true;
	}
	
	public void destroy(Entity destroyer) {
		Item item = new Item(Resource.LOG);
		item.setLocation(x, y, z);
		world.spawn(item);
		world.remove(this);
	}
	
}
