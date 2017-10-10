package game;

import java.util.Random;

public class OpenChest implements WorldObject {

	@Override
	public void use() {
		Random r = new Random();
		
		for (int i = 0; i < 10; i++) {
			Card c = new Card(r.nextInt(4) + 1, r.nextInt(8) + 1);
			Game.inventory.add(c);
		}
		
		Game.minigame.init();
		Game.minigame.open();
		Game.dialog.setDialog("game");
	}

}
