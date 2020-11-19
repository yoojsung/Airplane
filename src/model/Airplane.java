package model;

public class Airplane extends GameObject {
	private int hp;
	private int points;
	public Airplane(int x, int y, int width, int height) {
		super(x, y, width, height);
		hp = 5;
		points = 0;
	}
	//have not committed
	public int getPoint() {
		return points;
	}
	public int getHp() {
		return hp;
	}
	public void decHp() {
		hp--;
	}
	public void gainPoint(int dPoint) {
		points += dPoint;
	}
}
