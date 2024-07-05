package frames;

import shapes.Rectangle;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;

public class Player extends JFrame {
	private final Dimension player_dimension = new Dimension(250, 500);
	private final int speed = 10;

	public Player(String title, Color c) {
		setTitle(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Rectangle rect = new Rectangle(player_dimension, c);
		getContentPane().add(rect);

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

}