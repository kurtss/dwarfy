package com.wup.dorf.ai;

import java.util.ArrayList;
import java.util.List;

import com.wup.dorf.entity.Dorf;
import com.wup.dorf.entity.Item;

public class TaskGetItem extends TaskPath{

	private static final List<Item> pickingUp = new ArrayList<Item>();
	private Item moveTo;
	
	public TaskGetItem(Dorf d, int speed) {
		super(d, speed);
	}
	
	public boolean shouldStart(){
		if(dorf.holding != null) return false;
		//if(world.rand.nextInt(500) != 0) return false;
		if(world.getAmountOfType(Item.class) <= 0) return false;
		moveTo = (Item)world.getClosestOfType(Item.class, dorf.x, dorf.y);
		if(moveTo == null) return false;
		//if(moveTo.pickingUp != null) return false;
		//moveTo.pickingUp = dorf;
		if(pickingUp.contains(moveTo)) return false;
		path = AStar.makePath(world, dorf.x, dorf.y, moveTo.x, moveTo.y);
		if(path == null) pickingUp.remove(moveTo);
		return super.shouldStart();
	}
	
	public void start(){
		super.start();
		pickingUp.add(moveTo);
	}
	
	public boolean shouldContinue(){
		return pickingUp.contains(moveTo) && dorf.holding == null && moveTo != null && !moveTo.remove && super.shouldContinue();
	}
	
	public void update(){
		super.update();
	}
	
	public void reset(){
		super.reset();
		moveTo = null;
		pickingUp.remove(moveTo);
	}
	
	public void atEndOfPath(){
		dorf.holding = moveTo;
		world.remove(moveTo);
		pickingUp.remove(moveTo);
	}

}
