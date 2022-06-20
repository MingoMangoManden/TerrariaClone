package me.mingo.GameTest.utils;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.event.MouseInputListener;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.GameState;

import java.awt.MouseInfo;

public class Mouse implements MouseInputListener {
	
	public static int MOUSE_X, MOUSE_Y;

	public static void updateMousePosition() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		// cancelling out idk why
		MOUSE_X = p.x-250;
		MOUSE_Y = p.y-150;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (Game.gameState.equals(GameState.LaunchMenu)) {
				Game.setState(GameState.Playing);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
