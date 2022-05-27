package me.mingo.GameTest.entities;

import java.awt.Graphics2D;

import me.mingo.GameTest.GamePanel;

import java.awt.Color;

public class Sun extends Entity {
	
	private int X, Y;
	
	// the speed is how much time can pass without the sun moving
	private int speed;
	
	private int updatesSinceLastUpdate;
	private int framesUpdated;
	
	public Sun(int X, int Y, int speed) {
		this.X = X;
		this.Y = Y;
		this.speed = speed;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		
		g.fillOval(X, Y, 100, 100);
	}
	
	@Override
	public void update(int MOUSE_X, int MOUSE_Y) {
		if (updatesSinceLastUpdate == speed) {
			X += 1;
			Y += 1;
			updatesSinceLastUpdate = 0;
		} else {
			updatesSinceLastUpdate++;
		}
		framesUpdated++;
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
