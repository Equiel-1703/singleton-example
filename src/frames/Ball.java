package frames;

import shapes.Circle;
import singleton.Singleton;

import java.awt.Dimension;
import javax.swing.*;

public class Ball extends JFrame implements Runnable {
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

			java.awt.Rectangle ball_rect = new java.awt.Rectangle(new_x, new_y, ball_dimension.width, ball_dimension.height);
			var singleton = Singleton.getInstance();

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
