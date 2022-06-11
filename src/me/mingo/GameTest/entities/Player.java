package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import me.mingo.GameTest.Utils.Keyboard;

public class Player extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private int X, Y;
	private int size;
	private int speed;
	
	public Player(int startingX, int startingY, int size, int speed) {
		this.X = startingX;
		this.Y = startingY;
		this.size = size;
		this.speed = speed;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		
		g.fillRect(X, Y, size, size*2);
	}

	@Override
	public void update() {
		if (Keyboard.wPressed) {
			Y -= speed;
		}
		if (Keyboard.aPressed) {
			X -= speed;
		}
		if (Keyboard.sPressed) {
			Y += speed;
		}
		if (Keyboard.dPressed) {
			X += speed;
		}
	}
	
	void jump() {
		
	}

	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}
	
	public int getSpeed() {
		return speed;
	}

	@Override
	public void setX(int newX) {
		X = newX;
	}

	@Override
	public void setY(int newY) {
		Y = newY;
	}
	
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}

}
