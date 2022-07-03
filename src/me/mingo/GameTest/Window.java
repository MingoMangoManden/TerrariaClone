package me.mingo.GameTest;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 1440;
	public static int HEIGHT = 800;
	
	public static GamePanel gamePanel;
	
	public Window() {
		loadSettings();
		setSize(WIDTH, HEIGHT);
		
		gamePanel = new GamePanel();
		add(gamePanel);
		setVisible(true);
	}
	
	private void loadSettings() {
		String englishTitle = "A Blocky Adventure - Version 1.0";
		//String japaneseTitle = "ブロックワールド・バージョン 1.0";
		
		setTitle(englishTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false); // enabled sometimes for testing purposes
		
	}

}
