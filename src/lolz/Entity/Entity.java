package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import java.awt.*;

public abstract class Entity {
    public double x, y;
    public Image[][] img;
    public int width, height;
    public int maxHealth, health, damage, armor;
    double speed;
    public double animation_state;
    public boolean turnedRight, isMoving;
    boolean isHitting;
    public volatile boolean[] directions; // 0 is up, 1 is left, 2 is down, 3 is right
    public Map map;

    Entity(Map map, int x, int y, int maxHealth, int damage, int armor, double speed) {
        this.x = x;
        this.y = y;
        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDamage(damage);
        this.setArmor(armor);
        this.speed = speed;
        this.directions = new boolean[4];
        this.animation_state = Math.random() * 10;
        this.map = map;
    }

    public abstract void paint(Graphics g);

    public void paint(Graphics g, double k) {
        // paint player

        // TODO: fix modulo quick fixes

        // convert y from virtual to graphic
        this.y -= this.height;

        g.setColor(Color.BLACK);
        if (!turnedRight) {
            if (isHitting) { // is able to hit while running and while standing still -> always checks if hit is true regardless of moving
                int offset = (int) ((k * img[2][(int) this.animation_state % 5].getWidth(null) - this.width) / 2);
                g.drawImage(img[2][(int) this.animation_state % 5], (int) this.x - offset, (int) this.y, null); // set player's animation to hit animation
            } else if (isMoving) {
                int offset = (int) ((k * img[1][(int) this.animation_state % 6].getWidth(null) - this.width) / 2);
                g.drawImage(img[1][(int) this.animation_state % 6], (int) this.x - offset, (int) this.y, null);
            } else {
                int offset = (int) ((k * img[0][(int) this.animation_state % 4].getWidth(null) - this.width) / 2);
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

    void move(int time) {
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
            if (!map.get_tile_at((int) (this.x + d_x), (int) (this.y)).isGround()) {
                // player doesnt moved
                // test if fix is possible
                if (map.get_tile_at((int) (old_x + d_x), (int) (this.y)).isGround()) {
                    this.x = old_x;
                } else if (map.get_tile_at((int) (this.x + d_x), (int) (old_y)).isGround()) {
                    this.y = old_y;
                } else {
                    this.x = old_x;
                    this.y = old_y;
                }
            }
        }
    }

    public abstract void update(int time);

    //Getters and Setters
    public int getMaxHealth() {
        return maxHealth;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        if (health >= 0 && health <= this.getMaxHealth()) {
            this.health = health;
        } else {
            this.health = 0;
        }
    }

    private void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    private void setDamage(int attackdamage) {
        this.damage = attackdamage;
    }

    public int getArmor() {
        return armor;
    }

    private void setArmor(int armor) {
        this.armor = armor;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getVirtualLeftX() {
        return (int) (this.x / Main.TILE_SIZE);
    }

    public int getVirtualRightX() {
        return (int) ((this.x + this.width) / Main.TILE_SIZE);
    }

    public int getVirtualY() {
        return (int) (this.y / Main.TILE_SIZE);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
