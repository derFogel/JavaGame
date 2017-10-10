package game;

public class StartMsg implements WorldObject {

	@Override
	public void use() {
		Game.dialog.setDialog("play");
		Game.minigame.init();
		Game.minigame.open();
	}

}
