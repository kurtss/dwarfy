package com.wup.dorf.ai;

import com.wup.dorf.entity.Dorf;
import com.wup.dorf.world.Stockpile;

public class TaskItemToStockpile extends TaskPath{

	private Coord stockpile;
	
	public TaskItemToStockpile(Dorf d, int speed) {
		super(d, speed);
	}
	
	public boolean shouldStart(){
		if(dorf.holding == null) return false;
		Stockpile randpile = world.getRandomStockpile();
		if(randpile == null) return false;
		stockpile = randpile.getFreeItemSpot(dorf.holding);
		if(stockpile == null) return false;
		path = AStar.makePath(world, dorf.x, dorf.y, stockpile.x, stockpile.y);
		return super.shouldStart();
	}
	
	public void update(){
		/*if(world.getStockpile(stockpile.x, stockpile.y).getFreeItemSpot(dorf.holding) != stockpile){
			stockpile = world.getStockpile(stockpile.x, stockpile.y).getFreeItemSpot(dorf.holding);
			path = makePathFromDorf(stockpile.x, stockpile.y);
			pathIndex = 0;
			goal = path.get(path.size() - 1);
		}*/
		super.update();
	}
	
	public boolean shouldContinue(){
		return dorf.holding != null && /*world.getStockpile(stockpile.x, stockpile.y).getItem(stockpile.x, stockpile.y) == null && */super.shouldContinue();
	}
	
	/*public void update(){
		super.update();
		if(dorf.x == stockpile.x && dorf.y == stockpile.y){
			world.getStockpile(stockpile.x, stockpile.y).setItem(stockpile.x, stockpile.y, dorf.holding);
			dorf.holding = null;
		}
	}*/
	
	public void atEndOfPath(){
		world.getStockpile(stockpile.x, stockpile.y).setItem(stockpile.x, stockpile.y, dorf.holding);
		dorf.holding = null;
	}
	
	public void reset(){
		super.reset();
		stockpile = null;
	}

}
