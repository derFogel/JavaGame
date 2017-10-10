package game;

public class WaterMsg implements WorldObject {

	@Override
	public void use() {
		Game.dialog.setDialog("cant_swim");
	}

}
