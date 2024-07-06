package singleton;

import frames.Player;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Singleton {
	private Player player1;
	private Player player2;
	
	private static Singleton instance = null;
	
	public final int frameRateInterval = 20;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}

		return instance;
	}

	public void setPlayer1(Player player) {
		this.player1 = player;
	}

	public void setPlayer2(Player player) {
		this.player2 = player;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public boolean isCollidingWithPlayersVertical(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player1.getBounds();
		java.awt.Rectangle p2 = player2.getBounds();

		boolean isP1Colliding = p1.intersects(r) && ((r.y > p1.y + p1.height) || (r.y + r.height < p1.y));
		boolean isP2Colliding = p2.intersects(r) && ((r.y > p2.y + p2.height) || (r.y + r.height < p2.y));

		return isP1Colliding || isP2Colliding;
	}

	public boolean isCollidingWithPlayersHorizontal(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player1.getBounds();
		java.awt.Rectangle p2 = player2.getBounds();

		boolean isP1Colliding = p1.intersects(r);
		boolean isP2Colliding = p2.intersects(r);

		return isP1Colliding || isP2Colliding;
	}

	public Player getPlayerColliding(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player1.getBounds();
		java.awt.Rectangle p2 = player2.getBounds();

		boolean isP1Colliding = p1.intersects(r);
		boolean isP2Colliding = p2.intersects(r);

		if (isP1Colliding) {
			return player1;
		} else if (isP2Colliding) {
			return player2;
		}

		return null;
	}

	/**
	 * Get actual screen display sizes, ignores Windows font scaling, sort left to
	 * right
	 */
	public static Dimension getScreenSize() {
		GraphicsDevice d = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		java.awt.Rectangle r = d.getDefaultConfiguration().getBounds();

		System.out.println("Screen size: " + r.width + "x" + r.height);

		return new Dimension(r.width, r.height);
	}
}
