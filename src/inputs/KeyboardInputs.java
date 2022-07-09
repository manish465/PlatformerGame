package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {
	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W :
				gamePanel.changeYDeltaValue(-5);
				break;
			case KeyEvent.VK_A :
				gamePanel.changeXDeltaValue(-5);
				break;
			case KeyEvent.VK_S :
				gamePanel.changeYDeltaValue(5);
				break;
			case KeyEvent.VK_D :
				gamePanel.changeXDeltaValue(5);
				break;
		}
	}

}
