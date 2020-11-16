package model;

public abstract class GameObject {
	int x;
	int y;
	
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void moveX(int dx) {
		x += dx;
	}
	
	public void moveY(int dy) {
		y += dy;
	}
}
