package com.wup.dorf.ai;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

	private List<TaskEntry> idle;
	private List<TaskEntry> started;
	
	public TaskManager(){
		idle = new ArrayList<TaskEntry>();
		started = new ArrayList<TaskEntry>();
	}
	
	public void addTask(Task task){
		idle.add(new TaskEntry(task));
	}
	
	public void update(){
		for(int i = 0; i < idle.size(); i++){
			TaskEntry e = idle.get(i);
			Task task = e.task;
			if(task.shouldStart()){
				e.started = true;
				task.start();
				idle.remove(e);
				started.add(e);
			}else{
				task.reset();
			}
		}
		for(int s = 0; s < started.size(); s++){
			TaskEntry e = started.get(s);
			Task task = e.task;
			if(task.shouldContinue()){
				task.update();
			}else{
				task.reset();
				started.remove(e);
				idle.add(e);
			}
		}
	}
	
}
