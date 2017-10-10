/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Game {
    /**
	 * 
	 */
	private static JFrame frame;
    private static Canvas canvas;
    protected static DialogBox dialog = new DialogBox();
    protected static LinkedList<Character> characters;
    protected static ArrayList<Card> inventory;
    protected static Character player;
    protected static Narrator narrator;
    protected static game.Level level;
    protected static MiniGame minigame;
    
    private static void addKeyListenener() {
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
		    	synchronized (Game.class) {
					switch (ke.getID()) {
					case KeyEvent.KEY_PRESSED:
						if (ke.getKeyCode() == KeyEvent.VK_W) {
							if (dialog.isOpen() || minigame.isOpen()) break;
							Game.player.move(0, 1);
						} else if (ke.getKeyCode() == KeyEvent.VK_S) {
							if (dialog.isOpen() || minigame.isOpen()) break;
							Game.player.move(0, -1);
						} else if (ke.getKeyCode() == KeyEvent.VK_A) {
							if (dialog.isOpen() || minigame.isOpen()) break;
							Game.player.move(1, 0);
						} else if (ke.getKeyCode() == KeyEvent.VK_D) {
							if (dialog.isOpen() || minigame.isOpen()) break;
							Game.player.move(-1, 0);
						} else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
							dialog.next();
						} else if (ke.getKeyCode() == KeyEvent.VK_E) {
							if (minigame.isOpen()) break;
							player.use();
						} else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
							if (minigame.isOpen()) {
								minigame.select(-1);
							}
						} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
							if (minigame.isOpen()) {
								minigame.select(1);
							}
						} else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
							if (minigame.isOpen()) {
								minigame.play();
							}
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
        
        minigame = new MiniGame();
        
        level = new game.Level("plains.lvl");
        player = new Character(true, 8, 6, "player.png");
        player.direction = Character.UP;
        
        narrator = new Narrator(false, 8, 5, "narrator.png");
        narrator.direction = Character.DOWN;
        
        characters.add(player);
        characters.add(narrator);
        
        Villager test = null;
        
        test = new Villager(47, 29, new Position[]{new Position(47, 29), new Position(48, 21), new Position(44, 24)}, "blatt_woman.png");
        test.init();
        characters.add(test);
        
        test = new Villager(28, 39, new Position[]{new Position(28, 39), new Position(51, 25), new Position(44, 24)}, "blatt_woman.png");
        test.init();
        characters.add(test);
        
        test = new Villager(43, 21, new Position[]{new Position(43, 21), new Position(54, 25), new Position(44, 30)}, "blatt_man.png");
        test.init();
        characters.add(test);
        
        test = new Villager(44, 27, new Position[]{new Position(44, 27), new Position(44, 27), new Position(51, 22)}, "blatt_man.png");
        test.init();
        characters.add(test);
        
        test = new Villager(8, 85, new Position[]{new Position(8, 85), new Position(20, 81), new Position(13, 89)}, "woman1.png");
        test.init();
        characters.add(test);
        
        test = new Villager(4, 89, new Position[]{new Position(4, 89), new Position(11, 85), new Position(8, 82)}, "woman1.png");
        test.init();
        characters.add(test);
        
        test = new Villager(19, 89, new Position[]{new Position(19, 89), new Position(7, 89), new Position(16, 81)}, "man1.png");
        test.init();
        characters.add(test);
        
        test = new Villager(23, 83, new Position[]{new Position(23, 83), new Position(12, 85), new Position(16, 89)}, "man1.png");
        test.init();
        characters.add(test);
        
        Character boss = new Character(false, 66, 4, "master.png");
        characters.add(boss);
        
        level.map.map[66][4].wObject = new StartMsg();
        
        for (Character chars : characters) {
        	chars.load();
        }
        
        dialog.setDialog("tutorial");
        narrator.init();
        
        inventory = new ArrayList<>();

        while (running) {
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
            graphics.clearRect(0, 0, w, h);

            level.render(graphics);
            minigame.render(graphics);
            dialog.render(graphics);
            narrator.update();
            
            bufferStrategy.show();
            graphics.dispose();
        }
    }
}
