import Maps.ExampleMap;
import Maps.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GUI extends JPanel {
    private Player player;
    private Image img;
    private Map map;

    GUI() {
        // load background image
        img = Toolkit.getDefaultToolkit().getImage("res/background.jpg").getScaledInstance(950,-1,1);

        //create map
        map = new ExampleMap();

        // create the player
        player = new Player(0, 0);

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
                player.directions[dir] = pressed;
            }
        };
    }


    public void paint(Graphics g) {
        // paint background image
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        // paint graphic objects
        map.paint(g);
        player.paint(g);

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    void update(int time) {
        // update the players position
        player.update(time);
    }

}
