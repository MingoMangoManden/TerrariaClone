package me.mingo.GameTest.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.GamePanel;
import me.mingo.GameTest.GameState;
import me.mingo.GameTest.Location;
import me.mingo.GameTest.Vector;
import me.mingo.GameTest.utils.Keyboard;

public class Player extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	public int x, y;
	public int width, height; // block size
	public double speed; // acceleration
	public boolean dead = false;
	
	public Vector velocity = new Vector(0, 0);
	public double rotation = 0.0;
	
	public double goofiness = 0.8;
	public double gravityScale = 0.8; // 'weight', 'gravitational pull'
	
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
				// draw hitbox
				g2.setColor(Color.RED);
				g2.draw(getBounds());
				
				// draw somethg
				g2.setColor(Color.GREEN);
				g2.draw(new Rectangle(x, (int) (y+(height*16)+velocity.y), 1, 1));
			}
		}
	}
	
	@Override
	public void update(double deltaTime) {
		if (!dead) {
			updateVelocity(deltaTime);
			applyVelocity(deltaTime);
			normalizeVelocity(deltaTime);
		}
	}
	
	private void updateVelocity(double deltaTime) {
		
		Location headed = new Location(x, (int) (y+(height*16)+velocity.y));
		
		// gravity
		/*if (!isSolidBlock(headed)) {
			velocity.y += gravityScale;
		}*/
		
		// keyboard presses
		if (Keyboard.wPressed) {
			velocity.y -= speed * deltaTime;
		}
		if (Keyboard.aPressed) {
			velocity.x -= speed * deltaTime;
		}
		if (Keyboard.sPressed) {
			velocity.y += speed * deltaTime;
		}
		if (Keyboard.dPressed) {
			velocity.x += speed * deltaTime;
		}
	}
	
	private void applyVelocity(double deltaTime) {
		x += velocity.x * deltaTime;
		y += velocity.y * deltaTime;
	}
	
	private void normalizeVelocity(double deltaTime) {
		
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
		
		int someConstant = 2;
		
		if (velocity.x != 0) {
			velocity.x *= goofiness * deltaTime*someConstant;
		}
		if (velocity.y != 0) {
			velocity.y *= goofiness * deltaTime*someConstant;
		}
	}
	
	private boolean isSolidBlock(Location headed) {
		/*if (x < 0 || x >= Window.WIDTH-width) {
			System.out.println("hahah colisision");
			return true;
		}
		if (y < 0 || y >= Window.HEIGHT) {
			return true;
		}*/
		
		// all the corners of the hitbox
		boolean topLeft = GamePanel.world.getBlockAt(x, y) != null;
		boolean topRight = GamePanel.world.getBlockAt(x+width, y) != null;
		boolean bottomLeft = GamePanel.world.getBlockAt(x, y) != null;
		boolean bottomRight = GamePanel.world.getBlockAt(x+width, y) != null;
		
		/*System.out.println("topLeft " + topLeft);
		System.out.println("topRight " + topRight);
		System.out.println("bottomLeft " + bottomLeft);
		System.out.println("bottomRight " + bottomRight);
		
		if (!topLeft)
			if (!topRight)
				if (!bottomLeft)
					if (!bottomRight)
						return true;*/
		if (bottomRight || bottomLeft) {
			System.out.println("collision");
			return true;
		}
		
		return false;
	}
	
	@Override
	public void die() {
		dead = true;
		velocity.x = 0.0;
		velocity.y = 0.0;
		
		//
		Game.setState(GameState.Credits);
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


}
