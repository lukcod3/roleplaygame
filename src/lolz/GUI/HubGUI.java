package lolz.GUI;

import lolz.Entity.Mage;
import lolz.Entity.Player;
import lolz.Main;
import lolz.Maps.Hub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HubGUI extends JPanel {

    private Main main;
    public Hub map;
    private boolean teleport;
    private JButton black, red, saffron, green, pink, skyblue, blue;

    public HubGUI(final Main main, Player player) {

        this.main = main;

        this.black = new JButton("BLACK");
        this.black.setBackground(Color.BLACK);
        this.black.setForeground(Color.WHITE);
        this.black.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                     main.player_color = Main.COLORS.BLACK;
                 } else {
                     main.projectiles_color = Main.COLORS.BLACK;
                 }
             }
        });
        this.red = new JButton("RED");
        this.red.setBackground(Color.RED);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.RED;
                } else {
                    main.projectiles_color = Main.COLORS.RED;
                }
            }
        });
        this.saffron = new JButton("SAFFRON");
        this.saffron.setBackground(Color.YELLOW);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.SAFFRON;
                } else {
                    main.projectiles_color = Main.COLORS.SAFFRON;
                }
            }
        });
        this.green = new JButton("GREEN");
        this.green.setBackground(Color.GREEN);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.GREEN;
                } else {
                    main.projectiles_color = Main.COLORS.GREEN;
                }
            }
        });
        this.pink = new JButton("PINK");
        this.pink.setBackground(Color.PINK);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.PINK;
                } else {
                    main.projectiles_color = Main.COLORS.PINK;
                }
            }
        });
        this.skyblue = new JButton("SKYBLUE");
        this.skyblue.setBackground(new Color(0, 255, 255));
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.SKYBLUE;
                } else {
                    main.projectiles_color = Main.COLORS.SKYBLUE;
                }
            }
        });
        this.blue = new JButton("BLUE");
        this.blue.setBackground(Color.BLUE);
        this.black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (/*clickedCount /*wobei clickedCount speichert, ob der Benutzer bereits einmal einen der Knöpfe gedrückt hat = 0*/ 1 == 1) {
                    main.player_color = Main.COLORS.BLUE;
                } else {
                    main.projectiles_color = Main.COLORS.BLUE;
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
        }

        if(this.teleport && this.map.portalState > 5.9){
            main.startBattle();
            this.teleport = false;
            this.map.portalStage = 0;
            this.map.player.allowedToMove = true;
        }
        // update the players position
    }

}
