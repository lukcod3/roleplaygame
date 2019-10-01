package lolz.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Monster extends Entity {
    private Image[][] img;
    private double animation_state;
    private final String base_monster = "big_demon";

    public Monster(int x, int y, int maxHealth, int damage, int armor) {
        super(x, y, maxHealth, damage, armor, 10);
        this.img = new Image[2][4];
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/tiles/" + this.base_monster + "_idle_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 4; i++) {
                img[1][i] = ImageIO.read(new File("res/tiles/" + this.base_monster + "_run_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(50, -1, Image.SCALE_SMOOTH);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        this.width = img[0][0].getWidth(null);
        this.height = img[0][0].getHeight(null);
        this.y -= this.getHeight();
    }

    public void paint(Graphics g) {
        // paint monster
        g.setColor(Color.BLACK);
        g.drawImage(img[0][(int) this.animation_state], (int) this.x, (int) this.y, null);
    }

    public void update(int time) {
        // update monster graphic stats
        this.animation_state += (float) time / 150;
        this.animation_state %= 4;
    }
}
