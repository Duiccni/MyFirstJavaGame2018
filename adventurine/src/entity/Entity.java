package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import game.Transform;

public class Entity {
	public Transform transform, velocity, solid;
	public byte animIndex;
	public int direction;
	public float speed, healthLimit, health;
	
	List<BufferedImage> animImages = new ArrayList<>();
	
	void getImages(String path) {
		File imagesFolder = new File(path);
		File[] images = imagesFolder.listFiles();
		
		try {
			for (File image : images) {
				animImages.add(ImageIO.read(image));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
