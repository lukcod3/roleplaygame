package lolz;


import lolz.Entity.*;
import lolz.GUI.GameGUI;
import lolz.GUI.HubGUI;
import lolz.GUI.MainMenu;
import lolz.Maps.Hub;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Main {
    public static final int HEIGHT = 540;
    public static final int WIDTH = 960;
    public static final int TILE_SIZE = 50;

    public static int CONTENT_WIDTH, CONTENT_HEIGHT;
    public static int ENTITY_WIDTH = 75;
    public static int VIRTUAL_ENTITY_WIDTH = 45;

    private final JFrame frame;
    private JPanel activePanel;

    public Player player;

    private Main() {

        // setup main frame
        frame = new JFrame();

        // main game jpanel
        activePanel = new MainMenu(this);
        frame.add(activePanel);

        frame.setTitle("Monsters & Magic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        try {
            frame.setIconImage(ImageIO.read(new File("res/tiles/knight_f_hit_anim_f0.png")));
        } catch (IOException e) {
            System.out.println("Whoops...");
        }
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);

        // measure time for game logic
        long time0 = System.currentTimeMillis();

        frame.validate();

        CONTENT_WIDTH = frame.getContentPane().getWidth();
        CONTENT_HEIGHT = frame.getContentPane().getHeight();

        player = new Fighter(null, 0, 0);

        // updating the game
        while (true) {
            try {
                // wait 16ms
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            // measure time for updating game logic
            long time1 = System.currentTimeMillis();
            // System.out.println("Time delay:" + (int)(time1-time0-16));
            if (activePanel instanceof HubGUI) {
                updateHub((int) (time1 - time0));
            } else if (activePanel instanceof GameGUI) {
                updateGame((int) (time1 - time0));
            }
            time0 = time1;

            // update the frame
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public void startHub() {
        this.activePanel = new HubGUI(this, player);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(activePanel);
        frame.revalidate();
    }

    public void startBattle() {
        this.activePanel = new GameGUI(this, player);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(activePanel);
        frame.revalidate();
    }

    private void updateHub(int time) {
        ((HubGUI) activePanel).update(time);
    }

    private void updateGame(int time) {
        ((GameGUI) activePanel).update(time);
        ((GameGUI) activePanel).frameLocation = frame.getLocationOnScreen();
        if (((GameGUI) activePanel).map.monsterCount == 0) {
            startHub();
        }
    }
/*
    public void restartHub() {
        startHub();
        HubGUI a = ((HubGUI) activePanel);
        Hub b = a.map;
        a.teleport = false;
        b.portalStage = 0;
        b.player.allowedToMove = true;
        b.respawn();
    }

 */

    public static void drawReflectImage(Image i, Graphics g, int x, int y) {
        g.drawImage(i, x + i.getWidth(null), y, -i.getWidth(null), i.getHeight(null), null);
    }

}