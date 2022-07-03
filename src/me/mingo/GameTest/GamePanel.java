package me.mingo.GameTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.mingo.GameTest.entities.Entity;
import me.mingo.GameTest.entities.Player;
import me.mingo.GameTest.utils.Keyboard;
import me.mingo.GameTest.utils.Mouse;
import me.mingo.GameTest.utils.Utils;
import me.mingo.GameTest.world.Block;
import me.mingo.GameTest.world.World;

public class GamePanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	//////////////////////////
	//						//
	//	CONSTANT VARIABLES	//
	//						//
	//////////////////////////
	
	public static int tileSize = 16;
	public static int tilesHorizontal = tileSize * Window.WIDTH;
	public static int tilesVertical = tileSize * Window.HEIGHT;
	
	final double UPDATE_TIME = 1.0/120.0;
	
	Thread thread;
	Timer timer;
	
	
	//////////////////////////
	//						//
	//	GAME VARIABLES		//
	//						//
	//////////////////////////
	
	public static final boolean testMode = true;
	
	// world generation
	static long seed = new Random().nextLong();
	static int worldSize = Window.WIDTH/tileSize;
	
	double frequency = 10;
	double smoothness = 0.05;
	
	public static World world = new World(seed, worldSize);
	
	// time
	int TIME = 0;
	boolean shouldUpdateZoom = true;
	
	// sun
	public static int sunMovementSpeed = 1250;
	
	// player
	public static double playerSpeed = 5;
	public static Player player = new Player(100, 100, 1, GamePanel.playerSpeed);
	public static final int renderDistance = 250; // number of blocks rendered
	
	//////////////////////////////
	//							//
	//	Game Panel constructor	//
	//							//
	//////////////////////////////
	public GamePanel() {
		setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		
		addMouseListener(new Mouse());
		addKeyListener(new Keyboard());
		setFocusable(true);
		
		loadWorld();
		start();
	}
	
	private void loadWorld() {
		System.out.println("Loading world with seed " + seed);
		world.generate(frequency, smoothness);
		
		System.out.println("Loading entities...");
		world.spawnStartingEntities();
		
		//Utils.saveWorldData(world);
	}
	
	private void zoom(int intensity) {
		tileSize += intensity;
	}
	
	//////////////////////////
	//						//
	//	Start the game loop	//
	//						//
	//////////////////////////
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	//////////////////////////////////////////////////
	//												//
	//	Load all starting entities into the world	//
	//												//
	//////////////////////////////////////////////////
	/*public void loadEntities() {
		entities = new ArrayList<Entity>();
		
		//entities.add(new RedLine());
		//entities.add(new BlueLine());
		entities.add(new GreenBlob());
	}*/
	
	//////////////////
	//				//
	//	Game Loop	//
	//				//
	//////////////////
	@Override
	public void run() {
		
		boolean shouldRender = true;
		long frameRate = (long) (UPDATE_TIME*1000);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			// so all these variables are for calculating the time between each frame (delta time)
			double lastTime = System.currentTimeMillis();
			double currentTime = 0.0;
			double deltaTime = 0.0;
			
			// wow calculation of fps
			int frames = 0;
			double lastFpsCheck = System.currentTimeMillis();
			double fps = 0.0;

			@Override
			public void run() {
				if (shouldRender) {
					
					// delta time stuff
					currentTime = System.currentTimeMillis();
					deltaTime = (currentTime - lastTime) * 0.1;
					lastTime = currentTime;
					
					// fps calculation of independence
					frames++;
					if (System.currentTimeMillis() > lastFpsCheck + 1000) {
						lastFpsCheck = System.currentTimeMillis();
						fps = frames;
						frames = 0;
						
						//System.out.println("fps " + fps);
					}
					
					update(deltaTime*0.5); // update entity locations and such
					repaint(); // repaint the screen
					
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						Utils.outputError(e.getStackTrace().toString());
					}
				}
			}
			
		}, 0, frameRate);
		
		/*double lastTime = System.currentTimeMillis();
		double currentTime;
		double deltaTime;
		
		while (true) {
			currentTime = System.currentTimeMillis();
			deltaTime = currentTime - lastTime;
			
			if (deltaTime >= UPDATE_TIME*750) {
				update(deltaTime);
				repaint();
				lastTime = currentTime;
			}
		}*/
		
	}
	
	//////////////////////////////
	//							//
	//	Update entity locations	//
	//							//
	//////////////////////////////
	public void update(double deltaTime) {
		Mouse.updateMousePosition();
		
		System.out.println(deltaTime);
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity entity = world.entities.get(i);
			
			checkCollision(entity);
			entity.update(deltaTime);
		}
		
		// update zoom
		if (shouldUpdateZoom) {
			if (Keyboard.plusPressed) {
				GamePanel.tileSize += 1;
			} else if (Keyboard.minusPressed) {
				GamePanel.tileSize -= 1;
			}
			shouldUpdateZoom = false;
		} else {
			shouldUpdateZoom = true;
		}
	}
	
	//////////////////////////
	//						//
	//	 Check collisions	//
	//						//
	//////////////////////////
	private void checkCollision(Entity entity) {
		ArrayList<Block> nearbySurfaceBlocks = getNearbySurfaceBlocks(entity, 15);
		
		for (int j = 0; j < nearbySurfaceBlocks.size(); j++) {
			Block block = nearbySurfaceBlocks.get(j);
			
			if (block.isSolid) { // only check collision if the block is solid
				if (entity.getBounds().intersects(block.getBounds())) { // checking if a block is hit
					
					//System.out.println("block collision"); // alerting the console
					
					// make entity unable to pass through the wall
					
					
				}
			}
		}
	}
	
	private ArrayList<Block> getNearbySurfaceBlocks(Entity entity, int accuracy) {
		// algorithm to find nearby surface blocks with a curtain accuracy (how many blocks will be checked)
		
		// divide by 16
		int x = (int) (player.x*0.0625); // TODO: change to entity.getX()
		
		ArrayList<Block> nearbySurfaceBlocks = new ArrayList<>();
		
		if (testMode) {
			for (int i = 0; i < world.blocks.length; i++) {
				Block b = world.blocks[i];
				
				if (b.highlighted) {
					b.highlighted = false;
				}
			}
		}
		
		for (int i = 0; i < accuracy*0.5; i++) {
			try {
				Block b = world.blocks[x+i];
				nearbySurfaceBlocks.add(b);
				
				Block b2 = world.blocks[x-i];
				if (i > 0) {
					nearbySurfaceBlocks.add(b2);
				}
				
				if (testMode) {
					b.highlighted = true;
					b2.highlighted = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		}
		
		// highlight nearby surface blocks
		/*for (int i = 0; i < nearbySurfaceBlocks.length; i++) {
			nearbySurfaceBlocks[i].highlighted = true;
		}*/
		
		return nearbySurfaceBlocks;
	}
	
	//////////////////////////////////////////////////////////
	//														//
	//	Main paint function - used for painting everything	//
	//														//
	//////////////////////////////////////////////////////////
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		BufferedImage screen = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = (Graphics2D) screen.getGraphics();
		//Graphics2D g2 = (Graphics2D) g;
		
		switch(Game.gameState) {
			case LaunchMenu:
				g2.setColor(Colors.GRASS.clr);
				
				//
				Font font = new Font(Fonts.standard.name(), 0, 64);
				FontMetrics fm = Game.window.getFontMetrics(font);
				
				g2.setFont(font);
				
				String title = "Block World";
				g2.drawString(title,
						(Window.WIDTH/2) - (fm.stringWidth(title)/2),
						(int) (Window.HEIGHT*0.2)
				);
				
				//
				String pressToPlay = "PLAY";
				g2.setColor(Color.YELLOW);
				font = new Font(Fonts.standard.name(), 0, 32);
				g2.setFont(font);
				
				fm = Game.window.getFontMetrics(font);
				g2.drawString(pressToPlay,
						(Window.WIDTH/2) - (fm.stringWidth(pressToPlay)/2),
						(int) (Window.HEIGHT*0.4)
				);
				break;
			case Playing:
				world.draw(g2);
				
				// draw entities
				for (int i = 0; i < world.entities.size(); i++) {
					Entity entity = world.entities.get(i);
					
					entity.draw(g2);
				}
				break;
			case Dead:
				// playing but with 'you died' text
				break;
			case Credits:
				
				g2.setColor(Color.WHITE);
				
				// title
				font = new Font(Fonts.standard.name(), Font.ITALIC, 100);
				fm = Game.window.getFontMetrics(font);
				
				g2.setFont(font);
				
				title = "Block World";
				int x = (Window.WIDTH/2) - (fm.stringWidth(title)/2);
				int y = (int) (Window.HEIGHT*0.2);
				
				g2.drawString(title, x, y);
				
				// credits
				Credits[] credits = Credits.values();
				
				font = new Font(Fonts.standard.name(), Font.ITALIC, 20);
				fm = Game.window.getFontMetrics(font);
				
				g2.setFont(font);
				
				for (int i = 0; i < credits.length; i++) {
					Credits credit = credits[i];
					String text = credit.title;
					int space = 75;
					
					int creditTitleX = (Window.WIDTH/2) - (fm.stringWidth(text)/2);
					int creditTitleY = (int) (Window.HEIGHT*0.8-(i*space));
					
					g2.drawString(text, creditTitleX, creditTitleY);
					
					text = credit.person;
					
					int creditPersonX = (Window.WIDTH/2) - (fm.stringWidth(text)/2);
					int creditPersonY = (int) (Window.HEIGHT*0.8-(i*space))+25;
					
					g2.drawString(text, creditPersonX, creditPersonY);
				}
				
				break;
			default:
				break;
		}
		
		// screenshot if hotkey pressed
		
		
		// draw the entire screen
		g.drawImage(screen, 0, 0, this);
		
		g.dispose();
		g2.dispose(); // release any system resources it's using or something idk
		
		// draw giant string
		//g2.setFont(new Font("Comic Sans MS", 0, 64));
		//g2.drawString("Block world?", (int) (Window.WIDTH*0.35), 200);
	}
}
