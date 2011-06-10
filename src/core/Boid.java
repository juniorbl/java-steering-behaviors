package core;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import enums.BehaviorEnum;

/**
 * The simulated creature (http://www.red3d.com/cwr/boids).
 * 
 * @author Carlos Luz Junior
 */
public class Boid {
	public static final Float MASS = 1f;
	public static final int MAX_SPEED = 2;
	
	private Vector2d position;
	private Vector2d velocity;
	private Vector2d steerForce;
	private ArrayList<Vector2d> checkpoints;
	private Vector2d currentCheckpoint;
	private int currentCheckpointIndex;
	private double distanceToTarget;
	/**
	 * Vector in the direction from the character to the target (Craig W. Reynolds).
	 */
	private Vector2d desiredVelocity;
	
	public Boid(Vector2d initialPosition, ArrayList<Vector2d> checkpoints) {
		desiredVelocity = new Vector2d();
		position = initialPosition;
		velocity = new Vector2d(0, 0);
		steerForce = new Vector2d();
		distanceToTarget = 0d;
		this.checkpoints = checkpoints;
		currentCheckpoint = checkpoints.get(currentCheckpointIndex);
	}

	/**
	 * Steers the boid to move toward the target (http://www.red3d.com/cwr/steer/SeekFlee.html).
	 */
	public void seek(Vector2d targetPosition) {
		desiredVelocity.sub(targetPosition, position);
		distanceToTarget = desiredVelocity.length();
		desiredVelocity.normalize();
		desiredVelocity.scale(MAX_SPEED);
		steerForce.sub(desiredVelocity, velocity);
	}
	
	/**
	 * Steers the boid to move away from the target (http://www.red3d.com/cwr/steer/SeekFlee.html).
	 */
	public void flee(Vector2d targetPosition) {
		desiredVelocity.sub(position, targetPosition);
		distanceToTarget = desiredVelocity.length();
		desiredVelocity.normalize();
		desiredVelocity.scale(MAX_SPEED);
		steerForce.sub(desiredVelocity, velocity);
	}
	
	/**
	 * Steers the boid to arrive slowly on the target (http://www.red3d.com/cwr/steer/Arrival.html).
	 */
	public void arrival(Vector2d targetPosition) {
		int arriveRadius = 120;
		desiredVelocity.sub(targetPosition, position);
		distanceToTarget = desiredVelocity.length();
		if (distanceToTarget > 0) {
			Double speed = MAX_SPEED * (distanceToTarget / arriveRadius);
			speed = Math.min(speed, MAX_SPEED);
			desiredVelocity.scale(speed / distanceToTarget);
			steerForce.sub(desiredVelocity, velocity);
		}
	}
	
	/**
	 * Steers the boid to move along a predefined path. It follows a set of checkpoint until arrive at the last one, then start over again.
	 */
	public void pathFollowing() {
		seek(currentCheckpoint);
		if (distanceToTarget < 10) {
			currentCheckpointIndex++;
			if (currentCheckpointIndex == checkpoints.size()) {
				currentCheckpointIndex = 0;
			}
			currentCheckpoint = checkpoints.get(currentCheckpointIndex);
		}
	}
	
	public void update(Vector2d targetPosition, BehaviorEnum behavior) {
		chooseBehavior(targetPosition, behavior);
		// acceleration = force / mass
		Vector2d acceleration = new Vector2d();
		acceleration.setX(steerForce.getX() / MASS);
		acceleration.setY(steerForce.getY() / MASS);
		velocity.add(acceleration);
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
			arrival(targetPosition);
			break;
		case PATHFOLLOWING:
			pathFollowing();
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
