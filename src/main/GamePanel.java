package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private float xDir = 1f, yDir = 1f;
	private Color color = new Color(15, 90, 200);
	private Random random;

	public GamePanel() {
		random = new Random();
		mouseInputs = new MouseInputs(this);

		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	public void changeXDeltaValue(int value) {
		this.xDelta += value;
	}

	public void changeYDeltaValue(int value) {
		this.yDelta += value;
	}

	public void setRectPosition(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateRectangle();
		g.setColor(color);
		g.fillRect((int) xDelta, (int) yDelta, 200, 50);
	}

	private void updateRectangle() {
		xDelta += xDir;
		if (xDelta > 400 || xDelta < 0) {
			xDir *= -1;
			color = getRandomColor();
		}

		yDelta += yDir;
		if (yDelta > 400 || yDelta < 0) {
			yDir *= -1;
			color = getRandomColor();
		}

	}

	private Color getRandomColor() {
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);

		return new Color(r, g, b);
	}
}
