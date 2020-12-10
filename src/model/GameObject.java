package model;

public abstract class GameObject {
	private int x;
	private int y;
	private final int WIDTH;
	private final int HEIGHT;

	/**
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param width width of object
	 * @param height height of object
	 */
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
	}

	/**
	 *
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 *
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 *
	 * @return width of object
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 *
	 * @return height of object
	 */
	public int getHeight() {
		return HEIGHT;
	}

	/**
	 *
	 * @param dx
	 * move object to dx distance x direction
	 */
	public void moveX(int dx) {
		x += dx;
	}

	/**
	 *
	 * @param dy
	 * move object to dy distance y direction
	 */
	public void moveY(int dy) {
		y += dy;
	}
}
