package me.mingo.GameTest.world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.mingo.GameTest.Colors;
import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Location;
import me.mingo.GameTest.Window;
import me.mingo.GameTest.world.generation.OpenSimplexNoise;
import me.mingo.GameTest.entities.Bee;
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
		entities.add(GamePanel.player);
		entities.add(new Bee(1000, 300, 1, 0.5));
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
			
			blocks[x] = new Block(new Location(x, (int) height), true, Colors.GRASS);
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
				g2.setColor(Colors.DIRT.clr);
				g2.fillRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
				//g2.setColor(Color.RED);
				//g2.drawRect(i*GamePanel.tileSize, blocks[i].loc.y+(j*GamePanel.tileSize), GamePanel.tileSize, GamePanel.tileSize);
			}
		}
	}
	
	public Block getBlockAt(int x, int y) {
		
		Block b = null;
		Rectangle rect = new Rectangle(x, y, 1, 1);
		
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getBounds().intersects(rect)) {
				b = blocks[i];
				return b;
			}
		}
		
		return null;
	}

}
