package model;

public class Enemy extends GameObject {
	//speed for x and y
	private int[] dir = new int[2];
	private int cnt = 0;

	/**
	 *
	 * @param x position x coordinate
	 * @param y position y coordinate
	 * @param width enemy width
	 * @param height enemy height
	 */
	public Enemy(int x, int y, int width, int height) {
		super(x, y, width, height);
		dir[0] = (int) (Math.random() * 20) - 10;
		dir[1] = (int) (Math.random() * 5) + 10;
	}

	/**
	 * randomly move enemy x direction and y direction
	 * @param max_width frame
	 * @param max_height frame
	 */
	public void move(int max_width, int max_height) {
		if (getX() < 0)
			dir[0] = (int) (Math.random() * 5 + 5);
		else if (getX() + getWidth() > max_width - 15)
			dir[0] = (int) (-1 * (Math.random() * 5 + 5));
		if (getY() < 0)
			dir[1] = (int) (Math.random() * 5 + 5);
		else if (getY() + getHeight() > max_height)
			dir[1] = (int) (-1 * (Math.random() * 5 + 5));
		super.moveX(dir[0]);
		super.moveY(dir[1]);
	}

	/**
	 *
	 */
	public void inc_cnt() {
		cnt++;
	}

	/**
	 *
	 * @return
	 */
	public boolean bulletReady() {
		if (cnt == 18) {
			cnt = 0;
			return true;
		}
		return false;
	}
}
