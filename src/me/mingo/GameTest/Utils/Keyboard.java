package me.mingo.GameTest.Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static boolean wPressed, aPressed, sPressed, dPressed;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			wPressed = true;
		}
		
		if (code == KeyEvent.VK_A) {
			aPressed = true;
		}
		
		if (code == KeyEvent.VK_S) {
			sPressed = true;
		}
		
		if (code == KeyEvent.VK_D) {
			dPressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			wPressed = false;
		}
		
		if (code == KeyEvent.VK_A) {
			aPressed = false;
		}
		
		if (code == KeyEvent.VK_S) {
			sPressed = false;
		}
		
		if (code == KeyEvent.VK_D) {
			dPressed = false;
		}
	}
	
	

}
