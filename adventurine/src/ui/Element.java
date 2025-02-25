package ui;

public class Element {
	public int textIndex, voidIndex;
	public Object data;
	
	public Element(int i) {
		this.textIndex = i;
	}
	
	public Element(int textIndex, int voidIndex) {
		this.textIndex = textIndex;
		this.voidIndex = voidIndex;
	}
	
	public Element(int textIndex, int voidIndex, Object data) {
		this.textIndex = textIndex;
		this.voidIndex = voidIndex;
		this.data = data;
	}
}
