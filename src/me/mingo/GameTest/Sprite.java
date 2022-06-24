package me.mingo.GameTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	BufferedImage sprite = null;
	
	public Sprite(String sprite_src) {
		loadSprite(new File(sprite_src));
	}
	
	private void loadSprite(File spriteFile) {
		try {
			sprite = ImageIO.read(spriteFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
