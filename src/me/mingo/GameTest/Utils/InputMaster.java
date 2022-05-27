package me.mingo.GameTest.Utils;

import java.awt.Point;
import java.awt.MouseInfo;

public class InputMaster { //implements MouseListener, MouseMotionListener {
	
	public static int MOUSE_X, MOUSE_Y;

	/*@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		MOUSE_X = x;
		MOUSE_Y = y;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Game.Quit();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}*/

	public static void updateMousePosition() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		// cancelling out idk why
		MOUSE_X = p.x-250;
		MOUSE_Y = p.y-150;
	}
	
	

}
