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

			Player player_1 = new Player("Player 1", Color.CYAN);
			Player player_2 = new Player("Player 2", new Color(179, 33, 202));
			Ball ball = new Ball();

			s.setPlayer1(player_1);
			s.setPlayer2(player_2);

			// Set players positions
			player_1.setLocation(5, 5);
			player_2.setLocation(screenSize.width - player_2.getPlayerDimension().width - 15, 5);
			// Set ball position
			ball.setLocation(screenSize.width / 2 - ball.getBallDimension().width / 2,
					screenSize.height / 2 - ball.getBallDimension().height / 2);

			executor.submit(ball);

			// Set players visible
			player_1.setVisible(true);
			player_2.setVisible(true);
			// Set ball visible
			ball.setVisible(true);

			executor.shutdown();
		});
	}
}