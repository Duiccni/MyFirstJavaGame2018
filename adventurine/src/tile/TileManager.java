package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.Transform;

public class TileManager {
	GamePanel gp;
	Random rand = new Random();
	
	List<BufferedImage> tileImages = new ArrayList<>();
	List<BufferedImage> objectImages = new ArrayList<>();
	List<List<Tile>> tiles = new ArrayList<>();
	
	public Transform playerGroup;
	Transform renderDelay;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		getImages("res/tiles", tileImages);
		getImages("res/objects", objectImages);
		
		int squaregl = gp.groupLength*gp.groupLength;
		int squaregs = gp.groupSize*gp.groupSize;
		for (int l = 0; l < squaregl; l++) {
			List<Tile> tileList = new ArrayList<>();
			
			for (int r = 0; r < squaregs; r++) {
				tileList.add(new Tile((r%gp.groupSize+l%gp.groupLength*gp.groupSize)*gp.tileSize, (r/gp.groupSize+(l/gp.groupLength)*gp.groupSize)*gp.tileSize, rand.nextInt(3)));
			}
			
			tiles.add(tileList);
		}
	}
	
	void getImages(String path, List<BufferedImage> imageList) {
		File imagesFolder = new File(path);
		File[] images = imagesFolder.listFiles();
		
		try {
			for (File image : images) {
				imageList.add(ImageIO.read(image));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		playerGroup = new Transform((int)(gp.player.transform.x/gp.groupSize/gp.tileSize), (int)(gp.player.transform.y/gp.groupSize/gp.tileSize));
		renderDelay = new Transform(gp.screenMiddle.x-gp.player.transform.x+gp.tileSize/2, gp.screenMiddle.y-gp.player.transform.y+gp.tileSize);
		
		for (short hor = -1; hor < 2; hor++) {
			for (short ver = -1; ver < 2; ver++) {
				List<Tile> tileGroup = tiles.get((int)(playerGroup.y*gp.groupLength+playerGroup.x)-hor-ver*gp.groupLength);
				for (Tile tile : tileGroup) {
					g2.drawImage(tileImages.get(tile.imageIndex), tile.x+(int)renderDelay.x, tile.y+(int)renderDelay.y, gp.tileSize, gp.tileSize, null);
				}
			}
		}
	}
}
