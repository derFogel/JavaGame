package game;

import java.util.LinkedList;

public class Narrator extends Character {

	/**
	 * 
	 */
	private LinkedList<Position> path;
	private boolean next;

	public Narrator(boolean player, int spawnx, int spawny, String path) {
		super(player, spawnx, spawny, path);
		next = false;
	}
	
	public void init() {
		LinkedList<Position> weg = Game.level.pathfinder.findPath(7, 5, 22, 23);
		path = weg;
	}
	
	public void update() {
		if (Game.dialog.isOpen() == false) {
			if (path.size() > 0 && !isMoving() && calcDistanceTo(Game.player.x, Game.player.y) < 4) {
				Position p = path.getFirst();
				move(x - p.x, y - p.y);
				path.remove(0);
			}
			if (path.size() == 0 && calcDistanceTo(Game.player.x, Game.player.y) < 3) {
				if (Game.player.y > y) {
					direction = Character.DOWN;
				} else if (Game.player.y < y) {
					direction = Character.UP;
				} else if (Game.player.x > x) {
					direction = Character.RIGHT;
				} else if (Game.player.x < x) {
					direction = Character.LEFT;
				}
				
				if (!next) {
					Game.dialog.setDialog("tutorial_2");
					next = true;
				}
			}
		}
	}
}
