package controller;

public class MoveAirplaneMessage implements Message {
	private int xOrY; //0 = x, 1 = y
	private int speed;

	/**
	 *
	 * @param xOrY decide the direction to move
	 * @param speed
	 */
	public MoveAirplaneMessage(int xOrY, int speed) {
		this.xOrY = xOrY;
		this.speed = speed;
	}

	/**
	 *
	 * @return xOrY direction x = 0 y = 1
	 */
	public int getXorY() {
		return xOrY;
	}

	/**
	 *
	 * @return speed of airplane
	 */
	public int getSpeed() {
		return speed;
	}
	
}
