package lolz.Player;

import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player {
    private Map map;
    public double x, y;
    private int width, height;
    private float speed = 0.15f;
    public boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    private Image[][] img;
    private boolean moving;
    private double animation_state;
    private final String base_char = "elf_m";

    public Player(Map map, int x, int y) {
        // setup player stats
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];
        this.img = new Image[2][4];
        try {
            for (int i = 0; i < 4; i++) {
                img[0][i] = ImageIO.read(new File("res/tiles/" + this.base_char + "_idle_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
            }
            for (int i = 0; i < 4; i++) {
                img[1][i] = ImageIO.read(new File("res/tiles/" + this.base_char + "_run_anim_f" + Integer.toString(i) + ".png")).getScaledInstance(45, -1, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = img[0][0].getWidth(null);
        this.height = img[0][0].getHeight(null);

        // rearrange y (given x and y values are for the bottom left corner)
        // this.x -= this.width;
        this.y -= this.height;
    }

    public void paint(Graphics g) {
        // paint player
        g.setColor(Color.BLACK);
        if (moving) {
            g.drawImage(img[1][(int) this.animation_state], (int) this.x, (int) this.y, null);
        } else {
            g.drawImage(img[0][(int) this.animation_state], (int) this.x, (int) this.y, null);
        }
    }

    public void update(int time) {
        // update player graphic stats
        if (moving) {
            this.animation_state += (float) time / 100;
        } else {
            this.animation_state += (float) time / 150;
        }
        this.animation_state %= 4;

        // count how many directions are active
        int dir_count = 0;
        for (boolean b : this.directions) {
            if (b) {
                dir_count++;
            }
        }

        // if no or opposite keys are pressed the player doesnt move
        if (dir_count == 0 || dir_count > 2 || this.directions[0] && this.directions[2] || this.directions[1] && this.directions[3]) {
            // opposite keys are pressed => player doesnt move
            if (moving) {
                moving = false;
                animation_state = 0;
            }
            return;
        }

        if (!moving) {
            moving = true;
            animation_state = 0;
        }

        // calculate how much the player moves
        double movement = this.speed * time;

        // if the player moves in 2 directions he doesnt run faster
        if (dir_count == 2) {
            movement *= 1.0 / Math.pow(2, 0.5);
        }

        // safe previous position for restoring
        double old_x = this.x;
        double old_y = this.y;

        // move player
        if (this.directions[0]) {
            this.y -= movement;
        }
        if (this.directions[1]) {
            this.x -= movement;
        }
        if (this.directions[2]) {
            this.y += movement;
        }
        if (this.directions[3]) {
            this.x += movement;
        }

        // check if player in wall => reset movement
        for (int d_x : new int[]{0, this.width}) {
            if (map.get_tile_at((int) (this.x + d_x), (int) (this.y + this.height)).solid) {
                // player doesnt move
                // test if fix is possible
                if (!map.get_tile_at((int) (old_x + d_x), (int) (this.y + this.height)).solid) {
                    this.x = old_x;
                } else if (!map.get_tile_at((int) (this.x + d_x), (int) (old_y + this.height)).solid) {
                    this.y = old_y;
                } else {
                    this.x = old_x;
                    this.y = old_y;
                }
            }
        }

    }

}
