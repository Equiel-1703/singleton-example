package frames;

import shapes.Rectangle;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;

public class Player extends JFrame {
	private final Dimension player_dimension = new Dimension(125, 300);
	private final int speed = 10;
	private int score = 0;

	public Player(String title, Color c) {
		setTitle(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Rectangle rect = new Rectangle(player_dimension, c);
		JLabel score_label = new JLabel("Score: " + score);

		getContentPane().add(rect);
		getContentPane().setForeground(Color.BLACK);
		getContentPane().add(score_label);

		pack();
	}

	public Dimension getPlayerDimension() {
		return player_dimension;
	}

	public void moveUp() {
		SwingUtilities.invokeLater(() -> {
			setLocation(getX(), getY() - speed);
		});
	}

	public void moveDown() {
		SwingUtilities.invokeLater(() -> {
			setLocation(getX(), getY() + speed);
		});
	}

	public void increaseScore() {
		score++;

		SwingUtilities.invokeLater(() -> {
			JLabel score_label = (JLabel) getContentPane().getComponent(1);
			score_label.setForeground(Color.BLACK);
			score_label.setBackground(Color.RED);
			score_label.setText("Score: " + score);
		});
	}

}