package model;

import java.util.ArrayList;

public class EnemyList {
	ArrayList<Enemy> enemyList;
	public EnemyList() {
		enemyList = new ArrayList<Enemy>();
	}

	/**
	 * add enemy to enemylist
	 * @param x x position of enemy
	 * @param y y position of enemy
	 */
	public void add(int x, int y) {
		enemyList.add(new Enemy(x, y, 50, 50));
	}

	/**
	 *
	 * @param i index
	 * @return enemy
	 */
	public Enemy get(int i) {
		return enemyList.get(i);
	}

	/**
	 *
	 * @return size of enemylist
	 */
	public int getSize() {
		return enemyList.size();
	}

	/**
	 *
	 * @param i index
	 *  remove enemy
	 */
	public void remove(int i) {
		enemyList.remove(i);
	}
}
