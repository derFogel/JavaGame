package game;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Level {
	private int width;
	private int height;
	protected MapRenderer map;
	public PathFinding pathfinder;
	
	public Level(String path) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line = reader.readLine();
			
			String size[] = line.split(" ");
			width = Integer.parseInt(size[0]);
			height = Integer.parseInt(size[1]);
			
			String map_sprites = reader.readLine();
			
			HashMap<Byte, Byte> input_map = new HashMap<>();
			
			line = reader.readLine();
			
			for (int i = 0; i < line.length(); i++) {
				input_map.put(line.getBytes()[i], (byte)i);
			}
						
			MapTile map_data[][] = new MapTile[height][width];
			pathfinder = new PathFinding(width, height);
			
			WaterMsg msgw = new WaterMsg();
			HouseMsg hmsg = new HouseMsg();
			OpenChest chest = new OpenChest();
			
			for (int y = 0; y < width; y++) {
				line = reader.readLine();
				for (int x = 0; x < height; x++) {
					map_data[x][y] = new MapTile();
					byte b = line.getBytes()[x];
					if (b > '1' && b < '6') {
						int treesize = b - 49;
						map_data[x][y].texture = MapRenderer.TREE_BOTTOM;
						map_data[x][y].isWalkable = false;
						pathfinder.blockField(x, y);
						for (int i = y - 1; i > y - treesize; i--) {
							map_data[x][i].texture = MapRenderer.TREE_MIDDLE;
							map_data[x][i].isWalkable = true;
						}
						map_data[x][y - treesize].texture = MapRenderer.TREE_TOP;
						map_data[x][y - treesize].isWalkable = true;
					} else if (b == 'O') {
						map_data[x][y].texture = MapRenderer.WATER1;
						map_data[x][y].isWalkable = false;
						map_data[x][y].wObject = msgw;
						pathfinder.blockField(x, y);
					} else if (b == '#' || b == 'H' || b == 'W' || b == '_' || b == '|' || b == '"') {
						map_data[x][y].texture = input_map.get(b);
						map_data[x][y].isWalkable = false;
						pathfinder.blockField(x, y);
						if (b == 'H') {
							map_data[x][y].wObject = hmsg;
						}
					} else if (b == 'C') {
						map_data[x][y].texture = input_map.get(b);
						map_data[x][y].isWalkable = false;
						map_data[x][y].wObject = chest;
					} else {
						map_data[x][y].texture = input_map.get(b);
						if (b == 49 || b == 48) {
							pathfinder.blockField(x, y);
						} else {
							map_data[x][y].isWalkable = true;
						}
					}
				}
			}
			
			map = new MapRenderer(map_sprites, width, height, map_data);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics graphic) {
		map.render(graphic);
		
		try {
			Thread.sleep(1000 / 30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
