package me.mingo.GameTest.entities;

import java.awt.Graphics2D;

public abstract class Entity {
	
	public abstract void draw(Graphics2D g);
	public abstract void update(int MOUSE_X, int MOUSE_Y);
	
	public abstract int getX();
	public abstract int getY();
	
	public abstract void setX(int newX);
	public abstract void setY(int newY);

}
