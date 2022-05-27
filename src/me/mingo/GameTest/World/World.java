package me.mingo.GameTest.World;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;

import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Window;
import me.mingo.GameTest.Utils.Location;
import me.mingo.GameTest.World.Generation.OpenSimplexNoise;
import me.mingo.GameTest.entities.Entity;
import me.mingo.GameTest.entities.Sun;

public class World {
	
	long seed;
	int size;
	OpenSimplexNoise noise;
	
	int minimumGroundLevel = 55;
	int pixelCrustLevel = Window.HEIGHT-55;
	
	int sunMovementSpeed = 1250;
	
	public Block[] blocks;
	public List<Entity> entities;
	
	public World(long seed, int size) {
		this.seed = seed;
		this.size = size;
		this.noise = new OpenSimplexNoise(seed);
		
	}
	
	public void spawnStartingEntities() {
		entities = new ArrayList<Entity>();
		
		//entities.add(new RedLine());
		//entities.add(new BlueLine());
		entities.add(new Sun(50, 150, sunMovementSpeed));
	}
	
	public void generate(double multiplier, double smoothness, double frequency) {
		blocks = new Block[size];
		
		// noise.noise(i)
		for (int x = 0; x < size; x++) {
			//((noise.eval(x, 0)*multiplier)+minimumGroundLevel)
			double height = (pixelCrustLevel-(noise.eval(x*smoothness, 0)*multiplier)*GamePanel.tileSize)*frequency;
			
			// if block is under world, level it
			if (height >= pixelCrustLevel) {
				height = pixelCrustLevel;
			}
			
			System.out.println((int) height);
			
			blocks[x] = new Block(new Location(x, (int) height), Material.DIRT);
		}
	}
	
	public void draw(Graphics2D g2) {
		// draw tiles
		
		Color grassGreen = new Color(0, 154, 23);
		
		for (int i = 0; i < size; i++) {
			g2.setColor(grassGreen);
			g2.fillRect(i*GamePanel.tileSize, blocks[i].loc.y, GamePanel.tileSize, GamePanel.tileSize);
			
			// under ground tiles
			Color dirtColor = new Color(139, 69, 19);
			g2.setColor(dirtColor);
			for (int j = 1; j < 20; j++) {
				g2.fillRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
			}
		}
	}

}
