package me.mingo.GameTest.World;

import java.awt.Color;

public enum Material {
	
	GRASS(new Color(0, 154, 23)),
	DIRT(new Color(139, 69, 19)),
	AIR(new Color(0, 0, 0));
	
	public Color clr;
	
	Material(Color clr) {
		this.clr = clr;
	}

}
