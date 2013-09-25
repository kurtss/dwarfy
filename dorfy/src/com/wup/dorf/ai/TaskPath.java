package com.wup.dorf.ai;

import java.util.List;

import com.wup.dorf.entity.Dorf;
import com.wup.dorf.world.World;

public class TaskPath extends Task{

	protected List<Node> path;
	protected int pathIndex;
	protected World world;
	protected Dorf dorf;
	protected int walkSpeed;
	protected int walkTime;
	protected Node goal;
	
	public TaskPath(Dorf d, int speed){
		dorf = d;
		world = dorf.world;
		walkSpeed = speed;
	}
	
	@Override
	public boolean shouldStart() {
		return !dorf.hasPath && path != null;
	}
	
	public void start(){
		goal = path.get(path.size() - 1);
		dorf.hasPath = true;
	}
	
	public void update(){
		/*if(path != null && pathIndex < path.size() && --walkTime <= 0){
			Node node = path.get(pathIndex);
			int mx = 0;
			int my = 0;
			if(x != node.x){
				if(x < node.x) mx = 1;
				if(x > node.x) mx = -1;
			}
			if(y != node.y){
				if(y < node.y) my = 1;
				if(y > node.y) my = -1;
			}
			move(mx, my, 0);
			if(x == node.x && y == node.y){
				pathIndex++;
			}
			walkTime = 10;
		}*/
		if(--walkTime <= 0){
			Node node = path.get(pathIndex);
			if(world.solid(node.x, node.y)){
				path = AStar.makePath(world, dorf.x, dorf.y, goal.x, goal.y);
				pathIndex = 0;
				node = path.get(pathIndex);
			}
			int mx = 0;
			int my = 0;
			if(dorf.x != node.x){
				if(dorf.x < node.x) mx = 1;
				else if(dorf.x > node.x) mx = -1;
				else mx = 0;
			}else{
				mx = 0;
			}
			if(dorf.y != node.y){
				if(dorf.y < node.y) my = 1;
				else if(dorf.y > node.y) my = -1;
				else my = 0;
			}else{
				my = 0;
			}
			dorf.move(mx, my, 0);
			//if(dorf.x == node.x && dorf.y == node.y){
				pathIndex++;
			//}
			walkTime = walkSpeed;
		}
		if(pathIndex == path.size() && dorf.x == goal.x && dorf.y == goal.y){
			atEndOfPath();
		}
		if(!(this instanceof TaskChopTree)){
			//System.out.println(pathIndex + ", " + path.size());
		}
	}
	
	public boolean shouldContinue() {
		return pathIndex < path.size()/* && (dorf.x != goal.x && dorf.y != goal.y)*/;
	}
	
	public void reset(){
		path = null;
		pathIndex = 0;
		walkTime = 0;
		dorf.hasPath = false;
	}
	
	protected void atEndOfPath(){
		
	}
	
	protected List<Node> makePathFromDorf(int newx, int newy){
		return AStar.makePath(world, dorf.x, dorf.y, newx, newy);
	}

}
