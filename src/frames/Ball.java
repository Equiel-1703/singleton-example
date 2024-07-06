package frames;

import shapes.Circle;
import singleton.Singleton;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.*;

public class Ball extends JFrame implements Runnable {
	private class PlayerControls implements java.awt.event.KeyListener {
		public BitSet keyBits = new BitSet();

		@Override
		public void keyPressed(KeyEvent e) {
			char key = e.getKeyChar();

			if (key == 'w') {
				keyBits.set(0);
			} else if (key == 's') {
				keyBits.set(1);
			}
			
			if (key == 'i') {
				keyBits.set(2);
			} else if (key == 'k') {
				keyBits.set(3);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			char key = e.getKeyChar();

			if (key == 'w') {
				keyBits.clear(0);
			} else if (key == 's') {
				keyBits.clear(1);
			}
			
			if (key == 'i') {
				keyBits.clear(2);
			} else if (key == 'k') {
				keyBits.clear(3);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// Do nothing
		}

	}

	private final Dimension ballDimension = new Dimension(150, 150);
	private final int speed = 20;

	public Ball() {
		setTitle("Ball");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Circle circle = new Circle(ballDimension);
		getContentPane().add(circle);

		addKeyListener(new PlayerControls());

		pack();
	}

	public Dimension getBallDimension() {
		return ballDimension;
	}

	@Override
	public void run() {
		int xDirection = 1;
		int yDirection = 1;

		var singleton = Singleton.getInstance();
		Dimension screenSize = Singleton.getScreenSize();

		Player lastPlayerCollided = null;

		while (true) {
			if (getX() <= 0 || getX() >= screenSize.width - ballDimension.width) {
				xDirection *= -1;
			}

			if (getY() <= 0 || getY() >= screenSize.height - ballDimension.height) {
				yDirection *= -1;
			}

			int updatedX = getX() + (xDirection * speed);
			int updatedY = getY() + (yDirection * speed);

			java.awt.Rectangle ballRect = new java.awt.Rectangle(updatedX, updatedY, ballDimension.width,
					ballDimension.height);

			if (singleton.isCollidingWithPlayersVertical(ballRect) && lastPlayerCollided == null) {
				yDirection *= -1;
				updatedY = getY() + (yDirection * speed);
			} else if (singleton.isCollidingWithPlayersHorizontal(ballRect) && lastPlayerCollided == null) {
				xDirection *= -1;
				updatedX = getX() + (xDirection * speed);
			}
			lastPlayerCollided = singleton.getPlayerColliding(ballRect);

			Point updatedBallPosition = new Point(updatedX, updatedY);

			SwingUtilities.invokeLater(() -> {
				setLocation(updatedBallPosition.x, updatedBallPosition.y);
			});

			// Check if one of the players scored
			Point centerScreenPoint = new Point(screenSize.width / 2 - ballDimension.width / 2,
					screenSize.height / 2 - ballDimension.height / 2);

			if (getX() <= 0) {
				singleton.getPlayer2().increaseScore();
				SwingUtilities.invokeLater(() -> {
					setLocation(centerScreenPoint);
				});
			} else if (getX() >= screenSize.width - ballDimension.width) {
				singleton.getPlayer1().increaseScore();
				SwingUtilities.invokeLater(() -> {
					setLocation(centerScreenPoint);
				});
			}

			// Handle player controls
			PlayerControls playerControls = (PlayerControls) getKeyListeners()[0];
			if (playerControls.keyBits.get(0)) {
				singleton.getPlayer1().moveUp();
			} else if (playerControls.keyBits.get(1)) {
				singleton.getPlayer1().moveDown();
			}
			
			if (playerControls.keyBits.get(2)) {
				singleton.getPlayer2().moveUp();
			} else if (playerControls.keyBits.get(3)) {
				singleton.getPlayer2().moveDown();
			}

			try {
				Thread.sleep(singleton.frameRateInterval);
			} catch (InterruptedException e) {
				System.err.println("Ball: " + e.toString());
			}
		}
	}
}
