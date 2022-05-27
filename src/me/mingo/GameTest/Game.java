package me.mingo.GameTest;

public class Game {
	
	Window window;
	
	public Game() {
		init();
	}
	
	private void init() {
		// Load textures
		
		// Create Window
		Window window = new Window();
		
		window.setLocationRelativeTo(null);
	}
	
	public static void Quit() {
		// Make sure to save before quitting
		System.exit(0);
	}

}
