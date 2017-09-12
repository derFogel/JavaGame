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
						
			byte map_data[][] = new byte[height][width];			
			for (int y = 0; y < width; y++) {
				line = reader.readLine();
				for (int x = 0; x < height; x++) {
					byte b = line.getBytes()[x];
					if (b > '1' && b < '6') {
						int treesize = b - 49;
						map_data[x][y] = MapRenderer.TREE_BOTTOM;
						for (int i = y - 1; i > y - treesize; i--) {
							map_data[x][i] = MapRenderer.TREE_MIDDLE;
						}
						map_data[x][y - treesize] = MapRenderer.TREE_TOP;
					} else {
						map_data[x][y] = input_map.get(b);
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
