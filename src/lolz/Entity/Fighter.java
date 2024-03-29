package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import java.awt.*;

public class Fighter extends Player {
    public Fighter(Map map, int x, int y) {
        super(map, x, y);
        this.img = new Image[4][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
        this.img[3] = new Image[7];

        loadImages(null);

        this.width = Main.VIRTUAL_ENTITY_WIDTH;
        this.height = img[0][0].getHeight(null);

        // update before being drawn
        this.update(1);
    }

    public void paint(Graphics g) {
        // System.out.println(map.get_tile_at((int) (this.x), (int) (this.y + this.height)).toString());
        // paint player
        super.paint(g);
        // convert y from virtual to graphic
        this.y -= this.height;

        g.setColor(Color.BLACK);
        if (turnedRight) {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state % img[2].length].getWidth(null) - this.width) / 2;
                g.drawImage(img[2][(int) this.animation_state % img[2].length], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state % img[1].length].getWidth(null) - this.width) / 2;
                g.drawImage(img[1][(int) this.animation_state % img[1].length], (int) this.x - offset, (int) this.y, null);
            } else if (dieing) {
                int offset = (img[3][(int) this.animation_state % img[3].length].getWidth(null) - this.width) / 2;
                g.drawImage(img[3][(int) this.animation_state % img[3].length], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (img[0][(int) this.animation_state % img[0].length].getWidth(null) - this.width) / 2;
                g.drawImage(img[0][(int) this.animation_state % img[0].length], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state % img[2].length].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[2][(int) this.animation_state % img[2].length], g, (int) this.x - offset, (int) this.y);
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state % img[1].length].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[1][(int) this.animation_state % img[1].length], g, (int) this.x - offset, (int) this.y);
            } else if (dieing) {
                int offset = (img[3][(int) this.animation_state % img[3].length].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[3][(int) this.animation_state % img[3].length], g, (int) this.x - offset, (int) this.y);
            } else {
                int offset = (img[0][(int) this.animation_state % img[0].length].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[0][(int) this.animation_state % img[0].length], g, (int) this.x - offset, (int) this.y);
            }
        }

        // convert it back
        this.y += this.height;
    }

    public void loadImages(float[] rgba) {
        loadImages(rgba, "res/Individual Sprites/adventurer-idle-0", "res/Individual Sprites/adventurer-run-0", "res/Individual Sprites/adventurer-attack1-0", "res/Individual Sprites/adventurer-die-0");
    }

}
