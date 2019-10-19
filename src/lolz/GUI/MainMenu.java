package lolz.GUI;

import lolz.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {
    private Main main;
    private Image bgImage;
    private Image buttonImage;
    private int buttonWidth, buttonHeight;

    public MainMenu(final Main main) {
        this.main = main;
        this.buttonWidth = 400 / 2;
        this.buttonHeight = 128 / 2;
        try {
            bgImage = load_image("res/monsters_and_ other_atrocities/titelbildschirm.png").getScaledInstance(Main.WIDTH, Main.HEIGHT, Image.SCALE_SMOOTH);
            buttonImage = load_image("res/start_button.jpg").getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);

        // create start button
        JButton start = new JButton();
        start.setBounds((Main.WIDTH - buttonWidth) / 2, (Main.HEIGHT - buttonHeight) / 2, buttonWidth, buttonHeight);
        start.setIcon(new ImageIcon(buttonImage));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startHub();
            }
        });
        this.add(start);

        // create load game button
        JButton loadGame = new JButton("Load Game");
        loadGame.setBounds((Main.WIDTH - buttonWidth) / 2, (int) ((Main.HEIGHT - buttonHeight) / 2 + buttonHeight * 1.2), buttonWidth, buttonHeight);
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.loadGame();
            }
        });
        loadGame.setEnabled(Main.gameIsSaved());
        this.add(loadGame);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
    }

    private static Image load_image(String path) throws IOException {
        return ImageIO.read(new File(path));
    }
}
