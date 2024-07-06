package frames;

import shapes.Rectangle;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;

public class Player extends JFrame {
	private final int speed = 10;
	private Dimension playerDimension;
	private int score = 0;
	private JLayeredPane layeredPane;

	public Player(String title, Dimension d, Color c) {
		this.playerDimension = d;
		this.layeredPane = new JLayeredPane();

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Rectangle rect = new Rectangle(playerDimension, c);
		JLabel score_label = new JLabel("Score: " + score);

		score_label.setForeground(Color.BLACK);
		score_label.setLocation(0, 0);
		score_label.setSize(100, 50);


		layeredPane.add(rect, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(score_label, JLayeredPane.PALETTE_LAYER);

		add(layeredPane);

		setSize(rect.getSize());
	}

	public Dimension getPlayerDimension() {
		return playerDimension;
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
			JLabel score_label = (JLabel) layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)[0];
			score_label.setForeground(Color.BLACK);
			score_label.setBackground(Color.RED);
			score_label.setText("Score: " + score);
		});
	}

}