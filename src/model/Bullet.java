package model;

public class Bullet extends GameObject {
	private int speed;
	public Bullet(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		this.speed = speed;
	}
	
	public void move() {
		super.moveY(speed);
	}
}
