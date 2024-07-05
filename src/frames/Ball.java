package frames;

import shapes.Circle;
import singleton.Singleton;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Ball extends JFrame implements Runnable {
	private class PlayerControls implements java.awt.event.KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			char key = e.getKeyChar();
			var s = Singleton.getInstance();

			if (key == 'w') {
				s.getPlayer1().moveUp();
			} else if (key == 's') {
				s.getPlayer1().moveDown();
			} else if (key == 'i') {
				s.getPlayer2().moveUp();
			} else if (key == 'k') {
				s.getPlayer2().moveDown();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// Do nothing
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// Do nothing
		}

	}

	private final Dimension ball_dimension = new Dimension(150, 150);
	private final int speed = 20;
	private Dimension screenSize;

	public Ball(Dimension screenSize) {
		setTitle("Ball");

		this.screenSize = screenSize;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Circle circle = new Circle(ball_dimension);
		getContentPane().add(circle);

		addKeyListener(new PlayerControls());

		pack();
	}

	public Dimension getBallDimension() {
		return ball_dimension;
	}

	@Override
	public void run() {
		int direction_x = 1;
		int direction_y = 1;

		while (true) {
			if (getX() <= 0 || getX() >= screenSize.width - ball_dimension.width) {
				direction_x *= -1;
			}

			if (getY() <= 0 || getY() >= screenSize.height - ball_dimension.height) {
				direction_y *= -1;
			}

			int new_x = getX() + (direction_x * speed);
			int new_y = getY() + (direction_y * speed);

			var singleton = Singleton.getInstance();
			java.awt.Rectangle ball_rect = new java.awt.Rectangle(new_x, new_y, ball_dimension.width,
					ball_dimension.height);

			if (singleton.isCollidingWithPlayer1(ball_rect) || singleton.isCollidingWithPlayer2(ball_rect)) {
				direction_x *= -1;
			}

			SwingUtilities.invokeLater(() -> {
				setLocation(new_x, new_y);
			});

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err.println("Ball: " + e.toString());
			}
		}
	}
}
