package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.GameState;
import me.mingo.GameTest.utils.Keyboard;

public class Player extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	public int x, y;
	public int width, height; // block size
	public double speed;
	public boolean dead = false;
	
	public double[] velocity;
	public double goofiness = 0.8;
	
	public Rectangle hitbox;
	
	public Player(int startingX, int startingY, int size, double speed) {
		this.x = startingX;
		this.y = startingY;
		this.width = size;
		this.height = size*2;
		this.speed = speed;
		this.velocity = new double[2];
		this.hitbox = new Rectangle(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
	}

	@Override
	public void draw(Graphics2D g2) {
		if (!dead) {
			g2.setColor(Color.BLUE);
			
			g2.fillRect(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
			hitbox = new Rectangle((int) (x+velocity[0]), (int) (y+velocity[1]), width*GamePanel.tileSize, height*GamePanel.tileSize);
			
			// draw bounds
			g2.setColor(Color.RED);
			g2.draw(getBounds());
		}
	}

	@Override
	public void update() {
		if (Keyboard.wPressed) {
			velocity[1] -= speed;
		}
		if (Keyboard.aPressed) {
			velocity[0] -= speed;
		}
		if (Keyboard.sPressed) {
			velocity[1] += speed;
		}
		if (Keyboard.dPressed) {
			velocity[0] += speed;
		}
		normalizeVelocity();
	}
	
	private void normalizeVelocity() {
		x += velocity[0];
		y += velocity[1];
		
		
		// X VELOCITY //
		///////////////////////////////////////////////////////////////////
		
		// positive
		if (velocity[0] > 0 && velocity[0] < 0.75) { // if between 0.0 and 0.75
			velocity[0] = 0; // reset it
		}
		
		// negative
		if (velocity[0] < 0 && velocity[0] > -0.75) { // if between 0.0 and 0.75
			velocity[0] = 0; // reset it
		}
		
		///////////////////////////////////////////////////////////////////
		
		
		// Y VELOCITY //
		//////////////////////////////////////////////////////////////////////
		
		// positive
		if (velocity[1] > 0 && velocity[1] < 0.75) { // if between 0.0 and 0.75
			velocity[1] = 0; // reset it
		}
		
		// negative
		if (velocity[1] < 0 && velocity[1] > -0.75) { // if between 0.0 and 0.75
			velocity[1] = 0; // reset it
		}
		
		///////////////////////////////////////////////////////////////////
		
		if (velocity[0] != 0) {
			velocity[0] *= goofiness;
		}
		if (velocity[1] != 0) {
			velocity[1] *= goofiness;
		}
	}
	
	void jump() {
		// TODO
	}
	
	public int[] getMiddle() {
		return new int[] {
				(int) (width*0.5),
				(int) (height*0.5)
		};
	}
	
	public Rectangle getBounds() {
		return dead ? new Rectangle() : hitbox;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double[] getVelocity() {
		return velocity;
	}

	@Override
	public void die() {
		dead = true;
		velocity[0] = 0.0;
		velocity[1] = 0.0;
		
		//
		Game.setState(GameState.Credits);
	}

}
