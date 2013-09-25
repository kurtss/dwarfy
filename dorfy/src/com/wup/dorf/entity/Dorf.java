package com.wup.dorf.entity;

import com.wup.dorf.ai.TaskChopTree;
import com.wup.dorf.ai.TaskGetItem;
import com.wup.dorf.ai.TaskItemToStockpile;
import com.wup.dorf.ai.TaskManager;
import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;

public class Dorf extends Entity{

	private TaskManager manager;
	public Item holding;
	public boolean hasPath;
	
	public Dorf(){
		manager = new TaskManager();
	}
	
	public void init(){
		manager.addTask(new TaskChopTree(this, 10));
		manager.addTask(new TaskGetItem(this, 10));
		manager.addTask(new TaskItemToStockpile(this, 10));
	}
	
	public void tick(){
		super.tick();
		manager.update();
	}
	
	public void render(Screen s){
		s.draw(Art.dorf[holding == null ? 0 : 1], x * 16, y * 16);
		if(holding != null) s.draw(Art.items[holding.resource.tex], x * 16, y * 16 - 10);
	}
	
}
