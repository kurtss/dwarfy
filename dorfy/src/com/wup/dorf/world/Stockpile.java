package com.wup.dorf.world;

import com.wup.dorf.ai.Coord;
import com.wup.dorf.entity.Item;

public class Stockpile {

	//private boolean[] markedUsed;
	private Item[] items;
	public int x, y, width, height;
	
	public Stockpile(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		items = new Item[width * height];
		//markedUsed = new boolean[width * height];
	}
	
	public Coord getFreeItemSpot(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				/*if(!markedUsed[i + j * width]){
					markedUsed[i + j * width] = true;
					return new Coord(x + i, y + j);
				}*/
				if(items[i + j * width] == null){
					return new Coord(x + i, y + j);
				}
			}
		}
		return null;
	}
	
	public Coord getFreeItemSpot(Item item){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Item at = items[i + j * width];
				if(at != null && at.resource == item.resource && at.amount + item.amount <= item.resource.maxStackSize){
					return new Coord(x + i, y + j);
				}
				else{
					if(items[i + j * width] == null){
						return new Coord(x + i, y + j);
					}
				}
			}
		}
		return null;
	}
	
	public void setItem(int x, int y, Item i){
		i.x = x;
		i.y = y;
		//items[Math.abs(this.x - x) + Math.abs(this.y - y) * width] = i;
		if(items[Math.abs(this.x - x) + Math.abs(this.y - y) * width] != null && items[Math.abs(this.x - x) + Math.abs(this.y - y) * width].resource == i.resource){
			items[Math.abs(this.x - x) + Math.abs(this.y - y) * width].amount += i.amount;
		}else{
			items[Math.abs(this.x - x) + Math.abs(this.y - y) * width] = i;
		}
		//markedUsed[Math.abs(this.x - x) + Math.abs(this.y - y) * width] = true;
	}
	
	public Item getItem(int x, int y){
		return items[Math.abs(this.x - x) + Math.abs(this.y - y) * width];
	}
	
}
