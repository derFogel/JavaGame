package game;

import java.util.LinkedList;

public class PathFinding {
	private boolean[][] map;
	private int width;
	private int height;
	
	public PathFinding(int w, int h) {
		width = w;
		height = h;
		map = new boolean[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = true;
			}
		}
	}
	
	public void blockField(int x, int y) {
		map[x][y] = false;
	}
	
	public void unblockField(int x, int y) {
		map[x][y] = true;
	}
	
	public LinkedList<Position> findPath(int startx, int starty, int endx, int endy) {
		LinkedList<Position> out = new LinkedList<>();
		LinkedList<Position> openList = new LinkedList<>();
		
		Position start = new Position();
		start.x = startx;
		start.y = starty;
		start.prev = null;
		
		Position end = new Position();
		end.x = endx;
		end.y = endy;
		end.prev = null;
		
		openList.add(start);
		
		while (openList.size() > 0) {
			//int minD = Integer.MAX_VALUE;
			//Position next = null;

			//for (Position p : openList) {
			//	if (calcDistance(p, end) < minD) {
			//		next = p;
			//	}
			//}
			
			//openList.remove(next);
			//out.add(next);
			LinkedList<Position> changesList = new LinkedList<>();
			
			for (Position p : openList) {
				if (p.x == end.x && p.y == end.y) {
					Position n = p;
					while (n.prev != null) {
						out.addFirst(n);
						Position prev = n.prev;
						n = prev;
					}
										
					return out;
				}
				
				if (map[p.x - 1][p.y] && !isInList(openList, p.x - 1, p.y) && !isInList(changesList, p.x - 1, p.y)) {
					Position n = new Position();
					n.x = p.x - 1;
					n.y = p.y;
					n.prev = p;
					changesList.add(n);
				}
				
				if (map[p.x + 1][p.y] && !isInList(openList, p.x + 1, p.y) && !isInList(changesList, p.x + 1, p.y)) {
					Position n = new Position();
					n.x = p.x + 1;
					n.y = p.y;
					n.prev = p;
					changesList.add(n);
				}
				
				if (map[p.x][p.y - 1] && !isInList(openList, p.x, p.y - 1) && !isInList(changesList, p.x, p.y - 1)) {
					Position n = new Position();
					n.x = p.x;
					n.y = p.y - 1;
					n.prev = p;
					changesList.add(n);
				}
				
				if (map[p.x][p.y + 1] && !isInList(openList, p.x, p.y + 1) && !isInList(changesList, p.x, p.y + 1)) {
					Position n = new Position();
					n.x = p.x;
					n.y = p.y + 1;
					n.prev = p;
					changesList.add(n);
				}
			}
			
			for (Position p : changesList) {
				openList.add(p);
			}
		}
		
		return out;
	}
	
	/*private int calcDistance(Position current, Position target) {
		return Math.abs(target.x - current.x) + Math.abs(target.y - current.y);
	}*/
	
	private boolean isInList(LinkedList<Position> list, int x, int y) {
		for (Position p : list) {
			if (p.x == x && p.y == y) {
				return true;
			}
		}
		
		return false;
	}
}
