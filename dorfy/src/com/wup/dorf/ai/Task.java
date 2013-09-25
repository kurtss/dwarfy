package com.wup.dorf.ai;

public abstract class Task {

	public abstract boolean shouldStart();
	
	public void start(){}
	
	public boolean shouldContinue() { return false; }
	
	public void update(){}
	
	public void reset(){}
	
	public int getUnitsNeeded() { return 0; }
	
}
