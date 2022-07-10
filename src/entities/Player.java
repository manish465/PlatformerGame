package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.Constants.PlayerConstants;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animation;
	private int animationTick = 0, animationIndex = 0, animationSpeed = 20;
	private int playerAction = PlayerConstants.IDLE;
	private boolean left, up, down, right;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
	}

	public void update() {
		updatePostion();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(animation[playerAction][animationIndex], (int) x, (int) y,
				width, height, null);
	}

	private void updateAnimationTick() {
		animationTick++;
		if (animationTick >= animationSpeed) {
			animationTick = 0;
			animationIndex++;
			if (animationIndex >= PlayerConstants
					.getSpriteAmount(playerAction)) {
				animationIndex = 0;
				attacking = false;
			}

		}
	}

	public void setAnimation() {
		int startAnimation = playerAction;

		if (moving)
			playerAction = PlayerConstants.RUNNING;
		else
			playerAction = PlayerConstants.IDLE;

		if (attacking)
			playerAction = PlayerConstants.ATTACK_1;

		if (startAnimation != playerAction) {
			resetAnimationTick();
		}
	}

	private void resetAnimationTick() {
		animationTick = 0;
		animationIndex = 0;
	}

	public void updatePostion() {
		moving = false;

		if (left && !right) {
			x -= playerSpeed;
			moving = true;
		} else if (right && !left) {
			x += playerSpeed;
			moving = true;
		}

		if (up && !down) {
			y -= playerSpeed;
			moving = true;
		} else if (down && !up) {
			y += playerSpeed;
			moving = true;
		}
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animation = new BufferedImage[9][6];
		for (int j = 0; j < animation.length; j++)
			for (int i = 0; i < animation[j].length; i++) {
				animation[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
			}

	}

	public void resetDirectionBoolean() {
		left = false;
		up = false;
		down = false;
		right = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
