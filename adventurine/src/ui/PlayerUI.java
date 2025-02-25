package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.GamePanel;

public class PlayerUI {
	GamePanel gp;
	
	List<BufferedImage> uiImages = new ArrayList<>();
	
	public PlayerUI(GamePanel gp) {
		this.gp = gp;
		
		getImages("res/ui");
	}
	
	void getImages(String path) {
		File imagesFolder = new File(path);
		File[] images = imagesFolder.listFiles();
		
		try {
			for (File image : images) {
				uiImages.add(ImageIO.read(image));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void draw(Graphics2D g2) {
		if (!gp.onCutscene) {
			g2.setColor(Color.black);
			g2.fillRect(147, gp.screen.height-59, 134, 27);
			g2.fillRect(137, gp.screen.height-30, 144, 27);
			g2.fillRect(122, gp.screen.height-20, 15, 17);
			g2.setColor(gp.ui.colors[1]);
			g2.fillRect(147, gp.screen.height-59, (int)(134*((float)gp.player.endurance/(float)gp.player.enduranceLimit)), 27);
			g2.setColor(gp.ui.colors[2]);
			g2.fillRect(137, gp.screen.height-30, 144, 27);
			g2.fillRect(122, gp.screen.height-20, 15, 17);
			g2.fillRect(147, gp.screen.height-30, (int)(134*(gp.player.health/gp.player.healthLimit)), 27);
			
			g2.drawImage(uiImages.get(2), 114, gp.screen.height-33, 170, 33, null);
			g2.drawImage(uiImages.get(1), 114, gp.screen.height-62, 170, 33, null);
			g2.drawImage(uiImages.get(3), 0, gp.screen.height-120, 184, 120, null);
			g2.drawImage(gp.player.animImage, 92-gp.tileSize/2, gp.screen.height-60-gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		}
	}
}
