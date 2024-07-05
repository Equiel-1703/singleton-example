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

	public Player getPlayer1() {
		return player_1;
	}

	public Player getPlayer2() {
		return player_2;
	}

	public boolean isCollidingWithPlayersVertical(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player_1.getBounds();
		java.awt.Rectangle p2 = player_2.getBounds();

		boolean p1_collide = p1.intersects(r) && ((r.y > p1.y + p1.height) || (r.y + r.height < p1.y));
		boolean p2_collide = p2.intersects(r) && ((r.y > p2.y + p2.height) || (r.y + r.height < p2.y));

		return p1_collide || p2_collide;
	}

	public boolean isCollidingWithPlayersHorizontal(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player_1.getBounds();
		java.awt.Rectangle p2 = player_2.getBounds();

		boolean p1_collide = p1.intersects(r);
		boolean p2_collide = p2.intersects(r);

		return p1_collide || p2_collide;
	}

	public Player getPlayerColliding(java.awt.Rectangle r) {
		java.awt.Rectangle p1 = player_1.getBounds();
		java.awt.Rectangle p2 = player_2.getBounds();

		boolean p1_collide = p1.intersects(r);
		boolean p2_collide = p2.intersects(r);

		if (p1_collide) {
			return player_1;
		} else if (p2_collide) {
			return player_2;
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
