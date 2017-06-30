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

    private final byte[] map;
    private final Image[] tiles;

    public static final byte WALL = 0;
    public static final byte WALL_FRONT = 1;
    public static final byte GRASS = 2;

    public MapRenderer(String path) throws IOException {
        map = new byte[100];

        for (int i = 0; i < map.length; i++) {
            int x = i % 10;
            int y = i / 10;

            
            if (x == 0) {
                map[i] = WALL;
            } else if (y == 0) {
                map[i] = WALL_FRONT;
            } else {
                map[i] = GRASS;
            }
        }
        tiles = new Image[3];

        File pathtofile = new File(path);
        BufferedImage tiles_data = ImageIO.read(pathtofile);

        tiles[WALL] = tiles_data.getSubimage(80, 60, 20, 20);
        tiles[WALL_FRONT] = tiles_data.getSubimage(60, 80, 20, 20);
        tiles[GRASS] = tiles_data.getSubimage(0, 0, 20, 20);
    }

    public void render(Graphics graphic) {
        for (int i = 0; i < map.length; i++) {
            int x = i % 10;
            int y = i / 10;

            if (map[0] == -1) continue;
            graphic.drawImage(tiles[map[i]], x * 20, y * 20, null);
        }
    }
}
