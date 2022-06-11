package me.mingo.GameTest.entities;

import java.awt.Graphics2D;
import java.awt.Color;

import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Utils.Mouse;

public class RedLine extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private int X, Y;
	private int speed;
	
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
	public void update() {
		setX(Mouse.MOUSE_X - GamePanel.tileSize/2);
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
