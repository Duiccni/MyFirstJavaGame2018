package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean keyUp, keyDown, keyLeft, keyRight, keySprint, keyAction, holdDeveloper, holdInventory;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (gp.gameState == 'g') {
			switch (code) {
			case KeyEvent.VK_W:
				keyUp = true;
				break;
			case KeyEvent.VK_S:
				keyDown = true;
				break;
			case KeyEvent.VK_A:
				keyLeft = true;
				break;
			case KeyEvent.VK_D:
				keyRight = true;
				break;
			case KeyEvent.VK_SHIFT:
				keySprint = true;
				break;
			case KeyEvent.VK_F:
				keyAction = true;
				break;
			case KeyEvent.VK_I:
				holdInventory ^= true;
				break;
			case KeyEvent.VK_F3:
				holdDeveloper ^= true;
				break;
			case KeyEvent.VK_ESCAPE:
				gp.setGameState('s');
				break;
			}
		} else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.whileCfs = false;
			if (gp.ui.uiIndex == 1 && gp.ui.selectedIndex == 2 && !gp.ui.canLoadGame) {
				gp.ui.selectedIndex = 0;
			} else if (gp.ui.selectedIndex > 0) {
				gp.ui.selectedIndex--;
			} else {
				gp.ui.selectedIndex = gp.ui.uiLength;
			}
		} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.whileCfs = false;
			if (gp.ui.uiIndex == 1 && gp.ui.selectedIndex == 0 && !gp.ui.canLoadGame) {
				gp.ui.selectedIndex = 2;
			} else if (gp.ui.selectedIndex < gp.ui.uiLength) {
				gp.ui.selectedIndex++;
			} else {
				gp.ui.selectedIndex = 0;
			}
		} else if (code == KeyEvent.VK_ENTER) {
			gp.whileCfs = false;
			gp.ui.pressedEnter();
		} else if (code == KeyEvent.VK_F3) {
			gp.whileCfs = false;
			holdDeveloper ^= true;
		} else if (gp.gameState == 's' && code == KeyEvent.VK_ESCAPE) {
			gp.setGameState('g');
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (gp.gameState == 'g') {
			switch (code) {
			case KeyEvent.VK_W:
				keyUp = false;
				break;
			case KeyEvent.VK_S:
				keyDown = false;
				break;
			case KeyEvent.VK_A:
				keyLeft = false;
				break;
			case KeyEvent.VK_D:
				keyRight = false;
				break;
			case KeyEvent.VK_SHIFT:
				keySprint = false;
				break;
			case KeyEvent.VK_F:
				keyAction = false;
				break;
			}
		}
	}
}
