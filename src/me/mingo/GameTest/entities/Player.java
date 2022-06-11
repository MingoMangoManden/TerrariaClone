package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity {
	
	private int X, Y;
	private int size;
	
	public Player(int startingX, int startingY, int size) {
		this.X = startingX;
		this.Y = startingY;
		this.size = size;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		
		g.fillRect(X, Y, size, size*2);
	}

	@Override
	public void update(int MOUSE_X, int MOUSE_Y) {
		// TODO Auto-generated method stub
		
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
