package com.wup.dorf.entity;

import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;

public class Item extends Entity{

	public Resource resource;
	public int amount;
	public Dorf pickingUp;
	
	public Item(Resource r){
		resource = r;
		amount = 1;
	}
	
	public Item(Resource r, int amt){
		this(r);
		amount = amt;
	}
	
	public void render(Screen s){
		s.draw(Art.items[resource.tex], x * 16, y * 16);
	}
	
}
