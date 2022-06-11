package me.mingo.GameTest.World;

import java.io.Serializable;

import me.mingo.GameTest.Utils.Location;

public class Block implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Location loc;
	public Material mat;
	
	public Block(Location loc, Material mat) {
		this.loc = loc;
		this.mat = mat;
	}

}
