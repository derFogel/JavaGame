package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
		graphic.setColor(Color.white);
		graphic.fillRect(x + 5, y + 5, w - 10, 150);
		graphic.setColor(Color.black);
		((Graphics2D)graphic).setStroke(new BasicStroke(10));
		graphic.drawRoundRect(x + 5, y, w - 10, 158, 5, 5);
	}
}
