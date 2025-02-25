package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import game.GamePanel;

public class UI {
	GamePanel gp;
	Font slab16R, slab24R, slab48B;
	FontMetrics slab16R_metrics;
	
	public int selectedIndex, uiIndex = 1, uiLength = 3, currentLang, statSize[] = { 0, 0 };
	public boolean canLoadGame;
	
	List<Menu> menus = new ArrayList<>();
	List<String[]> languages = new ArrayList<>();
	List<String> statistics = new ArrayList<>();
	
	String[] lang;
	Runtime runtime;
	
	public Color colors[] = { new Color(0, 0, 0, 155), new Color(32, 16, 155), new Color(155, 16, 0), new Color(125, 125, 105), new Color(220, 220, 220) };
	public PlayerUI playerUI;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		menus.add(new Menu(false, 1, Arrays.asList(new Element(2, 6), new Element(4, 3, 'r'))));
		menus.add(new Menu(false, 0, Arrays.asList(new Element(5, 1, 4), new Element(-1), new Element(7, 1, 2), new Element(8, 3, 'e'))));
		menus.add(new Menu(false, 7, Arrays.asList(new Element(9, 1, 3), new Element(10), new Element(11), new Element(12), new Element(3, 1, 1))));
		menus.add(new Menu(true, 2, Arrays.asList(new Element(23, 4, 0), new Element(24, 4, 1), new Element(3, 1, 2))));
		menus.add(new Menu(false, 13, Arrays.asList(new Element(15, 2, 'e'), new Element(16, 2, 'm'), new Element(17, 2, 'h'), new Element(3, 1, 1))));
		menus.add(new Menu(false, 14, Arrays.asList(new Element(18, 5, 35), new Element(19, 5, 70), new Element(20, 5, 135), new Element(3, 1, 4))));
		
		File langsFolder = new File("res/langs");
		File[] langs = langsFolder.listFiles();
		try {
			for (File lang : langs) {
				languages.add(Files.readString(lang.toPath(), StandardCharsets.UTF_8).split(";"));
			}
			slab16R = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/RobotoSlab-Regular.ttf"));
			slab48B = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/RobotoSlab-Bold.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		slab24R = slab16R.deriveFont(Font.PLAIN, 24);
		slab16R = slab16R.deriveFont(Font.PLAIN, 16);
		slab48B = slab48B.deriveFont(Font.PLAIN, 48);
		
		lang = languages.get(0);
	}
	
	public void pressedEnter() {
		Element element = menus.get(uiIndex).elements.get(selectedIndex);
		switch (element.voidIndex) {
		case 1:
			uiIndex = (int)element.data;
			uiLength = menus.get(uiIndex).elements.size()-1;
			selectedIndex = 0;
			break;
		case 2:
			gp.difficulty = (char)element.data;
			uiIndex = 5;
			uiLength = 3;
			selectedIndex = 0;
			break;
		case 3:
			int msgBox;
			switch ((char)element.data) {
			case 'r':
				msgBox = JOptionPane.showConfirmDialog(gp, lang[22], "Adventurine", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (msgBox == 0) {
					gp.setGameState('m');
					uiIndex = 1;
					uiLength = 3;
					selectedIndex = 0;
				}
				break;
			case 'e':
				msgBox = JOptionPane.showConfirmDialog(gp, lang[21], "Adventurine", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (msgBox == 0) {
					System.exit(0);
				}
				break;
			}
			break;
		case 4:
			currentLang = (int)element.data;
			lang = languages.get(currentLang);
			uiIndex = 2;
			uiLength = 4;
			selectedIndex = 0;
			break;
		case 5:
			gp.startNewGame((int)element.data);
			uiIndex = 0;
			uiLength = 1;
			selectedIndex = 0;
			break;
		case 6:
			gp.setGameState('g');
		}
	}
	
	public void updateStats() {
		statistics.clear();
		runtime = Runtime.getRuntime();
		
		statistics.add((runtime.totalMemory()-runtime.freeMemory())/1000000 + "/" + runtime.totalMemory()/1000000 + "MB");
		statistics.add(Math.round(gp.FPS) + "/" + gp.FPSLimit + " FPS");
		statistics.add("Tick " + gp.tickCount);
		
		statSize[0] = statistics.size();
		
		if (gp.tileManager != null) {
			statistics.add((int)gp.player.transform.x + "/" + (int)gp.player.transform.y);
			statistics.add((int)gp.tileManager.playerGroup.x + "/" + (int)gp.tileManager.playerGroup.y);
			statistics.add(gp.player.endurance + "/" + gp.player.enduranceLimit + "/" + gp.player.enduranceDelay);
			
			statSize[1] = statistics.size();
		}
	}
	
	public void drawStats(Graphics2D g2, char align) {
		g2.setFont(slab16R);
		
		switch (align) {
		case 'l':
			for (byte i = 0; i < statSize[0]; i++) {
				g2.drawString(statistics.get(i), 10, 20+i*15);
			}
			for (byte i = (byte)statSize[0]; i < statSize[1]; i++) {
				g2.drawString(statistics.get(i), gp.screen.width-slab16R_metrics.stringWidth(statistics.get(i))-10, 20+(i-statSize[0])*15);
			}
			break;
		case 'r':
			for (byte i = 0; i < statSize[0]; i++) {
				g2.drawString(statistics.get(i), gp.screen.width-slab16R_metrics.stringWidth(statistics.get(i))-10, 20+i*15);
			}
			break;
		}
	}
	
	public void drawMenu(Graphics2D g2, Menu menu, boolean type, boolean onSub) {
		Color mainColor = Color.black, shadowColor = Color.gray;
		
		if (type) {
			mainColor = colors[3];
			shadowColor = Color.black;
		}
		
		int menuSize = menu.elements.size();
		
		if (menu.isSub) {
			drawMenu(g2, menus.get(menu.index), type, true);
			
			g2.setFont(slab24R);
			g2.setColor(shadowColor);
			g2.drawString(">", 291, 148+selectedIndex*40);
			g2.setColor(mainColor);
			g2.drawString(">", 290, 147+selectedIndex*40);
			
			for (byte i = 0; i < menuSize; i++) {
				String text = lang[menu.elements.get(i).textIndex];
				
				byte isSelected = 0;
				if (i == selectedIndex) {
					isSelected = 1;
				}
				
				g2.setColor(shadowColor);
				g2.drawString(text, 311+isSelected*11, 151+i*40);
				g2.setColor(mainColor);
				g2.drawString(text, 310+isSelected*10, 150+i*40);
			}
		} else {
			g2.setFont(slab48B);
			g2.setColor(shadowColor);
			g2.drawString(lang[menu.index], 51, 71);
			g2.setColor(mainColor);
			g2.drawString(lang[menu.index], 50, 70);
			
			g2.setFont(slab24R);
			if (!onSub) {
				g2.setColor(shadowColor);
				g2.drawString(">", 111, 148+selectedIndex*40);
				g2.setColor(mainColor);
				g2.drawString(">", 110, 147+selectedIndex*40);
			}
			
			for (byte i = 0; i < menuSize; i++) {
				int textIndex = menu.elements.get(i).textIndex;
				if (textIndex != -1) {
					String text = lang[textIndex];
					
					byte isSelected = 0;
					if (i == selectedIndex && !onSub) {
						isSelected = 1;
					}
					
					g2.setColor(shadowColor);
					g2.drawString(text, 131+isSelected*11, 151+i*40);
					g2.setColor(mainColor);
					g2.drawString(text, 130+isSelected*10, 150+i*40);
				}
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		if (gp.tickCount == 0) {
			slab16R_metrics = gp.getFontMetrics(slab16R);
		}
		
		switch (gp.gameState) {
		case 'g':
			playerUI.draw(g2);
			
			if (gp.keyH.holdDeveloper) {
				g2.setColor(colors[4]);
				drawStats(g2, 'l');
			}
			break;
		case 's':
			playerUI.draw(g2);
			
			g2.setColor(colors[0]);
			g2.fillRect(0, 0, gp.screen.width, gp.screen.height);
			
			drawMenu(g2, menus.get(0), true, false);
			
			if (gp.keyH.holdDeveloper) {
				drawStats(g2, 'r');
			}
			break;
		case 'm':
			drawMenu(g2, menus.get(uiIndex), false, false);
			
			if (uiIndex == 1) {
				if (canLoadGame) {
					g2.setColor(Color.gray);
					g2.drawString(lang[6], 131, 191);
					g2.setColor(Color.black);
					g2.drawString(lang[6], 130, 190);
				} else {
					g2.setColor(Color.lightGray);
					g2.drawString(lang[6], 131, 191);
					g2.setColor(Color.gray);
					g2.drawString(lang[6], 130, 190);
					g2.setColor(Color.black);
				}
			}
			
			if (gp.keyH.holdDeveloper) {
				drawStats(g2, 'r');
			}
			break;
		}
	}
}
