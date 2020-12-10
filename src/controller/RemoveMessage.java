package controller;

public class RemoveMessage implements Message {
	private int ind;
	private int whichObj; // 0 = enemy 1 = bullet 2 = enemyBullet

	/**
	 *
	 * @param whichObj check which object is removed
	 * @param index
	 */
	public RemoveMessage (int whichObj, int index) {
		ind = index;
		this.whichObj = whichObj;
	}

	/**
	 *
	 * @return index
	 */
	public int getIndex() {
		return ind;
	}

	/**
	 *
	 * @return removed object
	 */
	public int getWhichObj() {
		return whichObj;
	}
}

