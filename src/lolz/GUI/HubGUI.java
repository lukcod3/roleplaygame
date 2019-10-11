package lolz.GUI;

import lolz.Main;
import lolz.Maps.Hub;
import lolz.Maps.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class HubGUI extends JPanel {

    private Main main;
    public Hub map;
    public boolean teleport;


    public HubGUI(final Main main) {

        this.main = main;

        //create map
        this.map = new Hub();
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
        //add the key binding for the players attack
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false), KeyEvent.VK_J + "Pressed");
        this.getActionMap().put(KeyEvent.VK_J + "Pressed", generateAttackKeyAction());


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
                // check if player is in top right corner
                if (!map.player.getHitting()) {
                    map.player.setHitting(true);
                }
            }
        };
    }

    public void paint(Graphics g) {

        // paint map
        map.paint(g);
        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(int time) {
        if (map.player.getX() >= 400 && map.player.getY() <= 150 && !teleport) {
            teleport = true;
            map.portalStage = 1;
            map.portalState = 0.1;
        }

        if(teleport && map.portalState > 5.9){
            main.startBattle();
        }
        // update the players position
        map.update(time);
    }

}
