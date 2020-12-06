package model;

public abstract class GameObject {
	private int x;
	private int y;
	private final int WIDTH;
	private final int HEIGHT;
	
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public void moveX(int dx) {
		x += dx;
	}
	
	public void moveY(int dy) {
		y += dy;
	}
}
