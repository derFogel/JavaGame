package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Card {
	public static int ROT = 2;
	public static int SCHELLN = 1;
	public static int GRUN = 3;
	public static int EICHEL = 4;
	
	public static int SIEBEN = 1;
	public static int ACHT = 2;
	public static int NEUN = 3;
	public static int ZEHN = 4;
	public static int UNTER = 5;
	public static int OBER = 6;
	public static int KONIG = 7;
	public static int DAUS = 8;
	
	public int value;
	
	private int x;
	private int y;
	private int height = 200;
	private int width = 128;
	private Image texture;
	
	public Card(int color, int value) {
		switch (color) {
		case 2:
			y = 0;
			break;
		case 1:
			y = 1 * height;
			break;
		case 3:
			y = 2 * height;
			break;
		case 4:
			y = 3 * height;
			break;
		}
		
		x += (8 - value) * width;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		texture = toolkit.getImage("spiel.jpg");
		
		this.value = color * value;
	}
	
	public void render(Graphics graphics, int px, int py) {
		graphics.drawImage(texture, px, py, px + width, py + height, x, y, x + width, y + height, null);
	}
}
