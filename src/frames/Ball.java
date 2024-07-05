package frames;

import shapes.Circle;
import singleton.Singleton;

import java.awt.Dimension;
import java.awt.Point;
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

	public Ball() {
		setTitle("Ball");

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

		var singleton = Singleton.getInstance();
		Dimension screen_size = Singleton.getScreenSize();

		Player lastPlayerCollided = null;

		while (true) {
			if (getX() <= 0 || getX() >= screen_size.width - ball_dimension.width) {
				direction_x *= -1;
			}

			if (getY() <= 0 || getY() >= screen_size.height - ball_dimension.height) {
				direction_y *= -1;
			}

			int new_x = getX() + (direction_x * speed);
			int new_y = getY() + (direction_y * speed);

			java.awt.Rectangle ball_rect = new java.awt.Rectangle(new_x, new_y, ball_dimension.width,
					ball_dimension.height);

			if (singleton.isCollidingWithPlayersVertical(ball_rect) && lastPlayerCollided == null) {
				direction_y *= -1;
				new_y = getY() + (direction_y * speed);
			} else if (singleton.isCollidingWithPlayersHorizontal(ball_rect) && lastPlayerCollided == null) {
				direction_x *= -1;
				new_x = getX() + (direction_x * speed);
			}
			lastPlayerCollided = singleton.getPlayerColliding(ball_rect);

			Point new_ball_position = new Point(new_x, new_y);

			SwingUtilities.invokeLater(() -> {
				setLocation(new_ball_position.x, new_ball_position.y);
			});

			// Check if one of the players scored
			Point center_screen_pos = new Point(screen_size.width / 2 - ball_dimension.width / 2,
					screen_size.height / 2 - ball_dimension.height / 2);

			if (getX() <= 0) {
				singleton.getPlayer2().increaseScore();
				SwingUtilities.invokeLater(() -> {
					setLocation(center_screen_pos);
				});
			} else if (getX() >= screen_size.width - ball_dimension.width) {
				singleton.getPlayer1().increaseScore();
				SwingUtilities.invokeLater(() -> {
					setLocation(center_screen_pos);
				});
			}

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err.println("Ball: " + e.toString());
			}
		}
	}
}
