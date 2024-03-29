package lolz.GUI;

import lolz.Entity.Fighter;
import lolz.Entity.Mage;
import lolz.Entity.Player;
import lolz.Main;
import lolz.Maps.Hub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HubGUI extends JPanel {

    private Main main;
    public Hub map;
    private JButton standard, black, red, saffron, green, pink, skyblue, blue, exitButton, characterwahl, mage, fighter, returnB;
    private boolean teleport, mageClicked, inEscMenu, inCharMenu, inColorMenu;

    public HubGUI(final Main main, Player player) {
        this.setLayout(null);

        this.main = main;


        this.repaint();
        this.setOpaque(false);

        int button_width = 300;
        int button_height = 60;
        int color_button_width = 150;

        this.standard = new JButton("STANDARD");
        this.standard.setBounds(Main.WIDTH / 5 - color_button_width / 2, Main.HEIGHT / 2 - button_height / 2, color_button_width, button_height);
        this.standard.setVisible(false);
        this.standard.setBackground(Color.WHITE);
        this.standard.setForeground(Color.BLACK);
        this.standard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = null;
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(null);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.standard);
        this.black = new JButton("BLACK");
        this.black.setBounds(Main.WIDTH * 2 / 5 - color_button_width / 2, Main.HEIGHT / 2 - button_height / 2, color_button_width, button_height);
        this.black.setVisible(false);
        this.black.setBackground(Color.BLACK);
        this.black.setForeground(Color.WHITE);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{0f, 0f, 0f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.BLACK);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.black);
        this.red = new JButton("RED");
        this.red.setBounds(Main.WIDTH * 3 / 5 - color_button_width / 2, Main.HEIGHT / 2 - button_height / 2, color_button_width, button_height);
        this.red.setVisible(false);
        this.red.setBackground(Color.RED);
        this.red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{1f, 0f, 0f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.RED);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.red);
        this.green = new JButton("GREEN");
        this.green.setBounds(Main.WIDTH * 4 / 5 - color_button_width / 2, Main.HEIGHT / 2 - button_height / 2, color_button_width, button_height);
        this.green.setVisible(false);
        this.green.setBackground(Color.GREEN);
        this.green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{0f, 1f, 0f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.GREEN);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.green);
        this.blue = new JButton("BLUE");
        this.blue.setBounds(Main.WIDTH / 5 - color_button_width / 2, Main.HEIGHT * 3 / 4 - button_height / 2, color_button_width, button_height);
        this.blue.setVisible(false);
        this.blue.setBackground(Color.BLUE);
        this.blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{0f, 0f, 1f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.BLUE);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.blue);
        this.saffron = new JButton("SAFFRON");
        this.saffron.setBounds(Main.WIDTH * 2 / 5 - color_button_width / 2, Main.HEIGHT * 3 / 4 - button_height / 2, color_button_width, button_height);
        this.saffron.setVisible(false);
        this.saffron.setBackground(Color.YELLOW);
        this.saffron.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{1f, 1f, 0f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.SAFFRON);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.saffron);
        this.skyblue = new JButton("SKYBLUE");
        this.skyblue.setBounds(Main.WIDTH * 3 / 5 - color_button_width / 2, Main.HEIGHT * 3 / 4 - button_height / 2, color_button_width, button_height);
        this.skyblue.setVisible(false);
        this.skyblue.setBackground(new Color(0, 255, 255));
        this.skyblue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{0f, 1f, 1f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.SKYBLUE);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.skyblue);
        this.pink = new JButton("PINK");
        this.pink.setBounds(Main.WIDTH * 4 / 5 - color_button_width / 2, Main.HEIGHT * 3 / 4 - button_height / 2, color_button_width, button_height);
        this.pink.setVisible(false);
        this.pink.setBackground(Color.PINK);
        this.pink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!mageClicked) {
                    Main.rgba_player = new float[]{1f, 0f, 1f, 1f};
                    if (main.player instanceof Fighter) {
                        ((Fighter) main.player).loadImages(Main.rgba_player);
                        removeColorButtons();
                    } else {
                        ((Mage) main.player).loadImages(Main.rgba_player);
                        mageClicked = true;
                    }
                } else {
                    main.setRgba_projectiles(Main.COLORS.PINK);
                    mageClicked = false;
                    removeColorButtons();
                }
            }
        });
        this.add(this.pink);

        // add exit button
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

        // characterwahl
        this.characterwahl = new JButton("Choose character");
        this.characterwahl.setBounds((Main.WIDTH - button_width) / 2, 200, button_width, button_height);
        this.characterwahl.setVisible(false);
        this.characterwahl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inCharMenu = !inCharMenu;
                if (inCharMenu) {
                    addCharItems();
                } else {
                    removeCharItems();
                }
            }
        });
        this.add(this.characterwahl);

        this.returnB = new JButton("return");
        this.returnB.setBounds((Main.WIDTH - button_width) / 2, 300, button_width, button_height);
        this.returnB.setVisible(false);
        this.returnB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCharItems();
                addEscItems();

            }
        });
        this.add(this.characterwahl);

        //auswahl Mage/Fighter
        this.mage = new JButton("Mage");
        this.mage.setBounds((Main.WIDTH - button_width) / 2, 100, button_width, button_height);
        this.mage.setVisible(false);
        this.mage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mage newPlayer = new Mage(map, (int) map.player.getX(), (int) map.player.getY());
                newPlayer.loadImages(null);
                addNewPlayer(newPlayer);
            }
        });
        this.add(this.mage);

        this.fighter = new JButton("Fighter");
        this.fighter.setBounds((Main.WIDTH - button_width) / 2, 200, button_width, button_height);
        this.fighter.setVisible(false);
        this.fighter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fighter newPlayer = new Fighter(map, (int) map.player.getX(), (int) map.player.getY());
                newPlayer.loadImages(null);
                addNewPlayer(newPlayer);
            }
        });
        this.add(this.fighter);


        //create map
        this.map = new Hub(player);

        teleport = false;
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
        // inventory key binding
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, true), KeyEvent.VK_I + "Released");
        this.getActionMap().put(KeyEvent.VK_I + "Released", generateInventoryKeyAction());

        // shop key binding
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0, true), KeyEvent.VK_U + "Released");
        this.getActionMap().put(KeyEvent.VK_U + "Released", generateShopKeyAction());

        // save game key binding
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "saveGame");
        this.getActionMap().put("saveGame", generateSaveAction());

        // key bindings for esc menu
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), KeyEvent.VK_ESCAPE + "Pressed");
        this.getActionMap().put(KeyEvent.VK_ESCAPE + "Pressed", generateEscapeAction());


        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                map.player.inventory.updateInventory(e);
                map.shopkeeper.updateShop(e);
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
                Main.mouseCoordinates[0] = e.getX();
                Main.mouseCoordinates[1] = e.getY();
                map.player.inventory.updateInventory(e);
                map.shopkeeper.updateShop(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Main.mouseCoordinates[0] = e.getX();
                Main.mouseCoordinates[1] = e.getY();
                map.player.inventory.updateInventory(e);
                map.shopkeeper.updateShop(e);
            }
        });
    }

    private Action generateInventoryKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                if (!inEscMenu) {
                    if (!map.shopkeeper.showShop)
                        map.player.inventory.statsShown = !map.player.inventory.statsShown;
                    else {
                        map.shopkeeper.showShop = false;
                        map.player.allowedToMove = true;
                    }
                }
            }
        };
    }

    private Action generateShopKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                if (!inEscMenu) {
                    if (!map.player.inventory.statsShown && map.player.x < 200 && map.player.y > 340) {
                        map.shopkeeper.showShop = !map.shopkeeper.showShop;
                        map.player.allowedToMove = !map.player.allowedToMove;
                        map.player.isMoving = false;
                    } else map.player.inventory.statsShown = false;
                }
            }
        };
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

    private Action generateSaveAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.saveGame(map.player);
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

    public void paint(Graphics g) {
        // paint map
        this.map.paint(g);
        this.map.player.inventory.printStats(g);
        Font font = new Font("SansSerif", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.white);
        drawLevel(g);
        drawHealth(g);
        this.printEscMenu(g);
        super.paint(g);
        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(int time) {
        this.map.update(time);
        if (((this.map.player instanceof Mage && this.map.player.getY() <= 170) || (this.map.player.getY() <= 150)) && this.map.player.getX() >= 420 && !this.teleport) {
            this.teleport = true;
            this.map.player.allowedToMove = false;
            this.map.portalStage = 1;
            this.map.portalState = 0.1;
            this.map.player.isMoving = false;
        }

        if (this.teleport && this.map.portalState > 5.9) {
            main.startBattle();
            this.teleport = false;
            this.map.portalStage = 0;
            this.map.player.allowedToMove = true;
        }

    }

    private void drawLevel(Graphics g) {
        g.drawString("" + this.map.player.level, 20, 95);
        g.setColor(new Color(0, 255, 0, 255));
        g.fillRect(40, 83, (int) (100 * ((double) this.map.player.exp / (double) (90 + 10 * this.map.player.level * this.map.player.level))), 5);

        // draw border of health bar
        g.setColor(new Color(255, 255, 255, 255));
        g.drawRect(40, 83, 100, 5);
    }

    private void drawHealth(Graphics g) {
        //fill health bar
        g.setColor(new Color(0, 125, 0, 255));
        g.fillRoundRect(25, 25, (int) (200 * ((double) this.map.player.health / (double) this.map.player.getMaxHealth())), 35, 15, 15);

        // draw border of health bar
        g.setColor(new Color(255, 255, 255, 255));
        g.drawRoundRect(25, 25, 200, 35, 15, 15);

        // draw health values in somewhat centered position
        g.drawString("" + this.map.player.health, 25 + 200 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.health) / 2, 52);
        g.setColor(new Color(255, 255, 255, 255));
        g.drawString("/", 25 + 200 / 2 - g.getFontMetrics().stringWidth("/") / 2, 52);
        g.drawString("" + this.map.player.maxHealth, 25 + 200 * 3 / 4 - g.getFontMetrics().stringWidth("" + this.map.player.maxHealth) / 2, 52);

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
            if (inColorMenu) {
                if (!mageClicked) {
                    g.drawString("Wählen Sie die Farbe Ihres Helden:", Main.WIDTH / 2 - g.getFontMetrics().stringWidth("Wählen Sie die Farbe Ihres Helden:") / 2, Main.HEIGHT / 4);
                } else {
                    g.drawString("Wählen Sie die Farbe Ihres Projektils:", Main.WIDTH / 2 - g.getFontMetrics().stringWidth("Wählen Sie die Farbe Ihres Projektils:") / 2, Main.HEIGHT / 4);
                }
            }
        }


    }

    private void addEscItems() {
        this.exitButton.setVisible(true);
        this.characterwahl.setVisible(true);
        this.revalidate();
    }


    private void removeEscItems() {
        this.exitButton.setVisible(false);
        this.characterwahl.setVisible(false);
        if (this.inCharMenu) {
            this.removeCharItems();
            this.removeColorButtons();
        }
        this.revalidate();
    }


    private void addCharItems() {
        this.mage.setVisible(true);
        this.fighter.setVisible(true);
        this.characterwahl.setVisible(false);
        this.exitButton.setVisible(false);
        this.returnB.setVisible(true);
        this.revalidate();
    }

    private void removeCharItems() {
        this.mage.setVisible(false);
        this.fighter.setVisible(false);
        this.returnB.setVisible(false);
        this.revalidate();
    }

    private void addColorButtons() {
        inColorMenu = true;
        this.standard.setVisible(true);
        this.black.setVisible(true);
        this.red.setVisible(true);
        this.green.setVisible(true);
        this.blue.setVisible(true);
        this.saffron.setVisible(true);
        this.skyblue.setVisible(true);
        this.pink.setVisible(true);
        this.revalidate();
    }

    private void removeColorButtons() {
        inColorMenu = false;
        this.standard.setVisible(false);
        this.black.setVisible(false);
        this.red.setVisible(false);
        this.green.setVisible(false);
        this.blue.setVisible(false);
        this.saffron.setVisible(false);
        this.skyblue.setVisible(false);
        this.pink.setVisible(false);
        inCharMenu = false;
        inEscMenu = false;
        this.revalidate();
    }

    private void addNewPlayer(Player newPlayer) {
        newPlayer.level = map.player.level;
        newPlayer.exp = map.player.exp;
        newPlayer.gold = map.player.gold;
        newPlayer.inventory = map.player.inventory;
        newPlayer.inventory.player = newPlayer;
        newPlayer.health = map.player.health;
        map.player = newPlayer;
        main.player = newPlayer;
        removeCharItems();
        addColorButtons();
        this.map.shopkeeper.player = newPlayer;
    }

}
