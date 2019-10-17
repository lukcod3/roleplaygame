package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Fighter extends Player {
    public Fighter(Map map, int x, int y) {
        super(map, x, y);
        this.img = new Image[3][];
        this.img[0] = new Image[4];
        this.img[1] = new Image[6];
        this.img[2] = new Image[5];
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-idle-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 6; i++) {
                img[1][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-run-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 5; i++) {
                img[2][i] = ImageIO.read(new File("res/Individual Sprites/adventurer-attack1-0" + i + ".png")).getScaledInstance(Main.ENTITY_WIDTH, -1, Image.SCALE_SMOOTH);
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
        // System.out.println(map.get_tile_at((int) (this.x), (int) (this.y + this.height)).toString());
        // paint player

        // convert y from virtual to graphic
        this.y -= this.height;

        g.setColor(Color.BLACK);
        if (turnedRight) {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[2][(int) this.animation_state], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[1][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (img[0][(int) this.animation_state].getWidth(null) - this.width) / 2;
                g.drawImage(img[0][(int) this.animation_state], (int) this.x - offset, (int) this.y, null);
            }
        } else {
            if (getHitting()) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (img[2][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[2][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            } else if (isMoving) {
                int offset = (img[1][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[1][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            } else {
                int offset = (img[0][(int) this.animation_state].getWidth(null) - this.width) / 2;
                Main.drawReflectImage(img[0][(int) this.animation_state], g, (int) this.x - offset, (int) this.y);
            }
        }

        // convert it back
        this.y += this.height;
    }

    public boolean attack(Entity entity) {
        if (getHitting() && (int) animation_state % 5 == 2 && !hasDamaged) {
            this.hasDamaged = true;
            entity.setHealth(entity.getHealth() - this.getDamage());
            System.out.println("entity health: " + entity.getHealth());
            return entity.getHealth() == 0;
        }
        return false;
    }

}
