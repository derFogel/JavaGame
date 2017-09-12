/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game {
    private static JFrame frame;
    private static Canvas canvas;
    protected static LinkedList<Character> characters;
    protected static Character player;
    protected static game.Level level;
    
    private static void addKeyListenener() {
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
		    	synchronized (Game.class) {
					switch (ke.getID()) {
					case KeyEvent.KEY_PRESSED:
						if (ke.getKeyCode() == KeyEvent.VK_W) {
							Game.player.move(0, 1);
						} else if (ke.getKeyCode() == KeyEvent.VK_S) {
							Game.player.move(0, -1);
						} else if (ke.getKeyCode() == KeyEvent.VK_A) {
							Game.player.move(1, 0);
						} else if (ke.getKeyCode() == KeyEvent.VK_D) {
							Game.player.move(-1, 0);
						}
						break;
					default:
						break;
					}
					return true;
				}
		    }
		});
    }
    
    public static void openWindow(int w, int h) {
        final String title = "Java Game";

        //Creating the frame.
        frame = new JFrame(title);

        frame.setSize(w, h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        canvas = new Canvas();

        canvas.setSize(w, h);
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
    	characters = new LinkedList<>();
        boolean running = true;

        int w = 680;
        int h = 520;
        
        openWindow(w, h);
        addKeyListenener();
        
        BufferStrategy bufferStrategy;
        Graphics graphics;
        
        level = new game.Level("plains.lvl");
        player = new Character(true, 8, 6, "player.png");
        player.direction = Character.UP;
        
        Character narrator = new Character(false, 8, 5, "narrator.png");
        narrator.direction = Character.DOWN;
        
        characters.add(player);
        characters.add(narrator);
        
        for (Character chars : characters) {
        	chars.load();
        }
        
        DialogBox dialog = new DialogBox("Test");

        while (running) {
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
            graphics.clearRect(0, 0, w, h);

            level.render(graphics);
            dialog.render(graphics);
            
            bufferStrategy.show();
            graphics.dispose();
        }
    }

}
