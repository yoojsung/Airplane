package controller;

import model.Bullet;

/**
 * this is a message which will be executed in the controller
 * to add bullets to the bulletList
 * @author Airplane team
 * 
 */
public class AddBulletMessage implements Message {
	private Bullet bullet;
	/**
	 * 
	 * @param b is the bullet that's being added
	 */
	public AddBulletMessage (Bullet b) {
		this.bullet = b;
	}
	/**
	 * @return bullet that is being added
	 */
	public Bullet getBullet() {
		return bullet;
	}
}

