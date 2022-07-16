package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.Constants.PlayerConstants;
import utilz.HelpMethods;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animation;
	private int animationTick = 0, animationIndex = 0, animationSpeed = 20;
	private int playerAction = PlayerConstants.IDLE;
	private boolean left, up, down, right;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;
	private int[][] levelData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
	}

	public void update() {
		updatePostion();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(animation[playerAction][animationIndex],
				(int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
				width, height, null);
		drawHitbox(g);
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
		if (!left && !right && !down && !up)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left && !right)
			xSpeed = -playerSpeed;
		else if (right && !left)
			xSpeed = playerSpeed;

		if (up && !down)
			ySpeed = -playerSpeed;
		else if (down && !up)
			ySpeed = playerSpeed;

		// if (HelpMethods.canMoveHere(x + xSpeed, y + ySpeed, width, height,
		// levelData)) {
		// this.x += xSpeed;
		// this.y += ySpeed;
		// moving = true;
		// }

		if (HelpMethods.canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed,
				hitbox.width, hitbox.height, levelData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
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

	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
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
