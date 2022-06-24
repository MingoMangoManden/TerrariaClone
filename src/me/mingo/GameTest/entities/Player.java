package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.GameState;
import me.mingo.GameTest.Vector;
import me.mingo.GameTest.utils.Keyboard;

public class Player extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	public int x, y;
	public int width, height; // block size
	public double speed;
	public boolean dead = false;
	
	public Vector velocity = new Vector(0, 0);
	public double rotation = 0.0;
	public double goofiness = 0.8;
	
	public Rectangle hitbox;
	
	public Player(int startingX, int startingY, int size, double speed) {
		this.x = startingX;
		this.y = startingY;
		this.width = size;
		this.height = size*2;
		this.speed = speed;
		this.hitbox = new Rectangle(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
	}

	@Override
	public void draw(Graphics2D g2) {
		if (!dead) {
			g2.setColor(Color.BLUE);
			
			//g2.rotate(Math.toRadians(rotation));
			g2.fillRect(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
			hitbox = new Rectangle((int) (x+velocity.x), (int) (y+velocity.y), width*GamePanel.tileSize, height*GamePanel.tileSize);
			
			if (GamePanel.testMode) {
				// draw bounds
				g2.setColor(Color.RED);
				g2.draw(getBounds());
			}
		}
	}

	@Override
	public void update() {
		if (!dead) {
			if (Keyboard.wPressed) {
				velocity.y -= speed;
			}
			if (Keyboard.aPressed) {
				velocity.x -= speed;
			}
			if (Keyboard.sPressed) {
				velocity.y += speed;
			}
			if (Keyboard.dPressed) {
				velocity.x += speed;
			}
			normalizeVelocity();
		}
	}
	
	private void normalizeVelocity() {
		x += velocity.x;
		y += velocity.y;
		
		
		// X VELOCITY //
		///////////////////////////////////////////////////////////////////
		
		// positive
		if (velocity.x > 0 && velocity.x < 0.75) { // if between 0.0 and 0.75
			velocity.x = 0; // reset it
		}
		
		// negative
		if (velocity.x < 0 && velocity.x > -0.75) { // if between 0.0 and 0.75
			velocity.x = 0; // reset it
		}
		
		///////////////////////////////////////////////////////////////////
		
		
		// Y VELOCITY //
		//////////////////////////////////////////////////////////////////////
		
		// positive
		if (velocity.y > 0 && velocity.y < 0.75) { // if between 0.0 and 0.75
			velocity.y = 0; // reset it
		}
		
		// negative
		if (velocity.y < 0 && velocity.y > -0.75) { // if between 0.0 and 0.75
			velocity.y = 0; // reset it
		}
		
		///////////////////////////////////////////////////////////////////
		
		if (velocity.x != 0) {
			velocity.x *= goofiness;
		}
		if (velocity.y != 0) {
			velocity.y *= goofiness;
		}
	}
	
	void jump() {
		/*
		 * Newton's law of universal gravitation
		 * F  = G*(m1*m2/r^2)
		 */
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
	public Vector getVelocity() {
		return velocity;
	}
	
	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public void die() {
		dead = true;
		velocity.x = 0.0;
		velocity.y = 0.0;
		
		//
		Game.setState(GameState.Credits);
	}

}
