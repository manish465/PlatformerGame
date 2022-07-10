package main;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private Game game;
	private MouseInputs mouseInputs;

	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;

		setPanalSize();

		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanalSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
		System.out
				.println("WIDTH :" + GAME_WIDTH + " | HEIGHT :" + GAME_HEIGHT);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
}
