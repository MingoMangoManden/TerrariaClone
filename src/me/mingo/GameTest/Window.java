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
		
		gamePanel = new GamePanel();
		add(gamePanel);
		setVisible(true);
	}
	
	private void loadSettings() {
		String englishTitle = "Block World - Version 1.0";
		String japaneseTitle = "ブロックワールド・バージョン 1.0";
		String danishTitle = "Blok Verden - Version 1.0";
		
		setTitle(japaneseTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false); // enabled sometimes for testing purposes
		
	}

}
