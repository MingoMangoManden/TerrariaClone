package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Utils.Mouse;

public class BlueLine extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private int X, Y;
	private int speed;
	
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
	public void update() {
		this.setY(Mouse.MOUSE_Y - GamePanel.tileSize/2);
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

	@Override
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}

}
