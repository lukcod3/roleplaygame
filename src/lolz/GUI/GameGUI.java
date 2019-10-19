package lolz.GUI;

import lolz.Entity.Fighter;
import lolz.Entity.Player;
import lolz.Main;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;

public class GameGUI extends JPanel {
    public Map map;
    private Main main;
    private boolean inEscMenu;
    private JButton exitButton, hubButton;


    //public Hub hub;
    public GameGUI(Main main, Player player) {
        // call super class
        this.setLayout(null);

        // create map
        //hub = new Hub();
        this.map = new RandomMap(player);
        this.main = main;

        this.repaint();
        this.setOpaque(false);

        // add exit button
        int button_width = 300;
        int button_height = 60;
        this.exitButton = new JButton("Exit game");
        this.exitButton.setBounds((Main.WIDTH - button_width) / 2, 100, button_width, button_height);
        this.exitButton.setVisible(false);
        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(this.exitButton);

        // add exit button
        this.hubButton = new JButton("Back to Hub");
        this.hubButton.setBounds((Main.WIDTH - button_width) / 2, 200, button_width, button_height);
        this.hubButton.setVisible(false);
        this.hubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startHub();
            }
        });
        this.add(this.hubButton);

        // loads images for inventory




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
        this.getActionMap().put(KeyEvent.VK_J + "Pressed", generateAttackKeyAction(true));
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, true), KeyEvent.VK_J + "Released");
        this.getActionMap().put(KeyEvent.VK_J + "Released", generateAttackKeyAction(false));

        // key bindings for player stats
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, false), KeyEvent.VK_I + "Pressed");
        this.getActionMap().put(KeyEvent.VK_I + "Pressed", generateInventoryKeyAction());

        // key bindings for esc menu
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), KeyEvent.VK_ESCAPE + "Pressed");
        this.getActionMap().put(KeyEvent.VK_ESCAPE + "Pressed", generateEscapeAction());

        // key bindings for debugging mode
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), KeyEvent.VK_F5 + "Pressed");
        this.getActionMap().put(KeyEvent.VK_F5 + "Pressed", generateDebuggingAction());

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                map.player.inventory.updateInventory(e);
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
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                map.player.inventory.mouseCoordinates[0] = e.getX();
                map.player.inventory.mouseCoordinates[1] = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                map.player.inventory.mouseCoordinates[0] = e.getX();
                map.player.inventory.mouseCoordinates[1] = e.getY();
            }
        });
    }

    private Action generateMoveKeyAction(final int dir, final boolean pressed) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((!inEscMenu && map.player.allowedToMove) || !pressed) {
                        // change the players directions
                        map.player.directions[dir] = pressed;
                }
            }
        };
    }

    private Action generateAttackKeyAction(boolean pressed) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pressed) {
                    if (!inEscMenu && !map.player.getHitting()) {
                        map.player.setHitting(true);
                    }
                    map.player.allowedToMove = false;
                } else {
                    map.player.allowedToMove = true;
                }
                map.player.allowedToMove = map.player instanceof Fighter || map.player.allowedToMove;
                map.player.holdAttack = !map.player.allowedToMove;
            }
        };
    }

    private Action generateInventoryKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                if (!inEscMenu) {
                    map.player.inventory.statsShown = !map.player.inventory.statsShown;
                }
            }
        };
    }

    private Action generateEscapeAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
                if (map.player.inventory.statsShown) {
                    map.player.inventory.statsShown = false;
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

    private Action generateDebuggingAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (map.debugging) {
                    for (int y = 0; y < map.VIRTUAL_HEIGHT; y++) {
                        for (int x = 0; x < map.VIRTUAL_WIDTH; x++) {
                            map.tiles[y][x].reconstructBase();
                        }
                    }
                }
                map.debugging = !map.debugging;
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



        this.map.player.inventory.printStats(g);
        // draw player health rectangles
        Font font = new Font("SansSerif", Font.BOLD, 25);
        g.setFont(font);
        drawLevel(g);
        //g.setColor(new Color(255, 255, 255, 50));
        drawHealth(g);
        if ((double) this.map.player.health / (double) this.map.player.getMaxHealth() <= 0.1) {
            Color lowColor = new Color(100, 0, 0, 60);
            g.setColor(lowColor);
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
            g.setColor(Color.red);
        }




        this.printEscMenu(g);

        super.paint(g);

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    private void addEscItems() {
        this.exitButton.setVisible(true);
        this.hubButton.setVisible(true);
        this.revalidate();
    }

    private void removeEscItems() {
        this.exitButton.setVisible(false);
        this.hubButton.setVisible(false);
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

    public void drawLevel(Graphics g){

        g.drawString(""+this.map.player.level, 20, 95);
        g.setColor(new Color(0, 255, 0, 255));
        g.fillRect(40, 83, (int)(100 *((double)this.map.player.exp /  (double)(90 + 10 * this.map.player.level * this.map.player.level))), 5);

        // draw border of health bar
        g.setColor(new Color(255, 255, 255, 255));
        g.drawRect(40, 83, 100, 5);
    }

    public void drawHealth(Graphics g){
        //fill health bar
        g.setColor(new Color(0, 125, 0, 50));
        g.fillRoundRect(25, 25, (int) (200 * ((double) this.map.player.health / (double) this.map.player.getMaxHealth())), 35, 15, 15);

        // draw border of health bar
        g.setColor(new Color(255, 255, 255, 50));
        g.drawRoundRect(25, 25, 200, 35, 15, 15);

        // draw health values in somewhat centered position
        g.drawString("" + this.map.player.health, 25 + 200 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.health) / 2, 52);
        g.setColor(new Color(255, 255, 255, 50));
        g.drawString("/", 25 + 200 / 2 - g.getFontMetrics().stringWidth("/") / 2, 52);
        g.drawString("" + this.map.player.maxHealth, 25 + 200 * 3 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.maxHealth) / 2, 52);

        Font font = new Font("Avant Garde", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Monsters: " + this.map.monsterCount, Main.WIDTH - (g.getFontMetrics().stringWidth("Monsters: " + this.map.monsterCount) + 50), 50);
    }

    public void update(int time) {
        //System.out.println(this.getX());
        if (!inEscMenu) {
            // update map
            map.update(time);
        }
    }




}
