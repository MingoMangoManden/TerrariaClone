package me.mingo.GameTest.entities;

import java.awt.Graphics2D;
import java.awt.Color;

import me.mingo.GameTest.GamePanel;

public class RedLine extends Entity {
	
	private int tileSize;
	private int tilesVertical;
	
	private int X, Y;
	
	public RedLine() {
		tileSize = GamePanel.tileSize;
		tilesVertical = GamePanel.tilesVertical;
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.RED);
		
		for (int i = 0; i < tilesVertical; i++) {
			g.fillRect(X, i*tileSize, tileSize, tileSize);
		}
		
		// if line hits border
		/*if (X != Window.WIDTH-tileSize) {
			X++;
		} else {
			X = 0;
		}*/
	}
	
	@Override
	public void update(int MOUSE_X, int MOUSE_Y) {
		setX(MOUSE_X - tileSize/2);
	}
	
	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

	@Override
	public void setX(int newX) {
		X = newX;
	}

	@Override
	public void setY(int newY) {
		Y = newY;
	}

}
