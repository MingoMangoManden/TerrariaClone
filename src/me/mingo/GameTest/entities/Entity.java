package me.mingo.GameTest.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public abstract void draw(Graphics2D g2);
	public abstract void update();
	
	public abstract double getSpeed();
	public abstract double[] getVelocity();
	
	public abstract int[] getMiddle();
	public abstract Rectangle getBounds();

}
