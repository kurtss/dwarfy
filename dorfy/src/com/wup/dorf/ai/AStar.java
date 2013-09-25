package com.wup.dorf.ai;

import java.util.ArrayList;
import java.util.List;

import com.wup.dorf.world.World;

public class AStar {

	public static List<Node> makePath(World world, int sx, int sy, int ex, int ey){
		List<Node> open = new ArrayList<Node>();
		List<Node> closed = new ArrayList<Node>();
		
		Node beginning = new Node(sx, sy);
		Node ending = new Node(ex, ey);
		
		beginning.g = 0;
		beginning.h = estimateDistance(beginning, ending);
		beginning.f = beginning.g + beginning.h;
		
		open.add(beginning);
		
		if(world.solid(ex, ey) || world.solid(sx, sy)) return null;
		
		while(!open.isEmpty()){
			Node current = null;
			
			for(Node node : open){
				if(current == null || node.f < current.f){
					current = node;
				}
			}
						
			if(current.x == ex && current.y == ey){
				closed.add(new Node(ex, ey));
				break;
			}
			
			open.remove(current);
			closed.add(current);
			
            List<Node> cneighbor = current.getNeighbors(world, beginning);
			
	        for (Node neighbor : cneighbor) {
	            if (neighbor == null) {
	                continue;
	            }
	            
	            int ncost = 0;
	            
	            if(world.solid(neighbor.x, neighbor.y)) ncost += 10;
	            
	            float nextG = current.g + ncost;

	            if (nextG < neighbor.g) {
	                open.remove(neighbor);
	                closed.remove(neighbor);
	            }
	            
	            if(closed.contains(neighbor)){
	            	continue;
	            }
	            
	            if (!open.contains(neighbor) && !closed.contains(neighbor)) {
	                neighbor.g = nextG;
	                neighbor.h = estimateDistance(neighbor, ending) * 1.2f;
	                neighbor.f = neighbor.g + neighbor.h;
	                neighbor.parent = current;
	                open.add(neighbor);
	            }
	        	
	        } 
		}
		
		return closed;
	}
	
	/*public static List<Node> makePath(World world, int sx, int sy, int ex, int ey){
		List<Node> closed = new ArrayList<Node>();
		List<Node> open = new ArrayList<Node>();
		Node start = new Node(sx, sy);
		Node goal = new Node(ex, ey);
		open.add(start);
		start.g = 0;
		start.f = start.g + estimateDistance(start, goal);
		Node current = null;
		current = start;
		if(world.solid(sx, sy) || world.solid(ex, ey)) return null;
		while(!open.isEmpty()){
			for(int n = 0; n < open.size(); n++){
				Node node = open.get(n);
				if(current == null || node.f < current.f){
					current = node;
				}
			}
			open.remove(current);
			closed.add(current);
			if(current.x == ex && current.y == ey){
				break;
			}
			List<Node> neighbors = current.getNeighbors(world, start);
			for(Node neighbor : neighbors){
	            int ncost = 0;
	            //if(world.solid(neighbor.x, neighbor.y)) ncost += 10;
				float tentativeG = current.g + estimateDistance(current, neighbor) + ncost;
				if(closed.contains(neighbor)){
					continue;
				}
				if(!world.solid(neighbor.x, neighbor.y)) continue;
				if(!open.contains(neighbor) || tentativeG < neighbor.g){
					neighbor.parent = current;
					neighbor.g = tentativeG;
					neighbor.f = neighbor.g + estimateDistance(neighbor, goal) * 1.2f;
					if(!open.contains(neighbor)){
						open.add(neighbor);
					}
				}
			}
			//System.out.println("Open: " + open.size());
			//System.out.println("Closed: " + closed.size());
		}
		return closed;
	}*/
	
	public static float estimateDistance(Node node1, Node node2) {
	    return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
	}
	
}
