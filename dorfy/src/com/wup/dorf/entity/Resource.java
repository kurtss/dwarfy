package com.wup.dorf.entity;

public class Resource {

	public int tex;
	public int maxStackSize = 1;
	
	public static final Resource LOG = new Resource().setTexture(0).setMaxStackSize(5);
	
	public Resource(){
		
	}
	
	public Resource setTexture(int i){
		tex = i;
		return this;
	}
	
	public Resource setMaxStackSize(int mss){
		maxStackSize = mss;
		return this;
	}
	
}
