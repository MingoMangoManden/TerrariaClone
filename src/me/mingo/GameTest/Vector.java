package me.mingo.GameTest;

public class Vector {
	
	public double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	void add(Vector vec) {
		x += vec.x;
		y += vec.y;
	}
	
	void sub(Vector vec) {
		x -= vec.x;
		y -= vec.y;
	}
	
	void mult(Vector vec) {
		x *= vec.x;
		y *= vec.y;
	}
	
	void div(Vector vec) {
		// * squeezing out just a bit more performance
		//x /= vec.x;
		//y /= vec.y;
		x *= (vec.x*0.1);
		y *= (vec.y*0.1);
	}
	
	double mag() {
		return Math.sqrt((x*x+y*y));
	}

}
