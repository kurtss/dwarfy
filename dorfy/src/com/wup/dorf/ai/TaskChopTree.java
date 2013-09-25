package com.wup.dorf.ai;

import java.util.ArrayList;
import java.util.List;

import com.wup.dorf.entity.Dorf;
import com.wup.dorf.entity.Tree;
import com.wup.dorf.main.MathAssist;

public class TaskChopTree extends TaskPath{

	private static final List<Tree> cutting = new ArrayList<Tree>();
	private Tree tree;
	
	public TaskChopTree(Dorf d, int speed){
		super(d, speed);
	}
	
	public boolean shouldStart() {
		if(dorf.holding != null) return false;
		if(world.getAmountOfType(Tree.class) <= 0) return false;
		if(world.rand.nextInt(500) != 0) return false;
		//tree = (Tree)world.getRandomOfType(Tree.class);
		tree = (Tree)world.getClosestOfType(Tree.class, dorf.x, dorf.y);
		if(cutting.contains(tree)) return false;
		int rx = MathAssist.choose(-1, 0, 1);
		int ry = MathAssist.choose(-1, 0, 1);
		while(rx == 0 && ry == 0){
			rx = MathAssist.choose(-1, 0, 1);
			ry = MathAssist.choose(-1, 0, 1);
		}
		path = AStar.makePath(world, dorf.x, dorf.y, tree.x + rx, tree.y + ry);
		return tree != null && !tree.remove && super.shouldStart();
	}

	public void start(){
		super.start();
		cutting.add(tree);
	}
	
	public boolean shouldContinue(){
		return tree != null && !tree.remove && super.shouldContinue();
	}
	
	/*public void update(){
		super.update();
		Node end = path.get(path.size() - 1);
		if(dorf.x == end.x && dorf.y == end.y){
			tree.destroy(dorf);
		}
	}*/
	
	public void atEndOfPath(){
		tree.destroy(dorf);
		cutting.remove(tree);
	}
	
	public void reset(){
		super.reset();
		tree = null;
		cutting.remove(tree);
	}

}
