package lolz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Monster {
    private double x, y;
    private Image[][] img;
    private double animation_state;
    private final String base_monster = "big_demon";

    public Monster(int x, int y){
        this.x = x;
        this.y = y;
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
    }

    public void paint(Graphics g) {

        // paint player
        g.setColor(Color.BLACK);
        g.drawImage(img[0][(int)this.animation_state], (int) this.x, (int) this.y, null);
    }

    public void update(int time){
        this.animation_state += (float) time / 150;
        this.animation_state %= 4;
    }
}
