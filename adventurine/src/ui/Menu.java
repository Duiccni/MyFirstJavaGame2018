package ui;

import java.util.List;

public class Menu {
	public boolean isSub;
	public int index;
	public List<Element> elements;
	
	public Menu(boolean isSub, int index, List<Element> elements) {
		this.isSub = isSub;
		this.index = index;
		this.elements = elements;
	}
}
