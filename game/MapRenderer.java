/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author heyl
 */
public class MapRenderer {

    private final byte[][] map;
    private final Image[] tiles;
    private int width;
    private int height;
    private int offsetx;
    private int offsety;
    private int toMoveX;
    private int toMoveY;

    public static final byte WALL = 0;
    public static final byte WALL_FRONT = 1;
    public static final byte GRASS = 2;
    public static final byte TREE_BOTTOM = 3;
    public static final byte TREE_MIDDLE = 4;
    public static final byte TREE_TOP = 5;
    
    public static final int tile_size = 40;
    
    public MapRenderer(String path, int width, int heigt, byte[][] map) {
        this.map = map;

        this.width = width;
        this.height = heigt;
        
        tiles = new Image[6];

        File pathtofile = new File(path);
        BufferedImage tiles_data;
		try {
			tiles_data = ImageIO.read(pathtofile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

        tiles[WALL] = tiles_data.getSubimage(80, 60, 20, 20);
        tiles[WALL_FRONT] = tiles_data.getSubimage(60, 80, 20, 20);
        tiles[GRASS] = tiles_data.getSubimage(0, 0, 20, 20);
        tiles[TREE_BOTTOM] = tiles_data.getSubimage(60, 140, 20, 20);
        tiles[TREE_MIDDLE] = tiles_data.getSubimage(160, 120, 20, 20);
        tiles[TREE_TOP] = tiles_data.getSubimage(160, 100, 20, 20);
        
        offsetx = offsety = 0;
        toMoveX = toMoveY = 0;
    }
    
    public void move(int x, int y) {
    	if (toMoveX == 0 && toMoveY == 0) {
    		toMoveX = x * tile_size;
    		toMoveY = y * tile_size;
    	}
    }
    
    public boolean getTileWalkable(int x, int y) {
    	if (map[x][y] == GRASS || map[x][y] == TREE_MIDDLE || map[x][y] == TREE_TOP) {
    		for (Character chars : Game.characters) {
    			if (chars.x == x && chars.y == y) {
    				return false;
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
    public int getOffsetX() {
    	return offsetx;
    }
    
    public int getOffsetY() {
    	return offsety;
    }

    public void render(Graphics graphic) {
    	if (toMoveX != 0) {
			int delta = toMoveX > 0 ? -2 : 2;
			toMoveX += delta;
			offsetx -= delta;
		} else if (toMoveY != 0) {
			int delta = toMoveY > 0 ? -2 : 2;
			toMoveY += delta;
			offsety -= delta;
		}
    	
    	int startx = Game.player.x - 9 > 0 ? Game.player.x - 9 : 0;
    	int endx = Game.player.x + 10 < width ? Game.player.x + 10 : width;
    	int starty = Game.player.y - 7 > 0 ? Game.player.y - 7 : 0;
    	int endy = Game.player.y + 7 < height ? Game.player.y + 7 : height;
    	    	
        for (int x = startx; x < endx; x++) {
        	for (int y = starty; y < endy; y++) {
        		if (map[x][y] == TREE_MIDDLE || map[x][y] == TREE_TOP) {
        			graphic.drawImage(tiles[GRASS], offsetx + x * tile_size, offsety + y * tile_size, tile_size, tile_size, null);
        		} else {
        			graphic.drawImage(tiles[map[x][y]], offsetx + x * tile_size, offsety + y * tile_size, tile_size, tile_size, null);
        		}
        	}
        }
        
        for (Character chars : Game.characters ) {
        	chars.render(graphic);
        }
        
        for (int x = startx; x < endx; x++) {
        	for (int y = starty; y < endy; y++) {
        		if (map[x][y] == TREE_MIDDLE || map[x][y] == TREE_TOP) {
        			graphic.drawImage(tiles[map[x][y]], offsetx + x * tile_size, offsety + y * tile_size, tile_size, tile_size, null);
        		}
        	}
        }
    }
}
