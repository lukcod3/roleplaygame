package lolz;

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
    boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    private Image img;
    private boolean moving;
    private int animation_state;
    private String base_char = "elf_m";

    public Player(Map map, int x, int y) {
        // setup player stats
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];
        try {
            img = ImageIO.read(new File("res/player.png")).getScaledInstance(100, 100, Image.SCALE_SMOOTH);;
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void paint(Graphics g) {
        // paint player
        g.setColor(Color.BLACK);
        g.drawImage(img, (int) this.x, (int) this.y, null);
    }

    public void update(int time) {
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
            if (map.tile_at((int) (this.x + d_x), (int) (this.y + this.height)) != Map.Tile.GROUND) {
                // player doesnt move
                // test if fix is possible
                if (map.tile_at((int) (old_x + d_x), (int) (this.y + this.height)) == Map.Tile.GROUND) {
                    this.x = old_x;
                } else if (map.tile_at((int) (this.x + d_x), (int) (old_y + this.height)) == Map.Tile.GROUND) {
                    this.y = old_y;
                } else {
                    this.x = old_x;
                    this.y = old_y;
                }
            }
        }

    }

}
