package controller;

/**
 * this is a message which will be executed in the controller
 * to add enemy to the enemylist
 */
public class AddEnemyMessage implements Message {
	private int x;
	private int y;

	/**
	 *
	 * @param x x is x cordinate on frame
	 * @param y y is y cordinate on frame
	 */
	public AddEnemyMessage (int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @return return x cordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 *
	 * @return return y cordinate
	 */
	public int getY() {
		return y;
	}
}
