package me.mingo.GameTest;

import java.util.Random;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	//public static int WIDTH = 1440;
	public static int WIDTH = 1616;
	public static int HEIGHT = 800;
	
	public static GamePanel gamePanel;
	
	public Window() {
		loadSettings();
		setSize(WIDTH, HEIGHT);
		//setVisible(true);
		
		gamePanel = new GamePanel();
		add(gamePanel);
		setVisible(true);
	}
	
	private void loadSettings() {
		String[] SUFFIXES = new String[] {
				"This is probably a game with nice graphics!",
				"An amazing block game!"
		};
		int num = new Random().nextInt(SUFFIXES.length);
		String TITLE = "Some game: " + SUFFIXES[num];
		
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false); // enabled sometimes for testing purposes
		
	}

}
