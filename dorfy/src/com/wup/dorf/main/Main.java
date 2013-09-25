package com.wup.dorf.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.wup.dorf.render.Art;
import com.wup.dorf.render.Screen;

public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public boolean keepRunning = true;
	private Game game;
	private Screen screen;
	private Input input;
	
	public static void main(String[] args){
		Main main = new Main();
		main.game = new Game();
		JFrame frame = new JFrame(Game.TITLE);
		frame.add(main);
		frame.pack();
		frame.setSize(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		main.start();
	}
	
	public void paint(Graphics g){}
	public void repaint(){}
	
	public void start(){
		(new Thread(this, "Game")).start();
	}
	
	public void init(){
		screen = new Screen(Game.WIDTH, Game.HEIGHT);
		input = new Input(this, game);
		
		Art.loadAll();
		
		game.init();
	}
	
	public void run() {
		init();

		double nsPerFrame = 1000000000.0 / 60.0;
		double unprocessedTime = 0;
		double maxSkipFrames = 10;

		long lastTime = System.nanoTime();
		long lastFrameTime = System.currentTimeMillis();
		int frames = 0;

		while (keepRunning) {
			long now = System.nanoTime();
			double passedTime = (now - lastTime) / nsPerFrame;
			lastTime = now;

			if (passedTime < -maxSkipFrames) passedTime = -maxSkipFrames;
			if (passedTime > maxSkipFrames) passedTime = maxSkipFrames;

			unprocessedTime += passedTime;

			boolean render = false;
			while (unprocessedTime > 1) {
				unprocessedTime -= 1;
				tick();
				render = true;
			}
			render = true;
			if (render) {
			    render();
				frames++;
			}

			if (System.currentTimeMillis() > lastFrameTime + 1000) {
				if(Game.DEBUG) System.out.println(frames + " fps");
				lastFrameTime += 1000;
				frames = 0;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swap();
		}
		stop();
	}
	
	private void swap() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		int screenW = getWidth();
		int screenH = getHeight();
		int w = Game.WIDTH * Game.SCALE;
		int h = Game.HEIGHT * Game.SCALE;

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenW, screenH);
		g.drawImage(screen.toImage(), 0, 0, w, h, null);
		g.dispose();
		screen.clear();

		bs.show();
	}
	
	private void tick(){
		input.update();
		game.update();
	}
	
	private void render(){
		game.draw(screen);
	}
	
	private void stop(){
		game.stop();
	}
	
}
