package com.wup.dorf.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import libnoiseforjava.NoiseGen.NoiseQuality;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.module.Turbulence;
import libnoiseforjava.module.Voronoi;
import libnoiseforjava.util.NoiseMap;
import libnoiseforjava.util.NoiseMapBuilderPlane;

public class Art {
		
	public static BufferedImage[] tiles = cut("/res/tiles.png", 16, 16);
	public static BufferedImage[] items = cut("/res/items.png", 16, 16);
	public static BufferedImage guy = load("/res/guy.png");
	public static BufferedImage[] dorf = cut("/res/dorf.png", 16, 16);
	public static BufferedImage tree = load("/res/tree.png");
	public static BufferedImage stockpile = load("/res/stockpile.png");
	
	//public static BufferedImage perlin = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
	
	public static void loadAll(){
		/*try {
			NoiseMapBuilderPlane builder = new NoiseMapBuilderPlane();
			NoiseMap map = new NoiseMap(128, 128);
			Perlin perlinn = new Perlin();
			perlinn.setNoiseQuality(NoiseQuality.QUALITY_BEST);
			perlinn.setOctaveCount(6);
			perlinn.setFrequency(1);
			perlinn.setPersistence(.5);
			//Voronoi v = new Voronoi();
			//v.setFrequency(.25);
			//v.setSeed((int)(Math.random() * Integer.MAX_VALUE));
			builder.setSourceModule(perlinn);
			builder.setDestNoiseMap(map);
			builder.setDestSize(128, 128);
			builder.setBounds(-1, 1, -1, 1);
			builder.build();
			for(int i = 0; i < 128; i++){
				for(int j = 0; j < 128; j++){
					int r = (int)(map.getValue(i, j) * 256) << 16;
					int g = (int)(map.getValue(i, j) * 256) << 8;
					int b = (int)(map.getValue(i, j) * 256);
					int a = 255 << 24;
					perlin.setRGB(i, j, r + g + b + a);
				}
			}
		} catch (ExceptionInvalidParam e) {
			e.printStackTrace();
		}*/
	}
	
	public static BufferedImage load(String src){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Art.class.getResource(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage[] cut(String s, int spw, int sph){
		BufferedImage src = null;
		try {
			src = ImageIO.read(Art.class.getResource(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int framesX = src.getWidth() / spw;
		int framesY = src.getHeight() / sph;
		
		BufferedImage[] imgs = new BufferedImage[framesX * framesY];
		
		for(int i = 0; i < framesX; i++){
			for(int j = 0; j < framesY; j++){
				imgs[i + j * framesX] = src.getSubimage(i * spw, j * sph, spw, sph);
			}
		}
		return imgs;
	}
	
	public static BufferedImage[][] loadList(String folder, int spw, int sph){
		File[] files = (new File(Art.class.getResource(folder).getPath())).listFiles();
		BufferedImage[][] list = new BufferedImage[files.length][];
		for(int f = 0; f < files.length; f++){
			if(files[f].isFile()) list[f] = cut(folder + files[f].getName(), spw, sph);
		}
		return list;
	}
	
	public static BufferedImage[][] loadNumberedList(String folder, int spw, int sph){
		File[] files = (new File(Art.class.getResource(folder).getPath())).listFiles();
		BufferedImage[][] list = new BufferedImage[files.length][];
		
		List<File> all = new ArrayList<File>(Arrays.asList(files));
		Collections.sort(all, new Comparator<File>(){
			public int compare(File arg0, File arg1) {
				int fnum1 = Integer.parseInt(arg0.getName().substring(0, arg0.getName().indexOf('.')));
				int fnum2 = Integer.parseInt(arg1.getName().substring(0, arg1.getName().indexOf('.')));
				if(fnum1 < fnum2) return -1;
				if(fnum1 > fnum2) return 1;
				return 0;
			}			
		});
		
		for(int f = 0; f < all.size(); f++){
			if(all.get(f).isFile()) list[f] = cut(folder + all.get(f).getName(), spw, sph);
		}
		
		return list;
	}
	
	public static BufferedImage recolor(BufferedImage src, int col){
		BufferedImage newimg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < newimg.getWidth(); i++){
			for(int j = 0; j < newimg.getHeight(); j++){
				if(src.getRGB(i, j) >> 24 != 0) newimg.setRGB(i, j, additiveColor(src.getRGB(i, j), col));
			}
		}
		return newimg;
	}
	
	public static int additiveColor(int c1, int c2) {
		return (((c1&0xfefeff)+(c2&0xfefeff))>>1) + 0xff000000;
	}
	
}
