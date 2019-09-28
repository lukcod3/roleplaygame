package lolz.GUI;

import lolz.Maps.ExampleMap;
import lolz.Maps.Map;
import lolz.Maps.RandomMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JPanel {
    private Image img;
    private Map map;
    public boolean statsShown;
    public GUI() {
        // call super class
        super();
        statsShown = false;
        // create map
        map = new RandomMap();

        this.repaint();

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
        Graphics f = g.create();
        map.paint(g);
        // paint stats
        if(statsShown){
            Color myColor = new Color(56, 56, 56, 150);
            Font titleF = new Font( "SansSerif", Font.BOLD, 25);
            Font statsF = new Font("SansSerif", Font.PLAIN, 15);
            f.setColor(myColor);
            f.fillRect(150, 50, 660, 440);
            f.setFont(titleF);
            f.setColor(Color.white);
            f.drawString("Stats",450,100);
            f.setFont(statsF);
            f.drawString("Leben.............(", 100 ,100);
        }

        // sync graphic
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(int time) {
        // update the players position
        map.update(time);
    }

}
