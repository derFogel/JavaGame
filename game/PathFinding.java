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
		LinkedList<Position> closedList = new LinkedList<>();
		
		Position end = new Position();
		end.x = endx;
		end.y = endy;
		end.prev = null;
		
		Position start = new Position();
		start.x = startx;
		start.y = starty;
		start.f = calcDistance(start, end);
		start.prev = null;
		
		openList.add(start);
		
		while (openList.size() > 0) {
			Position shortestWay = new Position();
			shortestWay.f = Integer.MAX_VALUE;
			
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
				
				if (p.f < shortestWay.f) {
					shortestWay = p;
				}
			}
			
			closedList.add(shortestWay);
			openList.remove(shortestWay);
			
			if (map[shortestWay.x - 1][shortestWay.y] && !isInList(openList, shortestWay.x - 1, shortestWay.y) && !isInList(closedList, shortestWay.x - 1, shortestWay.y)) {
				Position n = new Position();
				n.x = shortestWay.x - 1;
				n.y = shortestWay.y;
				n.prev = shortestWay;
				n.f = calcDistance(n, end);
				openList.add(n);
			}
			
			if (map[shortestWay.x + 1][shortestWay.y] && !isInList(openList, shortestWay.x + 1, shortestWay.y) && !isInList(closedList, shortestWay.x + 1, shortestWay.y)) {
				Position n = new Position();
				n.x = shortestWay.x + 1;
				n.y = shortestWay.y;
				n.prev = shortestWay;
				n.f = calcDistance(n, end);
				openList.add(n);
			}
			
			if (map[shortestWay.x][shortestWay.y - 1] && !isInList(openList, shortestWay.x, shortestWay.y - 1) && !isInList(closedList, shortestWay.x, shortestWay.y - 1)) {
				Position n = new Position();
				n.x = shortestWay.x;
				n.y = shortestWay.y - 1;
				n.prev = shortestWay;
				n.f = calcDistance(n, end);
				openList.add(n);
			}
			
			if (map[shortestWay.x][shortestWay.y + 1] && !isInList(openList, shortestWay.x, shortestWay.y + 1) && !isInList(closedList, shortestWay.x, shortestWay.y + 1)) {
				Position n = new Position();
				n.x = shortestWay.x;
				n.y = shortestWay.y + 1;
				n.prev = shortestWay;
				n.f = calcDistance(n, end);
				openList.add(n);
			}
		}
		
		return null;
	}
	
	private int calcDistance(Position current, Position target) {
		return Math.abs(current.x - target.x) + Math.abs(current.y - target.y);
	}
	
	private boolean isInList(LinkedList<Position> list, int x, int y) {
		for (Position p : list) {
			if (p.x == x && p.y == y) {
				return true;
			}
		}
		
		return false;
	}
}
