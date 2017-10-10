package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class MiniGame {
	private boolean isOpen;
	private Image table;
	private Card[] playerCards;
	private Card[] npcCards;
	private int selected;
	private int player;
	private int npc;
	private Card[] played;
	
	public MiniGame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		isOpen = false;
		
		table = toolkit.getImage("table.jpg");
		playerCards = new Card[5];
		npcCards = new Card[5];
		played = new Card[2];
	}
	
	public void init() {
		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			Card c = new Card(r.nextInt(4) + 1, r.nextInt(8) + 1);
			npcCards[i] = c;
		}
		
		int number[] = {-1, -1, -1, -1, -1};
		
		for (int i = 0; i < 5; i++) {
			int card = r.nextInt(Game.inventory.size());
			
			while (isInArray(number, card)) {
				card = r.nextInt(Game.inventory.size());
			}
			
			number[i] = card;
			playerCards[i] = Game.inventory.get(card);
		}
		
		selected = player = npc = 0;
		played[0] = null;
		played[1] = null;
	}
	
	private boolean isInArray(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public void render(Graphics graphic) {
		if (!isOpen) return;
		
		graphic.drawImage(table, 0, 0, 680, 520, 0, 0, 275, 183, null);
		
		if (played[0] != null) {
			played[0].render(graphic, 140, 0);
		}
		
		if (played[1] != null) {
			played[1].render(graphic, 248, 0);
		}
		
		for (int i = 0; i < 5; i++) {
			if (playerCards[i] != null) {
				playerCards[i].render(graphic, i * 128, 280);
			}
		}
		
		graphic.setColor(Color.RED);
		((Graphics2D)graphic).setStroke(new BasicStroke(5));
		graphic.drawRect(selected * 128, 280, 128, 200);
	}
	
	public void open() {
		isOpen = true;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void close() {
		Game.narrator.x = -100;
		Game.narrator.y = -100;
		Game.dialog.setDialog("next");
		isOpen = false;
	}
	
	public void select(int x) {
		selected += x;
		
		if (selected < 0) {
			selected = 0;
		} else if (selected > 4) {
			selected = 4;
		}
	}
	
	public void play() {
		if (playerCards[selected] == null) {
			return;
		}
		
		if (played[0] != null) {
			played[0] = null;
			played[1] = null;
			return;
		}
		
		int playerValue = playerCards[selected].value;
		played[0] = playerCards[selected];
		playerCards[selected] = null;
		
		Card smaller = null;
		Card greater = null;
		for (int i = 0; i < npcCards.length; i++) {
			if (npcCards[i] != null) {
				if (playerValue > npcCards[i].value) {
					smaller = npcCards[i];
				} else {
					greater = npcCards[i];
				}
			}
		}
		
		if (greater == null) {
			player++;
			played[1] = smaller;
			for (int i = 0; i < npcCards.length; i++) {
				if (npcCards[i] == smaller) {
					npcCards[i] = null;
				}
			}
		} else {
			npc++;
			played[1] = greater;
			for (int i = 0; i < npcCards.length; i++) {
				if (npcCards[i] == greater) {
					npcCards[i] = null;
				}
			}
		}
		
		if ((player + npc) == 5) {
			close();
			if (player > npc) {
				Game.dialog.setDialog("player");
				addBetterCard();
				addBetterCard();
				
				if (Game.player.x == 65 || Game.player.x == 67) {
					if (Game.player.y == 3 || Game.player.y == 5) {
						Game.dialog.setDialog("win");
					}
				}
				
			} else {
				Game.dialog.setDialog("npc");
			}
		}
	}
	
	private void addBetterCard() {
		Random r = new Random();
		
		Card min = Game.inventory.get(0);
		for (Card c : Game.inventory) {
			if (c.value < min.value) {
				min = c;
			}
		}
		
		Card n = new Card(r.nextInt(4) + 1, r.nextInt(8) + 1);
		
		while (n.value < min.value) {
			n = new Card(r.nextInt(4) + 1, r.nextInt(8) + 1);
		}
		
		min = n;
	}
}
