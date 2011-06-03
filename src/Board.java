import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.vecmath.Vector2d;

import core.Boid;
import core.PlayerCharacter;
import enums.BehaviorEnum;

/**
 * @author Carlos Luz Junior
 */
public class Board extends JPanel implements ActionListener {
	private Timer timer;
	private Boid boid;
	private PlayerCharacter playerCharacter;
	private BehaviorEnum behavior;
	private Graphics2D graphics2d;

	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		boid = new Boid(new Vector2d(400, 300));
		playerCharacter = new PlayerCharacter();
		behavior = BehaviorEnum.SEEK;

		timer = new Timer(10, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		graphics2d = (Graphics2D) g;
		drawCircles();
		drawInfoRect();
		drawPredefinedPath();
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void drawInfoRect() {
		graphics2d.setColor(Color.LIGHT_GRAY);
		graphics2d.fillRect(0, 0, 800, 30);
		graphics2d.setColor(Color.BLACK);
		graphics2d.drawString("Current behavior: " + behavior.toString(), 10, 20);
		graphics2d.drawString("(S - Seek, F - Flee, A - Arrival, P - Path following)", 250, 20);
		graphics2d.drawString("Distance to the target:" + boid.getDistanceToTarget(), 600, 20);
	}

	private void drawPredefinedPath() {
		drawCheckPointNumbers();
		drawPathLines();
	}

	private void drawPathLines() {
		graphics2d.drawLine(120, 200, 20, 400);
		graphics2d.drawLine(20, 400, 400, 400);
		graphics2d.drawLine(400, 400, 600, 500);
		graphics2d.drawLine(600, 500, 750, 300);
		graphics2d.drawLine(750, 300, 400, 100);
		graphics2d.drawLine(400, 100, 300, 150);
		graphics2d.drawLine(300, 150, 300, 200);
		graphics2d.drawLine(300, 200, 120, 200);
	}

	private void drawCheckPointNumbers() {
		graphics2d.drawString("(1)", 120, 200);
		graphics2d.drawString("(2)", 20, 400);
		graphics2d.drawString("(3)", 400, 400);
		graphics2d.drawString("(4)", 600, 500);
		graphics2d.drawString("(5)", 750, 300);
		graphics2d.drawString("(6)", 400, 100);
		graphics2d.drawString("(7)", 300, 150);
		graphics2d.drawString("(8)", 300, 200);
	}

	private void drawCircles() {
		graphics2d.setColor(Color.BLUE);
		graphics2d.fillOval(boid.getX(), boid.getY(), 30, 30);
		graphics2d.setColor(Color.RED);
		graphics2d.fillOval(playerCharacter.getX(), playerCharacter.getY(), 30, 30);
	}

	public void actionPerformed(ActionEvent e) {
		playerCharacter.move();
		boid.update(playerCharacter.getPosition(), behavior);
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			playerCharacter.keyReleased(e);
		}
		public void keyPressed(KeyEvent e) {
			playerCharacter.keyPressed(e);
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_S) {
				behavior = BehaviorEnum.SEEK;
			}
			if (key == KeyEvent.VK_F) {
				behavior = BehaviorEnum.FLEE;
			}
			if (key == KeyEvent.VK_A) {
				behavior = BehaviorEnum.ARRIVAL;
			}
			if (key == KeyEvent.VK_P) {
				behavior = BehaviorEnum.PATHFOLLOWING;
				drawPredefinedPath();
			}
		}
	}
}
