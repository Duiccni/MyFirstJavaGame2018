package game;

public class Transform {
	public float x, y;
	public int width, height;
	
	public Transform(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Transform(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
