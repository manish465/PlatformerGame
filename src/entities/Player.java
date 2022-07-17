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
	private boolean left, up, down, right, jump;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;
	private int[][] levelData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);
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
		// drawHitbox(g);
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

		if (jump) {
			if (airSpeed < 0)
				playerAction = PlayerConstants.JUMP;
			else
				playerAction = PlayerConstants.FALLING;
		}

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

		if (jump)
			jump();
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!HelpMethods.isEntityOnFloor(hitbox, levelData))
				inAir = true;

		if (inAir) {
			if (HelpMethods.canMoveHere(hitbox.x, hitbox.y + airSpeed,
					hitbox.width, hitbox.height, levelData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = HelpMethods
						.getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					restInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		} else
			updateXPos(xSpeed);

		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void restInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (HelpMethods.canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width,
				hitbox.height, levelData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = HelpMethods.getEntityXPosNextToWall(hitbox, xSpeed);
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
		if (!HelpMethods.isEntityOnFloor(hitbox, levelData))
			inAir = true;
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

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
