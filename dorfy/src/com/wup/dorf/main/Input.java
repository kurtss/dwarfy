package com.wup.dorf.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener, MouseListener{

	private Main main;
	private Game game;
	private boolean[] keysDown = new boolean[65536];
	private boolean[] mouseDown = new boolean[4];
	private int[] mouseTimeDown = new int[4];
	private int[] keyTimeDown = new int[65536];
	public int mouseX;
	public int mouseY;
	
	public Input(Main m, Game g){
		main = m;
		game = g;
		game.input = this;
		main.addKeyListener(this);
		main.addMouseListener(this);
		main.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
	}

	public void mouseEntered(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
	}

	public void mouseExited(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
	}

	public void mousePressed(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
		if(e.getButton() >= 0 && e.getButton() < mouseDown.length){
			mouseDown[e.getButton()] = true;
			mouseTimeDown[e.getButton()]++;
		}
	}

	public void mouseReleased(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
		if(e.getButton() >= 0 && e.getButton() < mouseDown.length){
			mouseDown[e.getButton()] = false;
			mouseTimeDown[e.getButton()] = 0;
		}
		if(e.getButton() == 1) game.leftMouseClick(mouseX, mouseY);
		if(e.getButton() == 3) game.rightMouseClick(mouseX, mouseY);
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX() / Game.SCALE;
		mouseY = e.getY() / Game.SCALE;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() >= 0 && e.getKeyCode() < keysDown.length){
			keysDown[e.getKeyCode()] = true;
			keyTimeDown[e.getKeyCode()]++;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() >= 0 && e.getKeyCode() < keysDown.length){
			keysDown[e.getKeyCode()] = false;
			keyTimeDown[e.getKeyCode()] = 0;
		}
		game.keyTyped(e.getKeyCode(), e.getKeyChar());
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void update(){
		for(int k = 0; k < keysDown.length; k++){
			if(isKeyDown(k)) game.keyDown(k);
		}
	}
	
	public boolean isKeyDown(int key){
		return key >= 0 && key < keysDown.length && keysDown[key];
	}
	
}
