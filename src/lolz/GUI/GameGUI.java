package lolz.GUI;

import lolz.Main;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GameGUI extends JPanel {
    private Image img;
    private Map map;
    private boolean statsShown, inEscMenu;
    private JButton exitButton;

    private MouseEvent e;
    public Point frameLocation;
    public int aktInventar;
    public boolean showButton;
    public Image[] inventoryImages;
    public int[] mouseCoordinates;
    public boolean readyForSwitch;
    public Item[][] item;
    public Image emptyInventory;

    //public Hub hub;
    public GameGUI() {
        // call super class
        this.setLayout(null);
        mouseCoordinates = new int[2];
        // create map
        //hub = new Hub();
        map = new RandomMap();

        this.repaint();
        this.setOpaque(false);

        // add exit button
        String str = "Exit game";
        int button_width = 300;
        int button_height = 60;
        exitButton = new JButton("Exit game");
        exitButton.setBounds((Main.WIDTH - button_width) / 2, 100, button_width, button_height);
        exitButton.setVisible(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(exitButton);
        // loads images for inventory
        try {
            inventoryImages = new Image[4];
            inventoryImages[0] = ImageIO.read(new File("res/inventory/gegenstandAblegen_aus.PNG"));
            inventoryImages[1] = ImageIO.read(new File("res/inventory/gegenstandAblegen_an.PNG"));
            inventoryImages[2] = ImageIO.read(new File("res/inventory/anlegen_aus.PNG"));
            inventoryImages[3] = ImageIO.read(new File("res/inventory/anlegen_an.PNG"));
            emptyInventory = ImageIO.read(new File("res/inventory/freiPlatz.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        item = new Item[7][5];
        for (int i = 0; i <= 4; i++) {
            item[0][i] = new Item(0, 0, 0, 0, 0, 0, i);
            item[1][i] = new Item(0, 0, 0, 0, 0, 1, i);
            item[2][i] = new Item(0, 0, 0, 0, 0, 2, i);
            item[3][i] = new Item(0, 0, 0, 0, 0, 3, i);
            item[4][i] = new Item(0, 0, 0, 0, 0, 4, i);
            item[5][i] = new Item(0, 0, 0, 0, 0, 5, i);
            item[6][i] = new Item(0, 0, 0, 0, 0, 6, i);
        }
        this.map.player.equipment = new Item[]{item[0][0], item[1][0], item[2][0], item[3][0], item[4][0], item[5][0], item[6][0], null, null, null, null};
        testGeneratedRandomItemSet();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                //System.out.println("x: " + ((int) (p.x + map.player.x) - (map.VIRTUAL_WIDTH / 2) * Main.TILE_SIZE) / Main.TILE_SIZE +
                //        " | y: " + ((int) (p.y + map.player.y) - (map.VIRTUAL_HEIGHT / 2) * Main.TILE_SIZE) / Main.TILE_SIZE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // check if courser is on inventory field
                if (e.getButton() == 3 && statsShown) {
                    if (isCourserInRectangle(590, 650, 130, 190) && map.player.equipment[0].itemNr != 0) {
                        aktInventar = 1;
                    } else if (isCourserInRectangle(500, 560, 210, 270) && map.player.equipment[1].itemNr != 0) {
                        aktInventar = 2;
                    } else if (isCourserInRectangle(500, 560, 285, 345) && map.player.equipment[2].itemNr != 0) {
                        aktInventar = 3;
                    } else if (isCourserInRectangle(590, 640, 360, 420) && map.player.equipment[3].itemNr != 0) {
                        aktInventar = 4;
                    } else if (isCourserInRectangle(680, 730, 185, 245) && map.player.equipment[4].itemNr != 0) {
                        aktInventar = 5;
                    } else if (isCourserInRectangle(680, 730, 260, 320) && map.player.equipment[5].itemNr != 0) {
                        aktInventar = 6;
                    } else if (isCourserInRectangle(680, 730, 335, 395) && map.player.equipment[6].itemNr != 0) {
                        aktInventar = 7;
                    } else if (isCourserInRectangle(500, 560, 450, 510) && map.player.equipment[7] != null) {
                        aktInventar = 8;
                    } else if (isCourserInRectangle(561, 621, 450, 510) && map.player.equipment[8] != null) {
                        aktInventar = 9;
                    } else if (isCourserInRectangle(622, 682, 450, 510) && map.player.equipment[9] != null) {
                        aktInventar = 10;
                    } else if (isCourserInRectangle(683, 743, 450, 510) && map.player.equipment[10] != null) {
                        aktInventar = 11;

                    } else {
                        aktInventar = 0;
                    }
                    if (aktInventar != 0) {
                        showButton = true;
                        mouseCoordinates[0] = (int) (MouseInfo.getPointerInfo().getLocation().getX() - frameLocation.getX());
                        mouseCoordinates[1] = (int) (MouseInfo.getPointerInfo().getLocation().getY() - frameLocation.getY());
                    }
                // switches Item places
                } else if (e.getButton() == 1) {
                    if (readyForSwitch) {
                        readyForSwitch = false;
                        showButton = false;
                        if (aktInventar > 0 && aktInventar < 8) {
                            for (int i = 0; i < 4; i++) {
                                if (map.player.equipment[7 + i] == null) {
                                    map.player.equipment[7 + i] = map.player.equipment[aktInventar - 1];
                                    map.player.equipment[aktInventar - 1] = item[aktInventar - 1][0];
                                    break;
                                }
                            }

                        } else {
                            Item hilf = map.player.equipment[aktInventar - 1];
                            if (map.player.equipment[hilf.typ] == item[hilf.typ][0]) {
                                map.player.equipment[aktInventar - 1] = null;
                            } else {
                                map.player.equipment[aktInventar - 1] = map.player.equipment[hilf.typ];
                            }
                            map.player.equipment[hilf.typ] = hilf;
                        }
                    } else {
                        showButton = false;
                    }
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

        //add the key binding for the players attack
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false), KeyEvent.VK_J + "Pressed");
        this.getActionMap().put(KeyEvent.VK_J + "Pressed", generateAttackKeyAction());

        // key bindings for player stats
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, false), KeyEvent.VK_I + "Pressed");
        this.getActionMap().put(KeyEvent.VK_I + "Pressed", generateInventoryKeyAction());

        // key bindings for esc menu
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), KeyEvent.VK_ESCAPE + "Pressed");
        this.getActionMap().put(KeyEvent.VK_ESCAPE + "Pressed", generateEscapeAction());
    }

    private Action generateMoveKeyAction(final int dir, final boolean pressed) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inEscMenu) {
                    // change the players directions
                    map.player.directions[dir] = pressed;
                }
            }
        };
    }

    private Action generateAttackKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inEscMenu && !map.player.getHitting()) {
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
                if (!inEscMenu) {
                    statsShown = !statsShown;
                }
            }
        };
    }

    private Action generateEscapeAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
                if (statsShown) {
                    statsShown = false;
                } else {
                    inEscMenu = !inEscMenu;
                    if (inEscMenu) {
                        addEscItems();
                    } else {
                        removeEscItems();
                    }
                }
            }
        };
    }


    public void paint(Graphics g) {
        // black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.CONTENT_WIDTH, Main.CONTENT_HEIGHT);

        // paint map
        map.paint(g);

        // paint overlay interfaces

        //fill health bar
        g.setColor(Color.GREEN);
        g.fillRoundRect(25, 25, (int) (200 * ((double) this.map.player.health / (double) this.map.player.getMaxHealth())), 35, 15, 15);

        // draw border of health bar
        g.setColor(Color.WHITE);
        g.drawRoundRect(25, 25, 200, 35, 15, 15);

        g.setColor(Color.BLACK);
        this.printStats(g);
        Font font = new Font("SansSerif", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.WHITE);
        if((double) this.map.player.health / (double) this.map.player.getMaxHealth() <= 0.1) {
            Color lowColor = new Color(100, 0, 0, 60);
            g.setColor(lowColor);
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
            g.setColor(Color.red);
        }
        // draw health values in somewhat centered position
        g.drawString("" + this.map.player.health, 25 + 200 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.health) / 2, 52);
        g.drawString("/", 25 + 200 / 2 - g.getFontMetrics().stringWidth("/") / 2, 52);
        g.drawString("" + this.map.player.maxHealth, 25 + 200 * 3 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.maxHealth) / 2, 52);

        g.setColor(Color.WHITE);
        g.drawString("Monster: " + this.map.monsterCount, Main.WIDTH - (g.getFontMetrics().stringWidth("Monster: " + this.map.monsterCount) + 50), 50);

        this.printEscMenu(g);

        super.paint(g);

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    private void addEscItems() {
        this.exitButton.setVisible(true);
        this.revalidate();
    }

    private void removeEscItems() {
        this.exitButton.setVisible(false);
        this.revalidate();
    }

    private void printEscMenu(Graphics g) {
        int border_x = 40;
        int border_y = 30;
        if (inEscMenu) {
            Color myColor = new Color(56, 56, 56, 230);
            Font titleF = new Font("SansSerif", Font.BOLD, 25);
            Font statsF = new Font("SansSerif", Font.PLAIN, 15);
            g.drawRect(border_x, border_y, Main.CONTENT_WIDTH - 2 * border_x, Main.CONTENT_HEIGHT - 2 * border_y);
            g.setColor(myColor);
            g.fillRect(border_x, border_y, Main.CONTENT_WIDTH - 2 * border_x, Main.CONTENT_HEIGHT - 2 * border_y);
            g.setFont(titleF);
            g.setColor(Color.white);
            String str = "Menu";
            g.drawString(str, (Main.CONTENT_WIDTH - g.getFontMetrics().stringWidth(str)) / 2, 80);
            g.setFont(statsF);
        }
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

            g.drawImage(this.map.player.equipment[0].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 100, null);
            g.drawImage(this.map.player.equipment[1].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 180, null);
            g.drawImage(this.map.player.equipment[2].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500, 255, null);
            g.drawImage(this.map.player.equipment[3].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 590, 330, null);
            g.drawImage(this.map.player.equipment[4].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 155, null);
            g.drawImage(this.map.player.equipment[5].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 230, null);
            g.drawImage(this.map.player.equipment[6].image.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 680, 305, null);
            for (int i = 0; i <= 3; i++) {
                Image a;
                if (this.map.player.equipment[i + 7] == null) {
                    a = emptyInventory;
                } else {
                    //System.out.println((this.map.player.equipment[i + 7]-1) / 4+ " "+ (this.map.player.equipment[i + 7]-1) % 4);
                    a = this.map.player.equipment[i + 7].image;
                }
                g.drawImage(a.getScaledInstance(60, -1, Image.SCALE_DEFAULT), 500 + (i * 61), 420, null);
            }
            if (showButton && aktInventar > 0 && aktInventar < 8) {
                if (isCourserInRectangle(mouseCoordinates[0] + 10, mouseCoordinates[0] + 255, mouseCoordinates[1] - 30, mouseCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[1].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                    readyForSwitch = true;
                } else {
                    g.drawImage(inventoryImages[0].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                    readyForSwitch = false;
                }
            } else if (showButton && aktInventar > 7 && aktInventar < 12) {
                if (isCourserInRectangle(mouseCoordinates[0] + 10, mouseCoordinates[0] + 255, mouseCoordinates[1] - 30, mouseCoordinates[1] + 3)) {
                    g.drawImage(inventoryImages[3].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                    readyForSwitch = true;
                } else {
                    g.drawImage(inventoryImages[2].getScaledInstance(235, -1, Image.SCALE_DEFAULT), mouseCoordinates[0] + 10, mouseCoordinates[1] - 60, null);
                    readyForSwitch = false;
                }
            } else {
                readyForSwitch = false;
            }
        }
    }

    public void update(int time) {
        //System.out.println(this.getX());
        if (!inEscMenu) {
            // update map
            map.update(time);
        }
    }

    public boolean isCourserInRectangle(double x1, double x2, double y1, double y2) {
        return (MouseInfo.getPointerInfo().getLocation().getX() - frameLocation.getX() <= x2 && MouseInfo.getPointerInfo().getLocation().getX() - frameLocation.getX() >= x1 && MouseInfo.getPointerInfo().getLocation().getY() - frameLocation.getY() <= y2 && MouseInfo.getPointerInfo().getLocation().getY() - frameLocation.getY() >= y1);
    }
    public void testGeneratedRandomItemSet(){
        for(int i = 0; i<= 6; i++){
            this.map.player.equipment[i] = item[i][(int)(Math.random()*5)];
        }
        this.map.player.equipment[7] = item[(int) (Math.random()*7)][(int) (Math.random()*4)+1];
        this.map.player.equipment[8] = item[(int) (Math.random()*7)][(int) (Math.random()*4)+1];
    }
}
