package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.util.concurrent.*;

import frames.*;
import singleton.Singleton;

public class Main {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(1);

		SwingUtilities.invokeLater(() -> {
			Singleton s = Singleton.getInstance();

			// Get screen size
			Dimension screenSize = Singleton.getScreenSize();
			// Set players dimensions
			Dimension playerDimension = new Dimension(125, 300);

			Player player1 = new Player("Player 1", playerDimension, Color.CYAN);
			Player player2 = new Player("Player 2", playerDimension, new Color(179, 33, 202));
			Ball ball = new Ball();

			s.setPlayer1(player1);
			s.setPlayer2(player2);

			// Set players positions
			player1.setLocation(5, 5);
			player2.setLocation(screenSize.width - player2.getPlayerDimension().width - 15, 5);
			// Set ball position
			ball.setLocation(screenSize.width / 2 - ball.getBallDimension().width / 2,
					screenSize.height / 2 - ball.getBallDimension().height / 2);

			// Send ball to executor
			executor.submit(ball);

			// Set players visible
			player1.setVisible(true);
			player2.setVisible(true);
			// Set ball visible
			ball.setVisible(true);

			executor.shutdown();
		});
	}
}