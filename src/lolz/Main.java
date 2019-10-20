package lolz;


import lolz.Entity.Fighter;
import lolz.Entity.Mage;
import lolz.Entity.Player;
import lolz.GUI.GameGUI;
import lolz.GUI.HubGUI;
import lolz.GUI.MainMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    public static final int HEIGHT = 540;
    public static final int WIDTH = 960;
    public static final int TILE_SIZE = 50;

    public static int CONTENT_WIDTH, CONTENT_HEIGHT;
    public static int ENTITY_WIDTH = 75;
    public static int VIRTUAL_ENTITY_WIDTH = 45;
    public static Image goldImage;

    public static int[] mouseCoordinates = new int[2];

    private final JFrame frame;
    public JPanel activePanel;

    public Player player;

    public enum COLORS {BLACK, RED, SAFFRON, GREEN, PINK, SKYBLUE, BLUE}
    public static float[] rgba_projectiles;
    public static float[] rgba_player;

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
            goldImage = ImageIO.read(new File("res/inventory/gold.png"));
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

        this.player = new Fighter(null, 0, 0);

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

    public static void saveGame(Player player) {
        try {
            FileWriter fw = new FileWriter(new File("save.txt"), false);
            fw.write(player.getStats());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public void startHub() {
        this.activePanel = new HubGUI(this, player);
        player.allowedToMove = true;
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

    public static boolean gameIsSaved() {
        File f = new File("save.txt");
        return f.exists() && !f.isDirectory();
    }

    public void loadGame() {
        try {
            // parse saved game
            String saved = "";
            try {
                saved = new String(Files.readAllBytes(Paths.get("save.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] parsed = saved.split("\n");
            boolean isMage = Boolean.parseBoolean(parsed[0]);
            ArrayList<Integer> data = new ArrayList<>();
            for (int i = 1; i < parsed.length; i++) {
                data.add(Integer.parseInt(parsed[i]));
            }

            // generate player
            Player player;
            if (isMage) {
                player = new Mage(null, 0, 0);
            } else {
                player = new Fighter(null, 0, 0);
            }

            // set player stats
            player.level = data.get(0);
            player.exp = data.get(1);
            player.gold = data.get(2);
            data.remove(0);
            data.remove(0);
            data.remove(0);

            // set player inventory
            while (!data.isEmpty()) {
                // load item stats
                int index = data.get(0);
                int typ = 0;
                int itemNr = 0;

                // set player inventory
                player.inventory.equipment[index] = player.inventory.item[typ][itemNr];

                // delete item stats
                data.remove(0);
                data.remove(0);
                data.remove(0);
            }

            this.player = player;
            this.startHub();
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Fatal error while loading saved game!");
            System.exit(1);
        }
    }

    public void setRgba_projectiles(COLORS color) {
        if (color == null) {
            rgba_projectiles = null;
        } else {
            switch (color) {
                case BLACK:
                    rgba_projectiles = new float[]{0f, 0f, 0f, 1f};
                    break;

                case RED:
                    rgba_projectiles = new float[]{1f, 0f, 0f, 1f};
                    break;

                case GREEN:
                    rgba_projectiles = new float[]{0f, 01f, 0f, 1f};
                    break;

                case BLUE:
                    rgba_projectiles = new float[]{0f, 0f, 1f, 1f};
                    break;

                case SAFFRON:
                    rgba_projectiles = new float[]{1f, 1f, 0f, 1f};
                    break;

                case SKYBLUE:
                    rgba_projectiles = new float[]{0f, 1f, 1f, 1f};
                    break;

                case PINK:
                    rgba_projectiles = new float[]{1f, 0f, 1f, 1f};
                    break;

                default:
                    rgba_projectiles = null;
            }
        }
    }

    private void updateHub(int time) {
        ((HubGUI) activePanel).update(time);
    }

    private void updateGame(int time) {
        ((GameGUI) activePanel).update(time);
        ((GameGUI) activePanel).map.player.inventory.frameLocation = frame.getLocationOnScreen();
        if (((GameGUI) activePanel).map.player.goBack) {
            startHub();
        }

    }

    public static void drawReflectImage(Image i, Graphics g, int x, int y) {
        g.drawImage(i, x + i.getWidth(null), y, -i.getWidth(null), i.getHeight(null), null);
    }

}