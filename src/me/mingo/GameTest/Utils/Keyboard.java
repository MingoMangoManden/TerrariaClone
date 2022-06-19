package me.mingo.GameTest.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.GameState;

public class Keyboard implements KeyListener {
	
	public static boolean wPressed, aPressed, sPressed, dPressed, plusPressed, minusPressed;

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
		
		if (code == KeyEvent.VK_PLUS) {
			plusPressed = true;
		}
		
		if (code == KeyEvent.VK_MINUS) {
			minusPressed = true;
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
		
		if (code == KeyEvent.VK_PLUS) {
			plusPressed = false;
		}
		
		if (code == KeyEvent.VK_MINUS) {
			minusPressed = false;
		}
	}
	
	

}
