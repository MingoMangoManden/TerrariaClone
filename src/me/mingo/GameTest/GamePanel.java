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
	
	final double UPDATE_TIME = 1.0/60.0;
	
	Thread thread;
	Timer timer;
	
	
	//////////////////////////
	//						//
	//	GAME VARIABLES		//
	//						//
	//////////////////////////
	
	public static final boolean testMode = false;
	
	// world generation
	long seed = new Random().nextLong();
	int worldSize = Window.WIDTH/tileSize;
	World world = new World(seed, worldSize);
	
	double frequency = 10;
	double smoothness = 0.05;
	
	// time
	int TIME = 0;
	boolean shouldUpdateZoom = true;
	
	// sun
	public static int sunMovementSpeed = 1250;
	
	// player
	public static double playerSpeed = 1;
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
		
		if (testMode) {
			engageTestMode();
		}
		
		start();
	}
	
	private void loadWorld() {
		System.out.println("Loading world with seed " + seed);
		world.generate(frequency, smoothness);
		
		System.out.println("Loading entities...");
		world.spawnStartingEntities();
		
		//Utils.saveWorldData(world);
	}
	
	//////////////////
	//				//
	//	Test Mode	//
	//				//
	//////////////////
	private void engageTestMode() {
		// multiplier
		JSlider frequencySlider = new JSlider();
		frequencySlider.setToolTipText("Change multiplier");
		frequencySlider.setMinimum(0);
		frequencySlider.setMaximum(100);
		frequencySlider.setValue((int) frequency);
		frequencySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				frequency = frequencySlider.getValue();
				world.generate(frequency, smoothness);
			}
		});
		add(frequencySlider);
		
		// smoothness
		JSlider smoothnessSlider = new JSlider();
		smoothnessSlider.setToolTipText("Change smoothness");
		smoothnessSlider.setMinimum(0);
		smoothnessSlider.setMaximum(10);
		smoothnessSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				smoothness = smoothnessSlider.getValue()*0.01;
				world.generate(frequency, smoothness);
			}
		});
		add(smoothnessSlider);
		
		// zoom
		JButton zoomIn = new JButton("Zoom In");
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoom(1);
				
				// update tiles
				tilesHorizontal = tileSize * Window.WIDTH;
				tilesVertical = tileSize * Window.HEIGHT;
			}
		});
		add(zoomIn);
		
		JButton zoomOut = new JButton("Zoom Out");
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				zoom(-1);
				
				// update tiles
				tilesHorizontal = tileSize * Window.WIDTH;
				tilesVertical = tileSize * Window.HEIGHT;
			}
		});
		add(zoomOut);
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
		long frameRate = (long) (UPDATE_TIME*750);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (shouldRender) {
					update();
					
					repaint();
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						Utils.outputError(e.getStackTrace().toString());
					}
				}
			}
			
		}, 0, frameRate);
		
	}
	
	//////////////////////////////
	//							//
	//	Update entity locations	//
	//							//
	//////////////////////////////
	public void update() {
		Mouse.updateMousePosition();
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity entity = world.entities.get(i);
			
			checkCollision(entity);
			entity.update();
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
		Block[] nearbySurfaceBlocks = getNearbySurfaceBlocks(entity, 5);
		
		for (int j = 0; j < nearbySurfaceBlocks.length; j++) {
			Block block = nearbySurfaceBlocks[j];
			
			if (entity.getBounds().intersects(block.getBounds())) {
				// block collision
				System.out.println("block collision");
				
				//entity.die();
				
				// make entity unable to pass through the wall
				
				
			}
		}
	}
	
	private Block[] getNearbySurfaceBlocks(Entity entity, int accuracy) {
		// algorithm to find nearby surface blocks with a curtain accuracy (how many blocks will be checked)
		
		
		Block[] nearbySurfaceBlocks = new Block[] {};
		return world.blocks;
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
