package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JComponent;

public class Circle extends JComponent {
	Dimension d;
	Color c;

	public Circle(Dimension d, Color c) {
		this.d = d;
		this.c = c;

		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setVisible(true);
	}

	public Circle(Dimension d) {
		this(d, Color.YELLOW);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(c);
		g.fillOval(0, 0, d.width, d.height);
	}
}
