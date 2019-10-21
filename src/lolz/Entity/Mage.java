package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import java.awt.*;

public class Mage extends Player {
    public Mage(Map map, int x, int y) {
        super(map, x, y);
        this.img = new Image[4][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
        this.img[3] = new Image[10];

        loadImages(null);

        this.width = Main.VIRTUAL_ENTITY_WIDTH;
        this.height = img[0][0].getHeight(null);

        // update before being drawn
        this.update(1);
    }

    public void paint(Graphics g) {
        // paint player

        // TODO: fix modulo quick fixes
        super.paint(g);
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
            } else if (dieing) {
                int offset = (int) ((1.6 * img[3][(int) this.animation_state % 10].getWidth(null) - this.width) / 2);
                g.drawImage(img[3][(int) this.animation_state % 10], (int) this.x - offset, (int) this.y, null);
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
            } else if (dieing) {
                //int offset = (int) ((1*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[3][(int) this.animation_state % 10], g, (int) this.x, (int) this.y);
            }else {
                //int offset = (int) ((1*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[0][(int) this.animation_state % 4], g, (int) this.x, (int) this.y);
            }
        }

        // convert it back
        this.y += this.height;
    }

    public void loadImages(float[] rgba) {
        loadImages(rgba, "res/monster/Necromancer/Individual Sprites/necromancer-idle-0", "res/monster/Necromancer/Individual Sprites/necromancer-move-0", "res/monster/Necromancer/Individual Sprites/necromancer-attack-0", "res/monster/Necromancer/Individual Sprites/necromancer-die-0");
    }

}
