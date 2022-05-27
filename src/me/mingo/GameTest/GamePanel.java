package me.mingo.GameTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import me.mingo.GameTest.Utils.InputMaster;
import me.mingo.GameTest.Utils.Utils;
import me.mingo.GameTest.World.World;
import me.mingo.GameTest.entities.Entity;

public class GamePanel extends JPanel implements Runnable {
	
	//////////////////////////
	//						//
	//	CONSTANT VARIABLES	//
	//						//
	//////////////////////////
	public static final int tileSize = 16;
	public static final int tilesHorizontal = tileSize * Window.WIDTH;
	public static final int tilesVertical = tileSize * Window.HEIGHT;
	
	final double UPDATE_TIME = 1.0/60.0;
	
	Thread thread;
	Timer timer;
	
	
	//////////////////////////
	//						//
	//	GAME VARIABLES		//
	//						//
	//////////////////////////
	
	
	// world generation
	long seed = new Random().nextLong();
	int worldSize = 100;
	World world = new World(seed, worldSize);
	
	// time
	int TIME = 0;
	
	//////////////////////////////
	//							//
	//	Game Panel constructor	//
	//							//
	//////////////////////////////
	public GamePanel() {
		setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		
		Color skyBlue = new Color(0, 181, 226);
		setBackground(skyBlue); // paint da sky
		
		initialize();
		
		//addMouseMotionListener(new InputMaster());
		//addKeyListener(new InputMaster());
		//addMouseListener(new InputMaster());
		
		start();
	}
	
	private void initialize() {
		System.out.println("Loading world with seed " + seed);
		world.generate(10, 0.1, 0.75);
		
		System.out.println("Loading entities...");
		world.spawnStartingEntities();
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
		InputMaster.updateMousePosition();
		
		int mouseX = InputMaster.MOUSE_X;
		int mouseY = InputMaster.MOUSE_Y;
		
		// PUT THIS STUFF IN an_entity.update
		
		for (int i = 0; i < world.entities.size(); i++) {
			Entity entity = world.entities.get(i);
			
			entity.update(mouseX, mouseY);
		}
	}
	
	//////////////////////////////////////////////////////////
	//														//
	//	Main paint function - used for painting everything	//
	//														//
	//////////////////////////////////////////////////////////
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		world.draw(g2);
		
		// draw entities
		for (int i = 0; i < world.entities.size(); i++) {
			Entity entity = world.entities.get(i);
			
			entity.draw(g2);
		}
		
		// draw giant string
		g2.setFont(new Font("Comic Sans MS", 0, 64));
		g2.drawString("Game of Blocks", (int) (Window.WIDTH*0.35), 200);
	}
}
