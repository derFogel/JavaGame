package game;

public class Position {
	public Position() {
	}
	
	public Position(int nx, int ny) {
		x = nx;
		y = ny;
	}
	
	public int x;
	public int y;
	public int f;
	public Position prev;
}
