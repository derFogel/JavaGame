package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class MiniGame {
	private boolean isOpen;
	private Image table;
	
	public MiniGame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		isOpen = false;
		
		table = toolkit.getImage("table.jpg");
	}
	
	
	public void render(Graphics graphic) {
		if (!isOpen) return;
		
		graphic.drawImage(table, 0, 0, 680, 520, 0, 0, 275, 183, null);
	}
	
	public void open() {
		isOpen = true;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
}
