package model;

import java.util.ArrayList;

public class BulletList {
	ArrayList<Bullet> bulletList;

	public BulletList() {
		bulletList = new ArrayList<>();
	}

	public void add(Bullet bullet) {
		bulletList.add(bullet);
	}

	public void remove(int i) {
		bulletList.remove(i); }

	public int getSize() {
		return bulletList.size();
	}

	public Bullet get(int index) {
		return bulletList.get(index);
	}

}
