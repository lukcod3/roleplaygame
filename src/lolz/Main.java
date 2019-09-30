package lolz;

import lolz.GUI.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Main {
    public static final int HEIGHT = 540;
    public static final int WIDTH = 960;
    public static final int TILE_SIZE = 50;

    private GUI gui;


    private Main() {

        // setup main frame
        final JFrame frame = new JFrame();

        // main game jpanel
        gui = new GUI();
        frame.add(gui);


        frame.setTitle("RPG-lol");
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
            updateGame((int) (time1 - time0));
            time0 = time1;

            // update the frame
            frame.repaint();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private void updateGame(int time) {
        gui.update(time);
    }
    public static void drawReflectImage(Image i, Graphics g, int x, int y){
        g.drawImage(i, x+i.getWidth(null), y, -i.getWidth(null), i.getHeight(null), null);
    }
}
