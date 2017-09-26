package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class DialogBox {
	private String text;
	private JSONObject obj;
	private int index;
	private boolean isOpen;
	private Font font;
	
	public DialogBox() {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("dialogs.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = new String();
		while (scan.hasNext()) {
			str += scan.nextLine() + "\n";
		}
		scan.close();
		
		obj = new JSONObject(str);
		
		File fontfile = new File("font.ttf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontfile);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		font = font.deriveFont(Font.PLAIN, 20);
		
		index = 0;
		isOpen = false;
	}
	
	public void render(Graphics graphic) {
		JSONArray array = obj.getJSONArray(text);
		
		if (index >= array.length()) {
			isOpen = false;
		}
		
		if (isOpen) {
			graphic.setColor(Color.white);
			graphic.fillRect(5, 325, 670, 150);
			graphic.setColor(Color.black);
			((Graphics2D)graphic).setStroke(new BasicStroke(10));
			graphic.drawRoundRect(5, 320, 670, 158, 5, 5);
			graphic.setFont(font);
			char[] text = array.getString(index).toCharArray();
			int len = text.length;
			int offset = 0;
			while (len > offset) {
				int end = 32;
				if (end > (len - offset)) {
					end = len - offset;
				}
								
				graphic.drawChars(text, offset, end, 20, 360 + offset);
				offset += 32;
			}
		}
	}
	
	public void setDialog(String dialog) {
		text = dialog;
		index = 0;
		isOpen = true;
	}
	
	public void next() {
		index++;
	}
	
	public Boolean isOpen() {
		return isOpen;
	}
	
	public String getDialog() {
		return text;
	}
}
