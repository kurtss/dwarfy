package com.wup.dorf.ai;

import java.util.ArrayList;
import java.util.List;

import com.wup.dorf.world.World;

public class Node {

	public float f, g, h;
	public int x, y;
	public Node parent;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public List<Node> getNeighbors(World world, Node start){
		List<Node> neighbors = new ArrayList<Node>();
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				Node node = new Node(x + i, y + j);
				if(i == -1 && j == -1) node.g = 14;
				else if(i == 1 && j == -1) node.g = 14;
				else if(i == -1 && j == 1) node.g = 14;
				else if(i == 1 && j == 1) node.g = 14;
				else node.g = 10;
				node.h = AStar.estimateDistance(node, start);
				if(world.solid(node.x, node.y)){
					node.g += 999;
				}
				neighbors.add(node);
			}
		}
		return neighbors;
	}
	
}
