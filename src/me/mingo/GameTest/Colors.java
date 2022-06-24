package me.mingo.GameTest;

import java.awt.Color;

public enum Colors {
	
	GRASS(new Color(0, 154, 23)),
	DIRT(new Color(139, 69, 19)),
	AIR(new Color(0, 0, 0));
	
	public Color clr;
	
	Colors(Color clr) {
		this.clr = clr;
	}

}
