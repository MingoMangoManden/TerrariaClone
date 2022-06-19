package me.mingo.GameTest;

import me.mingo.GameTest.utils.Utils;

public class Game {
	
	public static Window window;
	public static GameState gameState;
	
	public Game() {
		init();
	}
	
	private void init() {
		// Load textures
		
		Utils.initializeGameData();
		//Utils.outputError("TEST ERROR");
		
		// Create Window
		gameState = GameState.LaunchMenu;
		window = new Window();
		window.setLocationRelativeTo(null);
	}
	
	public static void quit() {
		// Make sure to save before quitting
		System.exit(0);
	}

}
