/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author heyl
 */
public class Game {
    private static JFrame frame;
    private static Canvas canvas;
    
    public static void openWindow(int w, int h) {
        final String title = "Test Window";
        final int width = 1200;
        final int height = width / 16 * 9;

        //Creating the frame.
        JFrame frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        canvas = new Canvas();

        canvas.setSize(width, height);
        canvas.setBackground(Color.BLACK);
        canvas.setVisible(true);
        canvas.setFocusable(false);

        //Putting it all together.
        frame.add(canvas);

        canvas.createBufferStrategy(3);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean running = true;

        int w = 640;
        int h = 480;
        
        openWindow(w, h);
        
        BufferStrategy bufferStrategy;
        Graphics graphics;
        
        MapRenderer map = null;
        try {
            map = new MapRenderer("map.png");
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        while (running) {
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
            graphics.clearRect(0, 0, w, h);

            map.render(graphics);

            bufferStrategy.show();
            graphics.dispose();
        }
    }

}
