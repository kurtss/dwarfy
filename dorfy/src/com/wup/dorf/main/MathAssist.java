package com.wup.dorf.main;

public class MathAssist {

	public static int choose(int... choices){
		return choices[(int)(Math.random() * choices.length)];
	}
	
	public static double distance(float x1, float y1, float x2, float y2){
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
}
