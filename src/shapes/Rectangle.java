package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Rectangle
 */
public class Rectangle extends JComponent {
	private Dimension d;
	private Color c;

	public Rectangle(Dimension d, Color c) {
		this.d = d;
		this.c = c;

		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setVisible(true);
	}

	public Rectangle(Dimension d) {
		this(d, Color.BLACK);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(c);
		g.fillRect(0, 0, d.width, d.height);
	}
}