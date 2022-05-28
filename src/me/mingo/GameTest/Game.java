package me.mingo.GameTest;

import me.mingo.GameTest.Utils.Utils;

public class Game {
	
	Window window;
	
	public Game() {
		init();
	}
	
	private void init() {
		// Load textures
		
		Utils.initializeGameData();
		//Utils.outputError("TEST ERROR");
		
		// Create Window
		Window window = new Window();
		window.setLocationRelativeTo(null);
	}
	
	public static void Quit() {
		// Make sure to save before quitting
		System.exit(0);
	}

}
