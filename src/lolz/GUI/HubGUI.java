package lolz.GUI;

import lolz.Entity.Mage;
import lolz.Entity.Player;
import lolz.Main;
import lolz.Maps.Hub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HubGUI extends JPanel {

    private Main main;
    public Hub map;
    public boolean teleport;


    public HubGUI(final Main main, Player player) {

        this.main = main;

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
        if (((this.map.player instanceof Mage && this.map.player.getY() <= 170) ||(this.map.player.getY() <= 100)) && this.map.player.getX() >= 420 && !this.teleport) {
            this.teleport = true;
            this.map.player.allowedToMove = false;
            this.map.portalStage = 1;
            this.map.portalState = 0.1;
            this.map.player.directions = new boolean[4];
        }

        if(this.teleport && this.map.portalState > 5.9){
            main.startBattle();
            this.teleport = false;
            this.map.portalStage = 0;
            this.map.player.allowedToMove = true;
        }
        // update the players position
        this.map.update(time);
    }

}
