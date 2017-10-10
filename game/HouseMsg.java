package game;

public class HouseMsg implements WorldObject {

	@Override
	public void use() {
		Game.dialog.setDialog("house");
		
	}
	
}
