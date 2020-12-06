package model;

import java.util.ArrayList;

public class EnemyList {
	ArrayList<Enemy> enemyList;
	public EnemyList() {
		enemyList = new ArrayList<Enemy>();
	}
	public void add(int x, int y) {
		enemyList.add(new Enemy(x, y, 50, 50));
	}
	public Enemy get(int i) {
		return enemyList.get(i);
	}
	public int getSize() {
		return enemyList.size();
	}
	public void remove(int i) {
		enemyList.remove(i);
	}
}
