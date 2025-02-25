package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;
import ui.PlayerUI;
import ui.UI;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	final byte originalTileSize = 16;
	final byte scale = 4;
	
	public boolean isCfs = true, whileCfs = true, onCutscene;
	public short FPSLimit = 60;
	int intervalTime = 1000/FPSLimit;
	public float FPS = FPSLimit, deltaTime;
	
	public final byte tileSize = originalTileSize*scale;
	public final Transform screen = new Transform(12, 9, 12*tileSize, 9*tileSize);
	public final Transform screenMiddle = new Transform((screen.width-tileSize)/2, (screen.height-tileSize)/2);
	
	public int groupLength, groupSize = (int)(screen.x/2), mapSize;
	public char difficulty, gameState = 'm';
	
	boolean whileRepaint;
	public int tickCount;
	int forThree;
	
	Thread gameThread;
	public KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	public Player player;
	public TileManager tileManager;
	// TODO Define GameObjects
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screen.width, screen.height));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void setGameState(char state) {
		gameState = state;
		switch (state) {
		case 'g':
			isCfs = false;
			whileCfs = false;
			break;
		case 's':
			isCfs = true;
			whileCfs = false;
			break;
		case 'm':
			// TODO Make GameObjects Null
			ui.playerUI = null;
			tileManager = null;
			player = null;
			isCfs = true;
			whileCfs = false;
			break;
		}
	}
	
	public void startNewGame(int groupLength) {
		this.groupLength = groupLength;
		mapSize = groupLength*groupSize*tileSize;
		
		// TODO Create New GameObjects
		onCutscene = false;
		player = new Player(this);
		ui.playerUI = new PlayerUI(this);
		tileManager = new TileManager(this);
		setGameState('g');
	}
	
	@Override
	public void run() {
		long first, last;
		while (gameThread != null) {
			first = System.nanoTime();
			
			update();
			
			whileRepaint = true;
			repaint();
			while (whileRepaint || whileCfs) {
				try {
					Thread.sleep(intervalTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (isCfs) {
				whileCfs = true;
			}
			
			last = System.nanoTime();
			
			deltaTime = (float)(last-first)/1000000000;
			tickCount++;
		}
	}
	
	public void update() {
		// TODO Update GameObjects If Exist
		if (gameState == 'g') {
			player.update();
		}
		if (tickCount > forThree) {
			FPS = 1/deltaTime;
			ui.updateStats();
			if (gameState == 'g') {
				player.delayedUpdate();
				player.animIndex ^= 1;
			}
			
			forThree = tickCount+Math.round(FPS/3);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// TODO Draw GameObjects If Exist
		if (player != null) {
			tileManager.draw(g2);
			player.draw(g2);
		}
		ui.draw(g2);
		
		g2.dispose();
		whileRepaint = false;
	}
}
