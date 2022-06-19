package me.mingo.GameTest.World;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Window;
import me.mingo.GameTest.Utils.Location;
import me.mingo.GameTest.World.Generation.OpenSimplexNoise;
import me.mingo.GameTest.entities.Entity;
import me.mingo.GameTest.entities.Player;
import me.mingo.GameTest.environment.Sun;

public class World implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	long seed;
	int size;
	
	int minimumGroundLevel = 55;
	int pixelCrustLevel = Window.HEIGHT-55;
	
	Sun sun = new Sun(50, 150, GamePanel.sunMovementSpeed);
	
	public Block[] blocks;
	public List<Entity> entities;
	
	public World(long seed, int size) {
		this.seed = seed;
		this.size = size;
	}
	
	public void spawnStartingEntities() {
		entities = new ArrayList<Entity>();
		
		//entities.add(new RedLine());
		//entities.add(new BlueLine());
		entities.add(new Player(0, 0, 1, GamePanel.playerSpeed));
	}
	
	public void generate(double multiplier, double smoothness) {
		OpenSimplexNoise noise = new OpenSimplexNoise(seed);
		blocks = new Block[size];
		
		for (int x = 0; x < size; x++) {
			//((noise.eval(x, 0)*multiplier)+minimumGroundLevel)
			double height = (pixelCrustLevel-(noise.eval(x*smoothness, 0)*multiplier)*GamePanel.tileSize)*0.75;
			
			// if block is under world, level it
			if (height >= pixelCrustLevel) {
				height = pixelCrustLevel;
			}
			
			//System.out.println((int) height);
			
			blocks[x] = new Block(new Location(x, (int) height), Material.GRASS);
		}
	}
	
	public void draw(Graphics2D g2) {
		sun.draw(g2);
		
		// draw tiles/blocks
		for (int i = 0; i < size; i++) {
			Block b = blocks[i];
			b.loc.x = i*GamePanel.tileSize;
			b.draw(g2);
			
			
			// underground tiles
			int undergroundTilesCount = (int) (Window.HEIGHT-blocks[i].loc.y);
			
			/*for (int j = 1; j < 20; j++) {
				g2.fillRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
			}*/
			for (int j = 1; j < undergroundTilesCount; j++) {
				g2.setColor(Material.DIRT.clr);
				g2.fillRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
				//g2.setColor(Color.RED);
				//g2.drawRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
			}
		}
	}

}
