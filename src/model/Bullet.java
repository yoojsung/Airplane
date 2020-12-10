package model;

public class Bullet extends GameObject {
	private int speed;

	/**
	 * initialize bullet
	 * @param x position x coordinate
	 * @param y position y coordinate
	 * @param width bullet width
	 * @param height bullet height
	 * @param speed bullet speed
	 */
	public Bullet(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		this.speed = speed;
	}

	/**
	 * move y direction
	 */
	public void move() {
		super.moveY(speed);
	}
}
