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
    private boolean teleport;
    private boolean clicked, playerChose; //playerChose für Beenden des Auswahlprozesses -> if (player in certain coordinates && !playerChose)
    private JButton standard, black, red, saffron, green, pink, skyblue, blue;
    private boolean inEscMenu;

    public HubGUI(final Main main, Player player) {
        this.setLayout(null);

        this.main = main;

        this.standard = new JButton("STANDARD");
        this.standard.setBackground(Color.WHITE);
        this.standard.setForeground(Color.BLACK);
        this.standard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(null);
                    } else {
                        ((Mage) player).loadImages(null);
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(null);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.black = new JButton("BLACK");
        this.black.setBackground(Color.BLACK);
        this.black.setForeground(Color.WHITE);
        this.black.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 if (!clicked) {
                     if (player instanceof Fighter) {
                         ((Fighter) player).loadImages(new float[]{0f, 0f, 0f, 1f});
                     } else {
                         ((Mage) player).loadImages(new float[]{0f, 0f, 0f, 1f});
                     }
                     clicked = true;
                 } else {
                     main.setRgba_projectiles(Main.COLORS.BLACK);
                     clicked = false;
                     playerChose = true;
                 }
             }
        });
        this.red = new JButton("RED");
        this.red.setBackground(Color.RED);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{1f, 0f, 0f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{1f, 0f, 0f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.RED);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.green = new JButton("GREEN");
        this.green.setBackground(Color.GREEN);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{0f, 1f, 0f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{0f, 1f, 0f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.GREEN);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.blue = new JButton("BLUE");
        this.blue.setBackground(Color.BLUE);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{0f, 0f, 1f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{0f, 0f, 1f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.BLUE);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.saffron = new JButton("SAFFRON");
        this.saffron.setBackground(Color.YELLOW);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{1f, 1f, 0f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{1f, 1f, 0f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.SAFFRON);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.skyblue = new JButton("SKYBLUE");
        this.skyblue.setBackground(new Color(0, 255, 255));
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{0f, 1f, 1f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{0f, 1f, 1f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.SKYBLUE);
                    clicked = false;
                    playerChose = true;
                }
            }
        });
        this.pink = new JButton("PINK");
        this.pink.setBackground(Color.PINK);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!clicked) {
                    if (player instanceof Fighter) {
                        ((Fighter) player).loadImages(new float[]{1f, 0f, 1f, 1f});
                    } else {
                        ((Mage) player).loadImages(new float[]{1f, 0f, 1f, 1f});
                    }
                    clicked = true;
                } else {
                    main.setRgba_projectiles(Main.COLORS.PINK);
                    clicked = false;
                    playerChose = true;
                }
            }
        });


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

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0, true), KeyEvent.VK_U + "Released");
        this.getActionMap().put(KeyEvent.VK_U + "Released", generateShopKeyAction());



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

    private Action generateInventoryKeyAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // change the players directions
                if (!inEscMenu) {
                    if(!map.shopkeeper.showShop)
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
                    if(!map.player.inventory.statsShown && map.player.x <200 && map.player.y > 340) {
                        map.shopkeeper.showShop = !map.shopkeeper.showShop;
                        map.player.allowedToMove = !map.player.allowedToMove;
                        map.player.isMoving = false;
                    }
                    else map.player.inventory.statsShown = false;
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

    public void paint(Graphics g) {

        // paint map
        this.map.paint(g);
        this.map.player.inventory.printStats(g);
        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(int time) {
        this.map.update(time);
        if (((this.map.player instanceof Mage && this.map.player.getY() <= 170) ||(this.map.player.getY() <= 150)) && this.map.player.getX() >= 420 && !this.teleport) {
            this.teleport = true;
            this.map.player.allowedToMove = false;
            this.map.portalStage = 1;
            this.map.portalState = 0.1;
            this.map.player.isMoving = false;
        }

        if(this.teleport && this.map.portalState > 5.9){
            main.startBattle();
            this.teleport = false;
            this.map.portalStage = 0;
            this.map.player.allowedToMove = true;
        }

    }

}
