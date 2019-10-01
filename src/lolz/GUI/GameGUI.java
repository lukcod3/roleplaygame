package lolz.GUI;

import lolz.Main;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameGUI extends JPanel {
    private Image img;
    private Map map;
    private boolean statsShown;

    //public Hub hub;
    public GameGUI() {
        // call super class
        super();
        // create map
        //hub = new Hub();
        map = new RandomMap();

        this.repaint();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                System.out.println("x: " + ((int) (p.x + map.player.x) - (map.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE) / Main.TILE_SIZE +
                        " | y: " + ((int) (p.y + map.player.y) - (map.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE) / Main.TILE_SIZE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // add the key bindings for player movement
        char[] keys = {'W', 'A', 'S', 'D'};
        for (int i = 0; i < 4; i++) {
            char c = keys[i];

            // add the key binding for pressing and releasing wasd keys
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c, 0, false), c + "Pressed");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c, 0, true), c + "Released");

            this.getActionMap().put(c + "Pressed", generateMoveKeyAction(i, true));
            this.getActionMap().put(c + "Released", generateMoveKeyAction(i, false));
        }

        //add the key binding for the players attack (with the space key)
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false), KeyEvent.VK_J + "Pressed");
        this.getActionMap().put(KeyEvent.VK_J + "Pressed", generateAttackKeyAction());

        // key bindings for player stats
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, false), KeyEvent.VK_I + "Pressed");
        this.getActionMap().put(KeyEvent.VK_I + "Pressed", generateInventoryKeyAction());
    }

    private Action generateMoveKeyAction(final int dir, final boolean pressed) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                map.player.directions[dir] = pressed;
            }
        };
    }

    private Action generateAttackKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!map.player.getHitting()) {
                    map.player.setHitting(true);
                }
            }
        };
    }

    private Action generateInventoryKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                statsShown = !statsShown;
            }
        };
    }


    public void paint(Graphics g) {
        // paint map
        map.paint(g);

        // paint stats
        this.printStats(g);

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }


    private void printStats(Graphics g) {
        int xPositionImageInventory = 560;
        int yPositionImageInventory = 185;

        // paint player stats
        if (statsShown) {

            Color myColor = new Color(56, 56, 56, 165);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            Font statsF = new Font("SansSerif", Font.PLAIN, 15);
            g.drawRect(150, 50, 660, 440);
            g.setColor(myColor);
            g.fillRect(150, 50, 660, 440);
            g.setFont(titleF);
            g.setColor(Color.white);
            g.drawString("Profil", 260, 80);
            g.drawString("Inventar", 570, 80);
            g.setFont(statsF);
            g.drawString("Leben............................(" + this.map.player.health + "/" + this.map.player.maxHealth + ")", 200, 130);
            g.drawString("Angriffsschaden....................." + this.map.player.damage, 200, 160);
            g.drawString("Fähigkeitsstärke....................." + this.map.player.abilitypower, 200, 190);
            g.drawString("Rüstung..................................." + this.map.player.armor, 200, 220);
            g.drawString("Gold........................................" + this.map.player.gold, 200, 330);
            g.drawString("Level............." + this.map.player.level + "(" + this.map.player.exp + " XP/" + 200 + " XP)", 200, 360); // needs formula for maxXP

            if (this.map.player.turnedRight) {
                if (this.map.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                    g.drawImage(this.map.player.img[2][(int) this.map.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null); // set player's animation to hit animation
                } else if (this.map.player.isMoving) {
                    g.drawImage(this.map.player.img[1][(int) this.map.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null);
                } else {
                    g.drawImage(this.map.player.img[0][(int) this.map.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null);
                }
            } else {
                if (this.map.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                    Main.drawReflectImage(this.map.player.img[2][(int) this.map.player.animation_state % 5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                } else if (this.map.player.isMoving) {
                    Main.drawReflectImage(this.map.player.img[1][(int) this.map.player.animation_state % 6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                } else {
                    Main.drawReflectImage(this.map.player.img[0][(int) this.map.player.animation_state % 4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                }
            }
            /*
            if (moving) {
                g.drawImage((img[1][(int) this.animation_state]).getScaledInstance(120, -1, Image.SCALE_DEFAULT), 560, 110, null);
            } else {
                g.drawImage((img[0][(int) this.animation_state]).getScaledInstance(120, -1, Image.SCALE_DEFAULT), 560, 110, null);
            }
            */
            g.drawImage(this.map.player.inventoryImages[0][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 100, null);
            g.drawImage(this.map.player.inventoryImages[1][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 180, null);
            g.drawImage(this.map.player.inventoryImages[2][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 255, null);
            g.drawImage(this.map.player.inventoryImages[3][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 330, null);
            g.drawImage(this.map.player.inventoryImages[4][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 155, null);
            g.drawImage(this.map.player.inventoryImages[5][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 230, null);
            g.drawImage(this.map.player.inventoryImages[6][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            g.drawImage(this.map.player.inventoryImages[6][0].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 561, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 622, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 683, 420, null);

        }
    }

    public void update(int time) {
        // update the players position
        map.update(time);
    }
}