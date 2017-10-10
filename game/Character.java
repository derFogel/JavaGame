package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Character {
	public int x;
	public int y;
	private int toMoveX;
	private int toMoveY;
	private boolean isMoving;
	private Image texture;
	public int direction;
	private int anistate;
	private boolean isPlayer;
	private String path;
	
	public static int LEFT = 0;
	public static int RIGHT = 1;
	public static int UP = 2;
	public static int DOWN = 3;
	
	public Character(boolean player, int spawnx, int spawny, String path) {
		x = spawnx;
		y = spawny;
		direction = RIGHT;
		anistate = 0;
		toMoveX = 0;
		toMoveY = 0;
		isPlayer = player;
		this.path = path;
	}
	
	public void load() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();	
		texture = toolkit.getImage(path);
	}
	
	public boolean move(int x, int y) {
		if (toMoveX != 0 || toMoveY != 0) {
			return false;
		}
		
		if (x == 1) {
			direction = LEFT;
		} else if (x == -1) {
			direction = RIGHT;
		} else if (y == 1) {
			direction = UP;
		} else if (y == -1) {
			direction = DOWN;
		}
		
		if (Game.level.map.getTileWalkable(this.x - x, this.y - y)) {
			this.x -= x;
			this.y -= y;
			
			toMoveX = x * MapRenderer.tile_size;
			toMoveY = y * MapRenderer.tile_size;
			
			isMoving = true;
			
			if (isPlayer) {
				Game.level.map.move(x, y);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public void render(Graphics graphic) {
		int px = x * MapRenderer.tile_size + Game.level.map.getOffsetX() + toMoveX;
		int py = y * MapRenderer.tile_size + Game.level.map.getOffsetY() + toMoveY;
		
		if (toMoveX != 0) {
			int delta = toMoveX > 0 ? -2 : 2;
			toMoveX += delta;
			anistate++;
		} else if (toMoveY != 0) {
			int delta = toMoveY > 0 ? -2 : 2;
			toMoveY += delta;
			anistate++;
		} else {
			anistate = 0;
		}
		
		if (toMoveX == 0 && toMoveY == 0) {
			isMoving = false;
		}
		
		int sy = -1;
		
		switch (direction) {
		case 0:
			sy = 48;
			break;
		case 1:
			sy = 96;
			break;
		case 2:
			sy = 144;
			break;
		case 3:
			sy = 0;
			break;
		default:
			break;
		}
		
		graphic.drawImage(texture, px + 5, py, px + game.MapRenderer.tile_size - 5, py + game.MapRenderer.tile_size, 32 * (anistate / 4), sy, 32 * (anistate / 4) + 32, sy + 48, null);
		if (anistate >= 15) {
			anistate = 0;
		}
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public int calcDistanceTo(int tx, int ty) {
		return Math.abs(x - tx) + Math.abs(y - ty);
	}
	
	public void use() {
		switch (direction) {
		case 2:
			if (Game.level.map.map[x][y - 1].wObject != null) {
				Game.level.map.map[x][y - 1].wObject.use();
			}
			break;
		case 3:
			if (Game.level.map.map[x][y + 1].wObject != null) {
				Game.level.map.map[x][y + 1].wObject.use();
			}
			break;
		case 0:
			if (Game.level.map.map[x - 1][y].wObject != null) {
				Game.level.map.map[x - 1][y].wObject.use();
			}
			break;
		case 1:
			if (Game.level.map.map[x + 1][y].wObject != null) {
				Game.level.map.map[x + 1][y].wObject.use();
			}
			break;
		}
	}
}
