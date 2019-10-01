package lolz.GUI;

import lolz.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private Main main;

    public MainMenu(final Main main) {
        this.main = main;
        JButton start = new JButton("Start game");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startGame();
            }
        });
        this.add(start);
    }
}
