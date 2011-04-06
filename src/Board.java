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
		Graphics2D g2d = (Graphics2D) g;
		drawCircles(g2d);
		drawInfoRect(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void drawInfoRect(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, 800, 30);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Current behavior: " + behavior.toString(), 10, 20);
		g2d.drawString("(S - SEEK, F - FLEE, A - ARRIVAL / Arrows to move the red character)", 175, 20);
		g2d.drawString("Distance to the target:" + boid.getDistanceToTarget(), 600, 20);
	}

	private void drawCircles(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		g2d.fillOval(boid.getX(), boid.getY(), 30, 30);
		g2d.setColor(Color.RED);
		g2d.fillOval(playerCharacter.getX(), playerCharacter.getY(), 30, 30);
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
		}
	}
}
