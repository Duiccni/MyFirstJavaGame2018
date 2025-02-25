package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.GamePanel;
import game.Transform;

public class Player extends Entity {
	GamePanel gp;
	public BufferedImage animImage;
	
	float defaultSpeed;
	public byte enduranceLimit, endurance, enduranceDelay;
	
	Color semiBlack = new Color(0, 0, 0, 155);
	Transform shadow;
	
	public Player(GamePanel gp) {
		this.gp = gp;
		
		shadow = new Transform(gp.screenMiddle.x+gp.tileSize/5, (float)(gp.screenMiddle.y+gp.tileSize/1.25), (int)(gp.tileSize/1.5), gp.tileSize/4);
		transform = new Transform(gp.mapSize/2, gp.mapSize/2);
		solid = new Transform(gp.tileSize/4, gp.tileSize/2, gp.tileSize/2, gp.tileSize/2);
		velocity = new Transform(0, 0);
		direction = 3;
		
		setDeafultValues();
		getImages("res/player");
	}
	
	void setDeafultValues() {
		defaultSpeed = 0.8f;
		speed = 0.8f;
		healthLimit = 100;
		health = 100;
		enduranceLimit = 9;
		endurance = 9;
	}
	
	public void delayedUpdate() {
		if (gp.keyH.keySprint && endurance > 0) {
			enduranceDelay++;
			endurance--;
		} else if (endurance < enduranceLimit && enduranceDelay == 0) {
			endurance++;
		} else if (enduranceDelay > 0) {
			enduranceDelay--;
		}
	}
	
	public void update() {
		if (!gp.onCutscene) {
			if (gp.keyH.keySprint && endurance > 0) {
				speed = defaultSpeed*1.5f;
			} else if (speed != defaultSpeed) {
				speed = defaultSpeed;
			}
			
			if (gp.keyH.keyUp) {
				velocity.y = -1;
			} else if (gp.keyH.keyDown) {
				velocity.y = 1;
			} else if (velocity.y != 0) {
				velocity.y = 0;
			}
			
			if (gp.keyH.keyLeft) {
				velocity.x = -1;
			} else if (gp.keyH.keyRight) {
				velocity.x = 1;
			} else if (velocity.x != 0) {
				velocity.x = 0;
			}
		}
		
		float calculatedSpeed = speed*gp.deltaTime*gp.tileSize;
		
		if (velocity.y < 0) {
			direction = 1;
		} else if (velocity.y > 0) {
			direction = 3;
		} else if (velocity.x < 0) {
			direction = 4;
		} else if (velocity.x > 0) {
			direction = 2;
		} else if (direction > 0) {
			direction = -direction;
		}
		
		transform.x += calculatedSpeed*velocity.x;
		transform.y += calculatedSpeed*velocity.y;
	}
	
	public void draw(Graphics2D g2) {
		switch (direction) {
		case 1:
			switch (animIndex) {
			case 0:
				animImage = animImages.get(10);
				break;
			case 1:
				animImage = animImages.get(11);
				break;
			}
			break;
		case 2:
			switch (animIndex) {
			case 0:
				animImage = animImages.get(7);
				break;
			case 1:
				animImage = animImages.get(8);
				break;
			}
			break;
		case 3:
			switch (animIndex) {
			case 0:
				animImage = animImages.get(1);
				break;
			case 1:
				animImage = animImages.get(2);
				break;
			}
			break;
		case 4:
			switch (animIndex) {
			case 0:
				animImage = animImages.get(4);
				break;
			case 1:
				animImage = animImages.get(5);
				break;
			}
			break;
		case -1:
			animImage = animImages.get(9);
			break;
		case -2:
			animImage = animImages.get(6);
			break;
		case -3:
			animImage = animImages.get(0);
			break;
		case -4:
			animImage = animImages.get(3);
			break;
		}
		
		g2.setColor(semiBlack);
		g2.fillOval((int)shadow.x, (int)shadow.y, shadow.width, shadow.height);
		g2.drawImage(animImage, (int)gp.screenMiddle.x, (int)gp.screenMiddle.y, gp.tileSize, gp.tileSize, null);
	}
}
