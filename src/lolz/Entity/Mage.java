package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mage extends Player {

    public boolean hasDamaged;

    public Mage(Map map, int x, int y) {
        super(map, x, y);
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
        // load images
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 6; i++) {
                img[1][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-move-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 5; i++) {
                img[2][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.width = Main.VIRTUAL_ENTITY_WIDTH;
        this.height = img[0][0].getHeight(null);

        // update before being drawn
        this.update(1);
    }

    public void paint(Graphics g) {
        // paint player
        super.paint(g, 1.6);
    }
}
