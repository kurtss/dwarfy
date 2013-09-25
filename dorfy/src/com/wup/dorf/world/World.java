package com.wup.dorf.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import libnoiseforjava.NoiseGen.NoiseQuality;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.module.Turbulence;
import libnoiseforjava.util.NoiseMap;
import libnoiseforjava.util.NoiseMapBuilderPlane;

import com.wup.dorf.entity.Dorf;
import com.wup.dorf.entity.Entity;
import com.wup.dorf.entity.Tree;
import com.wup.dorf.main.MathAssist;
import com.wup.dorf.tile.Tile;

public class World {

	public int width, height, depth;
	public Random rand;
	private int[] tiles;
	private List<Entity> entities;
	private Stockpile[] stockpiles;
	
	public World(int w, int h, int d){
		width = w;
		height = h;
		depth = d;
		rand = new Random();
		tiles = new int[width * height];
		entities = new ArrayList<Entity>();
		stockpiles = new Stockpile[width * height];
		/*float[][] perlin = (new Perlin(rand)).generatePerlinNoise(width, height, 8);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				System.out.println(perlin[i][j]);
				if(perlin[i][j] > .55f){
					tiles[i + j * width] = Tile.grass.id;
				}else{
					tiles[i + j * width] = Tile.stone.id;
				}
			}
		}*/
		try {
			NoiseMapBuilderPlane builder = new NoiseMapBuilderPlane();
			NoiseMap map = new NoiseMap(width, height);
			Perlin perlin = new Perlin();
			perlin.setNoiseQuality(NoiseQuality.QUALITY_BEST);
			perlin.setOctaveCount(6);
			perlin.setFrequency(1);
			perlin.setPersistence(.5);
			builder.setSourceModule(new Turbulence(perlin));
			builder.setDestNoiseMap(map);
			builder.setDestSize(width, height);
			builder.setBounds(0, 1, 0, 1);
			builder.build();
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					double value = map.getValue(i, j);
					if(value > 0.1) setTile(i, j, Tile.grass.id);
					else setTile(i, j, Tile.stone.id);
					System.out.println((int)(map.getValue(i, j) * depth));
				}
			}
		} catch (ExceptionInvalidParam e) {
			e.printStackTrace();
		}
		for(int t = 0; t < 32; t++){
			Tree tree = new Tree();
			tree.setLocation(rand.nextInt(width), rand.nextInt(height), 0);
			spawn(tree);
		}
		/*for(int t = 0; t < 256 * 4; t++){
			Item item = new Item(Resource.LOG);
			item.setLocation(rand.nextInt(width), rand.nextInt(height), 0);
			spawn(item);
		}*/
		for(int dor = 0; dor < 50; dor++){
			Dorf dorf = new Dorf();
			dorf.setLocation(rand.nextInt(width), rand.nextInt(height), 0);
			while(solid(dorf.x, dorf.y)){
				dorf.setLocation(rand.nextInt(width), rand.nextInt(height), 0);
			}
			spawn(dorf);
		}
	}
	
	public void update(){
		/*for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
			}
		}*/
		for(int e = 0; e < entities.size(); e++){
			entities.get(e).tick();
		}
	}
	
	public boolean valid(int x, int y){
		return x >= 0 && y >= 0 && x < width && y < height;
	}
	
	public void setTile(int x, int y, int id){
		if(valid(x, y)) tiles[x + y * width] = id;
	}
	
	public int getTile(int x, int y){
		if(!valid(x, y)) return 0;
		return tiles[x + y * width];
	}
	
	public void spawn(Entity e){
		e.world = this;
		e.init();
		entities.add(e);
	}
	
	public void remove(Entity e){
		e.remove = true;
		entities.remove(e);
	}
	
	public List<Entity> getInTiles(int x1, int y1, int x2, int y2){
		List<Entity> within = new ArrayList<Entity>();
		for(int e = 0; e < entities.size(); e++){
			Entity entity = entities.get(e);
			if((entity.x >= x1 && entity.x <= x2) && (entity.y >= y1 && entity.y <= y2)) within.add(entity);
		}
		return within;
	}
	
	public List<Entity> getInTile(int x, int y){
		List<Entity> within = new ArrayList<Entity>();
		for(int e = 0; e < entities.size(); e++){
			Entity entity = entities.get(e);
			if(entity.x == x && entity.y == y) within.add(entity);
		}
		return within;
	}
	
	public boolean solid(int x, int y){
		if(!valid(x, y)) return true;
		List<Entity> inTile = getInTile(x, y);
		for(int e = 0; e < inTile.size(); e++){
			if(inTile.get(e).solid()) return true;
		}
		return false;
	}
	
	public Entity getRandomOfType(Class<? extends Entity> type){
		List<Entity> ofType = new ArrayList<Entity>();
		for(int e = 0; e < entities.size(); e++){
			Entity entity = entities.get(e);
			if(entity.getClass() == type) ofType.add(entity);
		}
		if(!ofType.isEmpty()){
			return ofType.get(rand.nextInt(ofType.size()));
		}else{
			return null;
		}
	}
	
	public Entity getClosestOfType(Class<? extends Entity> type, int x, int y){
		Entity near = null;
		for(int e = 0; e < entities.size(); e++){
			Entity entity = entities.get(e);
			if(entity.getClass() == type){
				if(near == null) near = entity;
				else if(MathAssist.distance(entity.x, entity.y, x, y) < MathAssist.distance(near.x, near.y, x, y)){
					near = entity;
				}
			}
		}
		return near;
	}
	
	public int getAmountOfType(Class<? extends Entity> type){
		int amount = 0;
		for(int e = 0; e < entities.size(); e++){
			Entity entity = entities.get(e);
			if(entity.getClass() == type) amount++;
		}
		return amount;
	}
	
	public void addStockpile(int x, int y, int w, int h){
		Stockpile stockpile = new Stockpile(x, y, w, h);
		for(int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				if(valid(x + i, y + j)){
					stockpiles[(x + i) + (y + j) * width] = stockpile;
				}
			}
		}
	}
	
	public Stockpile getStockpile(int x, int y){
		if(valid(x, y)) return stockpiles[x + y * width];
		return null;
	}
	
	public Stockpile getRandomStockpile(){
		boolean empty = true;
		for(int p = 0; p < stockpiles.length; p++){
			if(stockpiles[p] != null){
				empty = false;
			}
		}
		if(empty) return null;
		Stockpile pile = null;
		while(pile == null){
			pile = stockpiles[rand.nextInt(stockpiles.length)];
		}
		return pile;
	}
	
}
