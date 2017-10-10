package game;

import java.util.LinkedList;
import java.util.Random;

public class Villager extends Character {
	private LinkedList<Position> path;
	private int idle;
	private Position targets[];

	public Villager(int spawnx, int spawny, Position targets[], String path) {
		super(false, spawnx, spawny, path);
		this.targets = targets;
	}
	
	public void init() {
		LinkedList<Position> weg = null;
		
		Random r = new Random();
		
		Position t = targets[r.nextInt(targets.length)];
		
		weg = Game.level.pathfinder.findPath(x, y, t.x, t.y);
		path = weg;
		
		idle = (r.nextInt(5) + 1) * 30;
	}
	
	public void update() {	
		Game.level.map.map[x][y].wObject = null;
		if (!isMoving()) {
			Game.level.map.map[x][y].wObject = new StartMsg();
			if (path.size() == 0) {
				idle--;
				if (idle <= 0) {
					idle = 0;
					init();
				}
				
				return;
			}
			
			Position p = path.getFirst();
			if (!move(x - p.x, y - p.y)) {
				init();
			}
			if (path.size() == 0) {
				return;
			}
		}
	}

}
