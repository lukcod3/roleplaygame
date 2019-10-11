package lolz.Entity;

import lolz.GUI.Item;
import lolz.Maps.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public abstract class Player extends Entity {
    public Map map;
    public volatile boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    private boolean isHitting, hasDamaged; // variable true if user makes character hit
    public Item[] equipment; // 1 is hat, 2 is t-shirt, 3 is sword, 4 is shoes, 5 is necklace, 6 is ring, 7 is belt, 8-11 is depot
    private boolean mage;
    public boolean allowedToMove;
    // Ingame stats
    public int level, exp, gold;

    public Player(Map map, int x, int y) {
        // setup player stats
        super(x, y, 100, 10, 30, 0.15);
        this.health = 50; // test
        this.level = 1;
        this.mage = true;
        this.turnedRight = true;
        this.map = map;
        this.x = x;
        this.y = y;
        this.directions = new boolean[4];

        this.width = 45;

        // rearrange y (given x and y values are for the bottom left corner)
        // this.x -= this.width;
        this.y -= this.height;

        allowedToMove = true;
    }

    // set Getters and Setters for attribute hit
    public boolean getHitting() {
        return isHitting;
    }

    public void setHitting(boolean isHitting) {
        if (this.isHitting != isHitting) {
            if (isHitting) {
                // slow player down while attacking
                this.speed = 0.05;
            } else {
                // restore speed and attacking status
                this.speed = 0.15;
                this.hasDamaged = false;
            }
            this.animation_state = 0;
        }
        this.isHitting = isHitting;
    }


    public void update(int time) {
        // update player graphic stats
        if (isHitting) {
            this.animation_state += (float) time / 100;
            if (this.animation_state >= 5) {
                this.setHitting(false);
            }
        } else if (isMoving) {
            this.animation_state += (float) time / 100;
            this.animation_state %= 6;
        } else {
            this.animation_state += (float) time / 150;
            this.animation_state %= 4;
        }

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
            if (isMoving) {
                isMoving = false;
                animation_state = 0;
            }
            return;
        }

        if (!isMoving) {
            isMoving = true;
            animation_state = 0;
        }
        if (this.directions[3]) {
            turnedRight = true;
        } else if (this.directions[1]) {
            turnedRight = false;
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
            if (!map.get_tile_at((int) (this.x + d_x), (int) (this.y + this.height)).isGround()) {
                // player doesnt movedd
                // test if fix is possible
                if (map.get_tile_at((int) (old_x + d_x), (int) (this.y + this.height)).isGround()) {
                    this.x = old_x;
                } else if (map.get_tile_at((int) (this.x + d_x), (int) (old_y + this.height)).isGround()) {
                    this.y = old_y;
                } else {
                    this.x = old_x;
                    this.y = old_y;
                }
            }
        }
    }

    // check if any given entity is "touching" the hero or rather if the hero is touching it
    public boolean overlap(Entity entity) {
        for (int i : new int[]{0, entity.getWidth()}) { //checking for the left and right border of the entity's image
            for (int j : new int[]{0, entity.getHeight()}) { //checking for the top and bottom border of the entity's image
                //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getWidth()) && (this.getY() <= entity.getY() + j) && (entity.getY() + j <= this.getY() + this.getHeight())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean attack(Entity entity) {
        if (getHitting() && (int) animation_state % 5 == 2 && !hasDamaged) {
            this.hasDamaged = true;
            entity.setHealth(entity.getHealth() - this.damage);
            System.out.println("entity health: " + entity.getHealth());
            return entity.getHealth() == 0;
        }
        return false;
    }

}
