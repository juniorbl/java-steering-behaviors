package core;

import javax.vecmath.Vector2d;

import enums.BehaviorEnum;

/**
 * @author Carlos Luz Junior
 */
public class Boid {
	public static final Float MASS = 1f;
	public static final int MAX_SPEED = 2;
	private Vector2d position;
	private Vector2d velocity;
	private Vector2d steerForce;
	private double distanceToTarget;
	
	public Boid(Vector2d initialPosition) {
		position = initialPosition;
		velocity = new Vector2d(0, 0);
		steerForce = new Vector2d();
		distanceToTarget = 0d;
	}
	
	public void seek(Vector2d targetPosition) {
		Vector2d directionToTarget = new Vector2d();
		directionToTarget.sub(targetPosition, position);
		distanceToTarget = directionToTarget.length();
		directionToTarget.normalize();
		steerForce.sub(directionToTarget, velocity);
	}
	
	public void flee(Vector2d targetPosition) {
		Vector2d directionToTarget = new Vector2d();
		directionToTarget.sub(position, targetPosition);
		distanceToTarget = directionToTarget.length();
		directionToTarget.normalize();
		steerForce.sub(directionToTarget, velocity);
	}
	
	public void arrive(Vector2d targetPosition) {
		int arriveRadius = 160;
		Vector2d directionToTarget = new Vector2d();
		directionToTarget.sub(targetPosition, position);
		distanceToTarget = directionToTarget.length();
		if (distanceToTarget > 0) {
			Double speed = MAX_SPEED * (distanceToTarget / arriveRadius);
			speed = Math.min(speed, MAX_SPEED);
			directionToTarget.scale(speed / distanceToTarget);
			steerForce.sub(directionToTarget, velocity);
		}
	}
	
	public void update(Vector2d targetPosition, BehaviorEnum behavior) {
		chooseBehavior(targetPosition, behavior);
		// acceleration = force / mass
		Vector2d acceleration = new Vector2d();
		acceleration.setX(steerForce.getX() / MASS);
		acceleration.setY(steerForce.getY() / MASS);
		velocity.add(acceleration);
		velocity.scale(MAX_SPEED);
		position.add(velocity, position);
	}

	private void chooseBehavior(Vector2d targetPosition, BehaviorEnum behavior) {
		switch (behavior) {
		case SEEK:
			seek(targetPosition);
			break;
		case FLEE:
			flee(targetPosition);
			break;
		case ARRIVAL:
			arrive(targetPosition);
			break;
		}
	}
	
	public Vector2d getSteerForce() {
		return steerForce;
	}
	
	public int getX() {
		return (int) position.getX();
	}

	public int getY() {
		return (int) position.getY();
	}

	public int getDistanceToTarget() {
		return (int) distanceToTarget;
	}
}
