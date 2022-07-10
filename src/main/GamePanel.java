package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import utilz.Constants.Directions;
import utilz.Constants.PlayerConstants;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private MouseInputs mouseInputs;
	private int xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animation;
	private int animationTick = 0, animationIndex = 0, animationSpeed = 12;
	private int playerAction = PlayerConstants.IDLE;
	private int playerDirection = -1;
	private boolean moving = false;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);

		importImage();
		loadAnimations();

		setPanalSize();

		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void loadAnimations() {
		animation = new BufferedImage[9][6];
		for (int j = 0; j < animation.length; j++)
			for (int i = 0; i < animation[j].length; i++) {
				animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
			}
	}

	private void importImage() {
		InputStream is = getClass().getResourceAsStream("/player_sprites.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setPanalSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void setDirection(int direction) {
		this.playerDirection = direction;
		moving = true;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= PlayerConstants.getSpriteAmount(playerAction))
				animationIndex = 0;
		}
	}

	public void setAnimation() {
		if (moving)
			playerAction = PlayerConstants.RUNNING;
		else
			playerAction = PlayerConstants.IDLE;
	}

	public void updatePostion() {
		if (moving) {
			switch (playerDirection) {
				case Directions.LEFT :
					xDelta -= 5;
					break;
				case Directions.UP :
					yDelta -= 5;
					break;
				case Directions.RIGHT :
					xDelta += 5;
					break;
				case Directions.DOWN :
					yDelta += 5;
					break;
			}
		}
	}

	public void updateGame() {
		updateAnimationTick();
		setAnimation();
		updatePostion();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(animation[playerAction][animationIndex], xDelta, yDelta,
				128, 80, null);
	}
}
