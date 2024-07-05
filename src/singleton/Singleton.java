package singleton;

import frames.Player;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Singleton {
	private Player player_1;
	private Player player_2;
	
	private static Singleton instance = null;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}

		return instance;
	}

	public void setPlayer1(Player player) {
		this.player_1 = player;
	}

	public void setPlayer2(Player player) {
		this.player_2 = player;
	}

	public boolean isCollidingWithPlayer1(java.awt.Rectangle r) {
		return player_1.getBounds().intersects(r);
	}

	public boolean isCollidingWithPlayer2(java.awt.Rectangle r) {
		return player_2.getBounds().intersects(r);
	}

	/** Get actual screen display sizes, ignores Windows font scaling, sort left to right */
	public static Dimension getScreenSize() {
		GraphicsDevice d =  GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		java.awt.Rectangle r = d.getDefaultConfiguration().getBounds();
		
		System.out.println("Screen size: " + r.width + "x" + r.height);

		return new Dimension(r.width, r.height);
	}
}
