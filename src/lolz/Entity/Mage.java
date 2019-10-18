package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mage extends Player {

    public boolean hasDamaged;

    public Mage(Map map, int x, int y, float[] rgba) {
        super(map, x, y, rgba);
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
        // load images
        try {
            if (this.rgba == null) {
                for (int i = 0; i < 4; i++) {
                    img[0][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 6; i++) {
                    img[1][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-move-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 5; i++) {
                    img[2][i] = ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-attack-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    img[0][i] = tint(this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3], ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-idle-0" + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 6; i++) {
                    img[1][i] = tint(this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3], ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-move-0" + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
                for (int i = 0; i < 5; i++) {
                    img[2][i] = tint(this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3], ImageIO.read(new File("res/monster/Necromancer/Individual Sprites/necromancer-attack-0" + i + ".png"))).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
                }
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

        // TODO: fix modulo quick fixes

        // convert y from virtual to graphic
        this.y -= this.height;

        g.setColor(Color.BLACK);
        if (!turnedRight) {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (int) ((1.6 * img[2][(int) this.animation_state % 5].getWidth(null) - this.width) / 2);
                g.drawImage(img[2][(int) this.animation_state % 5], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (int) ((1.6 * img[1][(int) this.animation_state % 6].getWidth(null) - this.width) / 2);
                g.drawImage(img[1][(int) this.animation_state % 6], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (int) ((1.6 * img[0][(int) this.animation_state % 4].getWidth(null) - this.width) / 2);
                g.drawImage(img[0][(int) this.animation_state % 4], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                //int offset = (int) ((1*img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[2][(int) this.animation_state % 5], g, (int) this.x, (int) this.y);
            } else if (isMoving) {
                //int offset = (int) ((1*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[1][(int) this.animation_state % 6], g, (int) this.x, (int) this.y);
            } else {
                //int offset = (int) ((1*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[0][(int) this.animation_state % 4], g, (int) this.x, (int) this.y);
            }
        }

        // convert it back
        this.y += this.height;
    }
}
