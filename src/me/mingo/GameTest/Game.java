package me.mingo.GameTest;

import me.mingo.GameTest.utils.Utils;

public class Game {
	
	public static Window window;
	public static GameState gameState;
	
	public final static String dev = "VicDev";
	
	public Game() {
		init();
	}
	
	private void init() {
		// Load textures
		
		Utils.initializeGameData();
		//Utils.outputError("TEST ERROR");
		
		// Create Window
		window = new Window();
		window.setLocationRelativeTo(null);
		
		// set starting game state
		setState(GameState.Credits);
	}
	
	public static void setState(GameState gameState) {
		Game.gameState = gameState;
		Window.gamePanel.setBackground(gameState.clr);
	}
	
	public static void quit() {
		// Make sure to save before quitting
		System.exit(0);
	}

}
