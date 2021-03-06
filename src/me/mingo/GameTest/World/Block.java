package me.mingo.GameTest.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import me.mingo.GameTest.Colors;
import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Location;

public class Block implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Location loc;
	public boolean isSolid;
	public Colors mat;
	
	public boolean highlighted = false;
	
	public Block(Location loc, boolean solid, Colors mat) {
		this.loc = loc;
		this.isSolid = solid;
		this.mat = mat;
	}
	
	public void draw(Graphics2D g2) {
		// draw block
		g2.setColor(mat.clr);
		g2.fillRect(loc.x, loc.y, GamePanel.tileSize, GamePanel.tileSize);
		
		if (highlighted) {
			g2.setColor(Color.RED);
			g2.drawRect(loc.x, loc.y, GamePanel.tileSize, GamePanel.tileSize);
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(loc.x, loc.y, GamePanel.tileSize, GamePanel.tileSize);
	}

}
