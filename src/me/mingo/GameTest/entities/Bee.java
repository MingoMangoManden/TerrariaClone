package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.Vector;

public class Bee extends Entity {
	
	int x, y;
	int width, height;
	double speed;
	boolean dead = false;
	
	Vector velocity = new Vector(0, 0);
	double rotation = 0.0;
	double goofiness = 0.8;
	
	String heading = "";
	
	Rectangle hitbox;
	
	public Bee(int x, int y, int size, double speed) {
		this.x = x;
		this.y = y;
		this.width = size;
		this.height = size;
		this.speed = speed;
		this.hitbox = new Rectangle(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
		chooseFlyingDirection();
	}
	
	private void chooseFlyingDirection() {
		int num = new Random().nextInt(2);
		this.heading = (num == 0) ? "West" : "East";
	}

	@Override
	public void draw(Graphics2D g2) {
		if (!dead) {
			g2.setColor(Color.YELLOW);
			
			g2.fillRect(x, y, width*GamePanel.tileSize, height*GamePanel.tileSize);
			hitbox = new Rectangle((int) (x+velocity.x), (int) (y+velocity.y), width*GamePanel.tileSize, height*GamePanel.tileSize);
		}
	}

	@Override
	public void update() {
		if (!dead) {
			
			/*if (heading.equals("West")) {
				velocity.x -= speed;
				velocity.y -= Math.sin(x);
			} else if (heading.equals("East")) {
				velocity.x += speed;
				//velocity[1] += Math.sin(x);
			}*/
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

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
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
	public int[] getMiddle() {
		return new int[] {
				(int) (width*0.5),
				(int) (height*0.5)
		};
	}

	@Override
	public Rectangle getBounds() {
		return dead ? new Rectangle() : hitbox;
	}

}
