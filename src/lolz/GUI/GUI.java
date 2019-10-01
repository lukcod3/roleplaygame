package lolz.GUI;

import lolz.Main;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GUI extends JPanel {
    private Image img;
    private Map map;
    private boolean statsShown;
    private MouseEvent e;
    public Point frameLocation;
    public int aktInventar;
    public boolean showButton;
    public Image[] inventoryImages;
    public int[] mouseCoordinates;

    //public Hub hub;
    public GUI() {
        // call super class
        super();
        mouseCoordinates = new int[2];
        // create map
        //hub = new Hub();
        map = new RandomMap();
        this.repaint();
        try {
            inventoryImages = new Image[] { ImageIO.read(new File("res/inventory/gegenstandAblegen_aus.PNG")), ImageIO.read(new File("res/inventory/gegenstandAblegen_an.PNG")) };
        }catch (Exception e){

        }
        //showButton.setIcon(new ImageIcon("res/inventory/gegenstandAblegen_aus.PNG"));
        //showButton.setRolloverIcon(new ImageIcon("res/inventory/gegenstandAblegen_an.PNG"));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                //System.out.println("x: " + ((int) (p.x + map.player.x) - (map.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE) / Main.TILE_SIZE +
                //        " | y: " + ((int) (p.y + map.player.y) - (map.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE) / Main.TILE_SIZE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==3&&statsShown){
                    if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=650&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=590&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=190&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=130){
                        aktInventar = 1;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=560&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=500&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=270&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=210){
                        aktInventar = 2;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=560&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=500&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=345&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=285){
                        aktInventar = 3;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=640&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=590&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=420&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=360){
                        aktInventar = 4;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=730&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=680&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=245&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=185){
                        aktInventar = 5;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=730&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=680&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=320&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=260){
                        aktInventar = 6;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=730&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=680&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=395&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=335){
                        aktInventar = 7;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=560&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=500&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=510&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=450){
                        aktInventar = 8;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=621&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=561&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=510&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=450){
                        aktInventar = 9;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=682&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=622&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=510&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=450){
                        aktInventar = 10;
                    }else if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=743&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=683&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=510&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=450){
                        aktInventar = 11;
                    }else{
                        aktInventar = 0;
                    }
                    if(aktInventar!=0){
                        showButton = true;
                        mouseCoordinates[0] = (int)(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX());
                        mouseCoordinates[1] = (int)(MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY());

                    }

                }else if(e.getButton()==1){
                    showButton = false;

                }
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


    private void printStats(Graphics g){
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
            g.drawString("Angriffsschaden....................." + this.map.player.attackdamage, 200, 160);
            g.drawString("Fähigkeitsstärke....................." + this.map.player.abilitypower, 200, 190);
            g.drawString("Rüstung..................................." + this.map.player.armor, 200, 220);
            g.drawString("Gold........................................" + this.map.player.gold, 200, 330);
            g.drawString("Level............." + this.map.player.level + "(" + this.map.player.exp + " XP/" + 200 + " XP)", 200, 360); // needs formula for maxXP

            if(this.map.player.turnedRight){
                if (this.map.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                    g.drawImage(this.map.player.img[2][(int) this.map.player.animation_state%5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null); // set player's animation to hit animation
                } else if (this.map.player.isMoving) {
                    g.drawImage(this.map.player.img[1][(int) this.map.player.animation_state%6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null);
                } else {
                    g.drawImage(this.map.player.img[0][(int) this.map.player.animation_state%4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), xPositionImageInventory, yPositionImageInventory, null);
                }
            }else{
                if (this.map.player.getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                    Main.drawReflectImage(this.map.player.img[2][(int) this.map.player.animation_state%5].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                } else if (this.map.player.isMoving) {
                    Main.drawReflectImage(this.map.player.img[1][(int) this.map.player.animation_state%6].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                } else {
                    Main.drawReflectImage(this.map.player.img[0][(int) this.map.player.animation_state%4].getScaledInstance(120, -1, Image.SCALE_DEFAULT), g, xPositionImageInventory, yPositionImageInventory);
                }
            }

            g.drawImage(this.map.player.inventoryImages[0][this.map.player.equipment[0]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 100, null);
            g.drawImage(this.map.player.inventoryImages[1][this.map.player.equipment[1]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 180, null);
            g.drawImage(this.map.player.inventoryImages[2][this.map.player.equipment[2]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 255, null);
            g.drawImage(this.map.player.inventoryImages[3][this.map.player.equipment[3]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 330, null);
            g.drawImage(this.map.player.inventoryImages[4][this.map.player.equipment[4]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 155, null);
            g.drawImage(this.map.player.inventoryImages[5][this.map.player.equipment[5]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 230, null);
            g.drawImage(this.map.player.inventoryImages[6][this.map.player.equipment[6]].getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 561, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 622, 420, null);
            g.drawImage(this.map.player.empty.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 683, 420, null);
            if(showButton&&aktInventar!=0){
                if(MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()<=mouseCoordinates[0] +255&&MouseInfo.getPointerInfo().getLocation().getX()-frameLocation.getX()>=mouseCoordinates[0] + 10&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()<=mouseCoordinates[1]+3&&MouseInfo.getPointerInfo().getLocation().getY()-frameLocation.getY()>=mouseCoordinates[1] - 30) {
                    g.drawImage(inventoryImages[1].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                }else{
                    g.drawImage(inventoryImages[0].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                }
            }
        }
    }

    public void update(int time) {
        // update the players position
        map.update(time);
    }
}
