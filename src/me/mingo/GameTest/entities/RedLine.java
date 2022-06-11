package me.mingo.GameTest.entities;

import java.awt.Graphics2D;
import java.awt.Color;

import me.mingo.GameTest.GamePanel;

public class RedLine extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private int X, Y;
	
	public RedLine() {
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.RED);
		
		for (int i = 0; i < GamePanel.tilesVertical; i++) {
			g.fillRect(X, i*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
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
		setX(MOUSE_X - GamePanel.tileSize/2);
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
