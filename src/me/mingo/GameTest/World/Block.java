package me.mingo.GameTest.World;

import me.mingo.GameTest.Utils.Location;

public class Block {
	
	public Location loc;
	public Material mat;
	
	public Block(Location loc, Material mat) {
		this.loc = loc;
		this.mat = mat;
	}

}
