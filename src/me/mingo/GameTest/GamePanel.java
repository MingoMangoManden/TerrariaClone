package me.mingo.GameTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	boolean testMode = true;
	
	// world generation
	long seed = new Random().nextLong();
	int worldSize = 1000;
	World world = new World(seed, worldSize);
	
	double frequency = 10;
	double smoothness = 0.05;
	
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
		
		loadWorld();
		
		if (testMode) {
			engageTestMode();
		}
		
		//addMouseMotionListener(new InputMaster());
		//addKeyListener(new InputMaster());
		//addMouseListener(new InputMaster());
		
		start();
	}
	
	private void loadWorld() {
		System.out.println("Loading world with seed " + seed);
		world.generate(frequency, smoothness);
		
		System.out.println("Loading entities...");
		world.spawnStartingEntities();
		
		Utils.saveWorldData(world);
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
		g2.drawString("Block world?", (int) (Window.WIDTH*0.35), 200);
	}
}
