package frames;

import javax.swing.*;
import shapes.Rectangle;
import java.awt.Dimension;
import java.awt.event.KeyListener;

public class Player extends JFrame {
	private final Dimension player_dimension = new Dimension(250, 600);
	
	public Player(String title) {
		setTitle(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		Rectangle rect = new Rectangle(player_dimension);
		getContentPane().add(rect);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				// Do nothing
			}

			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyChar() == 'w') {
					setLocation(getX(), getY() - 10);
				} else if (e.getKeyChar() == 's') {
					setLocation(getX(), getY() + 10);
				}
			}

			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				// Do nothing
			}
		});

		pack();
	}

	public Dimension getPlayerDimension() {
		return player_dimension;
	}
	
}