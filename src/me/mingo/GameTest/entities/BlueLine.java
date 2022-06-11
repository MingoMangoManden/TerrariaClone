package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import me.mingo.GameTest.GamePanel;

public class BlueLine extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private int X, Y;
	
	public BlueLine() {
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLUE);
		
		for (int i = 0; i < GamePanel.tilesHorizontal; i++) {
			g.fillRect(i*GamePanel.tileSize, Y, GamePanel.tileSize, GamePanel.tileSize);
		}
		
		// if line hits border
		/*if (Y != Window.HEIGHT-tileSize) {
			Y++;
		} else {
			Y = 0;
		}*/
	}
	
	@Override
	public void update(int MOUSE_X, int MOUSE_Y) {
		this.setY(MOUSE_Y - GamePanel.tileSize/2);
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
