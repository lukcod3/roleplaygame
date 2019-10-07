package lolz.GUI;

import lolz.Maps.Hub;
import lolz.Maps.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HubGUI extends JPanel {

    private Map map;

    public HubGUI() {

        //create map
        map = new Hub();

        // add the key bindings for player movement
        char[] keys = {'W', 'A', 'S', 'D'};
        for (int i = 0; i < 4; i++) {
            char c = keys[i];
            // add the key binding for pressing and releasing wasd keys
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c, 0, false), c + "Pressed");
            this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c, 0, true), c + "Released");
            this.getActionMap().put(c + "Pressed", generateKeyAction(i, true));
            this.getActionMap().put(c + "Released", generateKeyAction(i, false));
        }
    }

    private Action generateKeyAction(final int dir, final boolean pressed) {
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
        map.paint(g);

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(int time) {
        // update the players position
        map.update(time);
    }

}
