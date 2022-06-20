package me.mingo.GameTest;

import java.awt.Color;

public enum GameState {
	
	LaunchMenu(new Color(0, 181, 226)),
	Playing(new Color(0, 181, 226)),
	Dead(new Color(0, 181, 226)),
	Credits(Color.BLACK);
	
	Color clr;
	
	GameState(Color clr) {
		this.clr = clr;
	}

}
