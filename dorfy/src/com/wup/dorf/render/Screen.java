package com.wup.dorf.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen {

	public int xOffset;
	public int yOffset;
	private int width;
	private int height;
	private static int[] pixels;
	private BufferedImage image;
	private Graphics2D g2d;
	private float xScale = 1, yScale = 1;
	
	public Screen(int w, int h){
		width = w;
		height = h;
		pixels = new int[width * height];
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = (Graphics2D)image.getGraphics();
	}
	
	public void setScale(float x, float y){
		xScale = x;
		yScale = y;
	}
	
	public void draw(BufferedImage img, int x, int y){
		draw(img, x, y, false, false);
	}
	
	public void draw(BufferedImage img, int x, int y, float rot){
		draw(img, x, y, false, false, rot);
	}
	
	public void draw(BufferedImage img, int x, int y, boolean xflip, boolean yflip){
		draw(img, x, y, xflip, yflip, 0);
	}
	
	public void draw(BufferedImage img, int x, int y, boolean xflip, boolean yflip, float rot){
		x -= xOffset;
		y -= yOffset;
		AffineTransform tf = g2d.getTransform();
		tf.translate(x + (xflip ? img.getWidth() : 0), y + (yflip ? img.getHeight() : 0));
		if(xScale != 1 || yScale != 1){
			if(xScale != 1) tf.translate(-img.getWidth() * xScale / 2, 0);
			if(yScale != 1) tf.translate(0, -img.getHeight() * yScale / 2);
			tf.scale(xScale, yScale);
		}
		if(rot != 0) tf.rotate(rot, img.getWidth() / 2, img.getHeight() / 2);
		if(xflip || yflip) tf.scale(xflip ? -1 : 1, yflip ? -1 : 1);
		g2d.drawImage(img, tf, null);
		setScale(1, 1);
	}
	
	public void fill(int x, int y, int w, int h, int color){
		x -= xOffset;
		y -= yOffset;
		g2d.setColor(new Color(color));
		g2d.fillRect(x, y, w, h);
	}
	
	public void line(int x1, int y1, int x2, int y2, int color){
		x1 -= xOffset;
		x2 -= xOffset;
		y1 -= yOffset;
		y2 -= yOffset;
		g2d.setColor(new Color(color));
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	public void string(String s, int x, int y, int color){
		x -= xOffset;
		y -= yOffset;
		g2d.setColor(new Color(color));
		g2d.drawString(s, x, y);
	}
	
	public void clear(){
		System.arraycopy(pixels, 0, ((DataBufferInt) image.getRaster().getDataBuffer()).getData(), 0, width * height);
	}
	
	public static void setBackgroundColor(int col){
		for(int i = 0; i < pixels.length; i++) pixels[i] = col;
	}
	
	public BufferedImage toImage(){
		return image;
	}
	
}

