package lolz.Entity;

import lolz.Main;
import lolz.Maps.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public double x, y, speed, animation_state;
    public Image[][] img;
    public int width, height, maxHealth, health, damage, armor;
    public boolean turnedRight, isMoving, isHitting, allowedToMove;
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
        this.allowedToMove = true;
    }

    public abstract void paint(Graphics g);

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

        if (!isMoving && this.allowedToMove) {
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
            if (!map.get_tile_at((int) (this.x + d_x), (int) this.y).isGround()) {
                // player doesnt moveddss
                // test if fix is possible
                if (map.get_tile_at((int) (old_x + d_x), (int) this.y).isGround()) {
                    this.x = old_x;
                } else if (map.get_tile_at((int) (this.x + d_x), (int) old_y).isGround()) {
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health >= 0 && health <= this.getMaxHealth()) {
            this.health = health;
        } else {
            this.health = 0;
        }
    }

    static BufferedImage tint(float r, float g, float b, float a, BufferedImage sprite) {
        BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(sprite, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
                int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
                int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
                int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedSprite;
    }

    // check if any given entity is "touching" the hero or rather if the hero is touching it
    public boolean overlap(Entity entity) {
        for (int i : new int[]{0, entity.getWidth()}) { //checking for the left and right border of the entity's image
            for (int j : new int[]{0, entity.getHeight()}) { //checking for the top and bottom border of the entity's image
                //if any of the entity's boundaries can be found between any of the hero's boundaries, they touch
                if ((this.getX() <= entity.getX() + i) && (entity.getX() + i <= this.getX() + this.getWidth()) && (this.getY() - this.getHeight() <= entity.getY() - j) && (entity.getY() - j <= this.getY())) {
                    return true;
                }
            }
        }
        return false;
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
