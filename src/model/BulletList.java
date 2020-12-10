package model;

import java.util.ArrayList;

public class BulletList {
	ArrayList<Bullet> bulletList;

	public BulletList() {
		bulletList = new ArrayList<>();
	}

	/**
	 *
	 * @param bullet add bullet to bulletList
	 */
	public void add(Bullet bullet) {
		bulletList.add(bullet);
	}

	/**
	 *
	 * @param i index
	 *  remove i index bullet from bulletList
	 */
	public void remove(int i) {
		bulletList.remove(i); }

	/**
	 *
	 * @return bulletList size
	 */
	public int getSize() {
		return bulletList.size();
	}

	/**
	 *
	 * @param index
	 * @return bullet from bulletlist
	 */
	public Bullet get(int index) {
		return bulletList.get(index);
	}

}
