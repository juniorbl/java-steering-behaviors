package core;
import java.awt.event.KeyEvent;

import javax.vecmath.Vector2d;

/**
 * @author Carlos Luz Junior
 */
public class PlayerCharacter {
	private Vector2d position = new Vector2d();
	public static final int MAX_SPEED = 5;
	private int newX;
	private int newY;
	
	public PlayerCharacter() {
		position.setX(655);
		position.setY(300);
	}
	
	public void move() {
		position.setX(position.getX() + newX);
		position.setY(position.getY() + newY);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			newX = -MAX_SPEED;
		}
		if (key == KeyEvent.VK_RIGHT) {
			newX = MAX_SPEED;
		}
		if (key == KeyEvent.VK_UP) {
			newY = -MAX_SPEED;
		}
		if (key == KeyEvent.VK_DOWN) {
			newY = MAX_SPEED;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			newX = 0;
		}
		if (key == KeyEvent.VK_RIGHT) {
			newX = 0;
		}
		if (key == KeyEvent.VK_UP) {
			newY = 0;
		}
		if (key == KeyEvent.VK_DOWN) {
			newY = 0;
		}
	}
	
	public int getX() {
		return (int) position.getX();
	}

	public int getY() {
		return (int) position.getY();
	}
	
	public Vector2d getPosition() {
		return position;
	}
}
