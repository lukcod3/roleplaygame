package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Mage extends Player {
    public Mage(Map map, int x, int y) {
        super(map, x, y);
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
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
    }

    // check if any given entity is "touching" a given projectile  or rather if the projectile is touching it
    public boolean overlap(Entity entity, Projectile projectile) {
        for (int i : new int[]{0, entity.getWidth()}) { //checking for the left and right border of the entity's image
            for (int j : new int[]{0, entity.getHeight()}) { //checking for the top and bottom border of the entity's image
                if (projectile.getTurnNumber() == Projectile.TurnNumber.EAST || projectile.getTurnNumber() == Projectile.TurnNumber.NORTHEAST || projectile.getTurnNumber() == Projectile.TurnNumber.SOUTHEAST) {
                    if ((projectile.getX() + projectile.getIx() <= entity.getX() + i) && (entity.getX() + i <= projectile.getX() + projectile.getIx() * 2) && (projectile.getY() <= entity.getY() + j) && (entity.getY() + j <= projectile.getY() + projectile.getIy() * 2)) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                        return true;
                    }
                } else if (projectile.getTurnNumber() == Projectile.TurnNumber.NORTH) {
                    if ((projectile.getX() <= entity.getX() + i) && (entity.getX() + i <= projectile.getX() + projectile.getIx() * 2) && (projectile.getY() <= entity.getY() + j) && (entity.getY() + j <= projectile.getY() + projectile.getIy())) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                        return true;
                    }
                } else if (projectile.getTurnNumber() == Projectile.TurnNumber.SOUTH) {
                    if ((projectile.getX() <= entity.getX() + i) && (entity.getX() + i <= projectile.getX() + projectile.getIx() * 2) && (projectile.getY() + projectile.getIy() <= entity.getY() + j) && (entity.getY() + j <= projectile.getY() + projectile.getIy() * 2)) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                        return true;
                    }
                } else {
                    if ((projectile.getX() <= entity.getX() + i) && (entity.getX() + i <= projectile.getX() + projectile.getIx()) && (projectile.getY() <= entity.getY() + j) && (entity.getY() + j <= projectile.getY() + projectile.getIy() * 2)) { //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                        //System.out.println("Player X: " + this.getX() + " | Y: " + this.getY() + " || Monster X: " + entity.getX() + " | Y: " + entity.getY());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void paint(Graphics g) {
        // paint player

        double k = 1.6;
        g.setColor(Color.BLACK);
        if (!turnedRight) {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (int) ((k*img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[2][(int) this.animation_state], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (int) ((k*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[1][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (int) ((k*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                g.drawImage(img[0][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                //int offset = (int) ((1*img[2][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[2][(int) this.animation_state], g, (int) this.x, (int) this.y);
            } else if (isMoving) {
                //int offset = (int) ((1*img[1][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[1][(int) this.animation_state], g, (int) this.x, (int) this.y);
            } else {
                //int offset = (int) ((1*img[0][(int) this.animation_state].getWidth(null) - this.width) / 2);
                Main.drawReflectImage(img[0][(int) this.animation_state], g, (int) this.x, (int) this.y);
            }
        }
    }
}
