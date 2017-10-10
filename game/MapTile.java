package game;

public class MapTile {
	public MapTile() {
		wObject = null;
		isWalkable = true;
	}
	
	public byte texture;
	WorldObject wObject;
	boolean isWalkable;
}
