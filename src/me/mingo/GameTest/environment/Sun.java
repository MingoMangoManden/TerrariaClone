package me.mingo.GameTest.environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.mingo.GameTest.entities.Entity;

public class Sun {
	
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

	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		
		g.fillOval(X, Y, 100, 100);
	}
	
	public void update() {
		if (updatesSinceLastUpdate == speed) {
			X += 1;
			Y += 1;
			updatesSinceLastUpdate = 0;
		} else {
			updatesSinceLastUpdate++;
		}
		framesUpdated++;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setX(int newX) {
		X = newX;
	}

	public void setY(int newY) {
		Y = newY;
	}

	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
	
	public Rectangle getBounds() {
		return new Rectangle();
	}

}
