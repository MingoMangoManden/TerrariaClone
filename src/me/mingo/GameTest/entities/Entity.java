package me.mingo.GameTest.entities;

import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public abstract void draw(Graphics2D g);
	public abstract void update();
	
	public abstract int getX();
	public abstract int getY();
	public abstract int getSpeed();
	
	public abstract void setX(int newX);
	public abstract void setY(int newY);
	public abstract void setSpeed(int newSpeed);

}
