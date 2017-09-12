package game;

import java.awt.Graphics;

public class DialogBox {
	private String text;
	private int x, y;
	private int w, h;
	
	public DialogBox(String text) {
		this.text = text;
		x = 0;
		y = 320;
		w = 680;
		h = 520;
	}
	
	public void render(Graphics graphic) {
		graphic.drawRect(x, y, w, h);
	}
}
